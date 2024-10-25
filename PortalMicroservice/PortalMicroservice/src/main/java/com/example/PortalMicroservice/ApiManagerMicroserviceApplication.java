package com.example.PortalMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiManagerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiManagerMicroserviceApplication.class, args);
	}
}