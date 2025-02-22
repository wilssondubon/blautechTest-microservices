package com.blautech.userprofile_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class UserprofileMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserprofileMicroserviceApplication.class, args);
	}

}
