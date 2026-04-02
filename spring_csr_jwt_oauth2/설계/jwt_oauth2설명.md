# CSR + JWT + OAuth2 (카카오/구글 로그인) 설명

> **프론트/백엔드 분리(CSR) 환경**에서 JWT(Access Token + Refresh Token) 인증 +  OAuth2(카카오/구글) 로그인  
> 전체코드 : [https://github.com/gks930620/spring_securty_all](https://github.com/gks930620/spring_securty_all)

---

## 1. 세션/JWT/OAuth2 개념 정리

| 개념 | 설명 |
|------|------|
| **세션 vs JWT** | 사용자 **인증 상태를 어디서 관리**하느냐의 차이 (서버 세션 vs 클라이언트가 보관하는 토큰) |
| **OAuth2** | 사용자 정보를 **어떻게 얻느냐**에 대한 것 (카카오 등 외부 제공자로부터) |

**전통적 SSR 방식**: OAuth2(인가코드) → 사용자정보 획득 → **세션에 저장**  
**프론트/백엔드 분리**: OAuth2(인가코드) → 사용자정보 획득 → **JWT에 저장**  

이 프로젝트는 **프론트/백엔드 분리** 환경에서  
**백엔드가 OAuth2 인가코드 방식으로 사용자정보를 얻고, JWT(Access + Refresh Token)로 관리**하는 방식이다.

### 책임분배 (프론트 vs 백엔드)

**프론트는 인가코드만 백엔드로 전달**하고, 나머지는 전부 백엔드가 담당한다.

![웹 session방식](jwt설명1/img.png)

- **보안성 증가**: Access Token(카카오)이 클라이언트에 노출되지 않음
- **클라이언트 부담 감소**: 인가코드만 받아서 서버로 넘기면 됨
- **토큰 관리가 서버에서 일관되게 이루어짐**

---

## 2. OAuth2 로그인 동작방식

### 2-1. 클라이언트가 로그인버튼 클릭 → 백엔드에 authorizationURL 요청

![클라이언트 로그인버튼 클릭](jwt설명1/img_1.png)

클라이언트가 `localhost:8080/custom-oauth2/login/kakao`에 요청한다.

웹 세션방식에서는 `/oauth2/authorization/kakao`(Security 기본 URL)로 요청하면  
Security가 자동으로 카카오 로그인페이지로 redirect시키지만,  
**JWT방식에서는 redirect를 시킬 수 없기 때문에** Custom Controller에서 직접 처리해야 한다.

Controller에서 해야 할 일:
1. **OAuth2AuthorizationRequest 객체 저장** (로그인 요청 클라이언트 식별용)
2. **클라이언트에게 authorizationURL JSON으로 전달**

> **OAuth2AuthorizationRequest란?**  
> 로그인페이지를 요청한 클라이언트와 인가코드로 로그인 요청하는 클라이언트가 같은지 확인하는 객체.  
> `state` 파라미터의 핵심 목적은 **CSRF(Cross-Site Request Forgery) 방지**이다.  
> 공격자가 피해자의 브라우저를 통해 공격자의 인가코드로 redirect-uri를 요청하게 만드는 것을 방지하며,  
> "이 요청이 내가 시작한 OAuth2 로그인 흐름의 일부인지" 확인하여 **로그인 흐름의 무결성을 보장**한다.

**Oauth2LoginController** — 이 URL은 SecurityConfig에서 **permitAll** 되어야 한다.

```java
@Controller
@RequestMapping("/custom-oauth2/login")
@RequiredArgsConstructor
public class Oauth2LoginController {

    private final InMemoryAuthorizationRequestRepository authorizationRequestRepository;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @GetMapping("/kakao")
    public ResponseEntity<?> kakaoOauth2Login(HttpServletRequest request, HttpServletResponse response){
        String state = "" + UUID.randomUUID();

        // ✅ OAuth2AuthorizationRequest 직접 생성 (클라이언트 구별용)
        OAuth2AuthorizationRequest authorizationRequest = OAuth2AuthorizationRequest.authorizationCode()
            .authorizationUri("https://kauth.kakao.com/oauth/authorize")
            .clientId(kakaoClientId)
            .redirectUri(kakaoRedirectUri)
            .state(state)
            .attributes(attrs -> attrs.put("registration_id", "kakao"))
            .build();

        authorizationRequestRepository.saveAuthorizationRequest(authorizationRequest, request, response);
        // 기본은 session에 저장하는데, JWT방식에서는 session이 없으니까 Map에 저장. 서버 여러대면 DB 등.

        // ✅ Authorization URL 생성
        String authorizationUrl = "https://kauth.kakao.com/oauth/authorize"
            + "?client_id=" + kakaoClientId
            + "&redirect_uri=" + kakaoRedirectUri
            + "&response_type=code"
            + "&state=" + state;

        return ResponseEntity.ok(Map.of("authorizationUrl", authorizationUrl));
    }

    @GetMapping("/google")
    public ResponseEntity<?> googleOauth2Login(HttpServletRequest request, HttpServletResponse response){
        String state = "" + UUID.randomUUID();

        OAuth2AuthorizationRequest authorizationRequest = OAuth2AuthorizationRequest.authorizationCode()
            .authorizationUri("https://accounts.google.com/o/oauth2/auth")
            .clientId(googleClientId)
            .redirectUri(googleRedirectUri)
            .state(state)
            .attributes(attrs -> attrs.put("registration_id", "google"))
            .build();

        authorizationRequestRepository.saveAuthorizationRequest(authorizationRequest, request, response);

        String authorizationUrl = "https://accounts.google.com/o/oauth2/auth"
            + "?client_id=" + googleClientId
            + "&redirect_uri=" + googleRedirectUri
            + "&response_type=code"
            + "&scope=profile email"
            + "&state=" + state;

        return ResponseEntity.ok(Map.of("authorizationUrl", authorizationUrl));
    }
}
```

**InMemoryAuthorizationRequestRepository** — 세션 없이 OAuth2AuthorizationRequest를 관리하는 저장소.

```java
@Component
public class InMemoryAuthorizationRequestRepository implements
    AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private final Map<String, OAuth2AuthorizationRequest> authorizationRequests = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        String state = request.getParameter("state");
        if (state == null) return null;
        return authorizationRequests.get(state);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
                                          HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) return;
        String state = authorizationRequest.getState();
        authorizationRequests.put(state, authorizationRequest);

        // 5분 후 자동 삭제 (보안) — ScheduledExecutorService로 스레드 안전하게 처리
        scheduler.schedule(() -> authorizationRequests.remove(state), 5, TimeUnit.MINUTES);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
                                                                  HttpServletResponse response) {
        String state = request.getParameter("state");
        if (state == null) return null;
        return authorizationRequests.remove(state);
    }
}
```

### 2-2. 클라이언트가 카카오 로그인페이지 요청 → ID/PW 입력 → 인가코드 받기

![카카오 로그인페이지 요청](jwt설명1/img_2.png)
![인가코드 받기](jwt설명1/img_3.png)

서버로부터 받은 authorizationURL로 카카오서버에 로그인페이지를 요청하고,  
ID/PW를 입력하면 카카오서버가 인가코드를 발급한다.  
이 부분은 클라이언트가 알아서 처리한다.

### 2-3. 클라이언트가 redirect-uri로 백엔드서버에 요청

![redirect-uri 요청](jwt설명1/img_4.png)

클라이언트는 **인가코드 + state**를 포함해서 백엔드 서버의 redirect-uri로 요청한다.  
이 **state 값**이 처음에 저장한 OAuth2AuthorizationRequest를 식별하는 데 쓰인다.

> **브라우저**: 카카오 서버가 HTTP 302 응답으로 브라우저를 **자동 redirect** 시킨다.  
> **앱(CSR)**: redirect를 가로채서 클라이언트가 **직접** 백엔드에 인가코드+state를 요청해야 한다.

### 2-4. 백엔드서버에서 카카오 OAuth2 로그인과정 → JWT 발급

![백엔드 로그인과정](jwt설명1/img_5.png)

OAuth2AuthorizationRequest 검사(같은 클라이언트인지 확인) 후:  
**인가코드로 토큰요청 → 카카오 access token 획득 → UserRequest → 유저정보 획득 → CustomOAuth2UserService → DB저장**

> 여기서 "access token"은 **카카오 인증서버가 발급한 카카오 access token**이다.  
> 이후 섹션 3에서 설명하는 "Access Token"은 **우리 서버가 JWT로 발급한 access token**으로 다른 것이다.

이 과정은 Security의 `OAuth2LoginAuthenticationFilter`가 자동으로 처리해주며,  
우리가 설정할 건 SecurityConfig에서  
`authorizationRequestRepository`를 위에서 만든 `InMemoryAuthorizationRequestRepository`로 지정해주는 것이다.

```java
http.oauth2Login(oauth2 -> oauth2
    .authorizationEndpoint(authEndpoint -> authEndpoint
        .authorizationRequestRepository(authorizationRequestRepository)) // ✅ 직접 구현한 저장소
    .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
    .successHandler(oAuth2LoginSuccessHandler) // ✅ 로그인 성공 시 JWT 발급
    .failureHandler((request, response, exception) -> {
        exception.printStackTrace();
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    })
);
```

![로그인과정 상세](jwt설명2/img_2.png)

![successHandler JWT 발급](jwt설명1/img_6.png)

이후 **OAuth2LoginSuccessHandler**에 의해 **Access Token + Refresh Token 발급**.

**CustomOAuth2UserService** — OAuth2 유저정보를 우리 DB에 저장 (OAuthProvider enum으로 provider별 분기)

```java
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false)
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 카카오로그인이든 구글로그인이든 폼로그인이든 똑같은 서비스를 제공하기 위해 DB 저장

        OAuth2User oAuth2User = super.loadUser(userRequest); // user-info-uri에서 유저정보를 가져옴

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // "kakao" or "google"
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // ✅ OAuthProvider enum으로 provider별 분기 처리 (카카오/구글 등 각 provider마다 응답 구조가 다름)
        OAuthProvider provider = OAuthProvider.from(registrationId);

        // DB에서 사용자 조회 (없으면 생성)
        String providerUsername = provider.extractUsername(attributes); // "kakao123456" or "google123456"
        UserEntity user = userRepository.findByUsername(providerUsername);
        if (user == null) {  // 해당 provider로 처음 로그인
            user = provider.toUserEntity(attributes, passwordEncoder);
            userRepository.save(user);
        } else {  // 재로그인 → provider에서 변경된 정보 반영
            user.setEmail(provider.extractEmail(attributes));
            user.setNickname(provider.extractNickname(attributes));
        }

        return new CustomUserAccount(user, attributes);
    }
}
```

> **OAuthProvider enum** — 카카오/구글 등 provider마다 다른 응답 구조를 각 enum 상수가 처리한다.  
> `toUserEntity()`, `extractUsername()`, `extractEmail()`, `extractNickname()` 등을 provider별로 구현.  
> 새로운 OAuth2 provider 추가 시 enum 상수만 추가하면 된다.

**OAuth2LoginSuccessHandler** — 로그인 성공 시 JWT 발급

```java
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshService refreshService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserAccount customUserAccount = (CustomUserAccount) authentication.getPrincipal();

        String accessToken = jwtUtil.createAccessToken(customUserAccount.getUsername());
        String refreshToken = jwtUtil.createRefreshToken(customUserAccount.getUsername());

        // ✅ Refresh Token 저장 (Service를 통해)
        refreshService.saveRefresh(refreshToken);

        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(
            Map.of("access_token", accessToken, "refresh_token", refreshToken)
        ));
    }
}
```

### 2-5. 로그인 성공 이후

![로그인 이후 access token 사용](jwt설명1/img_7.png)

로그인 성공 후에는 카카오 유저정보가 우리 DB에 저장되어 있으므로  
**일반 JWT 로그인과 완전히 동일하게** Access Token, Refresh Token을 사용하면 된다.

---

## 3. JWT 토큰 구조

| 토큰 | 용도 | 만료시간 | 저장 |
|------|------|----------|------|
| **Access Token** | API 요청 시 인증용 | 짧게 (테스트: 1분) | 클라이언트 |
| **Refresh Token** | Access Token 재발급용 | 길게 (테스트: 5분) | 클라이언트 + **서버 DB** |

- Access Token 만료 시 → Refresh Token으로 재발급 요청
- Refresh Token은 **서버 DB(RefreshEntity)에도 저장**하여 폐기(로그아웃) 관리
- 재발급 시 기존 Refresh Token 삭제 후 새 토큰 발급 (**Refresh Token Rotation**)

### token_type claim

JWT 토큰 내부에 `token_type` claim(`"access"` 또는 `"refresh"`)을 넣어 토큰 종류를 구분한다.  
클라이언트가 refresh token을 Authorization 헤더에 넣어 `/api/refresh/reissue`를 요청할 때,  
`JwtAccessTokenCheckAndSaveUserInfoFilter`가 refresh token을 access token처럼 처리하지 않도록 하기 위함.

### Refresh Token Rotation을 하는 이유

- 재발급 시 기존 refresh token을 **삭제**하고 새 refresh token을 발급
- **탈취된 refresh token**으로 재발급을 시도하면, 이미 삭제(rotation)되었으므로 실패
- 정상 사용자가 먼저 재발급을 받으면 공격자의 토큰은 자동으로 무효화됨
- 즉, **refresh token 탈취 시 피해를 최소화**하는 보안 전략

---

## 4. 일반 JWT 로그인 (ID/PW)

OAuth2가 아닌 일반 ID/PW 로그인도 동일한 JWT 방식으로 처리된다.

### 로그인 검증 흐름

```
JwtLoginFilter.attemptAuthentication()
  → AuthenticationManager.authenticate()
    → CustomUserDetailsService.loadUserByUsername()  ← DB에서 사용자 조회
      → CustomUserAccount 반환
    → 비밀번호 비교
  → 성공: successfulAuthentication() → JWT 발급
  → 실패: unsuccessfulAuthentication() → ERROR_CAUSE="로그인실패" 설정 → SecurityConfig의 entryPoint로 전달
```

### JwtLoginFilter (/login 처리)

```java
// /login URL일 때 동작. OAuth2 로그인과는 상관없음!
@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshService refreshService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            // API서버 분리 방식이므로 username, password는 body에 포함되어 옴
            // UsernamePasswordAuthenticationFilter는 parameter를 처리하므로 body 처리를 위해 재정의
            Map<String, String> credentials = new ObjectMapper().readValue(request.getInputStream(), HashMap.class);
            String username = credentials.get("username");
            String password = credentials.get("password");

            UsernamePasswordAuthenticationToken authRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(username, password);
            this.setDetails(request, authRequest);
            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse authentication request", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                             FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserAccount customUserAccount = (CustomUserAccount) authResult.getPrincipal();

        String accessToken = jwtUtil.createAccessToken(customUserAccount.getUsername());
        String refreshToken = jwtUtil.createRefreshToken(customUserAccount.getUsername());

        // ✅ Refresh Token 저장 (Service를 통해)
        refreshService.saveRefresh(refreshToken);

        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(
            Map.of("access_token", accessToken, "refresh_token", refreshToken)
        ));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        request.setAttribute("ERROR_CAUSE", "로그인실패"); // 실패 후 config의 entryPoint로
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
```

### JwtAccessTokenCheckAndSaveUserInfoFilter (매 요청마다 토큰 검증)

```java
@RequiredArgsConstructor
public class JwtAccessTokenCheckAndSaveUserInfoFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                     FilterChain chain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);  // Authorization 헤더에서 "Bearer " 추출

        if (token == null) {          // 토큰 없으면 통과
            chain.doFilter(request, response);
            return;
        }

        String tokenType = jwtUtil.getTokenType(token);
        if ("refresh".equals(tokenType)) {   // NPE 방지: tokenType이 null일 수 있음 (변조된 토큰 등)
            chain.doFilter(request, response);
            return;
        }

        // access token 만료 시
        if (!jwtUtil.validateToken(token)) {
            request.setAttribute("ERROR_CAUSE", "토큰만료");
            chain.doFilter(request, response);   // 인증필요한 url이면 → security가 authenticationException 발생
            return;
        }

        // ✅ 유효한 access token → SecurityContext에 인증정보 저장 → 로그인 상태!
        String username = jwtUtil.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
```

---

## 5. Refresh Token 관리

### 토큰 재발급 (RefreshController)

```java
@RestController
@RequestMapping("/api/refresh")
@RequiredArgsConstructor
public class RefreshController {

    private final JwtUtil jwtUtil;
    private final RefreshService refreshService;

    @PostMapping("/reissue")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("Authorization") String refreshToken) {
        String token = refreshToken.replace("Bearer ", "");

        // 폐기된 토큰인지 DB 검증 (로그아웃 or 탈취된 토큰)
        if (!refreshService.existsByToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Refresh Token discarded"));
        }

        // 만료 검증 (삭제 전에 먼저 검증)
        if (!jwtUtil.validateToken(token)) {
            refreshService.deleteRefresh(token);  // 만료된 토큰도 DB에서 정리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Refresh Token expired"));
        }

        // 기존 refresh 토큰 삭제 (Rotation)
        refreshService.deleteRefresh(token);

        // ✅ 새 토큰 발급
        String username = jwtUtil.extractUsername(token);
        String newAccessToken = jwtUtil.createAccessToken(username);
        String newRefreshToken = jwtUtil.createRefreshToken(username);

        refreshService.saveRefresh(newRefreshToken);
        return ResponseEntity.ok(Map.of("access_token", newAccessToken, "refresh_token", newRefreshToken));
    }
}
```

### 로그아웃 (LogoutController)

```java
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LogoutController {
    private final RefreshService refreshService;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("RefreshToken") String refreshToken) {
        refreshService.deleteRefresh(refreshToken);  // DB에서 refresh token 삭제 → 재발급 불가
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
```

> 로그아웃 시 서버 DB에서 Refresh Token을 삭제하여 해당 토큰으로 재발급이 불가능해진다.  
> 클라이언트 측에서도 저장된 Access Token, Refresh Token을 삭제해야 한다.

### RefreshService

```java
@Service
@RequiredArgsConstructor
public class RefreshService {
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public boolean existsByToken(String token) {
        return refreshRepository.findByToken(token) != null;
    }

    @Transactional
    public void saveRefresh(String token) {
        RefreshEntity refreshEntity = new RefreshEntity();
        String username = jwtUtil.extractUsername(token);
        refreshEntity.setUserEntity(userRepository.findByUsername(username));
        refreshEntity.setToken(token);
        refreshRepository.save(refreshEntity);
    }

    @Transactional
    public void deleteRefresh(String token) {
        refreshRepository.deleteByToken(token);
    }
}
```

---

## 6. 통합 인증 객체 (CustomUserAccount)

카카오로그인이든 구글로그인이든 폼로그인이든 **같은 UserEntity로 DB에 저장**하여 동일한 서비스를 제공한다.  
**Entity는 Service 계층까지만 사용**하고, CustomUserAccount는 `UserDTO`를 통해 정보를 보관한다.

**UserDTO** — Entity → DTO 변환 메소드는 DTO측에서 (`from()`)

```java
@Getter
@Builder
public class UserDTO {
    private final String username;
    private final String password;
    private final String email;
    private final String nickname;
    private final String provider;
    private final String roles;    // "USER" or "USER,ADMIN"

    // Entity → DTO 변환메소드는 DTO측에서
    public static UserDTO from(UserEntity entity) {
        return UserDTO.builder()
            .username(entity.getUsername())
            .password(entity.getPassword())
            .email(entity.getEmail())
            .nickname(entity.getNickname())
            .provider(entity.getProvider())
            .roles(entity.getRoles())
            .build();
    }
}
```

**CustomUserAccount** — `UserDTO`를 감싸서 UserDetails + OAuth2User 통합

```java
public class CustomUserAccount implements UserDetails, OAuth2User {
    private final UserDTO userDTO;  // Entity 대신 DTO 사용 (Entity는 Service 계층까지만)
    private final Map<String, Object> attributes; // OAuth2 로그인 시 provider로부터 받은 원본 정보

    public CustomUserAccount(UserDTO userDTO) {                                   // 일반 로그인
        this.userDTO = userDTO;
        this.attributes = null;
    }
    public CustomUserAccount(UserDTO userDTO, Map<String, Object> attributes) {   // OAuth2 로그인
        this.userDTO = userDTO;
        this.attributes = attributes;
    }

    // UserDetails
    @Override public String getUsername() { return userDTO.getUsername(); }
    @Override public String getPassword() { return userDTO.getPassword(); }

    // UserDetails + OAuth2User 공통
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles = userDTO.getRoles();
        if (roles == null || roles.isBlank()) { return Collections.emptyList(); }
        return Arrays.stream(roles.split(","))
            .map(String::trim)
            .map(SimpleGrantedAuthority::new)
            .toList();
    }

    // OAuth2User
    @Override public Map<String, Object> getAttributes() { return attributes; }

    /**
     * OAuth2User는 사용자를 getName()으로 식별하지만,
     * 통합 User 객체를 사용하기 때문에 UserDetails와 맞춰줌.
     */
    @Override public String getName() { return getUsername(); }

    // 편의 메소드
    public String getEmail() { return userDTO.getEmail(); }
    public String getNickname() { return userDTO.getNickname(); }
    public String getProvider() { return userDTO.getProvider(); }
}
```

Service에서 사용 예:
```java
// CustomUserDetailsService
return new CustomUserAccount(UserDTO.from(userData));

// CustomOAuth2UserService
return new CustomUserAccount(UserDTO.from(user), attributes);
```

---

## 7. SecurityConfig 전체

```java
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final RefreshService refreshService;
    private final AuthorizationRequestRepository authorizationRequestRepository;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http  // 내부 H2 DB 확인용
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll())
            // H2 콘솔 CSRF는 아래에서 전체 disable하므로 별도 설정 불필요
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        http  // 기본 session방식 관련 전부 비활성화
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(form -> form.disable())
            .logout(logout -> logout.disable())
            .httpBasic(basic -> basic.disable());

        http  // 경로와 인증/인가 설정
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/api/join", "/api/refresh/reissue", "/custom-oauth2/login/**").permitAll()
                .requestMatchers("/api/my/info", "/api/logout").authenticated()
            );

        http.oauth2Login(oauth2 -> oauth2  // ✅ OAuth2 로그인 설정
            .authorizationEndpoint(authEndpoint -> authEndpoint
                .authorizationRequestRepository(authorizationRequestRepository))
            .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
            .successHandler(oAuth2LoginSuccessHandler)
            .failureHandler((request, response, exception) -> {
                exception.printStackTrace();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            })
        );

        http  // 필터
            .userDetailsService(customUserDetailsService)
            .addFilterAt(
                new JwtLoginFilter(authenticationConfiguration.getAuthenticationManager(), jwtUtil, refreshService),
                UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(
                new JwtAccessTokenCheckAndSaveUserInfoFilter(jwtUtil, customUserDetailsService),
                UsernamePasswordAuthenticationFilter.class);

        http  // 예외 처리
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    String errorCause =
                        request.getAttribute("ERROR_CAUSE") != null
                            ? (String) request.getAttribute("ERROR_CAUSE") : null;
                    // access token 없이 인증 필요한 곳 접근
                    if (errorCause == null) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write("{\"error\": \"인증이 필요합니다.\"}");
                        return;
                    }
                    if (errorCause.equals("토큰만료")) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write("{\"error\": \"Access Token expired\"}");
                        return;
                    }
                    if (errorCause.equals("로그인실패")) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write("{\"error\": \"아이디 비번 틀림.\"}");
                    }
                })
            );
        return http.build();
    }
}
```

---

## 8. 실행결과

### 8-1. 로그인 페이지 요청 → authorizationURL 응답

![authorizationURL 응답](jwt설명2/img_3.png)

`/custom-oauth2/login/kakao`로 요청하면 authorizationURL을 응답받는다.

### 8-2. authorizationURL로 카카오 로그인 페이지

![카카오 로그인 페이지](jwt설명2/img_4.png)

authorizationURL로 요청하면 카카오 로그인 화면이 나온다.  
(테스트용으로 브라우저에서 직접 진행)

### 8-3. 로그인 → redirect-uri → JWT 발급

![redirect-uri 요청](jwt설명2/img_5.png)
![서버 로그 (인가코드 포함)](jwt설명2/img_6.png)

브라우저는 로그인 후 자동으로 redirect-uri(`/login/oauth2/code/kakao?code=...`)로 요청한다.  
**이 요청을 받는 순간** 서버에서는 카카오 인증서버 + 리소스서버 요청 → DB 저장 → JWT 발급까지 전부 처리한다.

### 8-4. Access Token으로 API 요청

![access token으로 /my/info 요청](jwt설명2/img_7.png)

발급받은 access token으로 `/api/my/info`에 요청하면 내 정보를 확인할 수 있다.  
이후 토큰 만료, refresh token 재발급 등은 **일반 회원과 완전히 동일하게 처리**된다.

---

## 9. API 요약

| API | Method | 설명 | 인증 |
|-----|--------|------|------|
| `/api/join` | POST | 회원가입 | ❌ |
| `/login` | POST | 일반 로그인 (ID/PW) → JWT 발급 | ❌ |
| `/custom-oauth2/login/kakao` | GET | 카카오 OAuth2 로그인 시작 → authorizationURL 응답 | ❌ |
| `/custom-oauth2/login/google` | GET | 구글 OAuth2 로그인 시작 → authorizationURL 응답 | ❌ |
| `/login/oauth2/code/kakao` | GET | 카카오 redirect-uri (Security 자동 처리) → JWT 발급 | ❌ |
| `/login/oauth2/code/google` | GET | 구글 redirect-uri (Security 자동 처리) → JWT 발급 | ❌ |
| `/api/refresh/reissue` | POST | Access Token 재발급 (Refresh Token 필요) | ❌ |
| `/api/my/info` | GET | 내 정보 조회 | ✅ |
| `/api/logout` | POST | 로그아웃 (Refresh Token 폐기) | ✅ |

---

## 10. 참고사항

### 토큰 노출 주의
> 이 프로젝트에서는 Postman과 브라우저로 테스트하기 때문에  
> 브라우저에 access_token과 refresh_token이 노출된다.  
> **실제 서비스에서 클라이언트가 브라우저(CSR 웹앱)일 때는 cookie 등에 포함시켜 노출을 피해야** 한다.

클라이언트 종류에 따라 OAuth2LoginSuccessHandler에서 분기 처리:

```java
@Override
public void onAuthenticationSuccess(...) {
    if (브라우저라면) {
        // 쿠키에 token 감추기
        return;
    }
    if (앱이라면) {
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(
            Map.of("access_token", accessToken, "refresh_token", refreshToken)
        ));
        return;
    }
}
```

### CORS 설정

프론트/백엔드 분리(CSR) 환경에서는 **CORS 설정이 필수**이다.  
이 프로젝트에서는 생략되어 있지만, 실제 서비스에서는 SecurityConfig 또는 별도 CorsConfig에서  
프론트엔드 도메인을 허용해야 한다.

### Access Token 무효화 한계

현재 로그아웃은 **Refresh Token만 DB에서 삭제**한다.  
이미 발급된 Access Token은 만료될 때까지 여전히 유효하다. (JWT의 근본적 한계)  
보안이 중요한 서비스에서는:
- Access Token **블랙리스트** (Redis 등)
- Access Token **만료시간을 매우 짧게** 설정

등의 추가 대책이 필요하다. (현재는 테스트용 1분이므로 큰 문제는 아님)

