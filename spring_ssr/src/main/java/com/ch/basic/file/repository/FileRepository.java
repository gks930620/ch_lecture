package com.ch.basic.file.repository;

import com.ch.basic.file.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 파일 Repository — JPA 기본 CRUD + QueryDSL 커스텀 쿼리
 *
 * JpaRepository: save, findById, delete 등 기본 CRUD
 * FileRepositoryCustom: QueryDSL 동적 검색 (searchFiles)
 */
public interface FileRepository extends JpaRepository<FileEntity, Long>, FileRepositoryCustom {

    /**
     * Spring Data JPA 쿼리 메서드: IN 쿼리로 여러 refId의 파일을 한번에 조회
     * findBy + RefIdIn + And + RefType
     * → SELECT * FROM files WHERE ref_id IN (?, ?, ?) AND ref_type = ?
     */
    List<FileEntity> findByRefIdInAndRefType(List<Long> refIds, FileEntity.RefType refType);

    /**
     * 고아 파일 조회: refId가 0이고, 마지막 수정일이 특정 시간 이전인 파일들
     */
    List<FileEntity> findByRefIdAndUpdatedAtBefore(Long refId, LocalDateTime updatedAt);

    /**
     * 배치 업데이트: 주어진 file id 목록의 refId를 한 번에 변경
     * 예: 에디터 업로드 시 refId=0으로 저장된 파일들을 글 ID로 연관지을 때 사용
     */
    @Modifying
    @Query("update FileEntity f set f.refId = :refId where f.id in :ids")
    int updateRefIdForIds(@Param("refId") Long refId, @Param("ids") List<Long> ids);

    /**
     * 안전한 파일 삭제(고아 처리):
     * 주어진 file id들이 실제로 해당 refId(게시글)에 속해 있을 때만 refId를 0으로 변경
     * (다른 게시글의 파일을 삭제하지 못하도록 방어)
     */
    @Modifying
    @Query("update FileEntity f set f.refId = 0 where f.id in :ids and f.refId = :currentRefId")
    int detachFiles(@Param("ids") List<Long> ids, @Param("currentRefId") Long currentRefId);

    /**
     * 삭제된 게시글에 속한 파일들을 고아 처리 (refId=0으로 변경) (스케줄러용)
     * 이후 FileCleanupScheduler가 물리 파일 + DB 삭제 수행
     */
    @Modifying
    @Query("update FileEntity f set f.refId = 0 where f.refId in :refIds")
    int detachFilesByRefIds(@Param("refIds") List<Long> refIds);
}
