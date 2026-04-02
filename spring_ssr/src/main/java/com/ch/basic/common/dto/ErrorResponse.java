package com.ch.basic.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * REST API 에러 응답 DTO — 모든 API 에러에서 일관된 JSON 구조로 응답
 *
 * fetch/axios에서 에러 시 항상 같은 형태로 파싱할 수 있도록 통일
 * { "status": 404, "errorCode": "NOT_FOUND", "message": "게시글을(를) 찾을 수 없습니다: 99" }
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final int status;        // HTTP 상태 코드 (404, 403, 400 등)
    private final String errorCode;  // 에러 코드 문자열 (클라이언트 분기용)
    private final String message;    // 사용자에게 보여줄 에러 메시지
}

