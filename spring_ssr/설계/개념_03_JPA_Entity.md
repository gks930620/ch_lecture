# JPA / Entity / Repository / QueryDSL

> 이 프로젝트에서 DB 접근에 사용되는 기술들을 개념 위주로 정리  
> 별도 실습 코드 없이, 프로젝트 코드에 자연스럽게 녹아 있음

---

## 1. JPA란?

**Java Persistence API** — Java 객체(Entity)와 DB 테이블을 자동으로 매핑해주는 ORM 기술.

```
기존 방식 (MyBatis 등):
  SQL을 직접 작성 → ResultSet → Java 객체로 변환

JPA 방식:
  Java 클래스(Entity) 정의 → JPA가 SQL을 자동 생성/실행 → 객체로 반환
```

### JPA를 쓰면 뭐가 좋은가?

| | SQL 직접 작성 (MyBatis) | JPA |
|--|------------------------|-----|
| SQL | 개발자가 직접 작성 | JPA가 자동 생성 |
| 테이블 변경 시 | SQL 전부 수정 | Entity 필드만 수정 |
| CRUD | 매번 SQL 작성 | `save()`, `findById()` 등 기본 제공 |
| DB 종류 변경 | SQL 방언 전부 수정 | 설정만 변경 |
| 학습 난이도 | SQL 잘 알면 쉬움 | 영속성 컨텍스트 등 개념 학습 필요 |

> 결국 **"SQL 대신 Java 객체로 DB를 다루겠다"** 는 것이 JPA의 핵심.

---

## 2. Entity — DB 테이블과 매핑되는 Java 클래스

```java
// CommunityEntity.java (이 프로젝트 실제 코드)
@Entity                          // JPA가 관리하는 엔티티 선언
@Table(name = "community")      // 매핑할 DB 테이블명
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityEntity {

    @Id                                              // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;

    @Column(nullable = false, length = 200)          // NOT NULL, VARCHAR(200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT") // TEXT 타입
    private String content;

    @Column(nullable = false)
    @Builder.Default
    private Integer viewCount = 0;                   // 기본값 0

    @Column(nullable = false, updatable = false)     // INSERT 후 수정 불가
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
```

### 주요 어노테이션 정리

| 어노테이션 | 설명 |
|-----------|------|
| `@Entity` | 이 클래스가 JPA 엔티티임을 선언 |
| `@Table(name = "...")` | 매핑할 DB 테이블명 (생략 시 클래스명 사용) |
| `@Id` | Primary Key 필드 |
| `@GeneratedValue(IDENTITY)` | AUTO_INCREMENT (DB가 ID 자동 생성) |
| `@Column` | 컬럼 속성 (nullable, length, unique, columnDefinition 등) |
| `@Builder.Default` | Builder 패턴 사용 시 기본값 적용 |
| `@Enumerated(EnumType.STRING)` | Enum을 문자열로 저장 (ORDINAL이면 숫자) |
| `@ManyToOne(fetch = LAZY)` | N:1 관계, 지연 로딩 |
| `@JoinColumn` | FK 컬럼명 지정 |

### @PrePersist / @PreUpdate — 생명주기 콜백

```java
// INSERT 직전에 자동 실행
@PrePersist
protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
}

// UPDATE 직전에 자동 실행
@PreUpdate
protected void onUpdate() {
    updatedAt = LocalDateTime.now();
}
```

> 매번 `entity.setCreatedAt(LocalDateTime.now())` 하지 않아도 JPA가 알아서 호출.

### Entity에 비즈니스 편의 메서드

```java
// Entity 내부에서 상태를 변경하는 메서드
// Service에서 entity.update() 형태로 호출
public void update(String title, String content) {
    this.title = title;
    this.content = content;
}

public void incrementViewCount() {
    this.viewCount++;
}

public boolean isWrittenBy(String username) {
    return this.username != null && this.username.equals(username);
}
```

---

## 3. 영속성 컨텍스트와 변경 감지 (Dirty Checking)

JPA의 가장 중요한 개념. **Entity를 수정하면 별도 UPDATE 쿼리 없이 자동으로 DB에 반영**.

```java
@Transactional
public void updateCommunity(Long id, CommunityUpdateDTO dto, String username) {
    // 1) DB에서 Entity 조회 → 영속성 컨텍스트에 저장됨 (관리 대상)
    CommunityEntity community = communityRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.of("게시글", id));

    // 2) Entity 필드 수정 (setter가 아니라 비즈니스 메서드로!)
    community.update(dto.getTitle(), dto.getContent());

    // 3) save() 호출 안 함! → 트랜잭션 종료 시 JPA가 변경된 필드를 감지하여 자동 UPDATE
}
```

```
영속성 컨텍스트 동작 흐름:
  findById() → Entity를 영속성 컨텍스트에 저장 (스냅샷 보관)
      ↓
  entity.update() → 메모리상 Entity 필드 변경
      ↓
  @Transactional 메서드 종료 → JPA가 스냅샷과 현재 상태 비교
      ↓
  변경된 필드 발견 → UPDATE SQL 자동 생성 및 실행
```

> **핵심**: `@Transactional` 메서드 안에서 Entity를 수정하면 자동 반영.  
> `repository.save(entity)`를 매번 호출할 필요 없음 (새로 생성할 때만 호출).

---

## 4. Spring Data JPA — Repository

### 기본 제공 CRUD

`JpaRepository<Entity, ID타입>`을 상속하면 기본 CRUD 메서드가 자동 제공.

```java
@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> {
    // save(), findById(), findAll(), delete(), count() 등 기본 제공
    // 한 줄도 구현하지 않아도 됨!
}
```

| 메서드 | SQL | 설명 |
|--------|-----|------|
| `save(entity)` | INSERT 또는 UPDATE | 새 Entity면 INSERT, 기존이면 UPDATE |
| `findById(id)` | SELECT ... WHERE id = ? | Optional 반환 |
| `findAll()` | SELECT * | 전체 조회 |
| `delete(entity)` | DELETE ... WHERE id = ? | 삭제 |
| `count()` | SELECT COUNT(*) | 전체 개수 |

### 쿼리 메서드 — 메서드 이름으로 SQL 자동 생성

```java
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 메서드 이름 규칙(findBy + 필드명)으로 SQL을 자동 생성
    // → SELECT * FROM users WHERE username = ?
    Optional<UserEntity> findByUsername(String username);
}

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    // → SELECT * FROM files WHERE ref_id IN (?, ?, ?) AND ref_type = ?
    List<FileEntity> findByRefIdInAndRefType(List<Long> refIds, FileEntity.RefType refType);

    // → SELECT * FROM files WHERE ref_id = ? AND updated_at < ?
    List<FileEntity> findByRefIdAndUpdatedAtBefore(Long refId, LocalDateTime updatedAt);
}
```

### @Query — JPQL 직접 작성

쿼리 메서드로 표현하기 어려운 경우 JPQL을 직접 작성.

```java
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    // JPQL: Entity 기반 쿼리 (테이블명 대신 엔티티명, 컬럼명 대신 필드명)
    @Query(value = "SELECT c FROM CommentEntity c WHERE c.community.id = :communityId ORDER BY c.createdAt DESC",
           countQuery = "SELECT count(c) FROM CommentEntity c WHERE c.community.id = :communityId")
    Page<CommentEntity> findByCommunityId(@Param("communityId") Long communityId, Pageable pageable);

    // UPDATE/DELETE는 @Modifying 필요
    @Modifying
    @Query("DELETE FROM CommentEntity c WHERE c.community.id IN :communityIds")
    int deleteByCommunityIds(@Param("communityIds") List<Long> communityIds);
}
```

### 쿼리 방식 선택 기준

```
단순 조회    → 쿼리 메서드 (findByUsername)
약간 복잡    → @Query + JPQL
동적 조건    → QueryDSL ★
```

---

## 5. QueryDSL — 동적 쿼리

### QueryDSL이란?

JPQL을 **Java 코드로 type-safe하게** 작성할 수 있게 해주는 라이브러리.

```java
// JPQL 문자열 방식 — 오타나 타입 오류를 런타임에서야 발견
@Query("SELECT c FROM CommuntyEntity c WHERE c.titl = :keyword")  // 오타! 컴파일 시 안 잡힘

// QueryDSL 방식 — 컴파일 시점에 오류 발견
queryFactory.selectFrom(community)
    .where(community.title.containsIgnoreCase(keyword))  // 오타 시 컴파일 에러
```

### 설정 (build.gradle)

```groovy
dependencies {
    // QueryDSL
    implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
    annotationProcessor "com.querydsl:querydsl-apt:5.0.0:jakarta"
    annotationProcessor "jakarta.annotation:jakarta.annotation-api"
    annotationProcessor "jakarta.persistence:jakarta.persistence-api"
}
```

> 빌드 시 `@Entity`가 붙은 클래스를 기반으로 **Q클래스**를 자동 생성.  
> `CommunityEntity` → `QCommunityEntity` (build/generated 폴더에 생성됨)

### JPAQueryFactory Bean 등록

```java
// QuerydslConfig.java
@Configuration
public class QuerydslConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
```

### 커스텀 Repository 구조 (이 프로젝트 패턴)

```
CommunityRepository (인터페이스)
    ├── extends JpaRepository<CommunityEntity, Long>    ← 기본 CRUD
    └── extends CommunityRepositoryCustom (인터페이스)   ← QueryDSL 메서드 선언
                     ↑
        CommunityRepositoryImpl (구현체)                 ← QueryDSL 실제 구현
```

> **클래스명 규칙**: `Custom 인터페이스를 포함하는 Repository명 + Impl`  
> `CommunityRepository`가 `CommunityRepositoryCustom`을 상속 → `CommunityRepositoryImpl` 자동 매칭

### 실제 코드 — 게시글 검색 (동적 where절)

```java
@Repository
@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CommunityDTO> searchCommunity(String searchType, String keyword, Pageable pageable) {
        QCommunityEntity community = QCommunityEntity.communityEntity;

        // 1) 전체 개수 조회
        Long total = queryFactory
                .select(community.count())
                .from(community)
                .where(searchCondition(community, searchType, keyword))
                .fetchOne();

        // 2) 현재 페이지 데이터 조회
        List<CommunityEntity> entities = queryFactory
                .selectFrom(community)
                .where(searchCondition(community, searchType, keyword))
                .orderBy(community.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 3) Entity → DTO 변환 후 Page 객체로 반환
        List<CommunityDTO> content = entities.stream()
                .map(CommunityDTO::from).toList();
        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 동적 검색 조건 — null 반환 시 where절에서 자동 무시됨
     * QueryDSL의 가장 큰 장점: 조건을 조합하기 쉬움
     */
    private BooleanExpression searchCondition(QCommunityEntity community,
                                               String searchType, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return null;  // 전체 조회
        return switch (searchType != null ? searchType : "") {
            case "title" -> community.title.containsIgnoreCase(keyword);
            case "nickname" -> community.nickname.containsIgnoreCase(keyword);
            default -> null;
        };
    }
}
```

### 실제 코드 — 파일 검색 (동적 조건 조합)

```java
public class FileRepositoryCustomImpl implements FileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FileEntity> searchFiles(Long refId, FileEntity.RefType refType, FileEntity.Usage fileUsage) {
        return queryFactory
                .selectFrom(fileEntity)
                .where(
                        eqRefId(refId),          // null이면 조건 무시
                        eqRefType(refType),      // null이면 조건 무시
                        eqFileUsage(fileUsage)   // null이면 조건 무시
                )
                .fetch();
    }

    // 각 조건 메서드: null 반환 → QueryDSL where에서 자동 무시
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
```

### QueryDSL의 핵심 포인트

| 포인트 | 설명 |
|--------|------|
| **BooleanExpression이 null이면 where에서 무시** | 동적 쿼리의 핵심. if문 없이 조건 조합 가능 |
| **Q클래스** | Entity 기반 자동 생성. 필드를 Java 코드로 접근 |
| **type-safe** | 오타 시 컴파일 에러. JPQL 문자열의 런타임 오류 방지 |
| **조합 가능** | `.and()`, `.or()` 로 조건을 자유롭게 조합 |

---

## 6. application.yml 주요 JPA 설정

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: create     # 서버 시작 시 테이블 DROP 후 재생성 (개발용)
                             # update: 변경분만 반영 / validate: 검증만 (운영용)
    properties:
      hibernate:
        show_sql: true       # 실행되는 SQL 콘솔 출력 (개발 시 디버깅용)
        format_sql: true     # SQL 포맷팅 (가독성 향상)
        default_batch_fetch_size: 100  # N+1 문제 완화
    open-in-view: false      # OSIV 비활성화 (권장)
```

### ddl-auto 옵션 정리

| 옵션 | 동작 | 사용 시점 |
|------|------|----------|
| `create` | 기존 테이블 DROP 후 재생성 | 개발 초기 |
| `create-drop` | create + 종료 시 DROP | 테스트 |
| `update` | 변경분만 반영 (컬럼 추가 등) | 개발 중반 |
| `validate` | Entity와 테이블 일치하는지 검증만 | 운영 |
| `none` | 아무것도 안 함 | 운영 |

> ⚠️ 운영 환경에서는 절대 `create`나 `update` 사용 금지. 데이터 날아감.

---

## 7. 이 프로젝트에서의 JPA/QueryDSL 사용 현황

| Repository | JPA 기본 | 쿼리 메서드 | @Query JPQL | QueryDSL |
|-----------|---------|-----------|-------------|----------|
| `CommunityRepository` | save, findById, delete | - | - | ✅ searchCommunity (동적 검색) |
| `UserRepository` | save, findById | findByUsername | - | - |
| `CommentRepository` | save, findById, delete | - | ✅ findByCommunityId, deleteByCommunityIds | - |
| `FileRepository` | save, findById, deleteAll | findByRefIdInAndRefType, findByRefIdAndUpdatedAtBefore | ✅ updateRefIdForIds, detachFiles, detachFilesByRefIds | ✅ searchFiles (동적 조건) |
