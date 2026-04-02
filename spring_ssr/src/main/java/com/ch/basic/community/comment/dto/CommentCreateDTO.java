package com.ch.basic.community.comment.dto;

import com.ch.basic.community.CommunityEntity;
import com.ch.basic.community.comment.CommentEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 댓글 작성 요청 DTO
 * JavaScript fetch에서 JSON body로 전달받음
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateDTO {

    @NotNull(message = "게시글 ID는 필수입니다")
    private Long communityId;  // 어떤 게시글에 달리는 댓글인지

    @NotBlank(message = "댓글 내용은 필수입니다")
    private String content;

    /**
     * DTO → Entity 변환
     * @param community 게시글 엔티티 (FK 관계 설정을 위해 엔티티 객체 필요)
     * @param username  작성자 로그인 ID
     * @param nickname  작성자 닉네임
     */
    public CommentEntity toEntity(CommunityEntity community, Long userId, String username, String nickname) {
        return CommentEntity.builder()
                .community(community)  // @ManyToOne 관계 설정 (FK: community_id)
                .userId(userId)
                .username(username)
                .nickname(nickname)
                .content(this.content)
                .build();
    }
}
