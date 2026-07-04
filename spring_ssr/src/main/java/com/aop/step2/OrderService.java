package com.aop.step2;

/**
 * 주문 서비스 인터페이스 — 프록시 패턴의 핵심!
 *
 * 클라이언트는 이 인터페이스만 바라봄.
 * 실제 객체(OrderServiceImpl)와 프록시(OrderServiceProxy)가 모두 이것을 구현하므로
 * 클라이언트 코드 수정 없이 프록시로 바꿔치기할 수 있다.
 */
public interface OrderService {
    String getOrder(Long id);
    String createOrder(String item);
}
