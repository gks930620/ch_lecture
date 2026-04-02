# 📚 spring_ssr 프로젝트 전체 정리

> Spring Boot 3.3 + Thymeleaf SSR 방식의 커뮤니티 게시판 프로젝트  
> MVC 패턴 기본기 + IoC/DI, AOP 개념 학습 + 게시글·댓글·파일 기능 구현

---

## 📁 프로젝트 구조 개요

```
spring_ssr/
├── 설계/                         ← md 강의 문서
├── src/main/java/com/
│   ├── ch/basic/                 ← 실제 애플리케이션 (com.ch.basic)
│   │   ├── DemoApplication.java
│   │   ├── HomeController.java
│   │   ├── community/            ← 게시글 (SSR)
│   │   │   ├── comment/          ← 댓글 (REST API)
│   │   │   ├── dto/
│   │   │   └── repository/
│   │   ├── user/                 ← 회원 (세션 로그인)
│   │   ├── file/                 ← 파일 (첨부파일 + 에디터 이미지)
│   │   ├── api/                  ← 공공데이터 API 프록시
│   │   └── common/               ← 공통 (config, exception, scheduler, dto)
│   ├── ioc/step1~4/              ← IoC/DI 개념 학습용 코드
│   └── aop/step1~4/              ← AOP 개념 학습용 코드
├── src/main/resources/
│   ├── application.yml
│   ├── data-*.sql                ← 초기 데이터
│   └── templates/                ← Thymeleaf 템플릿
└── build.gradle
```

### 기술 스택

| 항목 | 기술 |
|------|------|
| Framework | Spring Boot 3.3.4, Java 17 |
| View | Thymeleaf (SSR) |
| ORM | Spring Data JPA + QueryDSL 5.0 |
| DB | MariaDB |
| 빌드 | Gradle |
| 기타 | Lombok, spring-dotenv, Validation, AOP, DevTools |

---

## 📖 설계 문서 (md) ↔ 실제 코드 매칭 검증

### ✅ 일치하는 항목

| md 문서 | 실제 코드 | 상태 |
|---------|----------|------|
| `개념_01_IoC_DI.md` — step1~4 학습 흐름 | `com.ioc.step1~4` 패키지 존재, 각 step별 Main.java 있음 | ✅ 일치 |
| `개념_02_MVC패턴.md` — SSR 흐름, Controller/Model/View | `CommunityController`, `UserController`, `templates/` | ✅ 일치 |
| `개념_06_Transaction.md` — readOnly + @Transactional 패턴 | `CommunityService`, `CommentService`, `FileService` 모두 이 패턴 적용 | ✅ 일치 |
| `개념_07_Session_Login.md` — HttpSession 로그인 | `UserController.login()`, `LoginUserDTO` 세션 저장 | ✅ 일치 |
| `개념_08_Validation.md` — @Valid 설명 | `build.gradle`에 `spring-boot-starter-validation` 있음 | ✅ 일치 |
| `개념_09_Exception.md` — 커스텀 예외 계층 | `BusinessException` → `EntityNotFoundException`/`AccessDeniedException`/`BusinessRuleException`/`DuplicateResourceException` 존재 | ✅ 일치 |
| `개념_09_Exception.md` — GlobalExceptionHandler vs ApiExceptionHandler | `GlobalExceptionHandler(@ControllerAdvice)` + `ApiExceptionHandler(@RestControllerAdvice)` 분리 적용 | ✅ 일치 |
| `개념_10_AOP.md` — step1~4 학습 흐름 | `com.aop.step1~4` 패키지 존재, 각 step별 파일 구조 일치 | ✅ 일치 |
| `개념_11_Filter_Interceptor.md` — Interceptor 적용 | `LoginCheckInterceptor` + `WebConfig` 코드와 md 설명 일치 | ✅ 일치 |
| `프로젝트_구조_정리.md` — 댓글 REST API | `CommentApiController` 경로/메서드/응답 일치 | ✅ 일치 |
| `프로젝트_구조_정리.md` — 파일 처리 흐름 | `FileService`, `FileController`, `FileApiController` 동작 일치 | ✅ 일치 |
| `프로젝트_구조_정리.md` — 스케줄러 고아 파일 삭제 | `FileCleanupScheduler` (매일 새벽 4시, refId=0 + 24시간) 일치 | ✅ 일치 |
| `프로젝트_구조_정리.md` — 소프트/하드 삭제 정책 | User(소프트, `@SQLRestriction`), Community/Comment(하드), File(고아→스케줄러) 일치 | ✅ 일치 |
| `프로젝트_구조_정리.md` — 게시글 삭제 흐름 | `CommunityController.delete()`: 파일고아→댓글삭제→게시글삭제 순서 일치 | ✅ 일치 |

---

### ⚠️ md 문서 불일치 사항 — 수정 완료

아래 항목들은 모두 **수정 완료**됨.

| # | 파일 | 수정 내용 |
|---|------|----------|
| 1 | `개념_00_목차.md` | 존재하지 않는 `04_JPA_Repository`, `05_QueryDSL`, `12_Security` 제거. 실제 `12_스케쥴러` 추가. 참고 문서(서비스설계, 파일테이블설계 등) 섹션 추가 |
| 2 | `개념_07_Session_Login.md` | `UserEntity` → `LoginUserDTO`로 수정 (Entity를 세션에 저장하지 않는 이유 설명 추가) |
| 3 | `개념_08_Validation.md` | 현재 코드는 `@RequestParam` 방식이지만, 문서는 `@Valid` 정석 사용법 정리 목적임을 안내 추가 |
| 4 | `개념_01_IoC_DI.md` | 패키지 `com.test.test` → `com.ch.basic` |
| 5 | `개념_10_AOP.md` | 패키지 `com.test.test` → `com.ch.basic` (10건 전체 치환) |
| 6 | `개념_09_Exception.md` | `@ControllerAdvice` → `@ControllerAdvice(annotations = Controller.class)`, `@RestControllerAdvice` → `@RestControllerAdvice(annotations = RestController.class)`, `findByIdAndIsDeletedFalse` → `findById`, `@Slf4j` 제거 |

### 📌 남아있는 미작성 항목

모든 개념 문서 작성 완료.

---

## 🔧 실제 구현된 기능 정리

### 1. 게시글 (SSR — `@Controller`)

| 기능 | URL | HTTP | 처리 |
|------|-----|------|------|
| 목록 (검색+페이징) | `/community` | GET | QueryDSL 동적 쿼리, `PageResponse` |
| 작성 폼 | `/community/write` | GET | 로그인 필수 (Interceptor) |
| 작성 처리 | `/community/write` | POST | 글 등록 → 에디터이미지 연결 → 첨부파일 저장 → redirect |
| 상세 | `/community/{id}` | GET | 조회수 증가, 댓글·파일은 JS에서 API 호출 |
| 수정 폼 | `/community/{id}/edit` | GET | 로그인 필수 |
| 수정 처리 | `/community/{id}/edit` | POST | 글 수정 → 에디터이미지 동기화 → 파일삭제 → 새파일저장 |
| 삭제 | `/community/{id}/delete` | POST | 파일고아처리 → 댓글삭제 → 게시글삭제 |

### 2. 댓글 (REST API — `@RestController`)

| 기능 | URL | HTTP | 응답 |
|------|-----|------|------|
| 목록 | `GET /api/communities/{id}/comments?page=0&size=10` | GET | JSON (PageResponse) |
| 작성 | `POST /api/communities/{id}/comments` | POST | JSON (201 Created) |
| 수정 | `PUT /api/communities/{id}/comments/{commentId}` | PUT | JSON |
| 삭제 | `DELETE /api/communities/{id}/comments/{commentId}` | DELETE | JSON |

### 3. 파일

| 기능 | Controller | URL | 용도 |
|------|-----------|-----|------|
| 다운로드 | `FileController` (@Controller) | `GET /files/download/{fileId}` | 첨부파일 다운로드 |
| 이미지 서빙 | `FileController` (@Controller) | `GET /uploads/{filename}` | 에디터 본문 이미지 |
| 목록 조회 | `FileApiController` (@RestController) | `GET /api/files?refId=&refType=&usage=` | JS fetch |
| 업로드 | `FileApiController` (@RestController) | `POST /api/files/upload` | 에디터 이미지 즉시 업로드 |

### 4. 회원

| 기능 | URL | HTTP |
|------|-----|------|
| 로그인 폼 | `/login` | GET |
| 로그인 처리 | `/login` | POST |
| 로그아웃 | `/logout` | POST |
| 회원가입 폼 | `/signup` | GET |
| 회원가입 처리 | `/signup` | POST |
| 마이페이지 | `/mypage` | GET |

### 5. 공공데이터 API 프록시

| 기능 | URL |
|------|-----|
| API 테스트 페이지 | `GET /api/test` → `templates/api.html` |
| 시군구별 관광기후지수 | `GET /api/proxy/city-climate` |
| 동네예보 | `GET /api/proxy/tour-forecast` |

### 6. 개념 학습용 코드 (standalone)

| 패키지 | 내용 |
|--------|------|
| `com.ioc.step1` | 직접 객체 생성 → 강한 결합 |
| `com.ioc.step2` | 인터페이스 + 생성자 주입 → IoC 시작 |
| `com.ioc.step3` | Assembler 도입 → IoC 컨테이너 원형 |
| `com.ioc.step4` | Spring 컨테이너 (@Configuration + @Bean) |
| `com.aop.step1` | AOP 없이 — 부가 로직 중복 |
| `com.aop.step2` | 프록시 패턴 — 수동 분리 |
| `com.aop.step3` | JDK Dynamic Proxy — 자동 프록시 |
| `com.aop.step4` | Spring AOP — @Aspect + Pointcut |

---

## 📊 아키텍처 요약

```
[브라우저]
   │
   ├─ SSR 요청 (GET/POST form) ──→ [LoginCheckInterceptor] ──→ [@Controller]
   │                                                              │
   │                                                    CommunityController
   │                                                    UserController
   │                                                    FileController
   │                                                              │
   │                                                      ┌──────┴──────┐
   │                                                  [Service]    [Service]
   │                                                      │            │
   │                                              [Repository]  [Repository]
   │                                              (JPA+QueryDSL)
   │
   ├─ REST API (JS fetch) ──→ [@RestController]
   │                           CommentApiController  (자체 세션 체크 → 401 JSON)
   │                           FileApiController
   │                           ApiProxyController    (공공API 프록시)
   │
   │  예외 발생 시:
   │   @Controller  → GlobalExceptionHandler  → error.html
   │   @RestController → ApiExceptionHandler  → JSON ErrorResponse
   │
   └─ 스케줄러
       FileCleanupScheduler (매일 04:00 — 고아 파일 물리삭제 + DB삭제)
```

---

## ✅ 수정 완료 요약

| # | 위치 | 수정 내용 | 상태 |
|---|------|----------|------|
| 1 | `개념_00_목차.md` | `04_JPA_Repository`, `05_QueryDSL`, `12_Security` 제거, `12_스케쥴러` 및 참고 문서 추가 | ✅ 완료 |
| 2 | `개념_07_Session_Login.md` | `UserEntity` → `LoginUserDTO`로 전체 수정 | ✅ 완료 |
| 3 | `개념_08_Validation.md` | 현재 코드는 `@RequestParam`, 문서는 정석 사용법 목적임을 안내 추가 | ✅ 완료 |
| 4 | `개념_01_IoC_DI.md` | 패키지 `com.test.test` → `com.ch.basic` | ✅ 완료 |
| 5 | `개념_10_AOP.md` | 패키지 `com.test.test` → `com.ch.basic` (10건) | ✅ 완료 |
| 6 | `개념_09_Exception.md` | `@ControllerAdvice(annotations=...)` 추가, `findById` 수정, `@Slf4j` 제거 | ✅ 완료 |

### 📌 남아있는 미작성 항목

| 파일 | 상태 |
|------|------|
| `개념_03_JPA_Entity.md` | 메모만 있음 (내용 미작성) |
| `개념_12_스케쥴러.md` | 메모만 있음 (상세 내용은 `프로젝트_구조_정리.md`에 있음) |

---

## 📝 md 문서별 상태 총정리

| 파일 | 내용 충실도 | 코드 일치 | 비고 |
|------|-----------|----------|------|
| `개념_00_목차.md` | ★★★★★ | ✅ | 수정 완료 — 실제 파일과 일치, 참고 문서 섹션 추가 |
| `개념_01_IoC_DI.md` | ★★★★★ | ✅ | 수정 완료 — 패키지명 `com.ch.basic` |
| `개념_02_MVC패턴.md` | ★★★★★ | ✅ | MVC 상세 흐름도, SSR vs CSR 비교 |
| `개념_03_JPA_Entity.md` | ★★★★★ | ✅ | JPA, Entity, Repository, QueryDSL 개념 + 이 프로젝트 실제 코드 기반 설명 |
| `개념_06_Transaction.md` | ★★★★★ | ✅ | readOnly, 전파, 내부호출 주의사항 |
| `개념_07_Session_Login.md` | ★★★★★ | ✅ | 수정 완료 — LoginUserDTO 기준, Entity 미저장 이유 설명 |
| `개념_08_Validation.md` | ★★★★☆ | ✅ | 정석 사용법 정리 목적, 현재 코드와 차이 안내됨 |
| `개념_09_Exception.md` | ★★★★★ | ✅ | 수정 완료 — annotations 매개변수, findById |
| `개념_10_AOP.md` | ★★★★★ | ✅ | 수정 완료 — 패키지명 `com.ch.basic` |
| `개념_11_Filter_Interceptor.md` | ★★★★★ | ✅ | 실제 코드 인용, 적용 범위 정확 |
| `개념_12_스케쥴러.md` | ★★★★★ | ✅ | @Scheduled, cron, FileCleanupScheduler 적용 코드 기반 설명 |
| `프로젝트_구조_정리.md` | ★★★★★ | ✅ | 전체 설계 상세 문서 (참고/상식용) |
| `서비스설계_정리.md` | ★★★★★ | - | Facade 패턴, 계층 구조 (참고/상식용) |
| `파일테이블설계_정리.md` | ★★★★★ | - | 통합 vs 분리, 실무 기준 (참고/상식용) |
| `테스트코드_정리.md` | ★★★★☆ | - | SSR 테스트 가성비 분석 (참고/상식용) |
| `협업.md` | ★★☆☆☆ | - | 간략 메모 수준 |
| `휘 공공데이터API, CORS 정리.md` | ★★★★★ | ✅ | CORS 동작원리, CSRF, 서버 설정별 비교표 |

