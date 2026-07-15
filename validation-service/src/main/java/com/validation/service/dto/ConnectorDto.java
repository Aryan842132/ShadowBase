package com.validation.service.dto;

public record ConnectorDto(
		Long id,
		String name,
		String status,
		Long environmentId) {

	
}
