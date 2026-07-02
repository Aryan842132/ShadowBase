package com.environment.service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.environment.service.container.ContainerInfo;
import com.environment.service.container.TestContainerManager;
import com.environment.service.dto.EnvironmentRequest;
import com.environment.service.dto.EnvironmentResponse;
import com.environment.service.exception.DuplicateEnvironmentException;
import com.environment.service.exception.ResourceNotFoundException;
import com.environment.service.model.Environment;
import com.environment.service.model.EnvironmentStatus;
import com.environment.service.repository.EnvironmentRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnvironmentServiceImpl implements EnvironmentService {

	private final EnvironmentRepository environmentRepository;	
	private final TestContainerManager testContainerManager;
	
	@Override
	public EnvironmentResponse createEnvironment(EnvironmentRequest request) {
		log.info("Creating environment with name={}", request.getName());
		
		if(environmentRepository.existsByName(request.getName())) {
			throw new DuplicateEnvironmentException("Environment already exists with name: "+ request.getName());
		}
		
		Environment environment = Environment.builder()
				.name(request.getName())
				.status(EnvironmentStatus.CREATING)
				.build();
		
		environment = environmentRepository.save(environment);
		
		try {
			
		ContainerInfo containerInfo = testContainerManager.createContainer(environment.getId());
		
		environment.setContainerId(containerInfo.getContainerId());
		environment.setJdbcUrl(containerInfo.getJdbcUrl());
		environment.setHost(containerInfo.getHost());
		environment.setPort(containerInfo.getPort());
		environment.setUserName(containerInfo.getUsername());
		environment.setPassword(containerInfo.getPassword());
		environment.setStatus(EnvironmentStatus.RUNNING);
		
		environment = environmentRepository.save(environment);
		
		log.info("Environment created successfully. id={}", environment.getId());
		
		return mapToResponse(environment);
		}
		catch (Exception ex) {
			log.error("Failed to create environment. id={}, name={}",
					environment.getId(),
					environment.getName(),
					ex);
			
			environment.setStatus(EnvironmentStatus.FAILED);
			environmentRepository.save(environment);
			
			throw new RuntimeException(
					"Failed to create environment: " + ex.getMessage(),
					ex);
		}
	}
	
	@Override
	public EnvironmentResponse getEnvironment(Long id) {
		Environment environment = environmentRepository.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException("Environment not found with id: "+ id));
		
		return mapToResponse(environment);
	}

	@Override
	public List<EnvironmentResponse> getAllEnvironments() {
		return environmentRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Override
	public void stopEnvironment(Long id) {
    	Environment environment = environmentRepository.findById(id)
    			.orElseThrow(() -> 
    			     new ResourceNotFoundException("Environment not found with id: "+ id));
    	
    	testContainerManager.stopContainer(id);
    	
    	environment.setStatus(EnvironmentStatus.STOPPED);
    	
    	environmentRepository.save(environment);
    	
    	log.info("Environment stopped. id={}", id);
	  	
	}

	@Override
	public void deleteEnvironment(Long id) {
		Environment environment = environmentRepository.findById(id)
				.orElseThrow(() ->
				         new ResourceNotFoundException("Environment not found with id: "+ id));
		
		testContainerManager.removeContainer(id);
		
		environmentRepository.delete(environment);
		
		log.info("Environment deleted. id={}", id);
		
	}
	
	private EnvironmentResponse mapToResponse(Environment environment) {
		return EnvironmentResponse.builder()
				.id(environment.getId())
				.name(environment.getName())
				.containerId(environment.getContainerId())
				.jdbcUrl(environment.getJdbcUrl())
				.host(environment.getHost())
				.port(environment.getPort())
				.userName(environment.getUserName())
				.status(environment.getStatus())
				.build();
	}
	
}
