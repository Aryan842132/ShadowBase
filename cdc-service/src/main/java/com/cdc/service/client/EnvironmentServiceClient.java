package com.cdc.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

import com.cdc.service.dto.EnvironmentResponse;

@FeignClient(
		name = "environment-service",
		url = "${environment.service.url}"
		)
public interface EnvironmentServiceClient {

	EnvironmentResponse getEnvironmentById(
			@PathVariable("id") Long id);
}
