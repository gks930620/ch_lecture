# Spring Security — 세션 로그인 방식 정리


---

## 1. SecurityConfig (보안 설정)

Spring Security의 모든 보안 정책을 한 곳에서 설정하는 클래스.

```java
@Configuration       // Spring 설정 파일
@EnableWebSecurity   // Security 보안 설정 활성화 (Boot 3에선 생략 가능하지만 명시 권장)
public class SecurityConfig {

    @Bean  // 비밀번호를 BCrypt로 인코딩해서 DB에 저장, 비교 시 자동 디코딩
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // ── URL별 접근 권한 설정 ──
        http.authorizeHttpRequests((auth) -> auth
            .requestMatchers("/", "/login", "/signup").permitAll()       // 비로그인 허용
            .requestMatchers("/admin").hasAuthority("ADMIN")            // ADMIN만
            .requestMatchers("/mypage").hasAnyAuthority("ADMIN", "USER") // ADMIN 또는 USER
            .anyRequest().permitAll()                                    // 나머지는 허용
        );

        // ── 로그인 설정 ──
        http.formLogin((auth) -> auth
            .loginPage("/login")              // 로그인 페이지 URL (직접 Controller 만들어야 함)
            .loginProcessingUrl("/loginProc") // form action URL (Controller 불필요 — Security가 처리)
            .defaultSuccessUrl("/")           // 로그인 성공 후 리다이렉트
            .failureUrl("/login?error")       // 로그인 실패 후 리다이렉트
            .permitAll()
        );

        // ── 로그아웃 설정 ──
        http.logout((auth) -> auth
            .logoutUrl("/logout")         // 로그아웃 요청 URL (Controller 불필요)
            .logoutSuccessUrl("/")        // 로그아웃 후 리다이렉트
        );

        http.csrf((auth) -> auth.disable());  // CSRF 비활성화 (별도 설명)
        return http.build();
    }
}
```

### 핵심 포인트

| 설정 | 의미 |
|------|------|
| `loginPage("/login")` | 로그인 폼을 보여줄 URL → `@GetMapping("/login")` Controller 필요 |
| `loginProcessingUrl("/loginProc")` | form의 `action` URL → **Controller 없음**, Security가 자동 처리 |
| `failureUrl("/login?error")` | 로그인 실패 시 리다이렉트 URL → Controller에서 error 파라미터로 에러 메시지 표시 |
| `logoutUrl("/logout")` | 로그아웃 URL → **Controller 없음**, Security가 자동 처리 |
| `permitAll()` | 로그인 페이지 자체는 비로그인 상태에서도 접근 가능 |

---

## 2. 로그인 페이지 지정

### UserController — 로그인 페이지 반환

```java
@Controller
public class UserController {
    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return "login";  // login.html 렌더링
    }
}
```

> `@GetMapping("/login")`만 있으면 된다.  
> `@PostMapping("/loginProc")`은 만들지 않는다 — 로그인 처리는 Security가 한다.  
> `error` 파라미터는 SecurityConfig의 `failureUrl("/login?error")`에서 전달된다.

### login.html — form action이 핵심

```html
<form action="/loginProc" method="post">
    <!-- name이 반드시 "username"이어야 한다 -->
    <input type="text" name="username" placeholder="id(username)"/>
    <!-- name이 반드시 "password"이어야 한다 -->
    <input type="password" name="password" placeholder="password"/>
    <button type="submit">로그인</button>
</form>
```

- `action="/loginProc"` → SecurityConfig의 `loginProcessingUrl("/loginProc")`과 일치해야 함
- `name="username"`, `name="password"` → **반드시 이 이름**이어야 Security가 인식

![로그인 페이지 화면](./seucirty%20이미지/security1.png)

---

## 3. 로그인 과정 (핵심)

로그인 페이지에서 username/password를 입력하고 로그인 버튼을 누르면,  
`/loginProc`로 요청이 가고 **Security가 자동으로 로그인 과정을 진행**한다.

> Security 세션 방식에서 이 과정을 이해하는 것이 핵심이다.

![로그인 과정 흐름도 1](./seucirty%20이미지/security2.png)

![로그인 과정 흐름도 2](./seucirty%20이미지/security3.png)

> 참고: 공식 문서 Architecture 그림

![Spring Security 공식 문서 Architecture](./seucirty%20이미지/security4.png)

### 전체 흐름

```
사용자가 username/password 입력 → /loginProc POST 요청
    ↓
Security가 요청을 가로챔 (UsernamePasswordAuthenticationFilter)
    ↓
개발자가 만든 CustomUserDetailsService.loadUserByUsername(username) 호출
    → DB에서 username으로 사용자 조회
    → 조회 결과를 CustomUserAccount(UserDetails)로 감싸서 반환
    ↓
Security가 반환된 UserDetails의 password와 입력된 password를 BCrypt로 비교
    ↓
일치 → 로그인 성공 → Authentication 객체 생성 → 세션에 저장
불일치 → 로그인 실패
```

### 우리가 직접 작성해야 하는 것 (2가지)

SecurityConfig에는 DB 관련 설정이 없다.  
Security는 **다음 2가지를 개발자가 구현**하도록 인터페이스를 제공한다:

| 인터페이스 | 역할 | 구현 클래스 |
|-----------|------|------------|
| `UserDetailsService` | username으로 DB에서 사용자 조회 | `CustomUserDetailsService` |
| `UserDetails` | 조회한 사용자 정보를 Security가 이해할 수 있는 형태로 변환 | `CustomUserAccount` |

나머지(비밀번호 비교, 세션 저장 등)는 **Security가 자동으로** 처리한다.

---

## 4. CustomUserAccount (UserDetails 구현)

Security가 이해할 수 있는 "로그인 사용자 정보" 객체.  
DB에서 조회한 Entity의 정보를 **DTO(SessionUserDTO)로 변환**하여 보관한다.  
Entity를 직접 담지 않는다 — Controller까지 Entity가 올라가면 안 되므로.

```java
public class CustomUserAccount implements UserDetails {
    private final SessionUserDTO sessionUser;  // Entity 대신 DTO 사용 (세션 저장용)
    private final String password;              // Security 인증용

    // Entity → CustomUserAccount 변환 (정적 팩토리 메서드)
    // SessionUserDTO.from()을 재사용하여 일반 필드 변환 후, password만 추가
    public static CustomUserAccount from(UserEntity entity) {
        return new CustomUserAccount(
                SessionUserDTO.from(entity),
                entity.getPassword()
        );
    }

    private CustomUserAccount(SessionUserDTO sessionUser, String password) {
        this.sessionUser = sessionUser;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return sessionUser.getUsername();  // DTO에 위임
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        for (String role : sessionUser.getRoles()) {  // roles는 List<String>
            collection.add(() -> role);  // 람다로 GrantedAuthority 구현
        }
        return collection;
    }

    // 커스텀 getter — DTO에 위임
    public Long getId()              { return sessionUser.getId(); }
    public String getNickname()      { return sessionUser.getNickname(); }
    public String getEmail()         { return sessionUser.getEmail(); }
    public List<String> getRoles()   { return sessionUser.getRoles(); }

    // isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled
    // → Spring Boot 3부터는 default 메소드로 제공되어 구현 생략 가능
}
```

### roles(권한)는 왜 복수형(List)인가?

Security는 권한을 **`Collection<GrantedAuthority>`**(컬렉션)로 관리한다.  
한 사용자가 여러 권한을 가질 수 있기 때문이다.

```
일반 사용자: ["USER"]
관리자:      ["ADMIN", "USER"]    ← ADMIN이면서 동시에 USER
```

| 구분 | 설명 |
|------|------|
| DB 저장 | `roles VARCHAR` — 쉼표 구분 문자열 (`"ADMIN,USER"`) |
| DTO | `List<String> roles` — DB 문자열을 `split(",")`으로 변환 |
| UserDetails | `Collection<GrantedAuthority>` — List를 GrantedAuthority로 변환 |
| SecurityConfig | `hasAuthority("ADMIN")` — 컬렉션에 "ADMIN"이 **포함**되어 있는지 확인 |

```java
// SecurityConfig에서의 권한 검사 — "포함 여부"로 동작
.requestMatchers("/admin").hasAuthority("ADMIN")            // authorities에 "ADMIN" 포함?
.requestMatchers("/mypage").hasAnyAuthority("ADMIN","USER") // "ADMIN" 또는 "USER" 포함?
```

> 즉 `hasAuthority`는 `==` 비교가 아니라 **컬렉션 포함 여부**를 확인한다.  
> `roles = ["ADMIN", "USER"]`이면 `hasAuthority("ADMIN")`도 true, `hasAuthority("USER")`도 true.

---

## 5. CustomUserDetailsService (UserDetailsService 구현)

Security가 로그인 시 **자동으로 호출**하는 서비스.  
username으로 DB 조회 후 `UserDetails`를 반환하면, Security가 password 비교를 알아서 한다.

```java
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        return CustomUserAccount.from(userEntity);  // Entity → DTO 변환 후 UserDetails로 반환
    }
}
```

> **개발자는 DB 조회만 하면 된다.** password 비교는 Security가 BCryptPasswordEncoder로 처리.

다시 로그인 과정을 보면, 위에서 만든 UserDetailsService와 UserDetails가 어디에 들어가는지 알 수 있다.

![로그인 과정 — UserDetailsService 호출](./seucirty%20이미지/security5.png)

![로그인 과정 — UserDetails 반환 후 비밀번호 비교](./seucirty%20이미지/security6.png)

로그인 성공 시 Security가 로그인 정보를 자동으로 저장한다.

![로그인 성공 — 세션에 로그인 정보 저장](./seucirty%20이미지/security7.png)

---

## 6. 로그인 정보 저장 구조 (참고)

로그인 성공 시 Security가 자동으로 세션에 로그인 정보를 저장한다.

![SecurityContextHolder 구조](./seucirty%20이미지/security8.png)

```
SecurityContextHolder (싱글톤, ThreadLocal 기반)
    └── SecurityContext (요청마다 생성, 요청 후 세션에 복사)
            └── Authentication (인증 정보)
                    ├── Principal    → 우리가 만든 CustomUserAccount (UserDetails)
                    ├── Credentials  → 자격 증명 (비밀번호, 토큰 등)
                    └── Authorities  → 권한 목록
```

- `SecurityContextHolder`는 싱글톤 클래스. 내부적으로 **ThreadLocal**을 사용하여 각 요청마다 인증 정보를 저장·관리
- `SecurityContext`는 요청마다 생성되고 요청 종료 후 사라지지만, **사라지기 전에 Session에 복사**됨
- 다음 요청 시 Session에서 SecurityContext를 꺼내서 SecurityContextHolder에 다시 세팅
- 즉 **세션에 로그인 정보(Authentication)가 저장**된다고 이해하면 OK

### Authentication 내부 구성

| 필드 | 설명 |
|------|------|
| **Principal** | 사용자 정보. 우리가 만든 `CustomUserAccount`. Security 내부적으로는 Object 타입 (OAuth2 대응) |
| **Credentials** | 자격 증명에 사용되는 값 (비밀번호, 토큰, OTP 등) |
| **Authorities** | 권한 목록 |

---

## 7. 로그인 정보 사용하기

로그인 성공 후 Controller에서 로그인 정보를 사용하는 여러 방법.

```java
@Controller
public class UserController {

    @GetMapping("/mypage")
    public String mypage(
        @AuthenticationPrincipal CustomUserAccount userDetails,  // ✅ 권장!
        // 아래는 동일한 정보에 접근하는 다른 방법들 (참고용)
        // Principal principal,              // username만 가져올 수 있음
        // Authentication authentication,    // Security 내부 인증 객체
        // HttpSession session,
        // HttpServletRequest request
        Model model
    ) {
        // @AuthenticationPrincipal로 바로 사용 (가장 깔끔)
        model.addAttribute("user", userDetails);

        // 동일한 값을 다른 방법으로도 접근 가능
        // SecurityContextHolder.getContext().getAuthentication()  == authentication
        // request.getUserPrincipal()                              == principal
        // session.getAttribute("SPRING_SECURITY_CONTEXT")         == SecurityContextHolder.getContext()

        return "mypage";
    }
}
```

### 방법 비교

| 방법 | 설명 | 추천 |
|------|------|------|
| `@AuthenticationPrincipal CustomUserAccount` | 개발자가 만든 로그인 정보를 바로 주입 | ✅ **권장** |
| `Principal` | `getName()`으로 username만 가져올 수 있음 | △ |
| `Authentication` | Security 내부 객체. UserDetails 포함 + 추가 정보 | △ |
| `SecurityContextHolder.getContext()` | 정적 메서드로 어디서든 접근 가능 | Service 등에서 사용 |

---

## 8. Thymeleaf에서 Security 사용하기

### 의존성 추가

```groovy
implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'
```

### HTML에서 사용

```html
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity6">

<!-- 현재 로그인한 사용자 이름 -->
<p>현재 사용자: <span sec:authentication="name"></span></p>

<!-- 권한 출력 -->
<p>권한: <span sec:authentication="authorities"></span></p>

<!-- 로그인 상태일 때만 표시 -->
<div sec:authorize="isAuthenticated()">
    <p>로그인한 사용자만 볼 수 있습니다.</p>
    <form th:action="@{/logout}" method="post">
        <button type="submit">로그아웃</button>
    </form>
</div>

<!-- 비로그인 상태일 때만 표시 -->
<div sec:authorize="isAnonymous()">
    <p>로그인하지 않은 사용자입니다.</p>
    <a th:href="@{/login}">로그인</a>
</div>

<!-- 특정 권한일 때만 표시 -->
<div sec:authorize="hasAuthority('ADMIN')">
    <p>관리자 전용 페이지</p>
</div>
```

> `sec:authentication="name"`은 `Authentication.getName()` 값을 표시한다.  
> `@AuthenticationPrincipal`의 커스텀 필드를 Thymeleaf에서 사용하려면  
> **Controller에서 model에 직접 담아야** 한다.

### ✅ 이 프로젝트에서 사용하는 방식 — model에 담은 CustomUserAccount 사용

`sec:authentication`은 `name`, `authorities` 같은 기본 필드만 바로 접근 가능하다.  
`nickname`, `email` 같은 **커스텀 필드**를 Thymeleaf에서 쓰려면  
Controller에서 `@AuthenticationPrincipal`로 받은 CustomUserAccount를 model에 담고,  
HTML에서는 **일반 Thymeleaf EL(`${user.필드}`)로 사용**하는 것이 깔끔하다.

```java
// Controller
@GetMapping("/mypage")
public String mypage(@AuthenticationPrincipal CustomUserAccount userDetails, Model model) {
    model.addAttribute("user", userDetails);  // CustomUserAccount를 model에 담음
    return "mypage";
}
```

```html
<!-- HTML — 일반 EL로 CustomUserAccount의 getter 호출 -->
<span th:text="${user.nickname}">닉네임</span>     <!-- getNickname() -->
<span th:text="${user.username}">아이디</span>      <!-- getUsername() -->
<span th:text="${user.email}">이메일</span>         <!-- getEmail() -->
<span th:text="${user.id}">1</span>                 <!-- getId() -->
<span th:text="${user.roles}">USER</span>            <!-- getRoles() -->
```

> **sec:authentication vs model 방식 비교**
> - `sec:authentication="principal.nickname"` → Security 내부 객체를 직접 참조. Controller 없이 사용 가능하지만 표현이 길고 제한적.
> - `${user.nickname}` → Controller에서 model에 담은 객체를 참조. **기존 Thymeleaf와 동일한 방식**이라 직관적. **이 프로젝트에서는 이 방식을 사용한다.**
>
> 단, `sec:authorize="isAuthenticated()"` / `sec:authorize="isAnonymous()"` 같은 **로그인 여부 분기**는  
> Security 태그를 사용하는 것이 편하다 (index.html 헤더 등).

---

## 요약 — Security 세션 로그인에서 개발자가 할 일

| 순서 | 할 일 | 설명 |
|------|-------|------|
| 1 | `SecurityConfig` 작성 | URL 권한, 로그인/로그아웃 URL 설정 |
| 2 | `CustomUserAccount` 구현 | `UserDetails` 인터페이스 — Entity를 Security 형태로 감쌈 |
| 3 | `CustomUserDetailsService` 구현 | `UserDetailsService` 인터페이스 — username으로 DB 조회 |
| 4 | 로그인 페이지 (HTML) | `action`을 `loginProcessingUrl`과 맞추고, `name="username"`, `name="password"` |
| 5 | Controller에서 사용 | `@AuthenticationPrincipal`로 로그인 정보 주입받아 사용 |

> 비밀번호 비교, 세션 저장, 세션 복원 등은 **전부 Security가 자동으로** 처리한다.  
> 개발자는 **DB 조회(UserDetailsService)**와 **데이터 형태 변환(UserDetails)**만 구현하면 된다.
