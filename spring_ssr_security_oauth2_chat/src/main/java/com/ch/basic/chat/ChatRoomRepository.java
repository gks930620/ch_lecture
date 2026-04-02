package com.ch.basic.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 채팅방 Repository
 *
 * JpaRepository 상속으로 기본 CRUD 메서드 자동 제공 (save, findById, findAll, delete 등)
 * Spring Data JPA가 인터페이스를 보고 구현체를 자동 생성 → @Repository 불필요
 */
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    /**
     * 채팅방 목록 조회 — 최신순 정렬
     * Spring Data JPA 쿼리 메서드: 메서드 이름으로 쿼리 자동 생성
     *   findAll + OrderBy + CreatedAt + Desc
     *   → SELECT * FROM chat_rooms ORDER BY created_at DESC
     */
    List<ChatRoomEntity> findAllByOrderByCreatedAtDesc();
}

