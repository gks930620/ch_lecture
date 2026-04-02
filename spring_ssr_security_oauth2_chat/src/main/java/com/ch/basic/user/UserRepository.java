package com.ch.basic.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 사용자 Repository — DB의 users 테이블에 대한 CRUD 처리
 *
 * JpaRepository<UserEntity, Long>을 상속받으면:
 *   - save(), findById(), findAll(), delete() 등 기본 CRUD 메서드 자동 제공
 *   - 첫 번째 제네릭: 엔티티 타입 (UserEntity)
 *   - 두 번째 제네릭: PK 타입 (Long)
 *
 * @Repository: Spring이 이 인터페이스를 Repository Bean으로 등록
 *              (JpaRepository 상속 시 생략해도 되지만 명시적으로 선언)
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * username(로그인 ID)으로 사용자 조회
     *
     * Spring Data JPA의 "쿼리 메서드" 기능:
     *   메서드 이름 규칙(findBy + 필드명)으로 SQL을 자동 생성
     *   → SELECT * FROM users WHERE username = ?
     *
     * Optional: 결과가 없을 수 있으므로 null 대신 Optional로 감싸서 반환
     *   → 사용 시: .orElse(null), .orElseThrow(), .isPresent() 등
     */
    Optional<UserEntity> findByUsername(String username);
}
