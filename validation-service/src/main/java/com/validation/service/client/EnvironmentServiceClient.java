package com.validation.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.validation.service.dto.EnvironmentDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EnvironmentServiceClient {

	private final RestClient restClient;
	
	@Value("${services.environment.base-url}")
	private String environmentServiceurl;
	
	public EnvironmentDto getEnvironment(Long environmentId) {
		return restClient.get()
				.uri(environmentServiceurl + "/api/environments/{id}", environmentId)
				.retrieve()
				.body(EnvironmentDto.class);
	}
}
