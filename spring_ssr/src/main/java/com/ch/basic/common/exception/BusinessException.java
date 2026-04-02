package com.ch.basic.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 비즈니스 예외의 최상위 추상 클래스
 *
 * 프로젝트에서 발생하는 모든 커스텀 예외는 이 클래스를 상속받음
 *   - EntityNotFoundException (404)
 *   - AccessDeniedException (403)
 *   - BusinessRuleException (400)
 *   - DuplicateResourceException (409)
 *
 * RuntimeException을 상속:
 *   - Checked Exception이 아니므로 throws 선언 불필요
 *   - Spring의 @Transactional에서 기본적으로 롤백 대상
 *
 * @Getter: Lombok — status, errorCode 필드의 getter 자동 생성
 */
@Getter
public abstract class BusinessException extends RuntimeException {

    // HTTP 상태 코드 (404, 403, 400 등)
    private final HttpStatus status;

    // 에러 코드 문자열 (예: "NOT_FOUND", "ACCESS_DENIED" — 클라이언트에서 분기용)
    private final String errorCode;

    /**
     * @param message   사용자에게 보여줄 에러 메시지 (예: "게시글을 찾을 수 없습니다: 99")
     * @param status    HTTP 상태 코드
     * @param errorCode 에러 코드 문자열
     */
    protected BusinessException(String message, HttpStatus status, String errorCode) {
        super(message); // RuntimeException의 생성자에 메시지 전달
        this.status = status;
        this.errorCode = errorCode;
    }
}
