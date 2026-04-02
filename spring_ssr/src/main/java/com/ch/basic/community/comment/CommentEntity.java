package com.ch.basic.community.comment;

import com.ch.basic.community.CommunityEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 댓글 엔티티 — DB의 comment 테이블과 매핑
 *
 * @ManyToOne: 댓글 N : 게시글 1 관계 (FK: community_id)
 */
@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글과의 관계 (FK: community_id)
    // @ManyToOne: N:1 관계 — 댓글 여러 개가 하나의 게시글에 속함
    // fetch = LAZY: 댓글 조회 시 게시글은 바로 안 가져옴 (실제 접근할 때 조회 = 지연 로딩)
    // @JoinColumn: FK 컬럼명 지정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    private CommunityEntity community;

    // 작성자 ID — FK 없이 값만 저장
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;  // 작성자 로그인 ID (권한 체크용)

    @Column(name = "nickname")
    private String nickname;  // 작성자 닉네임 (화면 표시용)

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;   // 댓글 내용


    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ===== 비즈니스 편의 메서드 =====

    /** 댓글 내용 수정 */
    public void update(String content) {
        this.content = content;
    }


    /** 작성자 본인 확인 */
    public boolean isWrittenBy(String username) {
        return this.username != null && this.username.equals(username);
    }
}
