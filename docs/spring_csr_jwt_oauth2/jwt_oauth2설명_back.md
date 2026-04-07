# CSR + JWT + OAuth2 (카카오 로그인) 설명

> **프론트/백엔드 분리(CSR) 환경**에서 JWT(Access Token + Refresh Token) 인증 +  OAuth2(카카오) 로그인  
> 전체코드 : [https://github.com/gks930620/spring_securty_all](https://github.com/gks930620/spring_securty_all)

---

## 1. 세션/JWT/OAuth2 개념 정리

| 개념 | 설명 |
|------|------|
| **세션 vs JWT** | 사용자 정보를 **어디에 저장**하느냐의 차이 (서버 세션 vs 토큰) |
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
> (A가 ID/PW를 입력했는데, B가 A의 인가코드를 탈취해서 로그인 시도하는 상황 방지)

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

    @RequestMapping("/kakao")
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
}
```

**InMemoryAuthorizationRequestRepository** — 세션 없이 OAuth2AuthorizationRequest를 관리하는 저장소.

```java
@Component
public class InMemoryAuthorizationRequestRepository implements
    AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private final Map<String, OAuth2AuthorizationRequest> authorizationRequests = new ConcurrentHashMap<>();

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

        // 5분 후 자동 삭제 (보안)
        new Thread(() -> {
            try {
                TimeUnit.MINUTES.sleep(5);
                authorizationRequests.remove(state);
            } catch (InterruptedException ignored) {}
        }).start();
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

### 2-4. 백엔드서버에서 카카오 OAuth2 로그인과정 → JWT 발급

![백엔드 로그인과정](jwt설명1/img_5.png)

OAuth2AuthorizationRequest 검사(같은 클라이언트인지 확인) 후:  
**인가코드로 토큰요청 → access token 획득 → UserRequest → 유저정보 획득 → CustomOAuth2UserService → DB저장**

이 과정은 Security가 자동으로 해주며, 우리가 설정할 건 SecurityConfig에서  
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

**CustomOAuth2UserService** — 카카오 유저정보를 우리 DB에 저장

```java
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // user-info-uri에서 유저정보를 가져옴

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // "kakao"

        Map<String, Object> attributes = oAuth2User.getAttributes();
        // { "id": 1234567890(Long), "kakao_account": { "email": "...", "profile": { "nickname": "..." } } }
        Long id = (Long) attributes.get("id");
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakaoAccount.get("email");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");

        // DB에서 사용자 조회 (없으면 생성)
        UserEntity user = userRepository.findByUsername("kakao" + id);
        if (user == null) {  // 카카오로 처음 로그인
            user = new UserEntity();
            user.setUsername("kakao" + id);
            user.setPassword("{noop}oauth2user"); // OAuth2 로그인은 비밀번호 없음
            user.setEmail(email);
            user.setNickname(nickname);
            user.getRoles().add("USER");
            user.setProvider(registrationId);
            userRepository.save(user);
        } else {  // 재로그인 → 카카오에서 변경된 정보 반영
            user.setEmail(email);
            user.setNickname(nickname);
        }

        return new CustomUserAccount(user, attributes);
    }
}
```

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
- 재발급 시 기존 Refresh Token 삭제 후 새 토큰 발급 (Refresh Token Rotation)

---

## 4. 일반 JWT 로그인 (ID/PW)

OAuth2가 아닌 일반 ID/PW 로그인도 동일한 JWT 방식으로 처리된다.

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
                                             FilterChain chain, Authentication authResult) {
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
        HttpServletResponse response, AuthenticationException failed) {
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
        if (tokenType.equals("refresh")) {   // refresh 토큰이면 통과 (/api/refresh/reissue는 인증 불필요)
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

    @RequestMapping("/reissue")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("Authorization") String refreshToken) {
        String token = refreshToken.replace("Bearer ", "");

        // 폐기된 토큰인지 DB 검증 (로그아웃 or 탈취된 토큰)
        if (!refreshService.existsByToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Refresh Token discarded"));
        }

        // 기존 refresh 토큰 삭제 (Rotation)
        refreshService.deleteRefresh(token);

        // 만료 검증
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Refresh Token expired"));
        }

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

    @RequestMapping("/logout")
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

카카오로그인이든 폼로그인이든 **같은 UserEntity로 DB에 저장**하여 동일한 서비스를 제공한다.

```java
public class CustomUserAccount implements UserDetails, OAuth2User {
    private UserEntity userEntity;
    private final Map<String, Object> attributes; // OAuth2 로그인용

    public CustomUserAccount(UserEntity userEntity) {                                   // 일반 로그인
        this.userEntity = userEntity;
        this.attributes = null;
    }
    public CustomUserAccount(UserEntity userEntity, Map<String, Object> attributes) {   // OAuth2 로그인
        this.userEntity = userEntity;
        this.attributes = attributes;
    }

    public UserEntity getUserEntity() { return userEntity; }

    // UserDetails
    @Override public String getUsername() { return userEntity.getUsername(); }
    @Override public String getPassword() { return userEntity.getPassword(); }

    // UserDetails + OAuth2User 공통
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userEntity.getRoles().stream().map(SimpleGrantedAuthority::new).toList();
    }

    // OAuth2User
    @Override public Map<String, Object> getAttributes() { return attributes; }

    /**
     * OAuth2User는 사용자를 getName()으로 식별하지만,
     * 통합 User 객체를 사용하기 때문에 UserDetails와 맞춰줌.
     */
    @Override public String getName() { return getUsername(); }

    // 편의 메소드
    public String getEmail() { return userEntity.getEmail(); }
    public String getNickname() { return userEntity.getNickname(); }
    public String getProvider() { return userEntity.getProvider(); }
}
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
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
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
| `/login/oauth2/code/kakao` | GET | 카카오 redirect-uri (Security 자동 처리) → JWT 발급 | ❌ |
| `/api/refresh/reissue` | POST | Access Token 재발급 (Refresh Token 필요) | ❌ |
| `/api/my/info` | GET | 내 정보 조회 | ✅ |
| `/api/logout` | POST | 로그아웃 (Refresh Token 폐기) | ✅ |

---

## 10. 참고사항

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
