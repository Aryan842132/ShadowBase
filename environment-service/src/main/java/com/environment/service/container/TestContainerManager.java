package com.environment.service.container;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.testcontainers.containers.MySQLContainer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestContainerManager {

	private final Map<Long, MySQLContainer<?>> activeContainers = new ConcurrentHashMap<>();
	
	public ContainerInfo createContainer(Long environmentId) {
		log.info("Starting MySQL container for environmentId={}", environmentId);
		
		String networkAlias = "mysql-env-" + environmentId;
		
				
		MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
				                                  .withDatabaseName("testdb")
				                                  .withUsername("admin")
				                                  .withPassword("admin123")
				                                  .withNetworkMode("netflix-network")
			                                      .withNetworkAliases(networkAlias)
			                                      .withCommand(
			                                    		  "--log-bin=mysql-bin",
			                                    		  "--binlog-format=ROW",
			                                    		  "--server-id=" + environmentId,
			                                    		  "--gtid-mode=ON",
			                                    		  "--enforce-gtid-consistency=ON");
		                                         
		mysqlContainer.start();
		
		activeContainers.put(environmentId, mysqlContainer);
		log.info("Container started successfully. containerId={}", mysqlContainer.getContainerId());
		
		return ContainerInfo.builder()
				.containerId(mysqlContainer.getContainerId())
				.jdbcUrl(mysqlContainer.getJdbcUrl())
				.host(networkAlias)
				.port(3306)
				.username(mysqlContainer.getUsername())
				.password(mysqlContainer.getPassword())
				.build();
	}
	
	public void stopContainer(Long environmentId) {
		MySQLContainer<?> container = activeContainers.get(environmentId);
		
		if(container != null) {
			log.info("Stoping container for environmentId={}", environmentId);
			
			container.stop();
			
			log.info("Container stopped successfully");
		}
	}
	
	public void removeContainer(Long environmentId) {
		MySQLContainer<?> container = activeContainers.remove(environmentId);
		
		if(container != null) {
			log.info("Removing container for  environmentId={}", environmentId);
			
			container.stop();
			
			log.info("Container removed successfully.");
			
		}
	}
	
	public boolean isRunning(Long environmentId) {
		MySQLContainer<?> container = activeContainers.get(environmentId);
		
		return container != null && container.isRunning();
	}
	
	
	
	
}
