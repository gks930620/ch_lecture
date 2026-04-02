package com.ch.basic.chat;

import com.ch.basic.chat.dto.ChatRoomCreateRequest;
import com.ch.basic.chat.dto.ChatRoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 채팅방 서비스 — 비즈니스 로직 담당
 *
 * 프로젝트 원칙:
 *   - Service는 Entity ↔ DTO 변환 담당
 *   - request, response 등 웹 관련 내용 사용 금지
 *   - Controller에서 필요한 파라미터(닉네임 등)를 단순 값으로 전달받음
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅방 생성
     *
     * @param request          방 이름 등 생성 요청 DTO
     * @param creatorNickname  생성자 닉네임 (Controller에서 인증 정보로부터 추출하여 전달)
     * @return 생성된 채팅방 응답 DTO
     */
    @Transactional
    public ChatRoomResponse createRoom(ChatRoomCreateRequest request, String creatorNickname) {
        ChatRoomEntity entity = request.toEntity(creatorNickname);
        ChatRoomEntity saved = chatRoomRepository.save(entity);
        return ChatRoomResponse.from(saved);
    }

    /**
     * 전체 채팅방 목록 조회 (최신순)
     */
    public List<ChatRoomResponse> getAllRooms() {
        return chatRoomRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(ChatRoomResponse::from)
                .toList();
    }

    /**
     * 채팅방 단건 조회
     *
     * @param roomId 채팅방 ID
     * @return 채팅방 응답 DTO
     * @throws IllegalArgumentException 존재하지 않는 채팅방
     */
    public ChatRoomResponse getRoomById(Long roomId) {
        ChatRoomEntity entity = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다. id=" + roomId));
        return ChatRoomResponse.from(entity);
    }
}

