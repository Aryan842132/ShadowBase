package com.environment.service.dto;

import com.environment.service.model.EnvironmentStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnvironmentResponse {

	private Long id;
	
	private String name;
	
	private String containerId;
	
	private String jdbcUrl;
	
	private String host;
	
	private Integer port;
	
	private String userName;
	
	private String password;
	
	private EnvironmentStatus status;
}
