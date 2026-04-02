package com.ch.basic.file.controller;

import com.ch.basic.file.dto.FileDetailDTO;
import com.ch.basic.file.entity.FileEntity;
import com.ch.basic.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 파일 REST API Controller — 목록 조회, 업로드
 *
 * JS fetch로 호출되는 API 전용 → @RestController
 * 예외 발생 시 ApiExceptionHandler가 JSON(ErrorResponse)으로 응답
 *
 * ※ 파일 다운로드, 이미지 서빙은 브라우저가 직접 요청하므로 FileController(@Controller)에서 처리
 * ※ 파일 삭제 API는 없음 — 첨부파일 삭제는 CommunityController에서 form submit으로 처리,
 *    에디터 이미지는 글 등록/수정 시 editorFileIds 동기화로 처리, 물리 파일은 스케줄러가 정리
 */
@RestController
@RequiredArgsConstructor
public class FileApiController {

    private final FileService fileService;

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    /**
     * 파일 목록 조회
     * GET /api/files?refId={refId}&refType={refType}&usage={usage}
     *
     * 특정 게시글의 첨부파일/에디터 이미지 목록을 조회할 때 사용
     */
    @GetMapping("/api/files")
    public ResponseEntity<List<FileDetailDTO>> getFiles(
            @RequestParam Long refId,
            @RequestParam FileEntity.RefType refType,
            @RequestParam FileEntity.Usage usage) {

        List<FileDetailDTO> files = fileService.getFiles(refId, refType, usage)
                .stream().map(FileDetailDTO::from).toList();

        return ResponseEntity.ok(files);
    }

    /**
     * 파일 업로드 (Quill 에디터 이미지 업로드용)
     * POST /api/files/upload
     *
     * 에디터에서 이미지 삽입 시 JS fetch로 호출
     * 파일을 즉시 저장하고 URL을 반환 → 에디터 본문에 <img src="url"> 삽입
     *
     * @param files   업로드 파일 리스트
     * @param refId   참조 ID (에디터 이미지: 글 작성 시 0, 수정 시 글 ID)
     * @param refType 참조 타입 (COMMUNITY)
     * @param usage   파일 용도 (IMAGES)
     * @return [{fileId: 1, url: "/uploads/uuid.png"}, ...]
     */
    @PostMapping("/api/files/upload")
    public List<Map<String, Object>> uploadFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam Long refId,
            @RequestParam FileEntity.RefType refType,
            @RequestParam FileEntity.Usage usage) throws IOException {

        // 업로드 디렉토리 절대경로 + 없으면 생성
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        return files.stream()
                .filter(f -> !f.isEmpty())  // 빈 파일 제외
                .map(f -> {
                    try {
                        // 확장자 추출
                        String ext = "";
                        String original = f.getOriginalFilename();
                        if (original != null && original.contains(".")) {
                            ext = original.substring(original.lastIndexOf("."));
                        }
                        // UUID 파일명으로 물리 파일 저장
                        String stored = UUID.randomUUID() + ext;
                        Path dest = uploadPath.resolve(stored);
                        Files.write(dest, f.getBytes());

                        // DB에 파일 정보 저장
                        FileEntity saved = fileService.saveFile(original, stored, "/uploads/" + stored,
                                f.getSize(), f.getContentType(), refId, refType, usage);

                        // JSON 응답: fileId + 접근 URL
                        return Map.<String, Object>of(
                                "fileId", saved.getId(),
                                "url", "/uploads/" + stored
                        );
                    } catch (IOException e) {
                        throw new RuntimeException("파일 저장 실패: " + f.getOriginalFilename(), e);
                    }
                })
                .toList();
    }

}

