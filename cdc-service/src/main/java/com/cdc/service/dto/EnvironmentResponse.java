package com.cdc.service.dto;

import com.cdc.service.model.EnvironmentStatus;

import lombok.Data;

@Data
public class EnvironmentResponse {

	private Long id;
	
	private String name;
	
	private String contianerId;
	
	private String jdbcUrl;
	
	private String host;
	
	private Integer port;
	
	private String userName;
	
	private EnvironmentStatus status;
}
