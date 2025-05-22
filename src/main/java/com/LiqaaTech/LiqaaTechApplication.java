package com.LiqaaTech;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@SpringBootApplication
@EntityScan(basePackages = {"com.LiqaaTech.Models", "com.LiqaaTech.Entities"})
@EnableJpaRepositories(basePackages = "com.LiqaaTech.Repositories")
public class LiqaaTechApplication {
	public static void main(String[] args) {
		SpringApplication.run(LiqaaTechApplication.class, args);
	}
}