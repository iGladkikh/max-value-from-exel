package com.igladkikh.parser.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "REST API для получегния максимального значения их файла .xlsx",
                version = "1.0.0",
                contact = @Contact(
                        name = "Igor Gladkikh",
                        email = "ig.gladkikh@gmail.com"
                )
        )
)
public class SwaggerConfig {
}
