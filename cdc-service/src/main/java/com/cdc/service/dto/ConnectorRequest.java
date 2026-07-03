package com.cdc.service.dto;

import com.cdc.service.model.ConnectorType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConnectorRequest {

	@NotNull
	private Long environmentId;
	
	@NotNull
	private String connectorName;
	
	@NotNull
	private ConnectorType connectorType;
}
