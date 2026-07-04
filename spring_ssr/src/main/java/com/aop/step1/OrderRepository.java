package com.aop.step1;

import java.util.Map;

/**
 * 주문 저장소 (가상 DB)
 * DB 대신 Map으로 흉내만 냄 — AOP 학습에 집중하기 위해 단순화
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
