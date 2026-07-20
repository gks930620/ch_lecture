package com.test.test.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger(OpenAPI) 문서 설정
 * - JWT Bearer 인증 스키마 등록 (scheme=bearer라 Swagger UI가 "Bearer "를 자동 부착 → 토큰 값만 입력)
 * - 이전에는 QuerydslConfig 내부 클래스로 있었으나 역할이 달라 별도 파일로 분리
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // JWT 인증 스키마 정의
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .addSecurityItem(securityRequirement)
                .info(new Info()
                        .title("spring_csr_final API")
                        .description("커뮤니티/댓글/파일/실시간 채팅(STOMP) + JWT·OAuth2 인증 REST API 문서")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("개발자")
                                .email("developer@example.com")));
    }
}
