package com.ch.basic.community.comment.repository;

import com.ch.basic.community.comment.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 댓글 Repository
 *
 * JpaRepository 기본 CRUD + 커스텀 JPQL 쿼리
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    /**
     * 특정 게시글의 댓글 목록 조회 (페이징)
     *
     * @Query: JPQL (Java Persistence Query Language) — 엔티티 기반 쿼리
     *   - SQL과 비슷하지만 테이블명 대신 엔티티명(CommentEntity), 컬럼명 대신 필드명 사용
     *   - countQuery: 페이징을 위한 전체 개수 조회 쿼리 (별도 지정)
     *
     * @Param: JPQL의 :communityId와 메서드 파라미터를 바인딩
     */
    @Query(value = "SELECT c FROM CommentEntity c " +
           "WHERE c.community.id = :communityId " +
           "ORDER BY c.createdAt DESC",
           countQuery = "SELECT count(c) FROM CommentEntity c WHERE c.community.id = :communityId")
    Page<CommentEntity> findByCommunityId(@Param("communityId") Long communityId, Pageable pageable);

    /**
     * 특정 게시글에 속한 댓글들을 DB에서 완전 삭제 (게시글 삭제 시 사용)
     * 부모 게시글 삭제 시 해당 댓글도 함께 삭제
     */
    @Modifying
    @Query("DELETE FROM CommentEntity c WHERE c.community.id IN :communityIds")
    int deleteByCommunityIds(@Param("communityIds") List<Long> communityIds);
}
