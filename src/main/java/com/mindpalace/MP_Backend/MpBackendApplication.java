package com.mindpalace.MP_Backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers={@Server(url = "https://mindspalace.com/", description = "Production Server URL")})
@SpringBootApplication
public class MpBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(MpBackendApplication.class, args);
	}
}
