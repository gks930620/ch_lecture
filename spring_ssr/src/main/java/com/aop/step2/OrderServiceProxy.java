package com.aop.step2;

/**
 * ★ 프록시 — 부가 로직(로깅/시간측정)을 전담하는 클래스
 *
 * 같은 인터페이스(OrderService)를 구현하고, 실제 객체(target)를 감싼다.
 * 클라이언트 → OrderServiceProxy(부가 로직) → OrderServiceImpl(핵심 로직)
 *
 * 남은 문제:
 *   1. 메서드마다 같은 프록시 코드를 반복 작성해야 함 (getOrder, createOrder...)
 *   2. Service가 늘어나면 Proxy 클래스도 하나씩 더 만들어야 함
 * → Step3: JDK Dynamic Proxy로 자동화!
 */
public class OrderServiceProxy implements OrderService {

    private final OrderService target;   // 실제 객체 (OrderServiceImpl)

    public OrderServiceProxy(OrderService target) {
        this.target = target;
    }

    @Override
    public String getOrder(Long id) {
        // ─── 부가 로직: 실행 전 ───
        long start = System.currentTimeMillis();
        System.out.println("[PROXY LOG] getOrder() 시작");

        String result = target.getOrder(id);   // ★ 핵심 로직은 실제 객체에 위임

        // ─── 부가 로직: 실행 후 ───
        long end = System.currentTimeMillis();
        System.out.println("[PROXY LOG] getOrder() 종료 - 실행시간: " + (end - start) + "ms");
        return result;
    }

    @Override
    public String createOrder(String item) {
        // ─── 부가 로직: 실행 전 (getOrder와 같은 코드가 또 반복됨...) ───
        long start = System.currentTimeMillis();
        System.out.println("[PROXY LOG] createOrder() 시작");

        String result = target.createOrder(item);   // ★ 핵심 로직은 실제 객체에 위임

        // ─── 부가 로직: 실행 후 ───
        long end = System.currentTimeMillis();
        System.out.println("[PROXY LOG] createOrder() 종료 - 실행시간: " + (end - start) + "ms");
        return result;
    }
}
