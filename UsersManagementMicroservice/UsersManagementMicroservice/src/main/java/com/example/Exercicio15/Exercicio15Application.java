package com.example.Exercicio15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
public class Exercicio15Application {

	public static void main(String[] args) {
		SpringApplication.run(Exercicio15Application.class, args);
	}

}