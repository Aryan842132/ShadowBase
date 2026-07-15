package com.validation.service.dto;

import java.time.LocalDateTime;

import com.validation.service.model.ValidationStatus;

import lombok.Builder;

@Builder
public record ValidationResponse(
		Long id,
		ValidationStatus status,
		String message,
		LocalDateTime validatedAt) {
	
	
}
