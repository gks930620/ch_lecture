# 검수: 개념_09_Exception / 개념_11_Filter_Interceptor / 개념_12_스케쥴러

> 대조 소스: GlobalExceptionHandler, ApiExceptionHandler, BusinessException 계열 5종, ErrorResponse, LoginCheckInterceptor, WebConfig, FileCleanupScheduler, DemoApplication, error.html, CommunityService, FileService

## 개념_09_Exception.md

### [높음] 61행 — 권한 체크 조건문이 실제 코드와 논리가 반대 (검수자 직접 재확인 완료)
- 문서: `if (username != null && !community.isWrittenBy(username))` — username이 null이면 권한 체크를 **통과**시킴. 즉 문서대로 따라 치면 **비로그인 사용자가 수정 가능한 보안 결함 코드**가 됨.
- 실제 CommunityService.java:82: `if (username == null || !community.isWrittenBy(username))` — null이면 403.
- **수정 제안**: 문서 조건을 실제 코드와 동일하게 `username == null ||`로 반드시 수정.

### [중간] 74-96행 — GlobalExceptionHandler 예시가 4개 핸들러 중 2개만 제시 + 우선순위 설명 없음
- 실제로는 `EntityNotFoundException(404)`, `AccessDeniedException(403)`, `BusinessException(400)`, `Exception(500)` 4개. 문서 1장 계층도에서 예외 4종을 소개해놓고 3장에는 404/500만 있어 "403, 400은 누가 처리하지?"라는 의문 발생. "구체적인 예외부터 먼저 매칭"이라는 핵심 원리(실제 코드 주석에 있음)도 문서에 없음.
- **수정 제안**: 4개 핸들러 모두 싣거나, 구체적 타입 우선 매칭 설명 추가.

### [중간] 76-78행 — "@Controller에서 발생하는 예외**만** 잡음"은 기술적으로 부정확
- `@RestController`는 `@Controller`를 메타 어노테이션으로 포함하므로 `@ControllerAdvice(annotations = Controller.class)`는 @RestController 클래스에도 후보로 매칭됨. 이 프로젝트가 정상 동작하는 것은 advice 빈 순서 덕분. (실제 코드 GlobalExceptionHandler.java:14-15 주석도 같은 부정확한 서술.)
- **수정 제안**: "만 잡음" 단정을 피하고 "이 프로젝트에서는 SSR 예외를 GlobalExceptionHandler가, API 예외를 ApiExceptionHandler가 나눠 처리하도록 구성" 정도로 완화. 정확히 하려면 `basePackages` 분리 언급.

### [낮음] 114-118행 — ApiExceptionHandler 예시가 실제 코드와 다름 (단순화 미표기)
- 문서는 `new ErrorResponse(404, "NOT_FOUND", e.getMessage())` 하드코딩. 실제는 `HttpStatus.NOT_FOUND.value()`, `e.getErrorCode()` 사용, 핸들러 4개. "일부 발췌·단순화" 표기 권장.

### [낮음] 49-55행 — getCommunityDetail 인용이 실제와 차이
- 실제는 `@Transactional` + `incrementViewCount()` 포함, 파라미터명 `communityId`. 발췌·단순화 표시 필요.

## 개념_11_Filter_Interceptor.md

### [높음] 3행 — 머리말 "⚠️ 이 프로젝트에 코드 없음 - 개념만 정리"가 문서 5장 및 실제 코드와 정면 모순 (검수자 직접 재확인 완료)
- `LoginCheckInterceptor.java`, `WebConfig.java`가 실제 존재하고 같은 문서 5장("## 5. 이 프로젝트에 적용됨")에서 직접 인용까지 함. 프로젝트에 없는 것은 Filter뿐.
- **수정 제안**: "⚠️ Filter는 이 프로젝트에 코드 없음(개념만) / Interceptor는 LoginCheckInterceptor로 실제 적용됨"으로 수정.

### [중간] 158-159행 — "실제 코드" 인용의 세션 캐스팅 타입이 틀림
- 문서: `UserEntity loginUser = (UserEntity) session.getAttribute("loginUser")`. 실제 LoginCheckInterceptor.java:56: `LoginUserDTO loginUser = (LoginUserDTO) ...`.
- Entity 대신 DTO를 세션에 담는 것 자체가 이 프로젝트의 교육 포인트라 타입 불일치가 특히 유해. **`LoginUserDTO`로 수정.**

### [낮음] 178행 — WebConfig 인용에서 excludePathPatterns 일부 누락
- 실제 WebConfig.java:65-71은 `/uploads/**`, `/css/**`, `/js/**`도 제외 경로에 포함.

### [낮음] 139행 — "Interceptor: DispatcherServlet 이후 요청만"이 정적 파일에 대해 오해 유발
- Spring Boot에서는 정적 리소스도 DispatcherServlet(ResourceHttpRequestHandler)을 거치므로 Interceptor가 적용됨 — 실제로 이 프로젝트 WebConfig가 `/css/**`, `/js/**`를 exclude해야 했던 이유. 각주 권장.

### [낮음] 12행 — "javax.servlet" 표기 → 이 프로젝트(Boot 3)는 `jakarta.servlet`. "jakarta.servlet (구 javax.servlet)" 권장.

### [낮음] 73-75, 113-128행 — 3장 예시 코드의 혼란 요소
- ① final 필드가 없는데 `@RequiredArgsConstructor` 부착 ② 3장 예시가 실제 클래스와 같은 이름(LoginCheckInterceptor)인데 내용이 5장 "실제 코드"와 달라 어느 쪽이 진짜인지 혼동 ③ 3장 WebConfig 예시는 `private final` 필드만 있고 생성자 주입 수단이 없어 컴파일 불가.
- **수정 제안**: ① 어노테이션 제거 ② 예시 클래스명을 `MyInterceptor` 등으로 변경 ③ `@RequiredArgsConstructor` 추가.

## 개념_12_스케쥴러.md

cron 표현식 전수 계산 검증 결과 **모두 정확**: `0 0 4 * * *`=매일 04:00(실제 FileCleanupScheduler.java:23과 일치), 매초/10초/9-18시/9,18시/월요일 자정/0,30분/평일 예시 전부 해석 일치. 요일 "0-7 (0,7=일)" 스펙 일치. fixedRate(시작~시작) vs fixedDelay(종료~시작) 정확. @EnableScheduling — DemoApplication.java:15 실제 존재. FileCleanupScheduler 인용 코드 실제와 일치.

### [낮음] 파일명 — "스케**쥴**러" vs 본문 전체 "스케**줄**러" 표기 불일치
- 파일명을 `개념_12_스케줄러.md`로 변경 권장 (링크 걸린 곳 함께 수정).

### [낮음] 166-184행 — FileService 인용 스니펫이 그대로는 동작하지 않는 형태
- 실제 FileService.java:189의 `Path uploadPath = ...` 선언과 try-catch가 생략되어 스니펫만 보면 `uploadPath`가 미정의 변수. 선언 한 줄 포함 또는 "(일부 생략)" 주석.
