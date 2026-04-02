package com.ch.basic.community.comment;

import com.ch.basic.common.dto.PageResponse;
import com.ch.basic.community.comment.dto.CommentCreateDTO;
import com.ch.basic.community.comment.dto.CommentDTO;
import com.ch.basic.community.comment.dto.CommentUpdateDTO;
import com.ch.basic.user.dto.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 댓글 REST API Controller
 *
 * @RestController = @Controller + @ResponseBody
 *   → 모든 메서드의 반환값이 JSON으로 변환됨 (HTML 템플릿이 아님)
 *
 * 게시글(community)은 SSR(Thymeleaf)로 처리하지만,
 * 댓글은 detail.html의 JavaScript fetch로 비동기 처리 (페이지 새로고침 없이)
 *
 * URL 구조: /api/communities/{communityId}/comments — RESTful 중첩 리소스
 */
@RestController
@RequestMapping("/api/communities/{communityId}/comments")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    /**
     * 댓글 목록 조회 (페이징)
     * GET /api/communities/{communityId}/comments?page=0&size=10
     *
     * Pageable: Spring이 ?page=0&size=10 쿼리 파라미터를 자동으로 바인딩
     * @PageableDefault: 파라미터 없으면 size=10, 최신순 정렬 기본 적용
     *
     * @return ResponseEntity<PageResponse<CommentDTO>> — 필요한 페이징 필드만 담은 JSON 응답
     */
    @GetMapping
    public ResponseEntity<PageResponse<CommentDTO>> getComments(
            @PathVariable Long communityId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        PageResponse<CommentDTO> comments = commentService.getCommentsByCommunityId(communityId, pageable);
        // ResponseEntity.ok(): HTTP 200 + body에 JSON 데이터
        return ResponseEntity.ok(comments);
    }

    /**
     * 댓글 작성
     * POST /api/communities/{communityId}/comments
     * Body: { "content": "댓글 내용" }
     *
     * @RequestBody: HTTP 요청 body의 JSON을 Java 객체(Map)로 변환
     *
     * ※ Interceptor가 적용되지 않는 경로이므로 직접 세션 체크
     *    (Interceptor는 SSR 경로만 적용, API는 자체 인증 → 401 JSON 응답)
     */
    @PostMapping
    public ResponseEntity<?> createComment(
            @PathVariable Long communityId,
            @RequestBody Map<String, String> body,
            HttpSession session) {

        // 로그인 체크 — 비로그인이면 401 Unauthorized (JSON 응답)
        LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "로그인이 필요합니다."));
        }

        // 요청 body에서 content 추출
        String content = body.get("content");
        if (content == null || content.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "댓글 내용을 입력해주세요."));
        }

        // URL의 {communityId}와 content로 DTO 생성
        CommentCreateDTO createDTO = new CommentCreateDTO(communityId, content);
        CommentDTO created = commentService.createComment(createDTO, loginUser.getId(), loginUser.getUsername(), loginUser.getNickname());
        // 201 Created + 생성된 댓글 정보 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * 댓글 수정
     * PUT /api/communities/{communityId}/comments/{commentId}
     * Body: { "content": "수정된 내용" }
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long communityId,
            @PathVariable Long commentId,
            @RequestBody Map<String, String> body,
            HttpSession session) {

        LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "로그인이 필요합니다."));
        }

        String content = body.get("content");
        if (content == null || content.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "댓글 내용을 입력해주세요."));
        }

        CommentUpdateDTO updateDTO = new CommentUpdateDTO(content);
        CommentDTO updated = commentService.updateComment(commentId, updateDTO, loginUser.getUsername());
        return ResponseEntity.ok(updated);
    }

    /**
     * 댓글 삭제 (하드 삭제)
     * DELETE /api/communities/{communityId}/comments/{commentId}
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long communityId,
            @PathVariable Long commentId,
            HttpSession session) {

        LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "로그인이 필요합니다."));
        }

        commentService.deleteComment(commentId, loginUser.getUsername());
        return ResponseEntity.ok(Map.of("message", "삭제되었습니다."));
    }
}
