package com.ch.basic.chat.dto;

import com.ch.basic.chat.ChatRoomEntity;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 채팅방 응답 DTO
 *
 * 채팅방 목록, 채팅방 상세 정보를 클라이언트에 전달할 때 사용.
 * Entity를 직접 노출하지 않고 DTO로 변환하여 전달 (프로젝트 원칙).
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomResponse {

    private Long id;                   // 채팅방 ID
    private String roomName;           // 채팅방 이름
    private String creatorNickname;    // 생성자 닉네임
    private LocalDateTime createdAt;   // 생성 시각

    /**
     * Entity → DTO 변환 메서드
     * 프로젝트 원칙: 변환 메서드는 DTO 측에서 담당
     */
    public static ChatRoomResponse from(ChatRoomEntity entity) {
        return ChatRoomResponse.builder()
                .id(entity.getId())
                .roomName(entity.getRoomName())
                .creatorNickname(entity.getCreatorNickname())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

