package com.ioc.step4;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * [STEP 4] Spring 컨테이너 - Assembler를 Spring이 대신 해준다
 *
 * Step3의 AppAssembler를 Spring의 @Configuration + @Bean으로 대체.
 * AnnotationConfigApplicationContext = Spring의 IoC 컨테이너
 *
 * ※ 이 클래스는 Spring Boot가 아닌 순수 Spring 컨테이너로 실행됨
 *   (웹 서버 없이 main()으로 직접 실행 가능)
 */
public class Main {

    public static void main(String[] args) {
        // Spring 컨테이너 생성 (= Assembler 역할을 Spring이 대신)
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        // Bean 조회 (= assembler.getMemberController() 와 동일)
        MemberController controller = context.getBean(MemberController.class);
        System.out.println("[STEP4] " + controller.getMemberInfo(1L));

        // 등록된 Bean 목록 출력
        System.out.println("\n── 등록된 Bean 목록 ──");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("  - " + name);
        }

        context.close();

        /*
         * ★ 비교 정리
         *
         * ┌───────────────────┬──────────────────────────────────┐
         * │  Step3 (수동)     │  Step4 (Spring)                  │
         * ├───────────────────┼──────────────────────────────────┤
         * │  AppAssembler     │  @Configuration (AppConfig)      │
         * │  new 객체()       │  @Bean 메서드                     │
         * │  getter로 조회    │  context.getBean()               │
         * │  직접 관리        │  Spring 컨테이너가 관리 (싱글톤)  │
         * └───────────────────┴──────────────────────────────────┘
         *
         * 실제 Spring Boot 프로젝트에서는?
         * - @Configuration + @Bean 대신 @Component, @Service, @Repository 등을 사용
         * - @ComponentScan이 자동으로 Bean 등록 (= @SpringBootApplication에 포함)
         * - 생성자 주입은 @RequiredArgsConstructor (Lombok)로 축약
         */
    }
}

