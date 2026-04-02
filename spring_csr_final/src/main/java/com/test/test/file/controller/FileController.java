package com.test.test.file.controller;

import com.test.test.common.dto.ApiResponse;
import com.test.test.file.dto.FileDetailDTO;
import com.test.test.file.entity.FileEntity;
import com.test.test.file.service.FileService;
import com.test.test.file.strategy.FileStorageStrategy.FileUploadResult;
import com.test.test.file.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileService fileService;
    private final FileUtil fileUtil;

    @Value("${file.upload-dir:./uploads/}")
    private String uploadDir;

    /**
     * ?대?吏 ?뚯씪 ?쒕튃 (HTML img ?쒓렇?먯꽌 ?몄텧)
     * - 濡쒖뺄 ???紐⑤뱶?먯꽌留??ъ슜
     * - Supabase 紐⑤뱶?먯꽌??CDN URL濡?吏곸젒 ?묎렐?섎?濡???API ?ъ슜 ????
     */
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path file = uploadPath.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = "image/jpeg"; // 湲곕낯媛?
                if (filename.toLowerCase().endsWith(".png")) contentType = "image/png";
                else if (filename.toLowerCase().endsWith(".gif")) contentType = "image/gif";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * ?뚯씪 寃??API (?듯빀)
     * @param refId 李몄“ ID
     * @param refType COMMUNITY, USER
     * @param usage THUMBNAIL, IMAGES, ATTACHMENT (?좏깮)
     * @return ?뚯씪 寃쎈줈 由ъ뒪??
     */
    @GetMapping("/api/files")
    public ResponseEntity<List<String>> getFiles(
            @RequestParam Long refId,
            @RequestParam String refType,
            @RequestParam(required = false) String usage) {


        try {
            List<String> filePaths = fileService.getFilePaths(refId, refType, usage);
            return ResponseEntity.ok(filePaths);
        } catch (IllegalArgumentException e) {
            log.error("?섎せ???뚮씪誘명꽣: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * ?뚯씪 ?낅줈??API (怨듯넻)
     * @param files ?낅줈?쒗븷 ?뚯씪??
     * @param refId 李몄“ ID (由щ럭 ID, 媛寃?ID ??
     * @param refType COMMUNITY, USER
     * @param usage THUMBNAIL, IMAGES, ATTACHMENT
     * @return ?낅줈?쒕맂 ?뚯씪 寃쎈줈 由ъ뒪??
     */
    @PostMapping("/api/files/upload")
    public ResponseEntity<ApiResponse<List<String>>> uploadFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam Long refId,
            @RequestParam FileEntity.RefType refType,
            @RequestParam FileEntity.Usage usage) {

        try {
            // 1. FileUtil濡?臾쇰━???뚯씪 ???
            List<FileUploadResult> uploadResults = files.stream()
                    .filter(file -> !file.isEmpty())
                    .map(fileUtil::saveFile)
                    .toList();

            log.info("?뚯씪 臾쇰━??????꾨즺 - ?뚯씪 ?? {}", uploadResults.size());

            // 2. FileService濡?DB???뚯씪 ?뺣낫 ???
            List<String> savedPaths = fileService.saveFiles(uploadResults, refId, refType, usage);

            log.info("?뚯씪 ?낅줈???꾨즺 - refId: {}, refType: {}, ?뚯씪 ?? {}", refId, refType, savedPaths.size());

            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("?뚯씪 ?낅줈???깃났", savedPaths));

        } catch (IllegalArgumentException e) {
            log.error("?섎せ???뚮씪誘명꽣: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("?뚯씪 ?낅줈???ㅽ뙣: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 泥⑤??뚯씪 ?곸꽭 ?뺣낫 議고쉶 API (?먮낯 ?뚯씪紐??ы븿)
     * @param refId 李몄“ ID
     * @param refType COMMUNITY, USER
     * @param usage THUMBNAIL, IMAGES, ATTACHMENT (?좏깮)
     * @return ?뚯씪 ?곸꽭 ?뺣낫 由ъ뒪??
     */
    @GetMapping("/api/files/detail")
    public ResponseEntity<ApiResponse<List<FileDetailDTO>>> getFileDetails(
            @RequestParam Long refId,
            @RequestParam String refType,
            @RequestParam(required = false) String usage) {
        try {
            List<FileDetailDTO> fileDetails = fileService.getFileDetails(refId, refType, usage);
            return ResponseEntity.ok(ApiResponse.success("?뚯씪 議고쉶 ?깃났", fileDetails));
        } catch (IllegalArgumentException e) {
            log.error("?섎せ???뚮씪誘명꽣: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 泥⑤??뚯씪 ?ㅼ슫濡쒕뱶 API
     * - 濡쒖뺄 ?뚯씪: 吏곸젒 ?쒕튃
     * - Supabase ?뚯씪: CDN?먯꽌 媛?몄????먮낯 ?뚯씪紐낆쑝濡??묐떟
     * @param fileId ?뚯씪 ID
     * @return ?뚯씪 由ъ냼??(?먮낯 ?뚯씪紐낆쑝濡??ㅼ슫濡쒕뱶)
     */
    @GetMapping("/api/files/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        try {
            // 1. DB?먯꽌 ?뚯씪 ?뺣낫 議고쉶
            FileEntity fileEntity = fileService.getFileById(fileId);
            if (fileEntity == null) {
                log.warn("?뚯씪??李얠쓣 ???놁뒿?덈떎: fileId={}", fileId);
                return ResponseEntity.notFound().build();
            }

            String filePath = fileEntity.getFilePath();
            Resource resource;

            // 2. CDN URL?몄? 濡쒖뺄 ?뚯씪?몄? ?먮떒
            if (filePath != null && filePath.startsWith("http")) {
                // Supabase CDN URL ??URL Resource濡?濡쒕뱶
                log.info("CDN ?뚯씪 ?ㅼ슫濡쒕뱶: fileId={}, url={}", fileId, filePath);
                resource = new UrlResource(filePath);
            } else {
                // 濡쒖뺄 ?뚯씪 ??湲곗〈 諛⑹떇
                Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
                Path file = uploadPath.resolve(fileEntity.getStoredFileName());
                log.info("濡쒖뺄 ?뚯씪 ?ㅼ슫濡쒕뱶: fileId={}, path={}", fileId, file);
                resource = new UrlResource(file.toUri());
            }

            if (!resource.exists() || !resource.isReadable()) {
                log.error("?뚯씪??李얠쓣 ???놁뒿?덈떎: {}", filePath);
                return ResponseEntity.notFound().build();
            }

            // 3. ?먮낯 ?뚯씪紐??몄퐫??(?쒓? ?뚯씪紐?吏??
            String encodedFileName = URLEncoder.encode(fileEntity.getOriginalFileName(), StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20");

            // 4. ?ㅼ슫濡쒕뱶 ?묐떟 ?ㅻ뜑 ?ㅼ젙 (?먮낯 ?뚯씪紐낆쑝濡?)
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            log.error("?뚯씪 寃쎈줈 ?ㅻ쪟: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("?뚯씪 ?ㅼ슫濡쒕뱶 ?ㅽ뙣: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * ?뚯씪 ??젣 API
     * @param fileId ??젣???뚯씪 ID
     * @return ?깃났 ????젣 ?꾨즺 硫붿떆吏
     */
    @DeleteMapping("/api/files/{fileId}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(@PathVariable Long fileId) {
        try {
            log.info("?뚯씪 ??젣 ?붿껌: fileId={}", fileId);
            fileService.deleteFile(fileId);
            return ResponseEntity.ok(ApiResponse.success("?뚯씪????젣?섏뿀?듬땲??));
        } catch (IllegalArgumentException e) {
            log.error("?뚯씪 ??젣 ?ㅽ뙣: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("?뚯씪 ??젣 ?먮윭: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
