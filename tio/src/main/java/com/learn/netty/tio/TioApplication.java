package com.learn.netty.tio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tio.core.starter.annotation.EnableTioServerServer;

@SpringBootApplication
@EnableTioServerServer
public class TioApplication {

	public static void main(String[] args) {
		SpringApplication.run(TioApplication.class, args);
	}

}
