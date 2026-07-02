package com.environment.service.service;


import java.util.List;

import com.environment.service.dto.EnvironmentRequest;
import com.environment.service.dto.EnvironmentResponse;

public interface EnvironmentService {
	
	EnvironmentResponse createEnvironment(EnvironmentRequest request);
	
	EnvironmentResponse getEnvironment(Long Id);
	
	List<EnvironmentResponse> getAllEnvironment();
	
	void stopEnvironment(Long Id);
	
	void deleteEnvironment(Long Id);

}
