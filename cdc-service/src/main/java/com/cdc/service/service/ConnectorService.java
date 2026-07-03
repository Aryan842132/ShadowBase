package com.cdc.service.service;

import java.util.List;

import com.cdc.service.dto.ConnectorRequest;
import com.cdc.service.dto.ConnectorResponse;

public interface ConnectorService {

	ConnectorResponse createConnector(ConnectorRequest request);
	
	ConnectorResponse getConnectorbyId(Long id);
	
	List<ConnectorResponse> getAllConnectors();
	
	ConnectorResponse getConnectorByEnvironmentId(Long environmentId);
	
	void deleteConnector(Long id);
	
	
}
