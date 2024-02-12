package com.alpha.DLINK.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "D:LINK API 명세서",
                description = "알파프로젝트 API Spec.",
                version = "v1"
        )
)

@Configuration
public class SwaggerConfig {
}
