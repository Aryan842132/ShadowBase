package com.environment.service;

import org.springframework.boot.SpringApplication;

public class TestEnvironmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(EnvironmentServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
