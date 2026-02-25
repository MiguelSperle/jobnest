package com.miguel.jobnest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobnestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobnestApplication.class, args);
	}

}
