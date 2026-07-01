package com.environment.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.environment.service.model.Environment;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Long>{
	
	Optional<Environment> findbyName(String name);
	
	boolean existByName(String name);

}
