package com.ch.basic.community;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 커뮤니티 게시글 엔티티 — DB의 community 테이블과 매핑
 *
 * Entity에는 DB 매핑 + 비즈니스 편의 메서드만 둠
 * (화면 표시용 데이터는 DTO에서 처리)
 */
@Entity
@Table(name = "community")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자 ID (users 테이블의 PK) — FK 관계 없이 값만 저장 (느슨한 연결)
    @Column(name = "user_id")
    private Long userId;

    // 작성자 로그인 ID — 수정/삭제 시 권한 체크에 사용
    @Column(name = "username")
    private String username;

    // 작성자 닉네임 — 화면에 표시용 (비정규화: users 테이블에서 가져와서 저장)
    @Column(name = "nickname")
    private String nickname;

    // 게시글 제목 — 최대 200자, 필수 입력
    @Column(nullable = false, length = 200)
    private String title;

    // 게시글 본문 — TEXT 타입 (길이 제한 없음), Quill 에디터의 HTML 내용 저장
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 조회수 — 기본값 0, 상세 조회 시 +1 증가
    @Column(nullable = false)
    @Builder.Default  // Builder로 생성할 때 기본값 적용되도록 함
    private Integer viewCount = 0;


    // 생성일시 — updatable = false: 한번 저장되면 수정 불가 (INSERT 시에만 값 설정)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 수정일시 — 글 수정 시 자동 업데이트
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * @PrePersist: JPA가 INSERT 하기 직전에 자동 실행
     * 생성일시와 수정일시를 현재 시간으로 설정
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * @PreUpdate: JPA가 UPDATE 하기 직전에 자동 실행
     * 수정일시만 현재 시간으로 갱신
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ===== 비즈니스 편의 메서드 =====
    // Entity 내부에서 상태를 변경하는 메서드들
    // Service에서 entity.update() 형태로 호출

    /** 조회수 1 증가 */
    public void incrementViewCount() {
        this.viewCount++;
    }

    /** 게시글 수정 — 제목과 본문만 변경 (updatedAt은 @PreUpdate가 자동 처리) */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


    /**
     * 작성자 확인 — 수정/삭제 권한 체크 시 사용
     * @param username 현재 로그인한 사용자의 username
     * @return 작성자 본인이면 true
     */
    public boolean isWrittenBy(String username) {
        return this.username != null && this.username.equals(username);
    }
}
