package com.aop.step3;

/**
 * ✅ 핵심 로직만 있는 구현체 — Step2와 동일
 */
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository = new OrderRepository();

    @Override
    public String getOrder(Long id) {
        return orderRepository.findById(id);   // ★ 핵심 로직만!
    }

    @Override
    public String createOrder(String item) {
        return "주문 생성: " + item;            // ★ 핵심 로직만!
    }
}
