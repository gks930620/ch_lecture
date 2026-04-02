package com.aop.step4;

import org.springframework.stereotype.Service;

/**
 * PaymentService 구현체 - Spring Bean으로 등록
 */
@Service("aopPaymentService")   // Bean 이름 충돌 방지
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

