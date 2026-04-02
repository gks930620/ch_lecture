package com.ch.basic.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 엔티티를 찾을 수 없을 때 발생하는 예외
 * HTTP 404 Not Found
 *
 * 예: 존재하지 않는 게시글 ID로 조회할 때
 */
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "NOT_FOUND");
    }

    // 정적 팩토리 메서드: ID로 조회 실패 — "게시글을(를) 찾을 수 없습니다: 99"
    public static EntityNotFoundException of(String entityName, Long id) {
        return new EntityNotFoundException(entityName + "을(를) 찾을 수 없습니다: " + id);
    }

    // 정적 팩토리 메서드: 문자열 식별자로 조회 실패 — "사용자을(를) 찾을 수 없습니다: admin"
    public static EntityNotFoundException of(String entityName, String identifier) {
        return new EntityNotFoundException(entityName + "을(를) 찾을 수 없습니다: " + identifier);
    }
}
