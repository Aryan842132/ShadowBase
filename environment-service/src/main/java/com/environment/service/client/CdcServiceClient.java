package com.environment.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CdcServiceClient {

	private final RestClient restClient;
	
	@Value("${cdc.service.url}")
	private String cdcServiceUrl;
	
	public boolean hasConnectors(Long environmentId) {
		
		log.info("Checking connectors for environmentId={}", environmentId);
		
		Boolean response = restClient.get()
				.uri(cdcServiceUrl + "/api/connectors/environment/{environmentId}/exists", environmentId)
				.retrieve()
				.body(Boolean.class);
		
		return Boolean.TRUE.equals(response);
	}
}
