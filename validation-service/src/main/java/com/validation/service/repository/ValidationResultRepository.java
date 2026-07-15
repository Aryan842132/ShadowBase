package com.validation.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.validation.service.model.ValidationResult;
import com.validation.service.model.ValidationStatus;

public interface ValidationResultRepository extends JpaRepository<ValidationResult, Long> {

	List<ValidationResult> findByEnvironmentId(Long environmentId);
	
	List<ValidationResult> findByConnectorId(Long connectorId);
	
	List<ValidationResult> findByStatus(ValidationStatus status);
}
