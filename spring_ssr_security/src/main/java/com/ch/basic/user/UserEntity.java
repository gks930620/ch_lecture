package com.ch.basic.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

/**
 * 사용자 엔티티 — DB의 users 테이블과 매핑
 *
 * @Entity: JPA가 이 클래스를 DB 테이블과 매핑하는 엔티티로 인식
 * @Table(name = "users"): 실제 DB 테이블명 지정 (기본값은 클래스명)
 * @SQLRestriction: Hibernate가 이 엔티티를 조회할 때 자동으로 WHERE 조건 추가
 *         → findById, findByUsername 등 모든 JPA 조회에 is_deleted=false 조건이 붙음
 *         → 삭제된 사용자는 JPA를 통해 조회할 수 없음 (복구 시 직접 SQL 사용)
 *
 * @Getter: 모든 필드의 getter 자동 생성 (Lombok)
 * @NoArgsConstructor: 기본 생성자 자동 생성 (JPA 필수 — 리플렉션으로 객체 생성하기 때문)
 * @AllArgsConstructor: 모든 필드를 받는 생성자 자동 생성
 * @Builder: 빌더 패턴으로 객체 생성 가능 (UserEntity.builder().username("admin").build())
 */
@Entity
@Table(name = "users")
@SQLRestriction("is_deleted = false")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    // @Id: 이 필드가 테이블의 Primary Key(PK)
    // @GeneratedValue(IDENTITY): AUTO_INCREMENT — DB가 자동으로 ID 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // unique = true: 같은 username으로 중복 가입 불가 (DB 레벨 제약조건)
    // nullable = false: NULL 허용 안 함
    @Column(unique = true, nullable = false)
    private String username;  // 로그인 ID

    @Column(nullable = false)
    private String password;  // 비밀번호 (BCrypt 해시값 저장)

    private String email;     // 이메일 (선택 입력)

    private String nickname;  // 화면에 표시되는 닉네임

    // 사용자 권한 — 쉼표(,) 구분 문자열 (예: "USER", "ADMIN,USER")
    // Security의 getAuthorities()는 Collection<GrantedAuthority>를 반환하므로
    // DB에서는 쉼표 구분 문자열로 저장하고, DTO에서 List로 변환
    // 권한 종류가 몇개 없고 자주 변경되지않는다면 1정규화 무시해도 잘 동작함.
    //  나중에  특정권한 가진 사람들 검색 등을 하게 된다면  권한 테이블 따로 만들어야함.
    // 어쨋든 현재 DB에 컬럼은 하나니까 String으로.   DTO에는 split "," 으로 바로 처리가능
    @Column(nullable = false)
    @Builder.Default
    private String roles = "USER";

    // 소프트 삭제 여부 — 복구 가능하도록 실제 DB row는 유지
    // @SQLRestriction 어노테이션으로 조회 시 자동 필터링되므로 Repository에서 별도 조건 불필요
    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    /** 비밀번호 변경 — BCrypt 인코딩된 비밀번호로 업데이트 */
    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    /** 소프트 삭제 — isDeleted를 true로 변경 */
    public void softDelete() {
        this.isDeleted = true;
    }
}
