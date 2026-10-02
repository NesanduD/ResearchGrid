package com.university.ResearchGrid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ResearchGridApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResearchGridApplication.class, args);
	}

}
