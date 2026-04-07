# jwt_oauth2설명 부족한점 적용 내역

> `jwt_oauth2설명 부족한점.md`에서 지적한 항목들을 코드와 설명 모두에 적용한 내역.

---

## 코드 변경

### 1. InMemoryAuthorizationRequestRepository — `new Thread()` → `ScheduledExecutorService`

**문제**: 매 요청마다 `new Thread()`로 스레드를 생성하는 것은 운영 환경에서 부적절.

**변경**:
```java
// Before
new Thread(() -> {
    try {
        TimeUnit.MINUTES.sleep(5);
        authorizationRequests.remove(state);
    } catch (InterruptedException ignored) {}
}).start();

// After
private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
// ...
scheduler.schedule(() -> authorizationRequests.remove(state), 5, TimeUnit.MINUTES);
```
- 단일 스레드 스케줄러로 안전하게 만료 처리
- 불필요한 `System.out.println` 로그도 함께 제거

---

### 2. JwtUtil.getTokenType() — `JwtException` 처리 추가

**문제**: `ExpiredJwtException`만 catch하고, 변조/잘못된 형식 등의 `JwtException`은 uncaught.

**변경**:
```java
// Before: ExpiredJwtException만 catch

// After: JwtException | IllegalArgumentException 추가
} catch (ExpiredJwtException e) {
    Claims claims = e.getClaims();
    return claims != null ? claims.get("token_type", String.class) : null;
} catch (JwtException | IllegalArgumentException e) {  // 변조, 잘못된 형식 등
    return null;
}
```

---

### 3. RefreshController — 만료 검증 → 삭제 순서 변경

**문제**: 삭제를 먼저 하고 만료를 나중에 검증하면, 만료된 토큰으로 재시도 시 "discarded" 에러가 나와 혼란.

**변경**:
```java
// Before: 존재 확인 → 삭제 → 만료 검증
// After:  존재 확인 → 만료 검증(실패 시 삭제 후 에러) → 삭제(Rotation)

if (!refreshService.existsByToken(token)) { ... }  // 1. 존재 확인

if (!jwtUtil.validateToken(token)) {                // 2. 만료 검증 (삭제 전)
    refreshService.deleteRefresh(token);             //    만료된 토큰도 DB 정리
    return "Refresh Token expired";
}

refreshService.deleteRefresh(token);                 // 3. 정상일 때만 Rotation 삭제
```
- 만료 시 "expired"라는 명확한 에러 메시지를 보장
- 만료된 토큰도 DB에서 정리하여 불필요한 데이터 축적 방지

---

### 4. CustomOAuth2UserService — `{noop}oauth2user` → 랜덤 비밀번호

**문제**: 모든 OAuth2 사용자가 동일한 비밀번호 `oauth2user`를 가져, 일반 로그인 폼으로 접근 가능.

**변경**:
```java
// Before
user.setPassword("{noop}oauth2user");

// After
@Autowired
private PasswordEncoder passwordEncoder;
// ...
user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
```
- 매 사용자마다 예측 불가능한 랜덤 비밀번호 생성
- `passwordEncoder`로 인코딩하여 일관성 유지

---

### 5. Oauth2LoginController — `@RequestMapping` → `@GetMapping`

**변경**: `@RequestMapping("/kakao")` → `@GetMapping("/kakao")`
- 의도에 맞게 GET 메소드만 허용하도록 명시

---

## 설명(MD) 변경

### 1. 섹션 1: 개념 정리 문구 수정
- "사용자 정보를 어디에 저장" → **"사용자 인증 상태를 어디서 관리"**로 더 정확하게 표현

### 2. 섹션 2-1: state 파라미터 설명 보강
- 기존: "A가 B의 인가코드를 탈취해서 로그인 시도하는 상황 방지"
- **추가**: CSRF 방지가 핵심 목적이며, 로그인 흐름의 무결성을 보장한다는 설명

### 3. 섹션 2-3: redirect-uri 요청 주체 구분
- **추가**: 브라우저는 카카오가 자동 redirect, 앱(CSR)은 클라이언트가 직접 요청하는 차이 설명

### 4. 섹션 2-4: 카카오 access token vs JWT access token 구분
- "access token 획득" → **"카카오 access token 획득"**으로 명확히 구분
- **추가**: 이후 섹션의 "Access Token"은 우리 서버 JWT라는 주석
- **추가**: `OAuth2LoginAuthenticationFilter`가 처리한다는 필터 이름 명시

### 5. 섹션 3: token_type claim 설명 + Refresh Token Rotation 이유
- **추가**: `token_type` claim이 왜 필요한지 (access/refresh 구분)
- **추가**: Refresh Token Rotation의 보안적 이유 (탈취 시 피해 최소화)

### 6. 섹션 4: 로그인 검증 흐름 추가
- **추가**: JwtLoginFilter → AuthenticationManager → CustomUserDetailsService → 성공/실패 흐름도
- CustomUserDetailsService가 언급되지 않았던 부분 해결

### 7. 섹션 5: RefreshController 코드 순서 변경 반영
- 만료 검증 → 삭제 순서로 코드 블록 업데이트

### 8. 섹션 10: 참고사항 보강
- **추가**: CORS 설정 필요성 언급
- **추가**: Access Token 무효화 한계 (JWT 근본적 한계) 및 대안 (블랙리스트, 짧은 만료시간)

### 9. 코드 블록 업데이트
- InMemoryAuthorizationRequestRepository: `ScheduledExecutorService` 코드 반영
- CustomOAuth2UserService: `passwordEncoder + UUID` 코드 반영
- Oauth2LoginController: `@GetMapping` 반영
- RefreshController: 순서 변경 반영

---

## 추가 적용 (최종 검증 시 나만의참고.md 규칙 반영)

### 코드 변경

#### 1. JwtAccessTokenCheckAndSaveUserInfoFilter — NPE 방지
```java
// Before
tokenType.equals("refresh")   // tokenType이 null이면 NPE

// After
"refresh".equals(tokenType)   // null-safe
```

#### 2. RefreshController / LogoutController — HTTP 메소드 명시
```java
// Before
@RequestMapping("/reissue")   // 모든 HTTP 메소드 허용
@RequestMapping("/logout")

// After
@PostMapping("/reissue")      // POST만 허용
@PostMapping("/logout")
```

#### 3. MainController — @RestController + @GetMapping + JSON 응답
```java
// Before: @Controller + @ResponseBody + @RequestMapping + HTML 문자열 (비밀번호 포함!)
sb.append("password : "+ customUserAccount.getPassword());

// After: @RestController + @GetMapping + ResponseEntity<Map> (비밀번호 제거)
@RestController
@RequestMapping("/api")
public class MainController {
    @GetMapping("/my/info")
    public ResponseEntity<?> myInfo(@AuthenticationPrincipal CustomUserAccount customUserAccount) {
        Map<String, Object> info = Map.of(
            "username", customUserAccount.getUsername(),
            "email", ..., "nickname", ..., "roles", ...
        );
        return ResponseEntity.ok(info);
    }
}
```

#### 4. JoinController — @RestController
```java
// Before: @Controller + @ResponseBody
// After: @RestController (불필요한 @GetMapping import도 제거)
```

#### 5. OAuthProvider — {noop}oauth2user → PasswordEncoder + UUID
```java
// Before
user.setPassword("{noop}oauth2user");
public abstract UserEntity toUserEntity(Map<String, Object> attributes);

// After
user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
public abstract UserEntity toUserEntity(Map<String, Object> attributes, PasswordEncoder passwordEncoder);
```

### MD 변경
- JwtAccessTokenCheckAndSaveUserInfoFilter: `"refresh".equals(tokenType)` 반영
- JwtLoginFilter: `throws IOException, ServletException` 추가
- RefreshController: `@PostMapping` 반영
- LogoutController: `@PostMapping` 반영


