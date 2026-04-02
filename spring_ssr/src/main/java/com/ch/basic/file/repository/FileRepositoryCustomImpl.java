package com.ch.basic.file.repository;

import static com.ch.basic.file.entity.QFileEntity.fileEntity;

import com.ch.basic.file.entity.FileEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * FileRepositoryCustom의 QueryDSL 구현체
 *
 * ※ 클래스명 규칙: "Custom 인터페이스를 포함하는 Repository명 + Impl"
 *   FileRepository가 FileRepositoryCustom을 상속 → FileRepositoryCustomImpl (자동 매칭)
 */
@RequiredArgsConstructor
public class FileRepositoryCustomImpl implements FileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 동적 조건으로 파일 검색
     * 각 조건 메서드(eqRefId, eqRefType, eqFileUsage)가 null을 반환하면 해당 조건 무시
     */
    @Override
    public List<FileEntity> searchFiles(Long refId, FileEntity.RefType refType, FileEntity.Usage fileUsage) {
        return queryFactory
                .selectFrom(fileEntity)
                .where(
                        eqRefId(refId),          // ref_id = ? (null이면 조건 무시)
                        eqRefType(refType),      // ref_type = ? (null이면 조건 무시)
                        eqFileUsage(fileUsage)   // file_usage = ? (null이면 조건 무시)
                )
                .fetch();
    }

    // ===== 동적 조건 메서드들 =====
    // null을 반환하면 QueryDSL where절에서 해당 조건이 자동으로 무시됨

    private BooleanExpression eqRefId(Long refId) {
        return refId != null ? fileEntity.refId.eq(refId) : null;
    }

    private BooleanExpression eqRefType(FileEntity.RefType refType) {
        return refType != null ? fileEntity.refType.eq(refType) : null;
    }

    private BooleanExpression eqFileUsage(FileEntity.Usage fileUsage) {
        return fileUsage != null ? fileEntity.fileUsage.eq(fileUsage) : null;
    }
}
