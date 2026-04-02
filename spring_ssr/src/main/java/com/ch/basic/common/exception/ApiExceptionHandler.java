package com.ch.basic.common.exception;

import com.ch.basic.common.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * REST API 전역 예외 처리 핸들러
 *
 * @RestControllerAdvice: @RestController에서 발생하는 예외를 잡아서 JSON으로 응답
 *   - SSR용 GlobalExceptionHandler(@ControllerAdvice)와 역할 분리
 *   - GlobalExceptionHandler → error.html 렌더링 (브라우저 화면 전환)
 *   - ApiExceptionHandler    → JSON ErrorResponse 반환 (fetch/axios 콜백에서 처리)
 *
 * annotations 속성으로 @RestController가 붙은 Controller에서만 동작하도록 제한
 */
@RestControllerAdvice(annotations = org.springframework.web.bind.annotation.RestController.class)
public class ApiExceptionHandler {

    /**
     * 404 - 리소스를 찾을 수 없음
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * 403 - 접근 거부 (권한 없음)
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(), e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * 400 - 비즈니스 규칙 위반
     * BusinessException 하위 중 위에서 먼저 매칭되지 않은 것들
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException e) {
        ErrorResponse response = new ErrorResponse(
                e.getStatus().value(), e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    /**
     * 500 - 예상치 못한 예외 (최후의 방어선)
     * 사용자에게 구체적 에러 내용을 노출하지 않음
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_ERROR", "서버 내부 오류가 발생했습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

