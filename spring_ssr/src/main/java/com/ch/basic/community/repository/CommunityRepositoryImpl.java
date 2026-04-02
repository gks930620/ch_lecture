package com.ch.basic.community.repository;

import com.ch.basic.community.CommunityEntity;
import com.ch.basic.community.QCommunityEntity;
import com.ch.basic.community.comment.QCommentEntity;
import com.ch.basic.community.dto.CommunityDTO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CommunityRepositoryCustom의 QueryDSL 구현체
 *
 * ※ 클래스명 규칙: "인터페이스명 + Impl" → Spring Data JPA가 자동 인식하여 연결
 *   CommunityRepositoryCustom → CommunityRepositoryImpl (자동 매칭)
 *
 * @RequiredArgsConstructor: JPAQueryFactory를 생성자 주입 (QuerydslConfig에서 Bean 등록한 것)
 */
@Repository
@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryCustom {

    // QueryDSL 쿼리를 만들기 위한 팩토리 (QuerydslConfig에서 Bean 등록)
    private final JPAQueryFactory queryFactory;

    /**
     * 게시글 목록 검색 (QueryDSL 동적 쿼리)
     *
     * 1) 전체 개수 조회 (페이징 totalCount용)
     * 2) 현재 페이지의 게시글 목록 조회
     * 3) 해당 게시글들의 댓글 수를 한번에 조회 (N+1 문제 방지)
     * 4) Entity → DTO 변환 + 댓글 수 설정
     */
    @Override
    public Page<CommunityDTO> searchCommunity(String searchType, String keyword, Pageable pageable) {
        // Q클래스: QueryDSL이 Entity 기반으로 자동 생성한 쿼리용 클래스 (build 시 생성)
        QCommunityEntity community = QCommunityEntity.communityEntity;
        QCommentEntity comment = QCommentEntity.commentEntity;

        // ===== 1) 전체 개수 조회 (SELECT COUNT) =====
        // 검색 조건에 맞는 게시글의 총 개수
        Long total = queryFactory
                .select(community.count())
                .from(community)
                .where(
                        searchCondition(community, searchType, keyword)   // 동적 검색 조건 (null이면 무시됨)
                )
                .fetchOne(); // 단건 결과 반환

        if (total == null) total = 0L;

        // ===== 2) 현재 페이지의 게시글 목록 조회 =====
        List<CommunityEntity> entities = queryFactory
                .selectFrom(community)
                .where(
                        searchCondition(community, searchType, keyword)
                )
                .orderBy(community.createdAt.desc())    // 최신순 정렬
                .offset(pageable.getOffset())            // 시작 위치 (예: 2페이지면 offset=10)
                .limit(pageable.getPageSize())           // 가져올 개수 (예: 10건)
                .fetch();                                // 결과 리스트 반환

        // 조회된 게시글의 ID 리스트 추출 (댓글 수 조회에 사용)
        List<Long> communityIds = entities.stream().map(CommunityEntity::getId).toList();

        // ===== 3) 댓글 수 한번에 조회 (N+1 방지) =====
        // 게시글 N개에 대해 각각 댓글 수를 조회하면 N+1 쿼리 발생
        // → IN 쿼리로 한번에 조회하여 Map<게시글ID, 댓글수>로 변환
        Map<Long, Long> commentCountMap;
        if (communityIds.isEmpty()) {
            commentCountMap = Collections.emptyMap();
        } else {
            // SELECT community_id, COUNT(*) FROM comment WHERE community_id IN (...) GROUP BY community_id
            List<Tuple> counts = queryFactory
                    .select(comment.community.id, comment.count())
                    .from(comment)
                    .where(
                            comment.community.id.in(communityIds)   // IN 쿼리
                    )
                    .groupBy(comment.community.id)  // 게시글별 그룹핑
                    .fetch();

            // Tuple 리스트 → Map<게시글ID, 댓글수> 변환
            commentCountMap = counts.stream().collect(Collectors.toMap(
                    tuple -> tuple.get(comment.community.id),  // key: 게시글 ID
                    tuple -> { Long cnt = tuple.get(comment.count()); return cnt != null ? cnt : 0L; }  // value: 댓글 수
            ));
        }

        // ===== 4) Entity → DTO 변환 + 댓글 수 설정 =====
        List<CommunityDTO> content = entities.stream().map(entity -> {
            CommunityDTO dto = CommunityDTO.from(entity);  // Entity → DTO 변환
            dto.setCommentCount(commentCountMap.getOrDefault(entity.getId(), 0L));  // 댓글 수 설정 (없으면 0)
            return dto;
        }).toList();

        // PageImpl: Spring의 Page 인터페이스 구현체 (데이터 + 페이징 정보 + 전체 개수)
        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 동적 검색 조건 생성
     *
     * QueryDSL의 BooleanExpression: where절에 들어갈 조건
     * null을 반환하면 → where절에서 자동으로 무시됨 (전체 조회)
     *
     * @return 검색 조건 (null이면 조건 없음 = 전체 조회)
     */
    private BooleanExpression searchCondition(QCommunityEntity community, String searchType, String keyword) {
        // 키워드가 없으면 → 전체 조회 (조건 없음)
        if (keyword == null || keyword.trim().isEmpty()) return null;
        String k = keyword.trim();
        // searchType에 따라 다른 조건 생성
        return switch (searchType != null ? searchType : "") {
            case "title" -> community.title.containsIgnoreCase(k);      // 제목 검색 (대소문자 무시)
            case "nickname" -> community.nickname.containsIgnoreCase(k); // 닉네임 검색
            default -> null; // searchType이 없거나 잘못된 값이면 전체 조회
        };
    }
}
