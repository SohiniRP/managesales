package com.project.managesales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ManagesalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagesalesApplication.class, args);
	}

}
