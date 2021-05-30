package com.citi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.citi.demo.Repository")
@SpringBootApplication
public class BackendappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendappApplication.class, args);	
	}

}
