package com.ch.basic.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * SSR 전역 예외 처리 핸들러 — error.html 렌더링
 *
 * @ControllerAdvice(annotations = Controller.class):
 *   @Controller에서 발생하는 예외만 잡음 (SSR 전용)
 *   @RestController에서 발생하는 예외는 ApiExceptionHandler가 JSON으로 처리
 */
@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {

    /**
     * 404 - 리소스를 찾을 수 없음
     * EntityNotFoundException이 발생하면 이 메서드가 실행됨
     *
     * @ResponseStatus(NOT_FOUND): HTTP 응답 코드를 404로 설정
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(EntityNotFoundException e, Model model) {
        model.addAttribute("status", 404);       // error.html에서 사용할 상태 코드
        model.addAttribute("message", e.getMessage()); // error.html에서 사용할 에러 메시지
        return "error"; // templates/error.html 렌더링
    }

    /**
     * 403 - 접근 거부 (권한 없음)
     * 예: 다른 사람의 글을 수정/삭제하려 할 때
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException e, Model model) {
        model.addAttribute("status", 403);
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    /**
     * 400 - 비즈니스 규칙 위반
     * BusinessException의 하위 예외 중 위에서 먼저 매칭되지 않은 것들이 여기로 옴
     * (예외 매칭은 구체적인 것부터 먼저 매칭됨)
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBusiness(BusinessException e, Model model) {
        model.addAttribute("status", 400);
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    /**
     * 500 - 예상치 못한 예외 (최후의 방어선)
     * 위의 어떤 핸들러에도 매칭되지 않는 예외가 여기로 옴
     * 사용자에게는 구체적인 에러 내용을 보여주지 않음 (보안상)
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e, Model model) {
        model.addAttribute("status", 500);
        model.addAttribute("message", "서버 내부 오류가 발생했습니다.");
        return "error";
    }
}
