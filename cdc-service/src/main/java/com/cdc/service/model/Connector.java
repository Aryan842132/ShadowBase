package com.cdc.service.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "environment_id", nullable = false)
	private Long environmentId;
	
	@Column(name = "connector_name", nullable = false, unique = true)
	private String connectorName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "connector_type", nullable = false)
	private ConnectorType connectorType;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ConnectorStatus status;
	
	@Column(name = "kafka_topic")
	private String kafkaTopic;
	
	@Column(name = "database_name")
	private String databaseName;
	
	@Column(name = "connector_config", columnDefinition = "TEXT")
	private String connectorConfig;
	
	@Column(name = "last_error", columnDefinition = "TEXT")
	private String lastError;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
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
