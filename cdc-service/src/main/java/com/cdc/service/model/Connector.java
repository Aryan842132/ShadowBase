package com.cdc.service.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="connectors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Connector {

	private Long id;
	
	private Long environmentId;
	
	private String connectorName;
	
	private ConnectorType connectorType;
	
	private ConnectorStatus status;
	
	private String kafkaTopic;
	
	private String databaseName;
	
	private String connectorConfig;
	
	private String lastError;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@PrePersist
	public void prePersist() {
		this.createdAt=LocalDateTime.now();
		this.updatedAt=LocalDateTime.now();
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updatedAt=LocalDateTime.now();
	}
	
}
