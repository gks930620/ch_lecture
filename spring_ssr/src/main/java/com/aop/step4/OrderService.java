package com.aop.step4;

/**
 * 주문 서비스 인터페이스
 * Spring AOP는 인터페이스 기반 프록시(JDK Dynamic Proxy)를 기본으로 사용
 */
public interface OrderService {
    String getOrder(Long id);
    String createOrder(String item);
}

