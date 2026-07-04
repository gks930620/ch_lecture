package com.aop.step2;

/**
 * ✅ 핵심 로직만 있는 구현체 — 로깅 코드가 전혀 없음!
 *
 * Step1과 비교: 부가 로직(로깅/시간측정)이 모두 OrderServiceProxy로 분리됨.
 * 핵심 로직만 남아서 읽기 쉽고, 수정할 이유도 하나뿐.
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
