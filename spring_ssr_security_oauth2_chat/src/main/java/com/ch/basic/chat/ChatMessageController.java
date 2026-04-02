package com.ch.basic.chat;

import com.ch.basic.chat.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

/**
 * STOMP 메시지 컨트롤러 — 채팅 메시지 수신 및 브로드캐스트
 *
 * @MessageMapping: STOMP SEND 프레임의 destination과 매핑
 *   - 클라이언트가 /pub/chat/message로 메시지를 보내면 이 메서드가 호출됨
 *   - applicationDestinationPrefixes("/pub") + @MessageMapping("/chat/message")
 *     → 실제 매핑 경로: /pub/chat/message
 *
 * SimpMessageSendingOperations:
 *   - 서버에서 특정 destination으로 메시지를 전송하는 인터페이스
 *   - convertAndSend("/sub/chat/room/{id}", dto)
 *     → /sub/chat/room/{id}를 구독한 모든 클라이언트에게 메시지 브로드캐스트
 *
 * 보안:
 *   - WebSocket 레벨 보안 미적용 (요구사항)
 *   - 채팅방 입장 페이지(GET /chat/rooms/{id})에서 Security가 인증 확인
 *   - sender 닉네임은 클라이언트(Thymeleaf)에서 서버 세션 기반으로 주입
 */
@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final SimpMessageSendingOperations messageSendingOperations;

    /**
     * 채팅 메시지 수신 및 브로드캐스트
     *
     * 클라이언트 → /pub/chat/message (SEND)
     * 서버 → /sub/chat/room/{roomId} (브로드캐스트)
     *
     * 메시지 타입별 처리:
     *   ENTER → "○○님이 입장했습니다" 시스템 메시지
     *   TALK  → 그대로 전달
     *   LEAVE → "○○님이 퇴장했습니다" 시스템 메시지
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessageDTO message) {
        if (ChatMessageDTO.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장했습니다.");
        } else if (ChatMessageDTO.MessageType.LEAVE.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 퇴장했습니다.");
        }

        // 해당 채팅방을 구독한 모든 클라이언트에게 메시지 전송
        messageSendingOperations.convertAndSend(
                "/sub/chat/room/" + message.getRoomId(),
                message
        );
    }
}

