package com.aop.step4;

/**
 * 결제 서비스 인터페이스
 * OrderService와 별도 서비스지만, 같은 @Aspect가 자동 적용됨
 */
public interface PaymentService {
    String pay(String method, int amount);
    String refund(Long orderId);
}

