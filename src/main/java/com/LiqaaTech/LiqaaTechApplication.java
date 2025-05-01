package com.LiqaaTech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.LiqaaTech.Repositories")
@EntityScan(basePackages = "com.LiqaaTech.Entities")
public class LiqaaTechApplication {
	public static void main(String[] args) {
		SpringApplication.run(LiqaaTechApplication.class, args);
	}
}