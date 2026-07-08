package com.environment.service.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.environment.service.dto.EnvironmentRequest;
import com.environment.service.dto.EnvironmentResponse;
import com.environment.service.service.EnvironmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/environments")
@Slf4j
@RequiredArgsConstructor
public class EnvironmentController {

	private final EnvironmentService environmentService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EnvironmentResponse createEnvironment(@Valid @RequestBody EnvironmentRequest request) {
		log.info("create environment request received. name={}", request.getName());
		
		return environmentService.createEnvironment(request);
	}
	
	
	@GetMapping("/{id}")
	public EnvironmentResponse getEnvironment(@PathVariable Long id) {
		log.info("Get environment request received. id={}", id);
		
		return environmentService.getEnvironment(id);
	}
	
	@GetMapping
	public List<EnvironmentResponse> getAllEnvironments(){
		log.info("Get all environment request received");
		
		return environmentService.getAllEnvironments();
	}
	
	@PostMapping("/{id}/stop")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void stopEnvironment(@PathVariable Long id) {
		log.info("Stop environment request received. id={}", id);
		
		environmentService.stopEnvironment(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEnvironment(@PathVariable Long id) {
		log.info("Delete environment request received. id={}", id);
		
		environmentService.deleteEnvironment(id);
	}
	
}
