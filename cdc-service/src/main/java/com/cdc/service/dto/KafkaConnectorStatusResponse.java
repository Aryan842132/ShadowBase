package com.cdc.service.dto;

import lombok.Data;

@Data
public class KafkaConnectorStatusResponse {

	private String name;
	
	private KafkaConnectorState connector;
}
