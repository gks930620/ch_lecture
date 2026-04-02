package com.ch.basic.community.dto;

import com.ch.basic.community.CommunityEntity;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 게시글 응답 DTO — 화면(Thymeleaf)에 데이터를 전달할 때 사용
 *
 * Entity → DTO 변환: CommunityDTO.from(entity) (정적 팩토리 메서드)
 *
 * @Setter: 목록 조회 시 commentCount를 나중에 설정해야 하므로 Setter 필요
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityDTO {
    private Long id;
    private Long userId;
    private String username;           // 작성자 로그인 ID (권한 체크용)
    private String nickname;           // 작성자 닉네임 (화면 표시용)
    private String title;
    private String content;            // 본문 (HTML — Quill 에디터 내용)
    private Integer viewCount;
    private Long commentCount;         // 댓글 수 (Repository에서 별도 조회 후 설정)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Entity → DTO 변환
     *
     * @param entity 게시글 엔티티
     */
    public static CommunityDTO from(CommunityEntity entity) {
        if (entity == null) {
            return null;
        }
        return CommunityDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .nickname(entity.getNickname())
                .title(entity.getTitle())
                .content(entity.getContent())
                .viewCount(entity.getViewCount())
                .commentCount(0L)  // 기본값 0, 실제 댓글 수는 Repository에서 별도 조회 후 setter로 설정
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
