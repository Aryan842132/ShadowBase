package com.environment.service.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import com.environment.service.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(
			ResourceNotFoundException ex,
			HttpServletRequest request){
		log.warn("Resource not found: {}",ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorResponse.builder()
						.timestamp(LocalDateTime.now())
						.status(HttpStatus.NOT_FOUND.value())
						.error("Not Found")
						.message(ex.getMessage())
						.path(request.getRequestURI())
						.build());
	}
	
	@ExceptionHandler(DuplicateEnvironmentException.class)
	public ResponseEntity<ErrorResponse> handleDuplicate(
			DuplicateEnvironmentException ex,
			HttpServletRequest request){
		
		log.warn("Duplicate environment: {}", ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ErrorResponse.builder()
						.timestamp(LocalDateTime.now())
						.status(HttpStatus.CONFLICT.value())
						.error("Conflict")
						.message(ex.getMessage())
						.path(request.getRequestURI())
						.build());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(
			MethodArgumentNotValidException ex,
			HttpServletRequest request){
		
		String message = ex.getBindingResult()
				.getFieldError()
				.getDefaultMessage();
		
		return ResponseEntity.badRequest()
				.body(ErrorResponse.builder()
						.timestamp(LocalDateTime.now())
						.status(HttpStatus.BAD_REQUEST.value())
						.error("Validatiton error")
						.message(message)
						.path(request.getRequestURI())
						.build());
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneric(
			Exception ex,
			HttpServletRequest request){
		
		log.error("Unexpected error", ex);
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ErrorResponse.builder()
						.timestamp(LocalDateTime.now())
						.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.error("Internal Server Error")
						.message(ex.getMessage())
						.path(request.getRequestURI())
						.build());
	}
	
}
