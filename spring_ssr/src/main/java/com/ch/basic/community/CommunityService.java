package com.ch.basic.community;

import com.ch.basic.common.exception.AccessDeniedException;
import com.ch.basic.common.exception.EntityNotFoundException;
import com.ch.basic.community.dto.CommunityCreateDTO;
import com.ch.basic.community.dto.CommunityDTO;
import com.ch.basic.community.dto.CommunityUpdateDTO;
import com.ch.basic.community.repository.CommunityRepository;
import com.ch.basic.common.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 비즈니스 로직 Service
 *
 * @Service: Spring이 이 클래스를 Service Bean으로 등록
 * @Transactional(readOnly = true): 클래스 전체에 읽기 전용 트랜잭션 적용
 *   - 쓰기 메서드는 개별적으로 @Transactional을 붙여서 읽기 전용 해제
 *   - 읽기 전용 트랜잭션의 장점: JPA가 변경 감지(dirty checking)를 안 해서 성능 향상
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {

    private final CommunityRepository communityRepository;

    /**
     * 게시글 등록
     * @Transactional: 쓰기 작업이므로 읽기 전용 해제
     * @return 생성된 게시글 ID (리다이렉트용)
     */
    @Transactional
    public Long createCommunity(CommunityCreateDTO createDTO, Long userId, String username, String nickname) {
        // DTO → Entity 변환 후 DB 저장
        CommunityEntity community = createDTO.toEntity(userId, username, nickname);
        // save() 후 자동 생성된 ID 반환 (@GeneratedValue → DB가 ID 부여)
        return communityRepository.save(community).getId();
    }

    /**
     * 게시글 목록 조회 (검색 + 페이징)
     * 읽기 전용이므로 클래스 레벨 @Transactional(readOnly=true) 적용
     *
     * @return PageResponse<CommunityDTO> — Page<>에서 화면에 필요한 필드만 추출한 응답
     */
    public PageResponse<CommunityDTO> getCommunityList(String searchType, String keyword, Pageable pageable) {
        // QueryDSL 동적 쿼리 (CommunityRepositoryImpl.searchCommunity)
        Page<CommunityDTO> page = communityRepository.searchCommunity(searchType, keyword, pageable);
        return PageResponse.from(page);
    }

    /**
     * 게시글 상세 조회 + 조회수 증가
     * @Transactional: 조회수 증가가 UPDATE이므로 쓰기 트랜잭션 필요
     */
    @Transactional
    public CommunityDTO getCommunityDetail(Long communityId) {
        // 게시글 조회 — 없으면 EntityNotFoundException (404)
        CommunityEntity community = communityRepository.findById(communityId)
            .orElseThrow(() -> EntityNotFoundException.of("게시글", communityId));
        // 조회수 1 증가 (Entity의 비즈니스 메서드)
        // JPA 변경 감지(dirty checking): 트랜잭션 종료 시 변경된 필드를 자동 UPDATE
        community.incrementViewCount();
        return CommunityDTO.from(community);
    }

    /**
     * 게시글 수정
     * 1) 게시글 존재 여부 확인
     * 2) 작성자 본인인지 확인 (권한 체크)
     * 3) Entity의 update() 메서드로 수정 (JPA 변경 감지가 자동 UPDATE)
     */
    @Transactional
    public void updateCommunity(Long communityId, CommunityUpdateDTO updateDTO, String username) {
        CommunityEntity community = communityRepository.findById(communityId)
                .orElseThrow(() -> EntityNotFoundException.of("게시글", communityId));
        // 작성자 본인이 아니면 → AccessDeniedException (403)
        if (username == null || !community.isWrittenBy(username)) {
            throw AccessDeniedException.forUpdate("게시글");
        }
        // JPA 변경 감지: entity 필드를 수정하면 트랜잭션 종료 시 자동 UPDATE 쿼리 실행
        community.update(updateDTO.getTitle(), updateDTO.getContent());
    }

    /**
     * 게시글 삭제 (하드 삭제)
     * DB에서 실제로 삭제 (DELETE)
     */
    @Transactional
    public void deleteCommunity(Long communityId, String username) {
        CommunityEntity community = communityRepository.findById(communityId)
            .orElseThrow(() -> EntityNotFoundException.of("게시글", communityId));
        if (username == null || !community.isWrittenBy(username)) {
            throw AccessDeniedException.forDelete("게시글");
        }
        // 하드 삭제 — DB에서 실제 DELETE
        communityRepository.delete(community);
    }
}
