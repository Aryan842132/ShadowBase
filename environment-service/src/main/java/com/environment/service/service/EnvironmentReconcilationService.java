package com.environment.service.service;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.environment.service.container.TestContainerManager;
import com.environment.service.model.Environment;
import com.environment.service.model.EnvironmentStatus;
import com.environment.service.repository.EnvironmentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnvironmentReconcilationService {

	private final EnvironmentRepository environmentRepository;
	private final TestContainerManager testContainerManager;
	
	@EventListener(ApplicationReadyEvent.class)
	public void reconcile() {
		
		log.info("Starting environment reconciliation");

		
		List<Environment> environments = environmentRepository.findAll();
		
		log.info("Found {} environments to reconcile",
	            environments.size());
		
		for(Environment environment : environments) {
			String containerId= environment.getContainerId();
			
			if(containerId == null) {
				continue;
			}
			
			boolean exists = testContainerManager.containerExists(containerId);
			
			if(!exists) {
				environment.setStatus(EnvironmentStatus.ORPHANED);
				
				environmentRepository.save(environment);
				
				log.warn("Environment {} marked ORPHANED",
						environment.getName()
						);
				continue;
			}
			
			boolean running = testContainerManager.isContainerRunning(containerId);
			
			EnvironmentStatus actualStatus = 
					running
					        ? EnvironmentStatus.RUNNING
					        : EnvironmentStatus.STOPPED;
			
			if(environment.getStatus() != actualStatus) {
				environment.setStatus(actualStatus);
				environmentRepository.save(environment);
				
				log.info("Environment {} status updated to {}",
						environment.getName(),
						actualStatus
						);
			}
		}
	}
}
