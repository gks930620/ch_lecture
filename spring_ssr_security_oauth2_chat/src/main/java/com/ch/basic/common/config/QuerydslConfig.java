package com.ch.basic.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueryDSL 설정 클래스
 *
 * QueryDSL: JPQL을 Java 코드로 type-safe하게 작성할 수 있게 해주는 라이브러리
 * JPAQueryFactory를 Bean으로 등록해야 Repository에서 주입받아 사용 가능
 *
 * ※ 이 프로젝트(로그인+채팅)에서는 실제로 QueryDSL을 쓰는 코드가 없다.
 *    채팅방 조회는 파생 쿼리(ChatRoomRepository.findAllByOrderByCreatedAtDesc)만 사용.
 *    복잡한 동적 쿼리가 필요한 프로젝트에서 바로 재사용할 수 있도록 남겨둔 공통 템플릿 설정이다.
 *
 * @Configuration: 이 클래스가 Spring 설정 파일임을 선언 (@Bean 메서드를 포함)
 */
@Configuration
public class QuerydslConfig {

    // @PersistenceContext: JPA의 EntityManager를 주입받는 어노테이션
    // EntityManager: JPA의 핵심 인터페이스 — DB와의 CRUD 작업을 관리
    @PersistenceContext
    private EntityManager entityManager;

    // @Bean: 이 메서드의 반환 객체를 Spring 컨테이너에 Bean으로 등록
    // 다른 클래스에서 @RequiredArgsConstructor 등으로 JPAQueryFactory를 주입받아 사용 가능
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        // EntityManager를 넘겨서 JPAQueryFactory 생성
        // 이 팩토리를 통해 QueryDSL 쿼리를 작성할 수 있음
        return new JPAQueryFactory(entityManager);
    }
}
