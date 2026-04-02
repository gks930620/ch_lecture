package com.ch.basic.file.service;

import com.ch.basic.file.entity.FileEntity;
import com.ch.basic.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 파일 업로드/삭제 비즈니스 로직 Service
 *
 * 물리 파일 저장(디스크) + DB 저장(files 테이블)을 함께 처리
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final FileRepository fileRepository;

    // application.yml의 file.upload-dir 값 (예: "./uploads")
    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    /**
     * MultipartFile 리스트를 받아 물리 파일 저장 + DB 저장
     *
     * CommunityController에서 글 등록/수정 시 첨부파일 처리에 사용
     *
     * @param files   업로드된 파일 리스트 (form의 input[type=file])
     * @param refId   참조 ID (예: 게시글 ID)
     * @param refType 참조 타입 (COMMUNITY, USER)
     * @param usage   파일 용도 (ATTACHMENT, IMAGES)
     */
    @Transactional
    public void saveUploadedFiles(List<MultipartFile> files, Long refId,
                                   FileEntity.RefType refType, FileEntity.Usage usage) {
        if (files == null) return; // 첨부파일 없으면 스킵

        // 업로드 디렉토리 절대경로 생성
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            // 디렉토리가 없으면 생성 (최초 1회)
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("업로드 디렉토리 생성 실패", e);
        }

        // 빈 파일 제외하고 처리
        files.stream().filter(f -> !f.isEmpty()).forEach(f -> {
            try {
                // 원본 파일명에서 확장자 추출 (예: "report.pdf" → ".pdf")
                String original = f.getOriginalFilename();
                String ext = "";
                if (original != null && original.contains(".")) {
                    ext = original.substring(original.lastIndexOf("."));
                }
                // UUID + 확장자로 저장 파일명 생성 (파일명 중복 방지)
                String stored = UUID.randomUUID() + ext;
                // 물리 파일 저장 (디스크에 쓰기)
                Path dest = uploadPath.resolve(stored);
                Files.write(dest, f.getBytes());

                // DB에 파일 정보 저장
                saveFile(original, stored, "/uploads/" + stored,
                        f.getSize(), f.getContentType(), refId, refType, usage);
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 실패: " + f.getOriginalFilename(), e);
            }
        });
    }

    /**
     * 파일 정보 DB 저장
     * FileController(에디터 이미지 업로드)와 saveUploadedFiles(첨부파일)에서 공통 사용
     */
    @Transactional
    public FileEntity saveFile(String originalFileName, String storedFileName, String filePath,
                         Long fileSize, String contentType,
                         Long refId, FileEntity.RefType refType, FileEntity.Usage usage) {
        FileEntity file = FileEntity.builder()
                .originalFileName(originalFileName)
                .storedFileName(storedFileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .contentType(contentType)
                .refId(refId)
                .refType(refType)
                .fileUsage(usage)
                .build();
        return fileRepository.save(file); // JPA save → INSERT
    }

    /**
     * 에디터에서 미리 업로드된 파일(fileId 리스트)을 글 ID(refId)로 연관(associate)시킨다.
     * - 프론트에서 업로드 응답의 fileId를 수집하고 글 저장 시 editorFileIds로 전달하는 패턴을 따른다.
     */
    @Transactional
    public void associateFilesByIds(List<Long> fileIds, Long refId) {
        if (fileIds == null || fileIds.isEmpty()) return;
        fileRepository.updateRefIdForIds(refId, fileIds);
    }

    /**
     * 에디터 이미지 동기화 (글 수정 시 사용)
     * 1. 전달받은 fileIds(최종 본문 이미지)의 refId를 해당 글 ID로 업데이트
     * 2. 기존 글 ID로 저장된 이미지 중 fileIds에 없는 것은 refId=0(고아 상태)으로 변경하여 스케줄러가 처리하도록 함
     */
    @Transactional
    public void syncEditorImages(List<Long> fileIds, Long refId) {
        // 1. 현재 에디터에 있는 파일들을 글 ID로 연결
        if (fileIds != null && !fileIds.isEmpty()) {
            fileRepository.updateRefIdForIds(refId, fileIds);
        }

        // 2. DB에는 해당 글의 이미지로 되어있는데, 최종 fileIds에는 없는 파일 찾아서 고아 처리 (refId=0)
        List<FileEntity> currentFiles = getFiles(refId, FileEntity.RefType.COMMUNITY, FileEntity.Usage.IMAGES);

        List<Long> finalIds = (fileIds == null) ? Collections.emptyList() : fileIds;

        List<Long> orphanIds = currentFiles.stream()
                .map(FileEntity::getId)
                .filter(id -> !finalIds.contains(id))
                .toList();

        if (!orphanIds.isEmpty()) {
            fileRepository.updateRefIdForIds(0L, orphanIds);
        }
    }

    /** 파일 ID로 단건 조회 */
    public FileEntity getFileById(Long fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }

    /**
     * 특정 게시글의 파일 목록 조회 (QueryDSL 동적 쿼리)
     * 예: 게시글 5의 첨부파일 목록 → getFiles(5, COMMUNITY, ATTACHMENT)
     */
    public List<FileEntity> getFiles(Long refId, FileEntity.RefType refType, FileEntity.Usage usage) {
        return fileRepository.searchFiles(refId, refType, usage);
    }

    /**
     * 안전하게 파일 삭제 (원래 해당 글의 파일인지 체크)
     * CommunityController에서 수정 시 기존 파일 삭제할 때 호출
     */
    @Transactional
    public void deleteFilesSafely(List<Long> fileIds, Long currentRefId) {
        if (fileIds == null || fileIds.isEmpty()) return;
        // 다른 게시글의 파일을 건드리지 않도록 currentRefId 조건 추가
        fileRepository.detachFiles(fileIds, currentRefId);
    }

    /**
     * 특정 게시글의 모든 파일을 고아 처리 (refId=0으로 변경)
     * 게시글 삭제 시 CommunityController에서 호출
     * 물리 파일 삭제는 FileCleanupScheduler가 수행
     */
    @Transactional
    public void detachFilesByRefId(Long refId) {
        fileRepository.detachFilesByRefIds(List.of(refId));
    }


    /**
     * 고아 파일 정리 (스케줄러에서 호출)
     * refId=0이고, 일정 시간이 지난 파일들을 물리 파일 + DB에서 완전히 삭제
     */
    @Transactional
    public int deleteOrphanFiles(LocalDateTime timeLimit) {
        // 1. 고아 파일 조회 (refId=0 + 수정일 < timeLimit)
        List<FileEntity> orphans = fileRepository.findByRefIdAndUpdatedAtBefore(0L, timeLimit);

        if (orphans.isEmpty()) return 0;

        // 2. 물리 파일 삭제
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        for (FileEntity file : orphans) {
            try {
                Path filePath = uploadPath.resolve(file.getStoredFileName());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // 로그 제거됨, 예외 무시하고 진행
                System.out.println("파일 삭제 실패: " + file.getStoredFileName() + " - " + e.getMessage());
            }
        }

        // 3. DB 삭제 (Hard Delete)
        fileRepository.deleteAll(orphans);

        return orphans.size();
    }
}
