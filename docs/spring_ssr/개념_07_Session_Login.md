# 세션 로그인 (HttpSession)

---

## 1. 세션 기반 로그인 흐름

```
1. 사용자가 POST /login (username, password 입력)
2. Service에서 DB 조회 후 비밀번호 검증
3. 일치하면 Entity → LoginUserDTO 변환 후 session.setAttribute("loginUser", loginUserDTO)
4. 브라우저는 JSESSIONID 쿠키를 받아 저장
5. 이후 모든 요청에 JSESSIONID 자동 포함
6. 서버는 JSESSIONID로 세션을 찾아 loginUser(DTO) 꺼내 씀
```

> **Entity가 아닌 DTO를 세션에 저장하는 이유:**
> 1. Entity는 JPA가 관리하는 영속성 객체 — 세션에 넣으면 예기치 않은 동작 가능
> 2. password 같은 민감 정보가 세션에 저장되면 보안 위험
> 3. Entity 변경 시 세션에 영향 — DTO로 분리하면 독립적

---

## 2. 이 프로젝트 구현

```java
// UserController.java

/** 로그인 처리 */
@PostMapping("/login")
public String login(@RequestParam String username,
                    @RequestParam String password,
                    HttpSession session,
                    Model model) {

    // Service를 통해 로그인 처리 (Entity → DTO 변환은 Service 내부에서 수행)
    LoginUserDTO loginUser = userService.login(username, password);

    // 로그인 실패 (사용자 없거나 비밀번호 불일치)
    if (loginUser == null) {
        model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "login";
    }

    // DTO를 세션에 저장 (password 제외된 상태)
    session.setAttribute("loginUser", loginUser);
    return "redirect:/";
}

/** 로그아웃 */
@PostMapping("/logout")
public String logout(HttpSession session) {
    session.invalidate(); // 세션 전체 삭제
    return "redirect:/";
}
```

---

## 3. Thymeleaf에서 세션 사용

```html
<!-- 로그인 여부 확인 -->
<th:block th:if="${session.loginUser != null}">
    <span th:text="${session.loginUser.nickname}">닉네임</span>
    <form action="/logout" method="post">
        <button type="submit">로그아웃</button>
    </form>
</th:block>

<th:block th:if="${session.loginUser == null}">
    <a href="/login">로그인</a>
</th:block>

<!-- 로그인한 사용자에게만 버튼 표시 -->
<a href="/community/write" th:if="${session.loginUser != null}">글쓰기</a>
```

---

## 4. Controller에서 세션 사용

> ⚠️ 아래 두 방법은 **Interceptor 도입 전** Controller가 직접 로그인 체크하던 방식이다.  
> 이 프로젝트의 실제 `mypage()`에는 null 체크 코드가 없고, 로그인 체크는 아래 4-1의
> `LoginCheckInterceptor`가 공통으로 처리한다.

```java
// 방법 1: HttpSession 직접 주입 (Interceptor 도입 전 방식)
@GetMapping("/mypage")
public String mypage(HttpSession session, Model model) {
    LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("loginUser");
    if (loginUser == null) return "redirect:/login"; // 비로그인 차단
    model.addAttribute("user", loginUser);
    return "mypage";
}

// 방법 2: @SessionAttribute 사용 (Interceptor 도입 전 방식)
@GetMapping("/mypage")
public String mypage(@SessionAttribute(name = "loginUser", required = false)
                     LoginUserDTO loginUser, Model model) {
    if (loginUser == null) return "redirect:/login";
    model.addAttribute("user", loginUser);
    return "mypage";
}
```

---

## 4-1. 이 프로젝트의 실제 방식 — Interceptor로 공통 처리

로그인 체크를 Controller마다 반복하지 않고 `LoginCheckInterceptor`가 한 곳에서 처리한다.

```java
// LoginCheckInterceptor.java (실제 코드 요약)
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false); // 없으면 새로 만들지 않음
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect("/login");  // 비로그인 → 로그인 페이지로
            return false;                     // Controller 실행 차단
        }
        return true; // 로그인 확인 완료 → Controller 진행
    }
}
```

```java
// WebConfig.java — 적용 경로 등록 (실제 코드 요약)
registry.addInterceptor(loginCheckInterceptor)
        .addPathPatterns("/community/write", "/community/*/edit",
                         "/community/*/delete", "/mypage");   // 로그인 필요한 경로만 명시 등록
// 홈(/), 목록(/community), 상세(/community/{id}), 정적 리소스 등은
// 애초에 등록하지 않았으므로 exclude 없이도 Interceptor가 적용되지 않음
```

> ⚠️ **exclude 패턴 주의 (교육 포인트)**: `excludePathPatterns()`는 include보다 **우선 평가**된다.
> 예전 이 프로젝트에는 exclude에 상세 조회용 `/community/*`가 있었는데, 한 세그먼트 패턴이라
> `/community/write`에도 매칭되어 **글쓰기 로그인 체크가 무력화**되는 버그가 있었다
> (비로그인이 write 폼 접근 가능 → POST 시 NPE 500). include를 명시적으로 등록했다면
> exclude는 최소한만 쓰는 것이 안전하다.

덕분에 실제 `UserController.mypage()`에는 null 체크가 없다 — Interceptor를 통과했다면 반드시 로그인 상태이기 때문.

> **댓글 API(`/api/**`)는?** Interceptor에 등록하지 않고, `CommentApiController`가
> 자체적으로 세션을 체크한다. 단 **작성·수정·삭제** 시에만 체크해서 비로그인이면 **401 JSON**을
> 반환하고, **목록 조회(GET)는 비로그인도 200으로 허용**한다.
> (SSR 페이지는 redirect, API는 JSON 에러 — 응답 방식이 달라서 분리)

---

## 5. 세션 vs 쿠키 vs JWT 비교

| | 세션 (이 프로젝트) | 쿠키 | JWT |
|--|-------------------|------|-----|
| 저장 위치 | 서버 메모리 | 브라우저 | 브라우저 (토큰) |
| 서버 부하 | 있음 (세션 관리) | 없음 | 없음 |
| 보안 | 비교적 안전 | 탈취 위험 | 탈취 시 만료 전까지 유효 |
| 확장성 | 서버 여러 대면 공유 필요 | 좋음 | 좋음 (Stateless) |
| 적합한 상황 | SSR, 단일 서버 | 단순 설정값 | REST API, MSA |

---

## 6. 세션 설정 (application.yml)

이 프로젝트는 별도 설정 없이 **기본값(30분)** 을 그대로 사용한다.  
만료 시간을 바꾸려면 아래처럼 설정한다.

```yaml
server:
  servlet:
    session:
      timeout: 30m  # 세션 만료 시간 (기본 30분)
```

---

## 7. Spring Security와의 차이

이 프로젝트는 **직접 구현한 세션 로그인**이다.  
Spring Security를 쓰면 인증/인가를 프레임워크가 처리해준다.

| | 직접 구현 (이 프로젝트) | Spring Security |
|--|------------------------|----------------|
| 구현 난이도 | 쉬움 | 설정 복잡 |
| 기능 | 기본 로그인/로그아웃 | 권한 관리, OAuth2, JWT 등 |
| 학습 목적 | 세션 동작 원리 이해 | 실무 수준 인증/인가 |

