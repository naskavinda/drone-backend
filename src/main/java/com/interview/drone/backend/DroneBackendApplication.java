package com.interview.drone.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DroneBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneBackendApplication.class, args);
	}

}
