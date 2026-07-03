package com.cdc.service.dto;

import java.time.LocalDateTime;

import com.cdc.service.model.ConnectorStatus;
import com.cdc.service.model.ConnectorType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectorResponse {

	private Long id;
	private Long environmentId;
	private String connectorName;
	private ConnectorType connectorType;
	private ConnectorStatus status;
	private String kafkaTopic;
	private String databaseName;
	private String lastError;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}
