package com.cdc.service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cdc.service.dto.ConnectorRequest;
import com.cdc.service.dto.ConnectorResponse;
import com.cdc.service.exception.ConnectorNotFoundException;
import com.cdc.service.exception.DuplicateConnectorException;
import com.cdc.service.model.Connector;
import com.cdc.service.model.ConnectorStatus;
import com.cdc.service.repository.ConnectorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectorServiceImpl implements ConnectorService {
	
	private final ConnectorRepository connectorRepository;

	@Override
	public ConnectorResponse createConnector(ConnectorRequest request) {
		log.info("Creating connector with name={}, environmentId={}", 
				request.getConnectorName(),
				request.getEnvironmentId());
		
		if(connectorRepository.existsByConnectorName(request.getConnectorName())) {
			log.warn("Connector already exist with name={}", request.getConnectorName());
			
			throw new DuplicateConnectorException("Connector already exists with name: "+request.getConnectorName());
		}
		Connector connector = Connector.builder()
				.environmentId(request.getEnvironmentId())
				.connectorName(request.getConnectorName())
				.connectorType(request.getConnectorType())
				.status(ConnectorStatus.CREATING)
				.build();
		
	    Connector savedConnector = connectorRepository.save(connector);
	    
	    log.info("Connector created successfully with id={}", savedConnector.getId());
				
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

}
