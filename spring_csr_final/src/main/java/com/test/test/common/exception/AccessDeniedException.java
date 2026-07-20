package com.test.test.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 권한이 없을 때 발생하는 예외 (본인만 수정/삭제 가능한 경우)
 * HTTP 403 Forbidden
 */
public class AccessDeniedException extends BusinessException {

    public AccessDeniedException(String message) {
        super(message, HttpStatus.FORBIDDEN, "ACCESS_DENIED");
    }

    public static AccessDeniedException forUpdate(String entityName) {
        return new AccessDeniedException("You can only update your own " + entityName);
    }

    public static AccessDeniedException forDelete(String entityName) {
        return new AccessDeniedException("You can only delete your own " + entityName);
    }
}

