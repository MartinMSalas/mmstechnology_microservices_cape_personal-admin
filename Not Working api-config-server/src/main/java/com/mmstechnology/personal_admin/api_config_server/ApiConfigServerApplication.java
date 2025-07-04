package com.mmstechnology.personal_admin.api_config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ApiConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiConfigServerApplication.class, args);
	}

}
