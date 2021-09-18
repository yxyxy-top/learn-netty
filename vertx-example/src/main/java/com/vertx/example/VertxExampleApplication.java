package com.vertx.example;

import org.hswebframework.web.authorization.basic.configuration.EnableAopAuthorize;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAopAuthorize
public class VertxExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(VertxExampleApplication.class, args);
	}

}
