package com.aop.step1;

/**
 * ❌ AOP 없이 — 부가 로직(로깅/시간측정)이 핵심 로직에 직접 섞여있음
 *
 * 문제점:
 *   1. 모든 메서드에 같은 로깅 코드가 반복됨 (중복)
 *   2. 핵심 로직이 부가 로직에 묻혀서 읽기 어려움
 *   3. 로그 형식을 바꾸려면 모든 메서드를 수정해야 함 (OCP 위반)
 */
public class OrderService {

    private final OrderRepository orderRepository = new OrderRepository();

    public String getOrder(Long id) {
        long start = System.currentTimeMillis();                  // ← 중복 (부가 로직)
        System.out.println("[LOG] getOrder() 시작");               // ← 중복 (부가 로직)

        String result = orderRepository.findById(id);             // ★ 핵심 로직은 이것뿐!

        long end = System.currentTimeMillis();                    // ← 중복 (부가 로직)
        System.out.println("[LOG] getOrder() 종료 - 실행시간: " + (end - start) + "ms"); // ← 중복
        return result;
    }

    public String createOrder(String item) {
        long start = System.currentTimeMillis();                  // ← 중복 (부가 로직)
        System.out.println("[LOG] createOrder() 시작");            // ← 중복 (부가 로직)

        String result = "주문 생성: " + item;                      // ★ 핵심 로직은 이것뿐!

        long end = System.currentTimeMillis();                    // ← 중복 (부가 로직)
        System.out.println("[LOG] createOrder() 종료 - 실행시간: " + (end - start) + "ms"); // ← 중복
        return result;
    }
}
