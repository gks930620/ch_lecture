package com.ch.basic.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 권한이 없을 때 발생하는 예외 (본인만 수정/삭제 가능한 경우)
 * HTTP 403 Forbidden
 *
 * 예: 다른 사람의 게시글을 수정/삭제하려고 할 때
 *
 * ※ Spring Security 적용 후에는 Security가 제공하는 AccessDeniedException을 사용하게 됨
 *    현재는 Security 미적용 상태이므로 커스텀 예외로 처리
 */
public class AccessDeniedException extends BusinessException {

    public AccessDeniedException(String message) {
        super(message, HttpStatus.FORBIDDEN, "ACCESS_DENIED");
    }

    // 정적 팩토리 메서드: 수정 권한 없을 때 — "본인의 게시글만 수정할 수 있습니다." 형태
    public static AccessDeniedException forUpdate(String entityName) {
        return new AccessDeniedException("본인의 " + entityName + "만 수정할 수 있습니다.");
    }

    // 정적 팩토리 메서드: 삭제 권한 없을 때 — "본인의 게시글만 삭제할 수 있습니다." 형태
    public static AccessDeniedException forDelete(String entityName) {
        return new AccessDeniedException("본인의 " + entityName + "만 삭제할 수 있습니다.");
    }
}
