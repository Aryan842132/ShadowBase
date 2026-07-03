package com.cdc.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdc.service.model.Connector;

@Repository
public interface ConnectorRepository extends JpaRepository<Connector, Long>{
	
	boolean existsByConnectorName(String connectorName);
	
	Optional<Connector> findByEnvironmentId(Long environmentId);
	
	Optional<Connector> findByConnectorName(String connectorName);
 
}
