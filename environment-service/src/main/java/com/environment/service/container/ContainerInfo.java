package com.environment.service.container;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContainerInfo {

	private String containerId;
	
	private String jdbcUrl;
	
	private String host;
	
	private Integer port;
	
	private String username;
	
	private String password;
	
}
