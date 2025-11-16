package br.edu.atitus.api_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.atitus.api_example.controllers.AuthController;
import br.edu.atitus.api_example.services.UserServices;

@SpringBootApplication
public class ApiExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiExampleApplication.class, args);
		

		
	}

}
