package com.cdc.service.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ConnectorNotFoundException.class)
	public ResponseEntity<?> handleConnectorNotFound(
			ConnectorNotFoundException ex){
		
		log.error("Connnector not found exception occured: {}", ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Map.of(
						"timestamp",LocalDateTime.now(),
						"message", ex.getMessage()));
	}
	
	@ExceptionHandler(DuplicateConnectorException.class)
	public ResponseEntity<?> handleDuplicateConnector(
			DuplicateConnectorException ex){
		
		log.warn("Duplicate connector exception occured: {}", ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(Map.of(
						"timestamp", LocalDateTime.now(),
						"message", ex.getMessage()));
	}
	
	@ExceptionHandler(ConnectorCreationException.class)
	public ResponseEntity<?> handleConnectorCreation(
			ConnectorCreationException ex){
		
		log.error("Connector creation failed: {}", ex.getMessage(), ex);
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of(
						"timestamp", LocalDateTime.now(),
						"message", ex.getMessage()));
	}
	
	public ResponseEntity<?> handleGenericException(
			Exception ex){
		
		log.error("Unexpected exxception occured", ex);
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of(
						"timestamp", LocalDateTime.now(),
						"message", "An unexpected error occured"));
	}
}
