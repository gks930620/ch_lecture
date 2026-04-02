package com.aop.step4;

import java.util.Map;

/**
 * 주문 저장소 (가상 DB) — Step1~3과 동일한 역할
 */
public class OrderRepository {

    private final Map<Long, String> db = Map.of(
            1L, "노트북",
            2L, "키보드",
            3L, "마우스"
    );

    public String findById(Long id) {
        return db.getOrDefault(id, "상품 없음");
    }
}

