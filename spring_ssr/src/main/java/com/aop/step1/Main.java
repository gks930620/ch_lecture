package com.aop.step1;

/**
 * [STEP 1] AOP 없이 — 부가 로직이 핵심 로직에 직접 섞여있음
 *
 * 실행해보면 로깅은 잘 되지만,
 * OrderService의 모든 메서드에 같은 로깅 코드가 반복되는 것을 확인할 수 있다.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== [STEP1] AOP 없음 - 로깅 코드 중복 ===\n");

        OrderService orderService = new OrderService();

        System.out.println("결과: " + orderService.getOrder(1L));
        System.out.println();
        System.out.println("결과: " + orderService.createOrder("키보드"));

        System.out.println("\n─────────────────────────────────────");
        System.out.println("문제점:");
        System.out.println("  1. 모든 메서드에 같은 로깅 코드 반복 (중복)");
        System.out.println("  2. 핵심 로직이 부가 로직에 묻힘");
        System.out.println("  3. 로그 형식 변경 시 모든 메서드 수정 필요");
        System.out.println("→ Step2: 프록시 패턴으로 부가 로직을 분리해보자!");
    }
}
