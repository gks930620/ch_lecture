package com.ch.basic.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 설정 클래스
 *
 * WebMvcConfigurer: Spring MVC의 기본 설정을 커스터마이징할 수 있는 인터페이스
 *   - Interceptor 등록, 정적 리소스 매핑, CORS 설정 등을 여기서 함
 *
 * @Configuration: Spring 설정 클래스 선언
 * @RequiredArgsConstructor: final 필드에 대한 생성자 자동 생성 → 의존성 주입
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {


}
