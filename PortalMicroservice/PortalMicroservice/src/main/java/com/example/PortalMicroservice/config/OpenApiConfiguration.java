package com.example.PortalMicroservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Miguel Cardoso",
                        email = "miguel40cardoso@gmail.com",
                        url = "https://www.linkedin.com/in/miguelcardoso19/"
                ),
                description = "OpenApi documentation for API ManagerMicroservice",
                title = "OpenApi specification - API ManagerMicroservice",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local env",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production env",
                        url = "yooooooooo"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Insert the JWT bearer token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {}