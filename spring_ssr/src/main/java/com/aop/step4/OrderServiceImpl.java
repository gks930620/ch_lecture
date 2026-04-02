package com.aop.step4;

import org.springframework.stereotype.Service;

/**
 * ✅ 핵심 로직만 있는 Service - Spring Bean으로 등록
 *
 * Step1처럼 로깅 코드가 전혀 없음!
 * Spring AOP가 프록시를 자동 생성하여 부가 로직을 적용.
 */
@Service("aopOrderService")   // Bean 이름 충돌 방지
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl() {
        this.orderRepository = new OrderRepository();
    }

    @Override
    public String getOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public String createOrder(String item) {
        return "주문 생성: " + item;
    }
}

