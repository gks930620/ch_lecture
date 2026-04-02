package com.ch.basic.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 이미 처리된 리소스에 대한 중복 작업 시 발생하는 예외
 * HTTP 409 Conflict
 *
 * 예: 이미 삭제된 게시글을 다시 삭제하려고 할 때
 */
public class DuplicateResourceException extends BusinessException {

    public DuplicateResourceException(String message) {
        super(message, HttpStatus.CONFLICT, "DUPLICATE_RESOURCE");
    }

    // 정적 팩토리 메서드: 이미 삭제된 엔티티 — "이미 삭제된 게시글입니다."
    public static DuplicateResourceException alreadyDeleted(String entityName) {
        return new DuplicateResourceException("이미 삭제된 " + entityName + "입니다.");
    }

    // 정적 팩토리 메서드: 이미 존재하는 리소스 — 커스텀 메시지
    public static DuplicateResourceException alreadyExists(String message) {
        return new DuplicateResourceException(message);
    }
}
