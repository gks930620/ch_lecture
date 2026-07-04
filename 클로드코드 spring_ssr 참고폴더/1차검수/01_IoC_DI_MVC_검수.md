# 검수: 개념_01_IoC_DI / 개념_02_MVC패턴 / 개념_00_목차 / index

> 대조 소스: `com/ioc/step1~4`, HomeController, CommunityController, UserController, CommentApiController, QuerydslConfig, PageResponse, templates/, application.yml

## 개념_01_IoC_DI.md

### [높음] 238행 — "순환 참조 컴파일 시점에 감지"는 기술적 오류
- **실제**: 생성자 주입의 순환 참조는 컴파일 시점이 아니라 **애플리케이션 구동(컨테이너가 Bean을 생성하는) 시점**에 `BeanCurrentlyInCreationException`으로 감지됨. 컴파일은 정상 통과함.
- **수정 제안**: "순환 참조를 앱 시작(컨테이너 구동) 시점에 감지"로 수정.

### [중간] 10, 26, 57, 102행 — 존재하지 않는 파일명을 소제목에 표기
- 소제목에 `Step1_DirectCreation.java`, `Step2_ConstructorInjection.java`, `Step3_Assembler.java`, `Step4_SpringContainer.java`로 적혀 있으나, 실제 코드는 `com/ioc/step1~4/` 패키지의 `Main.java` + 개별 클래스들(MemberService, AppAssembler, AppConfig 등)임. 문서 상단 7~8행의 "각 step 패키지의 Main.java 실행" 안내와도 자기모순.
- **수정 제안**: 소제목 괄호를 `(com.ioc.step1 — Main.java)` 등 실제 패키지/파일명으로 교체.

### [중간] 178행 — 존재하지 않는 클래스 `LocalFileStorage`를 @Component 예시로 제시
- 소스 전체 grep 0건. 프로젝트의 실제 @Component 단독 사용 예는 `LoginCheckInterceptor`.
- **수정 제안**: 실제 존재하는 클래스로 교체하거나 가상 예시임을 명시.

### [중간] 184-193행 — QuerydslConfig 예제가 실제 소스와 불일치
- 문서는 `jpaQueryFactory(EntityManager em)` 파라미터 주입, 실제 `QuerydslConfig.java`는 `@PersistenceContext` 필드 주입 + 무인자 메서드.
- **수정 제안**: 실제 코드에 맞춰 스니펫 수정 (또는 소스를 파라미터 주입으로 통일).

### [중간] 150-152행 — 컴파일 불가능한 예제
- `CommunityRepository repository = new CommunityRepositoryImpl();` — 실제 `CommunityRepositoryImpl`은 `CommunityRepositoryCustom`만 구현하므로 이 대입은 타입 오류. `CommunityRepository`의 구현체는 Spring Data가 런타임 생성.
- **수정 제안**: "개념 설명용 가상 코드" 명시 또는 step1의 `new MemberRepository()` 예제로 교체.

### [중간] 269-270행 — Bean 흐름도에서 Service 계층 생략
- 문서: `UserController └── UserRepository`. 실제: UserController는 `UserService`만 주입받고, UserRepository는 UserService가 주입받음 (3계층).
- **수정 제안**: `UserController └── UserService └── UserRepository`.

### [낮음] 267행 — "CommunityRepository └── JPAQueryFactory" 부정확
- JPAQueryFactory는 인터페이스인 CommunityRepository가 아니라 커스텀 구현체 `CommunityRepositoryImpl`에 주입됨.
- **수정 제안**: `CommunityRepositoryImpl(QueryDSL)`로 표기.

## 개념_02_MVC패턴.md

### [중간] 122-128행 — CommentApiController 예제가 실제 코드와 불일치
- 실제: `@RequestMapping("/api/communities/{communityId}/comments")`, 메서드 `getComments`, 반환 `ResponseEntity<PageResponse<CommentDTO>>`, 서비스 메서드 `getCommentsByCommunityId`. 문서의 `@GetMapping("/api/comments")`, `List<CommentDTO> list()`는 모두 다름.
- **수정 제안**: 실제 시그니처로 교체하거나 "단순화한 예시" 명시.

### [중간] 145-147, 152-155행 — CommunityController 예제 시그니처 불일치
- 실제 `list()`는 `@PageableDefault Pageable` + `searchType`/`keyword`를 받고, 실제 `write()`는 개별 `@RequestParam` + `MultipartFile` 목록 + `HttpSession`을 받음. 문서와 다름.
- **수정 제안**: 실제 코드 기준으로 수정하거나 "축약 버전" 표기.

### [중간] 240-242행 — "뷰 이름 반환 = forward"는 부정확
- Thymeleaf에서는 뷰 이름 반환 시 서블릿 forward가 아니라 ViewResolver가 찾은 View가 **직접 렌더링**됨. 진짜 forward는 `forward:` 접두사 사용 시 발생.
- **수정 제안**: "뷰 이름 반환 = 서버에서 바로 렌더링(URL 변경 없음), forward는 `forward:` 접두사 사용 시"로 정정.

### [낮음] 53, 79행 — Controller가 "실제 비즈니스 로직"을 수행한다고 서술
- 같은 문서 2절과 실제 코드 모두 Service에 위임하는 구조. 문서 내 자기모순.
- **수정 제안**: "요청 처리 후 Service에 비즈니스 로직 위임, 뷰 이름 반환".

### [낮음] 184행 — 반환 타입 불일치
- 문서는 `Page<CommunityDTO>`, 실제 `getCommunityList()`는 자체 정의 `PageResponse<CommunityDTO>` 반환.

### [낮음] 86-92행 — application.yml 스니펫이 실제 파일과 다름
- 실제 파일의 thymeleaf 항목은 `cache: false`뿐 (prefix/suffix는 자동 설정 기본값).
- **수정 제안**: "기본값(직접 쓰지 않아도 적용됨)" 문구 추가.

## 개념_00_목차.md

### [높음] 30행 — 존재하지 않는 파일 참조 `휘 공공데이터API, CORS 정리.md`
- 실제 파일명은 `공공데이터API, CORS 정리.md` ("휘 " 접두사 없음).

### [중간] 13-14행 — 개념_04, 개념_05 번호가 설명 없이 누락 (03 → 06)
- **수정 제안**: 번호 연속 재부여 또는 "04, 05는 통합/삭제됨" 각주.

### [낮음] 33행 — 작성자 메모 잔존: "프레임워크 라이브러리 차이정도는 해야겠군"
### [낮음] 18행 — "step1~4 학습 코드"가 IoC의 step1~4와 혼동 가능 → `com.aop.step1~4` 경로 명시 권장

## index.md

### [높음] 29행 — 깨진 링크
- `[휘 공공데이터API, CORS 정리.md](휘 공공데이터API, CORS 정리.md)` → 클릭 시 404. 나머지 17개 링크는 모두 정상 확인.

## 소스코드 측 참고

### [낮음] UserController.java:14 — 주석 불일치
- "`@RequiredArgsConstructor`: final 필드(**userRepository**)에 대한 생성자 자동 생성" — 실제 final 필드는 `userService`.

## 문제 없음 확인
- IoC step1~4 코드 내용 자체(MemberService/AppAssembler/AppConfig/AnnotationConfigApplicationContext, Step3↔Step4 비교표)는 실제 소스와 정확히 일치
- MVC 요청 흐름(DispatcherServlet → HandlerMapping → HandlerAdapter → ViewResolver → View), @Controller vs @RestController, PRG 패턴 설명 정확
- Thymeleaf 문법 표는 실제 list.html 사용 패턴과 일치, 문서에 언급된 뷰 템플릿 8개 모두 존재
