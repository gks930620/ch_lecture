# 검수: 개념_03_JPA_Entity / 파일테이블설계_정리

> 대조 소스: CommunityEntity, CommentEntity, UserEntity, FileEntity, 각 Repository(QueryDSL 커스텀 포함), FileService, QuerydslConfig, build.gradle, application.yml, data-*.sql

## 개념_03_JPA_Entity.md

### [중간] 37행 — "이 프로젝트 실제 코드" CommunityEntity 예제에서 필드 누락
- 실제 `CommunityEntity.java:27-36`에는 `user_id`, `username`, `nickname` 매핑이 존재하는데 문서 예제에는 없음. 문제는 같은 문서 115~117행의 `isWrittenBy()` 예제가 `this.username`을 참조하므로, 문서만 읽는 초보자는 "존재하지 않는 필드를 쓰는 코드"로 오해함.
- **수정 제안**: 예제에 세 필드 포함, 또는 "일부 필드 생략" 주석 + isWrittenBy 예제 옆에 username 필드 존재 명시.

### [중간] 282-283행 — 커스텀 구현체 네이밍 규칙 설명이 문서 내 예제와 상충
- 문서는 "Repository명 + Impl" 규칙만 제시(→ CommunityRepositoryImpl)하는데, 바로 아래 339행 예제와 실제 코드는 `FileRepositoryCustomImpl`(Custom 인터페이스명 + Impl). Spring Data JPA 기본 규칙은 "프래그먼트(Custom) 인터페이스명 + Impl"이고 "Repository명 + Impl"도 허용 — 프로젝트에 두 컨벤션이 혼재하는데 문서는 한쪽만 설명.
- **수정 제안**: 두 방식 모두 유효함을 명시하고 프로젝트에서 이름이 다른 이유 한 줄 추가.

### [중간] 285-333행 — "실제 코드 — 게시글 검색"이 실제 구현과 다름
- 실제 `CommunityRepositoryImpl.java:44-109`는 댓글 수 IN 쿼리 조회(N+1 방지) + commentCount 설정, `total == null` 처리, `keyword.trim()`까지 포함. 문서 버전은 모두 생략.
- **수정 제안**: "실제 코드를 단순화한 버전(댓글 수 조회 로직 생략)" 명시 또는 실제 코드로 교체.

### [낮음] 184행 — `findByUsername` 생성 SQL 설명에 @SQLRestriction 누락
- 실제 `UserEntity.java:23`의 `@SQLRestriction("is_deleted = false")` 때문에 모든 조회에 `AND is_deleted = false`가 자동으로 붙음. 이 프로젝트 소프트 삭제의 핵심 장치인데 문서에 언급 없음.

### [낮음] 405행 — "운영에서 create나 update 금지. 데이터 날아감"은 update에 부정확
- `update`는 테이블을 DROP하지 않아 데이터가 삭제되지 않음. 데이터가 날아가는 것은 `create`/`create-drop`.
- **수정 제안**: "create는 데이터 삭제, update는 의도치 않은 스키마 변경 위험"으로 구분.

### [낮음] 339행 — FileRepositoryCustomImpl 예제에 `@RequiredArgsConstructor` 누락
- 실제 코드에는 있음. 문서대로 따라 치면 생성자가 없어 컴파일 에러. `QFileEntity.fileEntity` static import 설명도 없음.

### [낮음] 10행 — "Java Persistence API" 표기
- Spring Boot 3(jakarta.persistence) 기준 공식 명칭은 "Jakarta Persistence". "Java(현재는 Jakarta) Persistence API" 병기 권장.

### [낮음] 381-393행 — "주요 JPA 설정" 발췌에 `defer-datasource-initialization: true` 누락
- 실제 application.yml:39에 존재하며 ddl-auto: create + data-*.sql 조합이 동작하는 핵심 설정.

## 파일테이블설계_정리.md

### [중간] 25-34행 — 통합 files 테이블 다이어그램에 `file_usage` 컬럼 누락
- 실제 `FileEntity.java:56-58`의 `file_usage`(THUMBNAIL/IMAGES/ATTACHMENT)는 통합 테이블 설계의 핵심 구분 컬럼이고 data-files.sql INSERT에도 포함됨. `created_at`/`updated_at`도 다이어그램에 없음.
- **수정 제안**: 다이어그램에 `file_usage` 추가 (created_at/updated_at은 선택).

### [낮음] 27행 — `ref_type (BOARD, PRODUCT, USER ...)` 예시가 실제 값과 불일치
- 실제 `FileEntity.RefType`은 `COMMUNITY`, `USER` 두 개뿐. "(이 프로젝트: COMMUNITY, USER)" 병기 권장.

### [낮음] 56-67행 — "FileUtil은 항상 하나만" 섹션의 FileUtil이 프로젝트에 존재하지 않음
- 소스 전체에 FileUtil 클래스 없음. 실제로는 `FileService.java:47-82`가 물리 저장(UUID 파일명, Files.write)과 DB 저장을 모두 수행.
- **수정 제안**: "이 프로젝트에서는 FileService가 이 역할을 겸한다" 매핑 문장 추가.

### [낮음] (소스 참고) data-files.sql:3-4 — 주석 처리된 샘플의 original/stored 파일명이 설계 의도와 반대
- `stored_file_name`은 UUID 기반이어야 하는데 `sample.jpeg`로 되어 있음. 현재 주석 처리라 실행되진 않으나 교재 참고 시 혼란 여지.

## 문제 없음 확인
- 영속성 컨텍스트/Dirty Checking 설명 정확
- @PrePersist/@PreUpdate 예제 — 실제 코드와 일치
- FileRepository 쿼리 메서드, CommentRepository의 @Query/@Modifying JPQL — 문자 단위 일치
- QueryDSL 의존성(5.0.0:jakarta), QuerydslConfig, Q클래스 생성 위치 — 일치
- ddl-auto/show_sql/format_sql/default_batch_fetch_size/open-in-view 값 — application.yml과 일치
- §7 Repository 사용 현황 표 — 실제 4개 구성과 일치
- 234행 `CommuntyEntity`/`titl`은 의도된 오타 예시("오타!" 주석)로 문제 아님
- 통합 vs 분리 테이블 기준, @MappedSuperclass 설명 — 기술적으로 정확
