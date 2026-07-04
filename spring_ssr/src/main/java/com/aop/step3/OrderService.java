package com.aop.step3;

/**
 * 주문 서비스 인터페이스
 * JDK Dynamic Proxy는 인터페이스 기반으로만 프록시를 만들 수 있다 (인터페이스 필수!)
 */
public interface OrderService {
    String getOrder(Long id);
    String createOrder(String item);
}
