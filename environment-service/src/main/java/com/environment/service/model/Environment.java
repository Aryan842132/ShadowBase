package com.environment.service.model;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Environment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, unique = true, length = 100)
	private String name;
	
	@Column(name="container_Id")
	private String containerId;
	
	@Column(name="jdbc_url",length = 500)
	private String jdbcUrl;
	
	@Column(length = 100)
	private String host;
	
	private Integer port;
	
	@Column(length = 50)
	private String userName;
	
	@Column(length = 50)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EnvironmentStatus status;
	
	@Column(name="created_at", nullable = false)
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updatedAt=LocalDateTime.now();
	}
	
	
	
}
