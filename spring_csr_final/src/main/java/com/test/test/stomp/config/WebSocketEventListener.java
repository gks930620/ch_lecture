package com.test.test.stomp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    // 연결됐을 때 한번 실행
    // 순서: JwtChannelInterceptor → 실제 연결 → 여기
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        Object sessionUser = accessor.getSessionAttributes().get("user");
        Authentication auth = (Authentication) sessionUser;

        Object sessionRoomId = accessor.getSessionAttributes().get("roomId");
        String roomId = (String) sessionRoomId;

        if (auth != null && roomId != null) {
            String username = auth.getName();
            // 이 방송 문구는 채팅 화면에 그대로 노출되는 UI 텍스트라 (한국어 UI에 맞춰) 한국어로 유지한다.
            // 참고: API 응답 message는 영어로 통일되어 있으나, 화면 표시용 문구는 별개다.
            messagingTemplate.convertAndSend("/sub/room/" + roomId, username + "님이 입장했습니다.");
        }
    }

    // 연결 해제(퇴장) — 뒤로가기, 브라우저 종료 등 모두 감지
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Authentication auth = (Authentication) accessor.getSessionAttributes().get("user");
        String roomId = (String) accessor.getSessionAttributes().get("roomId");

        if (auth != null && roomId != null) {
            String username = auth.getName();
            messagingTemplate.convertAndSend("/sub/room/" + roomId, username + "님이 퇴장했습니다.");
        }
    }
}

