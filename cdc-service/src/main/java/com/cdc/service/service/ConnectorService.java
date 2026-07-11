package com.cdc.service.service;

import java.util.List;

import com.cdc.service.dto.ConnectorRequest;
import com.cdc.service.dto.ConnectorResponse;
import com.cdc.service.dto.ConnectorStatusResponse;

public interface ConnectorService {

	ConnectorResponse createConnector(ConnectorRequest request);
	
	ConnectorResponse getConnectorbyId(Long id);
	
	List<ConnectorResponse> getAllConnectors();
	
	ConnectorResponse getConnectorByEnvironmentId(Long environmentId);
	
	void deleteConnector(Long id);
	
	boolean hasConnectors(Long environmentId);
	
	ConnectorStatusResponse syncConnectorStatus(Long id);
	
	ConnectorStatusResponse pauseConnector(Long id);
	
	ConnectorStatusResponse resumeConnector(Long id);
}
