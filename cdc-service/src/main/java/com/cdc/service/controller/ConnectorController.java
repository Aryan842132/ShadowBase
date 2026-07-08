package com.cdc.service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdc.service.dto.ConnectorRequest;
import com.cdc.service.dto.ConnectorResponse;
import com.cdc.service.service.ConnectorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/connectors")
@RequiredArgsConstructor
@Validated
public class ConnectorController {

	private final ConnectorService connectorService;
	
	@PostMapping
	public ResponseEntity<ConnectorResponse> createConnector(
			@Valid @RequestBody ConnectorRequest request){
		
		log.info("Received request to create connector: {}",
				request.getConnectorName());
		
		ConnectorResponse response = connectorService.createConnector(request);
		
		log.info("Connector created successfully with id={}",
				response.getId());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ConnectorResponse> getConnectorById(
			@PathVariable Long id){
		
		log.info("Received request to fetch conncetor with id={}", id);
		
		ConnectorResponse response = connectorService.getConnectorbyId(id);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<List<ConnectorResponse>> getAllConnector(){
		log.info("Received request to fetch all connectors");
		
		List<ConnectorResponse> response = connectorService.getAllConnectors();
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/environment/{environmentId}")
	public ResponseEntity<ConnectorResponse> getConnectorbyEnvironmentId(
			@PathVariable Long environmentId){
		
		log.info("Received request to fetch connector for environmetnId={}",
				environmentId);
		
		ConnectorResponse response = connectorService.getConnectorByEnvironmentId(environmentId);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteConnector(
			@PathVariable Long id){
		
		log.info("Received request to delete connector with id={}", id);
		
		connectorService.deleteConnector(id);
		
		log.info("Connector deleted successfully with id={}", id);
		
		return ResponseEntity.noContent().build();
	}
	
	public ResponseEntity<Boolean> hasConnectors(
			@PathVariable Long environmentId){
		
		log.info("Received connector existence check for environmentId={}", environmentId);	
		
		return ResponseEntity.ok(connectorService.hasConnectors(environmentId));
	}
}
