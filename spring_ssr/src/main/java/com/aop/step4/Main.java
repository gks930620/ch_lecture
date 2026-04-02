package com.aop.step4;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * [STEP 4] Spring AOP - 선언적 AOP 적용
 *
 * Step1: 부가 로직이 핵심 로직에 직접 섞여있음 (중복 문제)
 * Step2: 프록시 패턴으로 분리 (메서드마다 프록시 코드 중복)
 * Step3: Dynamic Proxy로 자동화 (인터페이스 필요, Pointcut 불편)
 * Step4: Spring AOP - @Aspect + Pointcut으로 완전 자동화
 *
 * ※ 이 클래스는 Spring Boot가 아닌 순수 Spring 컨테이너로 실행됨
 *   (IoC step4와 동일한 방식)
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== [STEP4] Spring AOP - 선언적 AOP ===\n");

        // Spring 컨테이너 생성
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AopConfig.class);

        // ─────────────────────────────────────────────
        // 1. OrderService - Spring AOP 적용됨
        // ─────────────────────────────────────────────
        System.out.println("── OrderService ──");
        OrderService orderService = context.getBean(OrderService.class);

        // ★ 이 호출이 실제로는 프록시를 통해 실행됨!
        System.out.println("결과: " + orderService.getOrder(1L));
        System.out.println();
        System.out.println("결과: " + orderService.createOrder("키보드"));

        // ─────────────────────────────────────────────
        // 2. PaymentService - 같은 Aspect가 자동 적용됨!
        // ─────────────────────────────────────────────
        System.out.println("\n── PaymentService ──");
        PaymentService paymentService = context.getBean(PaymentService.class);

        System.out.println("결과: " + paymentService.pay("카카오페이", 50000));
        System.out.println();
        System.out.println("결과: " + paymentService.refund(1L));

        // ─────────────────────────────────────────────
        // 3. 프록시 확인 - Spring이 자동 생성한 프록시 객체
        // ─────────────────────────────────────────────
        System.out.println("\n── 프록시 객체 확인 ──");
        System.out.println("OrderService 실제 클래스: " + orderService.getClass().getName());
        System.out.println("PaymentService 실제 클래스: " + paymentService.getClass().getName());
        System.out.println("→ 프록시 클래스가 자동 생성된 것을 확인!");

        context.close();

        // ─────────────────────────────────────────────
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("★ AOP 전체 정리 (Step 1 → 4)");
        System.out.println("═══════════════════════════════════════");
        System.out.println();
        System.out.println("Step1: 부가 로직이 핵심 로직에 직접 섞여있음");
        System.out.println("  → 중복 코드 발생, OCP 위반");
        System.out.println();
        System.out.println("Step2: 프록시 패턴으로 분리");
        System.out.println("  → 핵심/부가 분리 성공! 하지만 프록시 클래스를 직접 작성해야 함");
        System.out.println();
        System.out.println("Step3: JDK Dynamic Proxy");
        System.out.println("  → 하나의 Handler로 모든 인터페이스에 적용 가능");
        System.out.println("  → 하지만 인터페이스 필수, Pointcut 지정 불편");
        System.out.println();
        System.out.println("Step4: Spring AOP (@Aspect)");
        System.out.println("  → @Aspect + Pointcut으로 선언적 적용");
        System.out.println("  → 프록시 자동 생성, Pointcut으로 적용 대상 세밀하게 지정");
        System.out.println("  → 실무에서 이 방식 사용!");
        System.out.println();
        System.out.println("┌──────────────────────────────────────────────────────┐");
        System.out.println("│  Step3 (수동)                 │  Step4 (Spring AOP)  │");
        System.out.println("├──────────────────────────────────────────────────────┤");
        System.out.println("│  Proxy.newProxyInstance()     │  Spring 자동 생성    │");
        System.out.println("│  InvocationHandler.invoke()   │  @Around 메서드      │");
        System.out.println("│  method.invoke(target, args)  │  joinPoint.proceed() │");
        System.out.println("│  모든 메서드 무조건 적용      │  Pointcut으로 선택   │");
        System.out.println("└──────────────────────────────────────────────────────┘");
    }
}

