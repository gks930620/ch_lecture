package com.ch.basic.community.repository;

import com.ch.basic.community.dto.CommunityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * QueryDSL 동적 쿼리용 커스텀 Repository 인터페이스
 *
 * Spring Data JPA의 쿼리 메서드로는 복잡한 검색(동적 where절)이 어려우므로
 * QueryDSL로 직접 구현하기 위한 인터페이스
 *
 * 구현체: CommunityRepositoryImpl
 * CommunityRepository가 이 인터페이스를 상속받으면 자동으로 구현체가 연결됨
 */
public interface CommunityRepositoryCustom {

    /**
     * 커뮤니티 목록 조회 / 검색 (QueryDSL 동적 쿼리)
     *
     * @param searchType "title" 또는 "nickname" (null이면 전체 조회)
     * @param keyword    검색 키워드 (null이면 전체 조회)
     * @param pageable   페이징 정보 (몇 페이지, 몇 건, 정렬 등)
     * @return 게시글 목록 (페이징 적용, 최신순 정렬, 댓글 수 포함)
     */
    Page<CommunityDTO> searchCommunity(String searchType, String keyword, Pageable pageable);
}
