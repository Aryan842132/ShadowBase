package com.cdc.service.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaConnectorRequest {

	private String name;
	
	private Map<String, String> config;
}
