# Filter vs Interceptor

> ⚠️ 이 프로젝트에 코드 없음 - 개념만 정리

---

## 1. 요청 처리 흐름

```
HTTP 요청
    ↓
[Filter 1]           ← javax.servlet (Spring 밖)
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
@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

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
// WebConfig.java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/community/write",    // 적용 경로
                                 "/community/*/edit",
                                 "/community/*/delete")
                .excludePathPatterns("/login", "/signup"); // 제외 경로
    }
}
```

---

## 4. Filter vs Interceptor 비교

| | Filter | Interceptor |
|--|--------|------------|
| 위치 | Servlet 컨테이너 (Spring 밖) | Spring MVC (Spring 안) |
| Spring Bean 사용 | 어렵 (가능은 함) | 가능 |
| 적용 범위 | 모든 요청 (정적 파일 포함) | DispatcherServlet 이후 요청만 |
| 주요 용도 | 인코딩, CORS, XSS | 로그인 체크, 공통 데이터 처리 |
| 예외 처리 | 직접 처리 | `@ControllerAdvice` 사용 가능 |

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
        UserEntity loginUser = (session != null)
                ? (UserEntity) session.getAttribute("loginUser") : null;
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
            "/mypage"                 // 마이페이지
        )
        .excludePathPatterns("/login", "/signup", "/", "/community", "/community/*");
// ※ 댓글 API(/api/**)는 CommentApiController에서 자체 인증 처리 (401 JSON 응답)
```

### 적용 전 vs 적용 후

```
적용 전: Controller마다 session null 체크 반복 (중복, 빠뜨릴 위험)
적용 후: Interceptor 한 곳에서 일괄 차단 → Controller는 비즈니스 로직에만 집중
```

