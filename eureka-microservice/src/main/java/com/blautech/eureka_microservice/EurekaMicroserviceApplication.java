package com.blautech.eureka_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnabledEurekaServer
public class EurekaMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaMicroserviceApplication.class, args);
	}

}
