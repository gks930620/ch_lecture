package com.ch.basic.file.controller;

import com.ch.basic.file.entity.FileEntity;
import com.ch.basic.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 파일 Controller — 다운로드, 이미지 서빙 (브라우저 직접 요청)
 *
 * 브라우저가 직접 URL로 접근하는 요청만 처리 → @Controller
 * 예외 발생 시 GlobalExceptionHandler가 error.html로 처리
 *
 * ※ 파일 목록 조회, 업로드, 삭제 등 JS fetch로 호출되는 API는
 *   FileApiController(@RestController)에서 처리
 */
@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    // 파일 저장 경로 (application.yml에서 주입)
    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    /**
     * 파일 다운로드
     * GET /files/download/{fileId}
     *
     * 첨부파일 클릭 시 브라우저가 파일을 다운로드하도록 응답
     * Content-Disposition: attachment → 브라우저가 파일 저장 다이얼로그 표시
     */
    @GetMapping("/files/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        // DB에서 파일 정보 조회
        FileEntity file = fileService.getFileById(fileId);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // 물리 파일 경로 → Resource 객체 생성
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(file.getStoredFileName()).normalize();

            // Path Traversal 방어
            if (!filePath.startsWith(uploadPath)) {
                return ResponseEntity.badRequest().build();
            }

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // 한글 파일명 URL 인코딩 (브라우저 호환)
            String encodedName = URLEncoder.encode(file.getOriginalFileName(), StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20"); // 공백 처리

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)  // 바이너리 다운로드
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedName + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 이미지 서빙 (에디터 본문 이미지 등)
     * GET /uploads/{filename}
     *
     * Quill 에디터 본문의 <img src="/uploads/uuid.png"> 요청을 처리
     * Content-Type을 자동 추론하여 이미지로 응답 (image/png, image/jpeg 등)
     *
     * ※ 정석은 WebConfig의 addResourceHandlers()로 정적 리소스 매핑하는 것이지만,
     *    파일 관련 처리를 FileController에서 일관되게 관리하기 위해 여기서 처리
     *
     * @param filename UUID 파일명 (예: "35cb63ec-5aed-4b52-a2dc-c357a235c7f6.png")
     *                 {filename:.+}: 파일명에 . 포함을 허용하는 정규식 (Spring 기본은 . 이후 무시)
     */
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(filename).normalize();

            // Path Traversal 방어: 경로가 업로드 디렉토리 밖으로 벗어나면 차단
            if (!filePath.startsWith(uploadPath)) {
                return ResponseEntity.badRequest().build();
            }

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // Files.probeContentType(): OS가 파일 확장자 기반으로 MIME 타입 추론
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // 추론 실패 시 기본값
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
