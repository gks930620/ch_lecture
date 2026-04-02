package com.ch.basic.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket + STOMP 설정
 *
 * @EnableWebSocketMessageBroker: STOMP 메시지 브로커 활성화
 *   - 단순 WebSocket이 아니라 STOMP 프로토콜 기반으로 메시지를 라우팅
 *   - 메시지 브로커(SimpleBroker)가 구독자에게 메시지를 전달하는 Pub/Sub 구조
 *
 * 동작 흐름:
 *   1. 클라이언트가 /ws-stomp로 SockJS 연결 (핸드셰이크)
 *   2. STOMP CONNECT 프레임으로 세션 수립
 *   3. /sub/chat/room/{roomId} 구독 (SUBSCRIBE)
 *   4. /pub/chat/message로 메시지 전송 (SEND)
 *   5. 서버가 /sub/chat/room/{roomId}로 브로드캐스트
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 메시지 브로커 설정
     *
     * enableSimpleBroker("/sub"):
     *   - 메모리 기반 메시지 브로커 활성화
     *   - /sub로 시작하는 destination을 구독(SUBSCRIBE)한 클라이언트에게 메시지 전달
     *   - 예: /sub/chat/room/1 구독 → 해당 채팅방 메시지 수신
     *
     * setApplicationDestinationPrefixes("/pub"):
     *   - 클라이언트가 메시지를 보낼 때(SEND) 사용하는 prefix
     *   - /pub로 시작하는 메시지 → @MessageMapping 메서드로 라우팅
     *   - 예: /pub/chat/message 전송 → @MessageMapping("/chat/message") 실행
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");          // 구독 경로 prefix (서버 → 클라이언트)
        config.setApplicationDestinationPrefixes("/pub");  // 발행 경로 prefix (클라이언트 → 서버)
    }

    /**
     * STOMP 엔드포인트 등록
     *
     * addEndpoint("/ws-stomp"):
     *   - WebSocket 핸드셰이크를 수행할 HTTP URL
     *   - 클라이언트는 이 URL로 최초 연결을 시도
     *
     * withSockJS():
     *   - SockJS fallback 활성화
     *   - WebSocket을 지원하지 않는 브라우저에서도 동작 (long-polling 등으로 대체)
     *   - /ws-stomp/info 엔드포인트가 자동 생성되어 SockJS가 전송 방식 결정
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                .withSockJS();  // SockJS fallback 지원
    }
}

