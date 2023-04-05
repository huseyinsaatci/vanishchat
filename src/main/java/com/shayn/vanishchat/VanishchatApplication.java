package com.shayn.vanishchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class VanishchatApplication {

	public static void main(String[] args) {
		SpringApplication.run(VanishchatApplication.class, args);
	}

}
