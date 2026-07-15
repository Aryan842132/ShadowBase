package com.validation.service.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Table(name="Validation_results")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "environment_id")
	private Long environmentId;
	
	@Column(name = "connector_id")
	private Long connectorId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ValidationType type;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ValidationStatus status;
	
	@Column(nullable = false, length = 500)
	private String message;
	
	@Column(name = "validated_at", nullable = false)
	private LocalDateTime validatedAt;
	
}
