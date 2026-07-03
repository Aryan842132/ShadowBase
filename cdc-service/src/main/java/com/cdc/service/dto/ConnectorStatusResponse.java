package com.cdc.service.dto;

import com.cdc.service.model.ConnectorStatus;

import lombok.Data;

@Data
public class ConnectorStatusResponse {

	private Long connectorId;
	private String connectorName;
	private ConnectorStatus status;
	private String message;
}
