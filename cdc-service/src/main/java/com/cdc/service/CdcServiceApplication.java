package com.cdc.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CdcServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CdcServiceApplication.class, args);
	}

}
