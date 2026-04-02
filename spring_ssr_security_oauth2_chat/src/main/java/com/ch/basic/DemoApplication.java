package com.ch.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 애플리케이션 진입점
 *
 * @SpringBootApplication 이 어노테이션 하나로 다음 3가지가 자동 적용됨:
 *   - @Configuration: 이 클래스 자체가 설정 파일 역할
 *   - @EnableAutoConfiguration: 의존성(build.gradle)에 맞춰 자동 설정 (예: JPA, Security 등)
 *   - @ComponentScan: 이 패키지(com.ch.basic) 하위의 @Component, @Controller, @Service 등을 자동 스캔하여 Bean 등록
 */
@SpringBootApplication
public class DemoApplication {

	// Java 프로그램의 시작점. JVM이 이 메서드를 가장 먼저 실행함
	public static void main(String[] args)
	{



		// SpringApplication.run()이 호출되면:
		// 1) Spring IoC 컨테이너(ApplicationContext) 생성
		// 2) Bean 등록 (ComponentScan + AutoConfiguration)
		// 3) 내장 Tomcat 서버 시작 (기본 포트 8080)
		SpringApplication.run(DemoApplication.class, args);
	}

}
