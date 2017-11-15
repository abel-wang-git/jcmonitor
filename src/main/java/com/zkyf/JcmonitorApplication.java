package com.zkyf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JcmonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(JcmonitorApplication.class, args);
	}



}
