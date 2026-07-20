package com.test.test.jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;  // Oauth2Provider 이름.
    @Column(unique = true)
    private String username;

    private String password;

    private String email;
    private String nickname;

    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

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

    // 권한 목록.
    // ⚠️ @ElementCollection/@Convert 없이 List<String>을 두면 JPA가 이 필드를 자바 직렬화(BLOB)로 저장한다.
    //    (DB에는 0xACED... 바이너리로 들어가 SQL로 직접 조회/수정이 어렵다)
    // 강의용으로 단순하게 두었으며, DB에서 사람이 읽고 쿼리하고 싶으면 아래처럼 별도 테이블 매핑을 쓰면 된다:
    //    @ElementCollection(fetch = FetchType.EAGER)
    //    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    //    @Column(name = "role")
    //  (단, 이 경우 data-users.sql의 roles 시드 데이터도 함께 바꿔야 한다)
    @Builder.Default
    private List<String> roles=new ArrayList<>();
}