package com.pragma.usuariosaws.infrastructura.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Usuarios API")
                        .version("1.0.0")
                        .description("API para gestión de Usuarios")
                        .contact(new Contact()
                                .name("Pragma")
                                .email("jose.gonzalez@pragma.com.co")));

    }
}
