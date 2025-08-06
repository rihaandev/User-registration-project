package com.registration.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
@ComponentScan(basePackages = "com.registration")
@EnableJpaRepositories(basePackages = "com.registration.repository")
@EntityScan(basePackages = "com.registration.domain")
public class UserRegistrationAppApplication {

	public static void main(String[] args)  {
		SpringApplication.run(UserRegistrationAppApplication.class, args);
	}
}
