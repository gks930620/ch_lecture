package com.ch.basic.chat.dto;

import lombok.*;

/**
 * 채팅 메시지 DTO (STOMP 메시지 전송/수신용)
 *
 * DB에 저장하지 않음 — STOMP를 통해 실시간으로만 교환됨.
 * 클라이언트 ↔ 서버 간 JSON으로 직렬화/역직렬화.
 *
 * type별 동작:
 *   - ENTER: "○○님이 입장했습니다" 메시지로 변환 후 브로드캐스트
 *   - TALK:  sender + message 그대로 브로드캐스트
 *   - LEAVE: "○○님이 퇴장했습니다" 메시지로 변환 후 브로드캐스트
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDTO {

    /**
     * 메시지 타입
     *   ENTER — 채팅방 입장 알림
     *   TALK  — 일반 채팅 메시지
     *   LEAVE — 채팅방 퇴장 알림
     */
    public enum MessageType {
        ENTER, TALK, LEAVE
    }

    private MessageType type;   // 메시지 타입
    private Long roomId;        // 채팅방 ID
    private String sender;      // 발신자 닉네임
    private String message;     // 메시지 내용
}

