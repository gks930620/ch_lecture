package com.ch.basic.chat;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 채팅방 엔티티 — DB의 chat_rooms 테이블과 매핑
 *
 * 채팅 메시지는 DB에 저장하지 않고 STOMP로 실시간 전송만 함.
 * 채팅방 정보(이름, 생성자)만 DB에 영속화하여 목록 조회에 사용.
 */
@Entity
@Table(name = "chat_rooms")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomName;           // 채팅방 이름

    @Column(nullable = false)
    private String creatorNickname;    // 생성자 닉네임

    @Column(nullable = false)
    private LocalDateTime createdAt;   // 생성 시각

    /**
     * 엔티티 저장 전 자동으로 생성 시각 세팅
     * JPA가 INSERT 실행 직전에 호출
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

