package com.ch.basic.community.repository;

import com.ch.basic.community.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 커뮤니티 Repository — JPA 기본 CRUD + QueryDSL 커스텀 쿼리
 *
 * JpaRepository: 기본 CRUD (save, findById, findAll, delete 등)
 * CommunityRepositoryCustom: QueryDSL 동적 검색 쿼리 (searchCommunity)
 *
 * 두 인터페이스를 동시에 상속 → Spring이 자동으로 구현체 조합
 *
 * ※ 게시글은 하드 삭제 방식이므로 isDeleted 조건 불필요
 *   → JPA 기본 findById()만으로 충분
 */
@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Long>, CommunityRepositoryCustom {
}
