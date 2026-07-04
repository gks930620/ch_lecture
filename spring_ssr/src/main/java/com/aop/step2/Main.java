package com.aop.step2;

/**
 * [STEP 2] 프록시 패턴 — 부가 로직을 프록시로 분리
 *
 * 핵심/부가 로직 분리에는 성공했지만,
 * 메서드마다 프록시 코드를 반복 작성해야 하고
 * Service가 늘어나면 Proxy 클래스도 계속 만들어야 한다.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== [STEP2] 프록시 패턴 - 부가 로직 분리 ===\n");

        // 실제 객체를 프록시로 감싼다
        OrderService target = new OrderServiceImpl();          // 핵심 로직
        OrderService orderService = new OrderServiceProxy(target); // 부가 로직 (프록시)

        // 클라이언트는 프록시인지 실제 객체인지 모른 채 인터페이스로만 사용
        System.out.println("결과: " + orderService.getOrder(1L));
        System.out.println();
        System.out.println("결과: " + orderService.createOrder("키보드"));

        System.out.println("\n─────────────────────────────────────");
        System.out.println("개선: 핵심 로직(Impl)과 부가 로직(Proxy) 분리 성공!");
        System.out.println("남은 문제:");
        System.out.println("  1. 메서드마다 같은 프록시 코드 반복 작성");
        System.out.println("  2. Service마다 Proxy 클래스가 하나씩 필요");
        System.out.println("→ Step3: JDK Dynamic Proxy로 자동화해보자!");
    }
}
