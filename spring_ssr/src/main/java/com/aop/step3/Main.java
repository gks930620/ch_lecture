package com.aop.step3;

import java.lang.reflect.Proxy;

/**
 * [STEP 3] JDK Dynamic Proxy — 리플렉션으로 프록시 자동 생성
 *
 * Step2의 문제(메서드마다/Service마다 프록시 코드 작성)를
 * Proxy.newProxyInstance() + InvocationHandler 하나로 해결.
 *
 * 남은 문제:
 *   1. 인터페이스가 반드시 필요 (클래스만으로는 프록시 생성 불가)
 *   2. "어떤 메서드에만 적용" 같은 Pointcut 지정이 불편 (모든 메서드에 무조건 적용)
 * → Step4: Spring AOP(@Aspect + Pointcut)로 해결!
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== [STEP3] JDK Dynamic Proxy - 프록시 자동 생성 ===\n");

        // ─────────────────────────────────────────────
        // 1. OrderService에 적용
        // ─────────────────────────────────────────────
        System.out.println("── OrderService ──");
        OrderService orderTarget = new OrderServiceImpl();

        // ★ 프록시 클래스를 직접 만들지 않고 런타임에 동적 생성!
        OrderService orderService = (OrderService) Proxy.newProxyInstance(
                OrderService.class.getClassLoader(),      // 클래스 로더
                new Class[]{OrderService.class},          // 구현할 인터페이스
                new LoggingHandler(orderTarget)           // 부가 로직 핸들러
        );

        System.out.println("결과: " + orderService.getOrder(1L));
        System.out.println();
        System.out.println("결과: " + orderService.createOrder("키보드"));

        // ─────────────────────────────────────────────
        // 2. PaymentService에도 같은 Handler 재사용!
        // ─────────────────────────────────────────────
        System.out.println("\n── PaymentService (같은 LoggingHandler 재사용) ──");
        PaymentService paymentTarget = new PaymentServiceImpl();

        PaymentService paymentService = (PaymentService) Proxy.newProxyInstance(
                PaymentService.class.getClassLoader(),
                new Class[]{PaymentService.class},
                new LoggingHandler(paymentTarget)         // ← 재사용!
        );

        System.out.println("결과: " + paymentService.pay("카카오페이", 50000));
        System.out.println();
        System.out.println("결과: " + paymentService.refund(1L));

        // ─────────────────────────────────────────────
        // 3. 프록시 클래스 확인
        // ─────────────────────────────────────────────
        System.out.println("\n── 프록시 객체 확인 ──");
        System.out.println("OrderService 실제 클래스: " + orderService.getClass().getName());
        System.out.println("→ JDK가 런타임에 생성한 $Proxy 클래스!");

        System.out.println("\n─────────────────────────────────────");
        System.out.println("개선: Handler 하나로 모든 Service에 프록시 자동 생성!");
        System.out.println("남은 문제:");
        System.out.println("  1. 인터페이스 필수");
        System.out.println("  2. Pointcut(적용 대상 선택) 지정 불편");
        System.out.println("→ Step4: Spring AOP로 완전 자동화!");
    }
}
