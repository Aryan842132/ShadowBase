package com.environment.service.exception;

public class EnvironmentDeletionNotAllowedException extends RuntimeException {

	public EnvironmentDeletionNotAllowedException(String message) {
		super(message);
	}
}
