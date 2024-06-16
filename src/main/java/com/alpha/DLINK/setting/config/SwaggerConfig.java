package com.alpha.DLINK.setting.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "D:LINK API 명세서",
                description = "LLM 모델 기반 음료 추천 API Spec.",
                version = "v1"
        ),
        servers = {
                @Server(url = "${baseUrl.server}")
        }
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

        /**
         * 그룹화된 OpenAPI 설정
         * 경로가 /api/** 인 API 설정
         */
        @Bean
        public GroupedOpenApi dlinkOpenApi() {
                return GroupedOpenApi.builder()
                        .group("D:LINK API v1")
                        .pathsToMatch("/api/**")
                        .build();
        }

        /**
         * OpenAPI 메인 설정
         * JWT 보안 스키마를 설정
         */
        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()
                        .addSecurityItem(new SecurityRequirement().addList("JWT"))
                        .components(new Components().addSecuritySchemes("JWT", createAPIKeyScheme()));
        }

        /**
         * JWT 보안 스키마 생성 메서드
         * @return SecurityScheme JWT 보안 스키마
         */
        private SecurityScheme createAPIKeyScheme() {
                return new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .bearerFormat("JWT")
                        .scheme("bearer");
        }
}
