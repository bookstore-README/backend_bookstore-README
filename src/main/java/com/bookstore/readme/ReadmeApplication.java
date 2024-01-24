package com.bookstore.readme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ReadmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadmeApplication.class, args);
	}

}
