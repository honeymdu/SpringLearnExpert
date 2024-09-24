package com.example.SpringLearnExpert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringLearnExpertApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringLearnExpertApplication.class, args);
	}

}
