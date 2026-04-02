package com.ch.basic.community.comment;

import com.ch.basic.common.exception.AccessDeniedException;
import com.ch.basic.common.exception.EntityNotFoundException;
import com.ch.basic.community.CommunityEntity;
import com.ch.basic.community.comment.dto.CommentCreateDTO;
import com.ch.basic.community.comment.dto.CommentDTO;
import com.ch.basic.community.comment.dto.CommentUpdateDTO;
import com.ch.basic.community.comment.repository.CommentRepository;
import com.ch.basic.community.repository.CommunityRepository;
import com.ch.basic.common.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 비즈니스 로직 Service
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;  // 댓글 작성 시 게시글 존재 확인용

    /**
     * 댓글 목록 조회 (특정 게시글의 댓글, 페이징)
     * @return PageResponse<CommentDTO> — Page<>에서 화면에 필요한 필드만 추출한 응답
     */
    public PageResponse<CommentDTO> getCommentsByCommunityId(Long communityId, Pageable pageable) {
        // Repository에서 Page<CommentEntity>로 조회 → .map()으로 DTO 변환 → PageResponse로 변환
        Page<CommentDTO> page = commentRepository.findByCommunityId(communityId, pageable)
                .map(CommentDTO::from);  // 메서드 참조: entity → CommentDTO.from(entity)
        return PageResponse.from(page);
    }

    /**
     * 댓글 작성
     * 1) 부모 게시글 존재 확인
     * 2) DTO → Entity 변환 (게시글 엔티티를 FK로 연결)
     * 3) DB 저장 → DTO 반환
     */
    @Transactional
    public CommentDTO createComment(CommentCreateDTO createDTO, Long userId, String username, String nickname) {
        // 부모 게시글 조회 — 없으면 404 (댓글을 달 게시글이 없으므로)
        CommunityEntity community = communityRepository.findById(createDTO.getCommunityId())
                .orElseThrow(() -> EntityNotFoundException.of("게시글", createDTO.getCommunityId()));
        // DTO → Entity 변환 (community 엔티티를 FK로 연결)
        CommentEntity comment = createDTO.toEntity(community, userId, username, nickname);
        // DB 저장 후 저장된 Entity → DTO 변환하여 반환
        return CommentDTO.from(commentRepository.save(comment));
    }

    /**
     * 댓글 수정
     * 1) 댓글 존재 확인
     * 2) 작성자 본인 확인
     * 3) Entity.update() → JPA 변경 감지 → 자동 UPDATE
     */
    @Transactional
    public CommentDTO updateComment(Long commentId, CommentUpdateDTO updateDTO, String username) {
        CommentEntity comment = findCommentById(commentId);
        // 본인이 아니면 403
        if (username == null || !comment.isWrittenBy(username)) {
            throw AccessDeniedException.forUpdate("댓글");
        }
        comment.update(updateDTO.getContent());
        return CommentDTO.from(comment);
    }

    /**
     * 댓글 삭제 (하드 삭제)
     */
    @Transactional
    public void deleteComment(Long commentId, String username) {
        CommentEntity comment = findCommentById(commentId);
        if (username == null || !comment.isWrittenBy(username)) {
            throw AccessDeniedException.forDelete("댓글");
        }
        commentRepository.delete(comment);
    }

    /**
     * 특정 게시글의 모든 댓글 하드 삭제 (게시글 삭제 시 사용)
     * CommunityController에서 게시글 삭제 전에 호출
     */
    @Transactional
    public void deleteCommentsByCommunityId(Long communityId) {
        commentRepository.deleteByCommunityIds(java.util.List.of(communityId));
    }

    /**
     * 댓글 ID로 조회하는 헬퍼 메서드 (중복 코드 추출)
     * 없으면 EntityNotFoundException (404)
     */
    private CommentEntity findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> EntityNotFoundException.of("댓글", commentId));
    }
}
