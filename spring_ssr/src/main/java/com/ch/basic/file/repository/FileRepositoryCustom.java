package com.ch.basic.file.repository;

import com.ch.basic.file.entity.FileEntity;

import java.util.List;

/**
 * 파일 QueryDSL 동적 쿼리용 커스텀 Repository 인터페이스
 *
 * 구현체: FileRepositoryCustomImpl
 */
public interface FileRepositoryCustom {
    /**
     * 동적 조건으로 파일 검색
     * 조건이 null이면 해당 조건은 무시됨 (QueryDSL BooleanExpression)
     *
     * @param refId     참조 ID (예: 게시글 ID)
     * @param refType   참조 타입 (COMMUNITY, USER)
     * @param fileUsage 파일 용도 (ATTACHMENT, IMAGES, THUMBNAIL)
     * @return 조건에 맞는 파일 엔티티 리스트
     */
    List<FileEntity> searchFiles(Long refId, FileEntity.RefType refType, FileEntity.Usage fileUsage);
}
