package com.cdc.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.cdc.service.dto.KafkaConnectorRequest;
import com.cdc.service.dto.KafkaConnectorResponse;
import com.cdc.service.dto.KafkaConnectorStatusResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConnectClient {
	
	private final RestClient restClient;
	
	@Value("${kafka.connect.url}")
	private String kafkaConnectUrl;

	
	public KafkaConnectorResponse createConnector(
			KafkaConnectorRequest request) {
		
		log.info("create kafka connect connector with name={}",
				request.getName());
		
		KafkaConnectorResponse response = restClient.post()
				.uri(kafkaConnectUrl + "/connectors")
				.body(request)
				.retrieve()
				.body(KafkaConnectorResponse.class);
		
		log.info("Kafka connect connector created successfully. name={}",
				request.getName());
		
		return response;
	}
	
	public void deleteConnector(String connectorName) {
		log.info("Deleting kafka connect connector. name={}", connectorName);
		
		restClient.delete()
		        .uri(kafkaConnectUrl + "/connectors/{name}", connectorName)
		        .retrieve()
		        .toBodilessEntity();
		
		log.info("Kafka connect connector deleted successfully. name={}", connectorName);
	}
	
	public KafkaConnectorStatusResponse getConnectorStatus(String connectorName) {
		log.info("Fetching connector status. name={}", connectorName);
		
		KafkaConnectorStatusResponse response = restClient.get()
				.uri(kafkaConnectUrl + "/connector/{name}/status", connectorName)
				.retrieve()
				.body(KafkaConnectorStatusResponse.class);
		
		log.info("Connector status fetched successfully. name={}", connectorName);
		
		return response;
	}
}
