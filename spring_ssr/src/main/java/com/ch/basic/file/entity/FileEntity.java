package com.ch.basic.file.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 파일 엔티티 — DB의 files 테이블과 매핑
 *
 * 하나의 테이블로 모든 종류의 파일을 관리 (에디터 이미지, 첨부파일 등)
 * refId + refType으로 어떤 도메인(게시글/사용자)의 파일인지 구분
 * fileUsage로 파일의 용도(이미지/첨부파일)를 구분
 */
@Entity
@Table(name = "files")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자가 업로드한 원본 파일명 (예: "보고서.pdf")
    @Column(nullable = false)
    private String originalFileName;

    // 서버에 저장된 파일명 — UUID + 확장자 (예: "35cb63ec-5aed-4b52-a2dc-c357a235c7f6.pdf")
    // 파일명 중복 방지 + 보안 (원본 파일명 노출 방지)
    @Column(nullable = false)
    private String storedFileName;

    // 파일 접근 경로 (예: "/uploads/35cb63ec...pdf")
    // 이미지 서빙(img src)이나 다운로드 URL에 사용
    @Column(nullable = false)
    private String filePath;

    // 파일 크기 (바이트 단위)
    private Long fileSize;

    // MIME 타입 (예: "image/png", "application/pdf")
    private String contentType;

    // 참조 ID — 이 파일이 연결된 부모 엔티티의 PK
    // 예: 게시글 ID = 5인 글의 첨부파일이면 refId = 5
    private Long refId;

    // 참조 타입 — 어떤 도메인의 파일인지 (COMMUNITY, USER)
    @Enumerated(EnumType.STRING)  // DB에 "COMMUNITY" 문자열로 저장 (기본값 ORDINAL은 숫자)
    private RefType refType;

    // 파일 용도 — 같은 게시글이라도 에디터 이미지 vs 첨부파일 구분
    @Column(name = "file_usage")
    @Enumerated(EnumType.STRING)
    private Usage fileUsage;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 참조 타입 — 파일이 연결된 도메인 구분
     */
    public enum RefType {
        COMMUNITY,  // 커뮤니티 게시글
        USER        // 사용자 (프로필 이미지 등)
    }

    /**
     * 파일 용도 — 같은 도메인 내에서 파일 종류 구분
     */
    public enum Usage {
        THUMBNAIL,   // 썸네일/대표 이미지 (현재 미사용)
        IMAGES,      // 본문 내용 이미지 (Quill 에디터에서 삽입한 이미지)
        ATTACHMENT   // 첨부 파일 (다운로드용)
    }
}
