package com.ch.basic.community;

import com.ch.basic.common.dto.PageResponse;
import com.ch.basic.community.comment.CommentService;
import com.ch.basic.community.dto.CommunityCreateDTO;
import com.ch.basic.community.dto.CommunityDTO;
import com.ch.basic.community.dto.CommunityUpdateDTO;
import com.ch.basic.file.entity.FileEntity;
import com.ch.basic.file.service.FileService;
import com.ch.basic.user.dto.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 게시글 SSR 컨트롤러 — Thymeleaf 템플릿 렌더링
 *
 * 게시글 CRUD는 SSR (form submit → redirect) 방식
 * 파일 처리도 form submit (multipart/form-data)으로 한번에 처리
 *
 * ※ 댓글은 CommentApiController(@RestController)에서 REST API로 별도 처리
 */
@Controller
@RequestMapping("/community")  // 이 컨트롤러의 모든 URL은 /community로 시작
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final CommentService commentService;
    private final FileService fileService;


    /**
     * 목록 페이지
     *
     * Pageable: Spring이 ?page=0&size=10 쿼리 파라미터를 자동으로 Pageable 객체로 바인딩
     *   - page: 0부터 시작 (클라이언트에서 0-based로 전송, 화면 표시만 +1)
     *   - size, sort도 쿼리 파라미터로 제어 가능 (?sort=createdAt,desc)
     * @PageableDefault: 파라미터 없이 /community만 요청했을 때 적용되는 기본값
     *
     * @param searchType 검색 타입 ("title" 또는 "nickname")
     * @param keyword    검색 키워드
     */
    @GetMapping
    public String list(
              @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword,
            Model model) {


        // Service → Repository(QueryDSL) → DB 조회
        PageResponse<CommunityDTO> communities = communityService.getCommunityList(searchType, keyword, pageable);

        // Model에 데이터 담기 → Thymeleaf 템플릿에서 사용 (th:each="community : ${communities}")
        model.addAttribute("communities", communities);
        model.addAttribute("searchType", searchType);   // 검색 폼 유지용
        model.addAttribute("keyword", keyword);          // 검색 폼 유지용
        return "community/list"; // templates/community/list.html 렌더링
    }

    /** 작성 폼 페이지 (※ Interceptor에서 로그인 체크 완료) */
    @GetMapping("/write")
    public String writeForm(Model model) {
        // 빈 DTO를 model에 담아서 form 바인딩 (th:object="${communityCreateDTO}")
        model.addAttribute("communityCreateDTO", new CommunityCreateDTO());
        return "community/write"; // templates/community/write.html 렌더링
    }

    /**
     * 작성 처리 (form submit → POST)
     *
     * 처리 순서: 1) 글 등록 → 2) 첨부파일 저장 → 3) 상세 페이지로 리다이렉트
     *
     * ※ 글 등록을 먼저 해야 글 ID(refId)를 얻을 수 있어서 파일 저장이 가능
     *
     * @param files form의 <input type="file" name="files"> (multiple 가능)
     */
    @PostMapping("/write")
    public String write(@RequestParam String title,
                        @RequestParam String content,
                        @RequestParam(value = "files", required = false) List<MultipartFile> files,
                        @RequestParam(value = "editorFileIds", required = false) List<Long> editorFileIds,
                        HttpSession session) {
        // 세션에서 로그인 사용자 정보 꺼냄 (Interceptor 통과 → null 아님 보장)
        LoginUserDTO loginUser = getLoginUser(session);

        // 1) 글 등록 → DB INSERT 후 생성된 글 ID 반환
        CommunityCreateDTO createDTO = new CommunityCreateDTO();
        createDTO.setTitle(title);
        createDTO.setContent(content);
        Long id = communityService.createCommunity(createDTO, loginUser.getId(), loginUser.getUsername(), loginUser.getNickname());

        // 2) 에디터 이미지 연결 (refId=0 → 생성된 글 ID로 업데이트)
        //    프론트엔드에서 업로드된 에디터 이미지들의 ID를 받아와서 처리
        fileService.associateFilesByIds(editorFileIds, id);

        // 3) 첨부파일 저장 — 물리 파일 저장 + DB INSERT (글 ID를 refId로 연결)
        fileService.saveUploadedFiles(files, id, FileEntity.RefType.COMMUNITY, FileEntity.Usage.ATTACHMENT);

        // 4) 작성한 글 상세 페이지로 리다이렉트 (PRG 패턴 — Post/Redirect/Get)
        return "redirect:/community/" + id;
    }

    /**
     * 상세 페이지
     * @PathVariable: URL의 {id} 부분을 파라미터로 받음 (예: /community/5 → id=5)
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        // 게시글 조회 + 조회수 증가
        CommunityDTO community = communityService.getCommunityDetail(id);

        model.addAttribute("community", community);
        return "community/detail"; // templates/community/detail.html 렌더링
        // ※ 댓글, 첨부파일은 detail.html의 JavaScript에서 API 호출로 로드
        //    첨부파일: GET /api/files?refId={id}&refType=COMMUNITY&usage=ATTACHMENT
    }

    /** 수정 폼 페이지 */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        CommunityDTO community = communityService.getCommunityDetail(id);
        // 기존 제목/내용을 수정 DTO에 담아서 form에 표시
        CommunityUpdateDTO updateDTO = new CommunityUpdateDTO(community.getTitle(), community.getContent());

        model.addAttribute("community", community);
        model.addAttribute("communityUpdateDTO", updateDTO);
        return "community/edit"; // templates/community/edit.html 렌더링
        // ※ 첨부파일, 에디터 이미지는 edit.html의 JavaScript에서 API 호출로 로드
        //    첨부파일: GET /api/files?refId={id}&refType=COMMUNITY&usage=ATTACHMENT
        //    에디터 이미지: GET /api/files?refId={id}&refType=COMMUNITY&usage=IMAGES
    }

    /**
     * 수정 처리 (form submit → POST)
     *
     * 처리 순서: 1) 글 수정 → 2) 기존 파일 삭제 → 3) 새 파일 업로드 → 4) 리다이렉트
     *
     * @param deleteFileIds 수정 화면에서 삭제 버튼을 누른 기존 파일들의 ID 리스트
     *                      (hidden input으로 전달: <input name="deleteFileIds" value="파일ID">)
     * @param files         새로 추가할 첨부파일들
     */
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @RequestParam String title,
                       @RequestParam String content,
                       @RequestParam(value = "deleteFileIds", required = false) List<Long> deleteFileIds,
                       @RequestParam(value = "files", required = false) List<MultipartFile> files,
                       @RequestParam(value = "editorFileIds", required = false) List<Long> editorFileIds,
                       HttpSession session) {
        LoginUserDTO loginUser = getLoginUser(session);

        // 1) 글 수정 (Service → Entity.update() → JPA 변경 감지 → UPDATE)
        CommunityUpdateDTO updateDTO = new CommunityUpdateDTO(title, content);
        communityService.updateCommunity(id, updateDTO, loginUser.getUsername());

        // 2) 에디터 이미지 동기화 (최종 본문에 없는 이미지는 삭제)
        fileService.syncEditorImages(editorFileIds, id);

        // 3) 삭제 요청된 기존 파일 안전하게 삭제 (본인 글의 파일인지 확인 후 고아 처리)
        if (deleteFileIds != null && !deleteFileIds.isEmpty()) {
            fileService.deleteFilesSafely(deleteFileIds, id);
        }

        // 4) 새 첨부파일 저장
        fileService.saveUploadedFiles(files, id, FileEntity.RefType.COMMUNITY, FileEntity.Usage.ATTACHMENT);

        return "redirect:/community/" + id;
    }

    /**
     * 삭제 처리 (하드 삭제)
     *
     * 처리 순서: 1) 파일 고아 처리 → 2) 댓글 하드 삭제 → 3) 게시글 하드 삭제
     * ※ FK 제약 때문에 댓글을 먼저 삭제해야 게시글 삭제 가능
     * ※ 파일은 고아 처리(refId=0)만 하고 물리 삭제는 FileCleanupScheduler가 수행
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        LoginUserDTO loginUser = getLoginUser(session);

        // 1) 파일 고아 처리 (refId=0으로 변경, 물리 삭제는 스케줄러)
        fileService.detachFilesByRefId(id);

        // 2) 댓글 하드 삭제
        commentService.deleteCommentsByCommunityId(id);

        // 3) 게시글 하드 삭제 (Service에서 권한 체크 + DELETE)
        communityService.deleteCommunity(id, loginUser.getUsername());

        return "redirect:/community"; // 목록 페이지로 리다이렉트
    }

    /**
     * 세션에서 로그인 사용자 정보를 꺼내는 헬퍼 메서드
     * 여러 메서드에서 반복되는 코드를 추출
     */
    private LoginUserDTO getLoginUser(HttpSession session) {
        return (LoginUserDTO) session.getAttribute("loginUser");
    }
}
