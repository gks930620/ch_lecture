package com.aop.step3;

/**
 * 결제 서비스 인터페이스 — 두 번째 서비스
 *
 * Step2라면 PaymentServiceProxy를 또 만들어야 하지만,
 * Step3에서는 같은 LoggingHandler 하나로 재사용 가능! (재사용성 증명용)
 */
public interface PaymentService {
    String pay(String method, int amount);
    String refund(Long orderId);
}
