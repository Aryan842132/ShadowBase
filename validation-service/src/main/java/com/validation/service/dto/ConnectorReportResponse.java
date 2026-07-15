package com.validation.service.dto;

import lombok.Builder;

@Builder
public record ConnectorReportResponse(
		Long connectorId,
		String connectorName,
		String status,
		Long environmentId) {

	
}
