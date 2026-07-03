package com.cdc.service.exception;

public class ConnectorCreationException extends RuntimeException {

	public ConnectorCreationException(String message) {
		super(message);
	}
	
	public ConnectorCreationException(String message, Throwable cause) {
		super(message, cause);
	}
}
