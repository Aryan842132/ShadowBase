package com.cdc.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cdc.service.client.EnvironmentServiceClient;
import com.cdc.service.client.KafkaConnectClient;
import com.cdc.service.dto.ConnectorRequest;
import com.cdc.service.dto.ConnectorResponse;
import com.cdc.service.dto.EnvironmentResponse;
import com.cdc.service.dto.KafkaConnectorRequest;
import com.cdc.service.exception.ConnectorCreationException;
import com.cdc.service.exception.ConnectorNotFoundException;
import com.cdc.service.exception.DuplicateConnectorException;
import com.cdc.service.model.Connector;
import com.cdc.service.model.ConnectorStatus;
import com.cdc.service.model.EnvironmentStatus;
import com.cdc.service.repository.ConnectorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectorServiceImpl implements ConnectorService {
	
	private final ConnectorRepository connectorRepository;
	
	private final EnvironmentServiceClient environmentServiceClient;
	
	private final KafkaConnectClient kafkaConnectClient;

	@Override
	public ConnectorResponse createConnector(ConnectorRequest request) {

	    log.info("Creating connector with name={}, environmentId={}",
	            request.getConnectorName(),
	            request.getEnvironmentId());

	    if (connectorRepository.existsByConnectorName(request.getConnectorName())) {

	        log.warn("Connector already exists with name={}",
	                request.getConnectorName());

	        throw new DuplicateConnectorException(
	                "Connector already exists with name: "
	                        + request.getConnectorName());
	    }

	    EnvironmentResponse environment;

	    try {

	        environment = environmentServiceClient
	                .getEnvironment(request.getEnvironmentId());

	        log.info(
	                "Environment fetched successfully. id={}, name={}, status={}",
	                environment.getId(),
	                environment.getName(),
	                environment.getStatus());

	    } catch (Exception ex) {

	        log.error("Failed to fetch environment with id={}",
	                request.getEnvironmentId(), ex);

	        throw new ConnectorCreationException(
	                "Environment not found with id: "
	                        + request.getEnvironmentId());
	    }
	    
	    KafkaConnectorRequest kafkaRequest = buildDebeziumConfig(request, environment);
	    
	    try {
	    	kafkaConnectClient.createConnector(kafkaRequest);
	    	
	    	log.info("Debezium connector created  successfully. name={}",request.getConnectorName());
	    }
	    catch (Exception ex) {
			log.error("Failed to create Debezium connector. name={}",
					request.getConnectorName(), ex);
			throw new ConnectorCreationException("Failed to create Debezium connector");
		}

	    Connector connector = Connector.builder()
	            .environmentId(request.getEnvironmentId())
	            .connectorName(request.getConnectorName())
	            .connectorType(request.getConnectorType())
	            .status(ConnectorStatus.CREATING)
	            .databaseName(environment.getName())
	            .kafkaTopic("cdc." + environment.getName())
	            .build();

	    Connector savedConnector = connectorRepository.save(connector);

	    log.info("Connector created successfully with id={}",
	            savedConnector.getId());

	    return mapToResponse(savedConnector);
	}

	@Override
	public ConnectorResponse getConnectorbyId(Long id) {
		
		log.info("Fetching connector with id={}", id);
		
		Connector connector = connectorRepository.findById(id)
				.orElseThrow(() -> {
					log.error("Connector not found with id={}", id);
					return new ConnectorNotFoundException(
							"Connector not found with id: " + id);
				});
		
		return mapToResponse(connector);
	}

	@Override
	public List<ConnectorResponse> getAllConnectors() {
		log.info("Fetching all connectors");
		
		List<ConnectorResponse> connectors = connectorRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.toList();
		
		log.info("Total connectors found={}", connectors.size());
		
		return connectors;
	}

	@Override
	public ConnectorResponse getConnectorByEnvironmentId(Long environmentId) {
		log.info("Fetching connector for environmentId={}", environmentId);
		
		Connector connector = connectorRepository.findByEnvironmentId(environmentId)
				.orElseThrow(() -> {
					log.error("Connector not found for environmentId={}", 
							environmentId);
					
					return new ConnectorNotFoundException(
							"Connector not found for environment id: "+ environmentId);
				});
		
		return mapToResponse(connector);
	}

	@Override
	public void deleteConnector(Long id) {
		log.info("Deleting connector with id={}",id);
		
		Connector connector = connectorRepository.findById(id)
				.orElseThrow(() -> {
					log.error("Connector not found with id={}",id);
					
					return new ConnectorNotFoundException(
							"Connector not found with id={}" +id);
				});
		connectorRepository.delete(connector);
		log.info("Connector deleted successfully with id={}",id);
	}
	
	private ConnectorResponse mapToResponse(Connector connector) {
		return ConnectorResponse.builder()
				.id(connector.getId())
				.environmentId(connector.getEnvironmentId())
				.connectorName(connector.getConnectorName())
				.connectorType(connector.getConnectorType())
				.status(connector.getStatus())
				.kafkaTopic(connector.getKafkaTopic())
				.databaseName(connector.getDatabaseName())
				.lastError(connector.getLastError())
				.createdAt(connector.getCreatedAt())
				.updatedAt(connector.getUpdatedAt())
				.build();
	}
	
	private KafkaConnectorRequest buildDebeziumConfig(
			ConnectorRequest request,
			EnvironmentResponse environment) {
		
		Map<String, String> config = new HashMap<>();
		
		config.put("connector.class", "io.debezium.connector.mysql.MySqlConnector");
		config.put("database.hostname", environment.getHost());
		config.put("database.port", String.valueOf(environment.getPort()));
		config.put("database.user", environment.getUserName());
		config.put("database.password", environment.getPassword());
		config.put("database.server.id", String.valueOf(System.currentTimeMillis()));
		config.put("topic.prefix", environment.getName());
		config.put("database.include.list", "testdb");
		config.put("schema.history.internal.kafka.bootstrap.servers", "kafka:29092");
		config.put("schema.history.internal.kafka.topic", "schema-changes."+environment.getName());
		
		return KafkaConnectorRequest.builder()
				.name(request.getConnectorName())
				.config(config)
				.build();
	}

}
