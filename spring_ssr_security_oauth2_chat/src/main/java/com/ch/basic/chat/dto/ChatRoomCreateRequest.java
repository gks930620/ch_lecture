package com.ch.basic.chat.dto;

import com.ch.basic.chat.ChatRoomEntity;
import lombok.*;

/**
 * 채팅방 생성 요청 DTO
 *
 * 클라이언트(폼)에서 전달하는 데이터: 방 이름만.
 * 생성자 닉네임은 Controller에서 @AuthenticationPrincipal로 주입받아 Service에 전달.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomCreateRequest {

    private String roomName;  // 생성할 채팅방 이름

    /**
     * DTO → Entity 변환
     * 프로젝트 원칙: 변환 메서드는 DTO 측에서 담당
     *
     * @param creatorNickname 생성자 닉네임 (Controller에서 전달)
     */
    public ChatRoomEntity toEntity(String creatorNickname) {
        return ChatRoomEntity.builder()
                .roomName(this.roomName)
                .creatorNickname(creatorNickname)
                .build();
    }
}

