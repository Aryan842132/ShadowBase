package com.cdc.service.exception;

public class InvalidConnectorStateException extends RuntimeException{

	public InvalidConnectorStateException(String message) {
		super(message);
	}
}
