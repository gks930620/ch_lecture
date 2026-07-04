package com.aop.step3;

/**
 * PaymentService 구현체 — 핵심 로직만
 */
public class PaymentServiceImpl implements PaymentService {

    @Override
    public String pay(String method, int amount) {
        return method + "로 " + amount + "원 결제 완료";
    }

    @Override
    public String refund(Long orderId) {
        return "주문_" + orderId + " 환불 완료";
    }
}
