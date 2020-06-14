package com.fss.AuthServer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
public class AuthServer1Application {

	public static void main(String[] args) {
		SpringApplication.run(AuthServer1Application.class, args);
	}

}
