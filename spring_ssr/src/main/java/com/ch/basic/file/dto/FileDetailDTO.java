package com.ch.basic.file.dto;

import com.ch.basic.file.entity.FileEntity;
import lombok.*;

/**
 * 파일 상세 정보 DTO — 화면(Thymeleaf)에서 첨부파일 목록 표시용
 *
 * Entity → DTO 변환: FileDetailDTO.from(entity)
 * formattedFileSize: "1.5 MB" 또는 "300.0 KB" 형태로 변환하여 화면에 표시
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDetailDTO {
    private Long fileId;
    private String originalFileName;    // 원본 파일명 (사용자에게 표시)
    private String storedFileName;      // 서버 저장 파일명 (내부 사용)
    private Long fileSize;              // 파일 크기 (바이트)
    private String formattedFileSize;   // 포맷된 파일 크기 ("1.5 MB" 또는 "300.0 KB")
    private String downloadUrl;         // 다운로드 URL (예: "/files/download/1")
    private String previewUrl;          // 미리보기 URL (예: "/uploads/uuid.png")

    /**
     * Entity → DTO 변환
     */
    public static FileDetailDTO from(FileEntity entity) {
        return FileDetailDTO.builder()
                .fileId(entity.getId())
                .originalFileName(entity.getOriginalFileName())
                .storedFileName(entity.getStoredFileName())
                .fileSize(entity.getFileSize())
                .formattedFileSize(formatSize(entity.getFileSize()))  // 크기 포맷 변환
                .downloadUrl("/files/download/" + entity.getId())      // 다운로드 URL 생성
                .previewUrl("/uploads/" + entity.getStoredFileName())   // 이미지 미리보기 URL
                .build();
    }

    /**
     * 파일 크기를 사람이 읽기 쉬운 형태로 변환
     * 1MB 이상 → "1.5 MB", 1MB 미만 → "300.0 KB"
     */
    private static String formatSize(Long fileSize) {
        if (fileSize == null) return "";
        if (fileSize > 1048576) return String.format("%.1f MB", fileSize / 1048576.0);  // 1024*1024 = 1MB
        return String.format("%.1f KB", fileSize / 1024.0);
    }
}
