# Filter vs Interceptor

> ⚠️ **Filter**는 이 프로젝트에 코드 없음(개념만 정리) / **Interceptor**는 `LoginCheckInterceptor`로 실제 적용되어 있음 (5장 참고)

---

## 1. 요청 처리 흐름

```
HTTP 요청
    ↓
[Filter 1]           ← jakarta.servlet (구 javax.servlet, Spring 밖)
[Filter 2]
    ↓
DispatcherServlet    ← 여기서부터 Spring 영역
    ↓
[Interceptor 1]      ← Spring MVC
[Interceptor 2]
    ↓
Controller
    ↓
[Interceptor - postHandle]
    ↓
View 렌더링
    ↓
[Interceptor - afterCompletion]
    ↓
[Filter - doFilter 이후]
    ↓
HTTP 응답
```

---

## 2. Filter

**Servlet 스펙** - Spring과 무관하게 동작.  
DispatcherServlet **앞**에서 실행.

```java
@Component
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.info("요청 URI: {}", httpRequest.getRequestURI());

        chain.doFilter(request, response); // 다음 필터 or Servlet으로 전달

        log.info("응답 완료");
    }
}
```

### Filter 활용 예시

- 인코딩 설정 (CharacterEncodingFilter - Spring이 기본 제공)
- CORS 처리
- XSS 방어 (요청 데이터 치환)
- 모든 요청/응답 로깅

---

## 3. Interceptor

**Spring MVC** - Spring Bean 사용 가능.  
Controller **앞/뒤**에서 실행.

```java
// Interceptor의 3개 메서드 구조를 보여주는 예시 (실제 적용 코드는 5장 참고)
@Component
public class MyInterceptor implements HandlerInterceptor {

    // Controller 실행 전
    @Override
    public boolean preHandle(HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            // 비로그인 → 로그인 페이지로 리다이렉트
            response.sendRedirect("/login");
            return false; // false 반환 시 Controller 실행 안 됨
        }
        return true; // true 반환 시 계속 진행
    }

    // Controller 실행 후, View 렌더링 전
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {
        // 모델에 공통 데이터 추가 등
    }

    // View 렌더링 후 (예외 발생해도 실행)
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) {
        // 리소스 정리, 로깅 등
    }
}
```

### Interceptor 등록

```java
// WebConfig.java (등록 방법 예시 — 실제 등록 코드는 5장 참고)
@Configuration
@RequiredArgsConstructor  // final 필드 생성자 주입
public class WebConfig implements WebMvcConfigurer {

    private final MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/admin/**")              // 적용 경로
                .excludePathPatterns("/admin/login");      // 제외 경로
    }
}
```

> ⚠️ `excludePathPatterns()`는 include보다 **우선 평가**되고, `/community/*` 같은 한 세그먼트
> 패턴은 `/community/write`에도 매칭된다. exclude를 잘못 넓게 잡으면 로그인 체크가
> 무력화될 수 있으므로 주의 (실제 사례는 개념_07 4-1장 참고).

---

## 4. Filter vs Interceptor 비교

| | Filter | Interceptor |
|--|--------|------------|
| 위치 | Servlet 컨테이너 (Spring 밖) | Spring MVC (Spring 안) |
| Spring Bean 사용 | 서블릿 컨테이너에 직접 등록 시 어려움 / Spring Boot에서 `@Component`·`FilterRegistrationBean`으로 등록하면 가능 (2장 예시가 `@Component` Filter) | 가능 |
| 적용 범위 | 모든 요청 (정적 파일 포함) | DispatcherServlet 이후 요청만 ※ |
| 주요 용도 | 인코딩, CORS, XSS | 로그인 체크, 공통 데이터 처리 |
| 예외 처리 | 직접 처리 | `@ControllerAdvice` 사용 가능 |

> ※ Spring Boot에서는 css/js 같은 정적 리소스도 DispatcherServlet을 거치므로 **Interceptor가 적용될 수 있다**.  
> 그래서 `addPathPatterns("/**")`처럼 넓게 걸 때는 `/css/**`, `/js/**`를 exclude에 넣어야 한다.  
> (이 프로젝트는 로그인이 필요한 4개 경로만 명시적으로 등록하므로 exclude 없이도 정적 리소스에 적용되지 않음 — 5장 참고)

---

## 5. 이 프로젝트에 적용됨

> 📁 `LoginCheckInterceptor.java`, `WebConfig.java`

```java
// LoginCheckInterceptor.java - 세션에 loginUser가 없으면 /login으로 리다이렉트
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        // 세션에는 Entity가 아니라 LoginUserDTO를 저장해 둠 (개념_07 참고)
        LoginUserDTO loginUser = (session != null)
                ? (LoginUserDTO) session.getAttribute("loginUser") : null;
        if (loginUser == null) {
            response.sendRedirect("/login");
            return false;   // Controller 진입 차단
        }
        return true;
    }
}
```

```java
// WebConfig.java - Interceptor 등록
registry.addInterceptor(loginCheckInterceptor)
        .addPathPatterns(
            "/community/write",       // 글쓰기
            "/community/*/edit",      // 수정
            "/community/*/delete",    // 삭제
            "/mypage"                 // 마이페이지 → 로그인 필요한 경로만 명시 등록
        );
// ※ 홈(/), 목록, 상세, 정적 리소스 등은 등록하지 않았으므로 exclude 없이도 적용 안 됨
//    (exclude는 include보다 우선 평가되므로 잘못 넓게 잡으면 로그인 체크가 뚫림 — 개념_07 4-1 참고)
// ※ 댓글 API(/api/**)는 CommentApiController에서 자체 인증 처리 (401 JSON 응답)
```

### 적용 전 vs 적용 후

```
적용 전: Controller마다 session null 체크 반복 (중복, 빠뜨릴 위험)
적용 후: Interceptor 한 곳에서 일괄 차단 → Controller는 비즈니스 로직에만 집중
```

