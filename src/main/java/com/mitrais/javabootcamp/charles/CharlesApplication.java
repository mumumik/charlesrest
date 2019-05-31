package com.mitrais.javabootcamp.charles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CharlesApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CharlesApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CharlesApplication.class);
	}

}
