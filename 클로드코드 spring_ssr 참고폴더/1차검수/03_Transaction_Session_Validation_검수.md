# 검수: 개념_06_Transaction / 개념_07_Session_Login / 개념_08_Validation

> 대조 소스: CommunityService, CommentService, UserService, FileService, UserController, LoginUserDTO, LoginCheckInterceptor, WebConfig, 각 DTO(Validation), CommentApiController, build.gradle, application.yml, login.html, signup.html

## 개념_06_Transaction.md

### [높음] 55-60행 — 작성 중이던 초안 메모가 강의 자료에 그대로 노출
- "전파는 빼자..", "기본적으로 service1에서 service2 호출. ... 어쨋든 @Trasactional 있는 다른 service호출할 때는 주의" — 비문·반말 메모 + 오타 2건(`@Trasactional`→`@Transactional`, "어쨋든"→"어쨌든"). "전파는 빼자"라고 해놓고 바로 아래에 전파 옵션 표가 이어져 매우 혼란스러움.
- **수정 제안**: 메모 삭제 후 정식 문장으로 교체. 예: "REQUIRED(기본값)에서는 Service A가 B를 호출하면 하나의 트랜잭션에 묶여, B에서 예외가 나면 A의 작업까지 함께 롤백된다. 메일 발송 실패 기록처럼 메인 로직과 무관하게 남겨야 하는 작업은 REQUIRES_NEW를 고려한다."

### [중간] 130-132행 — `getCommunityDetail`을 "조회 → readOnly 상속"으로 분류했으나 실제와 불일치
- 실제 CommunityService.java:60-61에서는 `@Transactional`(쓰기)이 붙어 있음 — 조회수 증가(`incrementViewCount()`)가 dirty checking으로 UPDATE를 발생시키기 때문.
- **수정 제안**: 쓰기 트랜잭션 쪽으로 옮기고 "조회 메서드지만 조회수 증가(UPDATE)가 있어 readOnly 해제" 설명 추가 — readOnly 개념을 가르치기 좋은 실제 사례.

### [중간] 70-75행 — REQUIRES_NEW 예시 주석이 조건 누락으로 부정확
- "로그 저장 실패해도 외부 트랜잭션은 그대로 진행" — REQUIRES_NEW라도 내부 예외가 호출자까지 전파되면 외부도 롤백됨. 호출하는 쪽에서 try-catch로 잡아야만 외부가 계속 진행됨.
- **수정 제안**: "단, 호출하는 쪽에서 예외를 try-catch로 잡아야 외부 트랜잭션이 영향받지 않음" 한 줄 추가.

### [낮음] 28, 31, 131행 — 예시 시그니처가 실제 코드와 불일치
- 실제 `getCommunityList`는 `PageResponse<CommunityDTO>` 반환, `createCommunity`는 `(createDTO, userId, username, nickname)` 4개 파라미터. 특히 "이 프로젝트에서의 패턴" 섹션은 실제 시그니처와 맞추거나 단순화 명시 필요.

## 개념_07_Session_Login.md

### [중간] 82-99행 (4장 전체) — 이 프로젝트의 핵심인 LoginCheckInterceptor가 문서에 전혀 없음
- 4장 "방법 1"은 mypage에서 Controller가 직접 null 체크하는 코드를 보여주는데, 실제 UserController.java:118-131의 mypage는 null 체크가 제거되어 있고(주석: "로그인 체크는 LoginCheckInterceptor에서 처리") LoginCheckInterceptor.java:48-61이 공통 처리, WebConfig.java:53-71에서 `/community/write`, `/community/*/edit`, `/community/*/delete`, `/mypage`에 적용 — 문서와 실제 코드가 반대.
- **수정 제안**: "Interceptor로 공통 처리" 섹션 추가, 4장 예시는 "Interceptor 도입 전 방식"임을 명시. 댓글 API(/api/**)는 CommentApiController가 자체 세션 체크 후 401 JSON 반환한다는 점도 추가하면 좋음.

### [낮음] 116-123행 — "6. 세션 설정 (application.yml)"이 프로젝트에 있는 설정처럼 읽힘
- 실제 application.yml에는 `server.servlet.session.timeout` 설정 없음 (기본 30분 사용 중).
- **수정 제안**: "이 프로젝트는 기본값(30분)을 그대로 사용하며, 변경하려면 아래처럼 설정한다"로 수정.

## 개념_08_Validation.md

### [중간] 19-31행 — `// CommunityCreateDTO.java`라고 실제 파일 인용처럼 표기했으나 내용이 다름
- 실제 CommunityCreateDTO.java:23-27의 메시지는 "제목은 필수입니다", "제목은 200자 이하여야 합니다", "내용은 필수입니다"이고 `@Setter`, `@Builder`도 있음. 문서는 "제목을 입력해주세요." 등 다른 메시지.
- **수정 제안**: 실제 파일 내용과 일치시키거나 일반 예시임을 명시.

### [낮음] 47행 — 예시 호출 `createCommunity(createDTO, null)`이 실제 시그니처(4개 파라미터)와 불일치
### [낮음] 3-4행 — 상단 주의문에 "프로젝트 전체에서 @Valid가 실제 실행되는 곳이 없다"는 사실 누락
- CommentCreateDTO/CommentUpdateDTO에 validation 애노테이션이 있으나 CommentApiController는 `@RequestBody Map<String, String>`으로 받아 수동 검증(`content.isBlank()`). 게시글도 @RequestParam 개별 수신. 즉 애노테이션은 선언만 되어 있고 동작하지 않음 — "애노테이션만 붙이면 검증된다"는 오해 방지 문구 필요.

## 문제 없음 확인
- 롤백 규칙(RuntimeException/Error만 기본 롤백, checked 미롤백, rollbackFor), 자기호출(프록시 미경유) 문제, readOnly 효과 설명 — 정확
- 클래스 레벨 `@Transactional(readOnly = true)` + 쓰기 메서드 오버라이드 패턴 — 4개 서비스 모두와 일치
- 세션 키 `"loginUser"` — UserController/LoginCheckInterceptor/CommentApiController 모두 일치
- login/logout 인용 코드 — 실제 UserController.java:45-77과 사실상 동일
- LoginUserDTO(password 제외, Serializable) 및 "Entity 대신 DTO 저장 이유" — 실제 구현과 일치
- 세션/쿠키/JWT 비교표, JSESSIONID 흐름 — 정확
- `spring-boot-starter-validation` 의존성 build.gradle:41 존재, BindingResult 위치 규칙, @NotBlank/@NotEmpty/@NotNull 구분, Thymeleaf `#fields.hasErrors`/`th:errors` — 정확
