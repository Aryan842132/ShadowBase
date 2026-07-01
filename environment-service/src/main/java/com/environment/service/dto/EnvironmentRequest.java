package com.environment.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EnvironmentRequest {

	@NotBlank(message = "Environment name is required")
	@Size(min= 3, max = 50,
	       message = "Environment name must be between 3 and 50 characters")
	
	private String name;
	
}
