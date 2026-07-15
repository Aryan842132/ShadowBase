package com.validation.service.dto;

import lombok.Builder;

@Builder
public record EnvironmentReportResponse(
		Long environmentId,
		String environmentName,
		String status,
		int connectorCount,
		int runningConnectorCount) {

}
