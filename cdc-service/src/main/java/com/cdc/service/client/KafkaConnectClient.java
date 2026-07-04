package com.cdc.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConnectClient {
	
	private final RestClient restClient;
	
	@Value("${kafka.connect.url")
	private String kafkaConnectUrl;

}
