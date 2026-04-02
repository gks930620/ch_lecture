package com.aop.step4;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * ★ Spring AOP 설정
 *
 * @EnableAspectJAutoProxy: Spring이 @Aspect 클래스를 인식하고 프록시를 자동 생성하게 함
 * @ComponentScan: com.aop.step4 패키지의 @Component, @Service 등을 자동 스캔
 *
 * 이것이 Step3의 Proxy.newProxyInstance()를 대체한다!
 * Spring이 알아서 프록시 객체를 생성하고, @Aspect의 Advice를 적용해줌.
 */
@Configuration
@EnableAspectJAutoProxy    // AOP 프록시 자동 생성 활성화
@ComponentScan("com.aop.step4")
public class AopConfig {
    // Bean 등록은 @Service, @Component 어노테이션으로 자동 처리
}

