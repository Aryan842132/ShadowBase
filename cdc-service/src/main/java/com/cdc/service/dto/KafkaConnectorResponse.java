package com.cdc.service.dto;

import java.util.Map;

import lombok.Data;

@Data
public class KafkaConnectorResponse {

	private String name;
	
	private Map<String , Object> config;
}
