package com.example.minilms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI configuration for Mini LMS
 * Provides Swagger documentation setup
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI miniLmsOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Development server");

        Contact contact = new Contact();
        contact.setEmail("admin@minilms.com");
        contact.setName("Mini LMS Team");
        contact.setUrl("https://www.minilms.com");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Mini LMS API")
                .version("1.0.0")
                .contact(contact)
                .description("Mini Learning Management System Backend API")
                .termsOfService("https://www.minilms.com/terms")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}