# JWT 토큰 인증방식 (Access Token + Refresh Token)

전체코드 : https://github.com/gks930620/spring_securty_all

---

## 1. JWT를 이용한 토큰인증방식

JWT라는 거는 **JSON Web Token**의 약자로서 토큰의 하나의 형식 또는 종류라 할 수 있다.  
토큰인증방식을 꼭 JWT를 쓸 필요는 없지만 JWT가 많이 쓰인다.  
토큰인증방식은 다음과 같은 방식으로 진행된다.

1. 사용자가 로그인하면 JWT를 발급
2. 이후 요청마다 JWT를 HTTP Header에 포함 (`Authorization: Bearer <토큰>`)
3. 서버는 토큰을 검증하여 사용자를 인증

---

## 2. 토큰인증방식이 필요한 이유

기존의 세션방식 말고 왜 토큰인증방식이 필요한가?

### 2-1. 서버확장성

세션방식은 사용자 수가 많아질수록 서버의 세션 메모리 부담이 증가하고  
하나의 서버가 세션을 관리하는 세션방식에서 여러대의 서버로 확장하는 경우 세션관리를 위한  
별도의 방법(세션 클러스터링 등)이 필요하게 됨.

토큰인증방식에서는 사용자가 많아져도 사용자가 토큰을 가지고 있기때문에 서버의 메모리 부담이 없음.  
또 서버가 여러개여도 서버는 토큰을 검증하기만 하면 됨.

### 2-2. API 서버와 클라이언트 분리

API서버와 클라이언트가 분리된 환경(React, Vue 등)에서 인증을 적용할 때  
세션방식보다 토큰방식이 적합함.  
(JWT 방식은 헤더방식이기 때문에 브라우저의 CORS 쿠키정책 영향을 받지 않는 등)

또 API 서버가 분리되었기 때문에 웹 뿐만 아니라 모바일 앱, 외부API 서비스 등  
여러 클라이언트에서도 인증을 쉽게 할 수 있음.

---

## 3. Access Token과 Refresh Token

기본적으로 토큰인증방식에서 JWT는 토큰의 형식/종류이다.  
이 JWT를 가지고 토큰인증방식에서 **access token** 역할과 **refresh token** 역할을 부여한다.

- **access token** : username을 포함하고 있는 토큰.  
  클라이언트는 매 요청마다 access token을 서버에 보내 인증된 사용자라는 걸 알린다.  
  (서버는 매 요청마다 검사. username으로 매번 DB 조회)  
  기본적으로 **30분**의 만료기간을 갖는다.

- **refresh token** : access token을 재발급하는 용도로만 쓰이며 보통 **7일 or 30일**의 만료기간을 갖는다.  
  refresh token조차 만료된다면 클라이언트는 다시 로그인을 해야한다.

이 30분과 7일이라는건 **보안과 사용자 편의성 간의 균형** 때문이다.  
30분마다 재로그인을 하는건 불편, 그렇다고 7일동안 사용자 정보가 포함된 토큰을 사용한다면 보안위험.  
(물론 access token이 탈취된 30분 동안은 위험함)  
그래서 사용자 정보는 30분으로 제한하고 7일동안은 로그인을 보장하기 위한 refresh token을 병행하게 됨.

refresh token은 토큰인증방식의 stateless를 보완하기 위해 예외적으로  
**서버가 DB 등에 저장 + 클라이언트에도 저장**함 (저장 방법은 클라이언트마다 다름).  
access token이 만료되었을 때  
클라이언트는 refresh token을 다시 서버에 보내고 서버는 DB의 refresh token과 비교 후 같다면  
로그인 없이 다시 access token을 재발급 함.

---

## 4. 동작방식

### 로그인 시도
![로그인 시도](1.session방식과jwt방식차이설명이미지/img.png)

### 로그인 시도 후 인증된 사용자 요청
![인증된 사용자 요청](1.session방식과jwt방식차이설명이미지/img_1.png)

클라이언트는 로그인 필요한 곳에 access token을 포함해 요청하게 된다.

### access token 만료 후 요청
![access token 만료 후 요청](1.session방식과jwt방식차이설명이미지/img_2.png)

클라이언트는 access token이 만료된 줄 모르고 access token을 가지고 요청한다.  
서버는 access token 검사 후 만료됐다는 사실을 클라이언트에 전달한다.

### refresh token으로 재발급
![refresh token 전달](1.session방식과jwt방식차이설명이미지/img_3.png)

이후 클라이언트는 기존의 access token은 버리고, 새롭게 받은 access token으로 다시  
인증된 사용자 요청을 하게 된다.

### 만료된 refresh token 전달
![만료된 refresh token 전달](1.session방식과jwt방식차이설명이미지/img_4.png)

클라이언트는 refresh token가 만료된줄 모르고 access token 재발급을 시도한다.  
서버는 클라이언트에게 refresh token이 만료됐다는 사실을 전달한다.  
이후 **클라이언트는 다시 로그인을 시도**하면 된다.

### 클라이언트와 API서버

클라이언트와 API서버를 분리했을 때 서버는 각각의 요청에 맞게 잘 처리하면 되고  
redirect 등을 통해 클라이언트가 어떻게 재요청하는지는 신경쓰지 않아도 된다.  
**서버입장에서는 위의 동작방식에서의 모든 요청을 각각 처리할 뿐**이다.

---

## 5. 프로젝트 세팅

https://start.spring.io/ 에서 프로젝트를 생성합니다.  
필요한 library는  
Spring Data JDBC, H2 Database, Spring Data JPA, Spring Web, Thymeleaf,  
Spring Boot Devtools, Lombok, Spring Security 입니다.

jwt, dotenv(.env 환경변수 설정) 라이브러리는 build.gradle에 직접 추가합니다.

```groovy
implementation 'io.jsonwebtoken:jjwt-api:0.12.3'
implementation 'io.jsonwebtoken:jjwt-impl:0.12.3'
implementation 'io.jsonwebtoken:jjwt-jackson:0.12.3'

implementation 'io.github.cdimascio:dotenv-java:2.2.0'
```

### application.yml

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:security
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: create
    properties:
      hibernate:
        show_sql: true
        format_sql: true
        default_batch_fetch_size: 100
    open-in-view: false
  devtools:
    livereload:
      enabled: true
    freemarker:
      cache: false
    restart:
      enabled: true
  thymeleaf:
    cache: false

jwt:
  secret : ${JWT_SECRET_KEY}
    #키는 길이만 충분하고 노출되지만 않으면 됨.
  expiration_access: 60000   #1분 테스트용
  expiration_refresh : 300000   #테스트용 5분

logging:
  level:
    org.hibernate.SQL: debug
    org.hibernate.type: trace
    org.springframework.security : DEBUG
```

---

## 6. 회원가입 및 Security 기본 세팅

회원가입, 로그인 판단(CustomUserDetailsService), security 사용자 정보 등은 기존 security 방식과 동일하다.  
JWT에서 달라지는 부분은 로그인 성공/실패 처리, 매 요청마다 토큰 검증 부분이다.

### JoinDTO

```java
@Setter
@Getter
public class JoinDTO {
    private String username;
    private String password;
}
```

### UserEntity

```java
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private List<String> roles = new ArrayList<>();
}
```

### RefreshEntity

refresh token은 서버 DB에 저장해야 한다.

```java
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @Column(unique = true)
    private String token;
}
```

### CustomUserAccount (security에서 사용자정보로 사용할 객체)

```java
@Getter
public class CustomUserAccount implements UserDetails {
    private UserEntity userEntity;

    public CustomUserAccount(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userEntity.getRoles().stream()
            .map(SimpleGrantedAuthority::new)
            .toList();
    }
}
```

### UserRepository / RefreshRepository

```java
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
```

```java
public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {
    public void deleteByToken(String token);
    public RefreshEntity findByToken(String token);
}
```

### JoinService

```java
@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void joinProcess(JoinDTO joinDTO) {
        UserEntity find = userRepository.findByUsername(joinDTO.getUsername());
        if (find != null) {
            System.out.println("이미 있는 ID입니다.");
            return;
        }
        UserEntity user = new UserEntity();
        user.setUsername(joinDTO.getUsername());
        user.setPassword(passwordEncoder.encode(joinDTO.getPassword()));
        user.getRoles().add("USER");
        userRepository.save(user);
    }
}
```

### CustomUserDetailsService

```java
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUsername(username);
        if (userData != null) {
            return new CustomUserAccount(userData);
        }
        throw new UsernameNotFoundException(username + "에 대한 회원정보가 없습니다.");
    }
}
```

### JoinController / MainController

```java
@Controller
@RequestMapping("/api/join")
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @ResponseBody
    @PostMapping
    public String joinPost(@RequestBody JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return "회원가입 완료!";
    }
}
```

```java
@Controller
@RequestMapping("/api")
public class MainController {

    @RequestMapping("/my/info")
    @ResponseBody
    public String myInfo(@AuthenticationPrincipal CustomUserAccount customUserAccount) {
        StringBuilder sb = new StringBuilder();
        sb.append("권한 : " + customUserAccount.getAuthorities().iterator().next().getAuthority() + "<br>");
        sb.append("password : " + customUserAccount.getPassword() + "<br>");
        sb.append("username : " + customUserAccount.getUsername() + "<br>");
        return sb.toString();
    }
}
```

---

## 7. JwtUtil

JWT 토큰 생성, 검증, 추출 기능을 제공하는 유틸 클래스.  
access token과 refresh token 두 종류를 생성하며, `token_type` 클레임으로 구분한다.

```java
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration_access}")
    private long expirationAccess;

    @Value("${jwt.expiration_refresh}")
    private long expirationRefresh;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Access Token 생성
    public String createAccessToken(String username) {
        return Jwts.builder()
            .subject(username)
            .claim("token_type", "access")
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expirationAccess))
            .signWith(getSigningKey())
            .compact();
    }

    // Refresh Token 생성
    public String createRefreshToken(String username) {
        return Jwts.builder()
            .subject(username)
            .claim("token_type", "refresh")
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expirationRefresh))
            .signWith(getSigningKey())
            .compact();
    }

    // 토큰에서 username 추출
    public String extractUsername(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    // 토큰 유효성 검증 (만료되었으면 false)
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰에서 token_type 클레임 추출 (만료되었어도 추출 가능)
    public String getTokenType(String token) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
            return claims.get("token_type", String.class);
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            return claims != null ? claims.get("token_type", String.class) : null;
        }
    }
}
// jjwt 버전에 따라 구현방식이 다르다. 현재는 0.12.3 버전.
```

---

## 8. SecurityConfig

```java
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final RefreshService refreshService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http  //내부H2DB 확인용.
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        http    //기본 session방식관련 다 X
            .csrf(csrf -> csrf.disable())
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(form -> form.disable())
            .logout(logout -> logout.disable())  //기본 로그아웃 사용X
            .httpBasic(basic -> basic.disable());

        http   //경로와 인증/인가 설정.
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/api/join", "/api/refresh/reissue").permitAll()
                .requestMatchers("/api/my/info", "/api/logout").authenticated()
            );

        http          //필터
            .userDetailsService(customUserDetailsService)
            .addFilterAt(
                new JwtLoginFilter(authenticationConfiguration.getAuthenticationManager(), jwtUtil,
                    refreshService),
                UsernamePasswordAuthenticationFilter.class)  //기존 세션방식의 로그인 검증필터 대체.
            .addFilterBefore(
                new JwtAccessTokenCheckAndSaveUserInfoFilter(jwtUtil, customUserDetailsService),
                UsernamePasswordAuthenticationFilter.class);

        http
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    String errorCause =
                        request.getAttribute("ERROR_CAUSE") != null
                            ? (String) request.getAttribute("ERROR_CAUSE") : null;

                    //인증없이(access token없이) 인증필요한 곳에 접근했을 때.
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
                        return;
                    }
                })
            );
        return http.build();
    }
}
```

`exceptionHandling.authenticationEntryPoint`는 다음 3가지 상황에서 진입한다:
- 인증없이(access token없이) 인증 필요한 url에 접근했을 때
- 토큰만료
- 로그인실패

이후 각 필터 코드에서 `request.setAttribute("ERROR_CAUSE", ...)` 로 원인을 구분한다.

---

## 9. 로그인 - JwtLoginFilter

![로그인시도](3refresh_token도%20있을%20때%20설명이미지/img.png)

`/login`으로 요청이 오면 security는 기본적으로 `UsernamePasswordAuthenticationFilter`가 동작한다.  
이 필터를 상속받아 JWT용으로 대체한 것이 `JwtLoginFilter`이다.

- **로그인 판단**: `CustomUserDetailsService`에서 username/password 비교 (기존 security와 동일)
- **성공 시**: access token + refresh token 발급, refresh token은 DB에 저장
- **실패 시**: `ERROR_CAUSE`를 세팅하고 config의 `authenticationEntryPoint`로

```java
@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshService refreshService;

    // 로그인 시도 - body에서 username, password 추출
    // (API서버 분리 방식이라 parameter가 아닌 body로 옴. 이것때문에 재정의)
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Map<String, String> credentials = new ObjectMapper().readValue(request.getInputStream(), HashMap.class);
            String username = credentials.get("username");
            String password = credentials.get("password");

            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
            this.setDetails(request, authRequest);
            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse authentication request", e);
        }
    }

    // 로그인 성공 → access token + refresh token 발급
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserAccount customUserAccount = (CustomUserAccount) authResult.getPrincipal();

        String accessToken = jwtUtil.createAccessToken(customUserAccount.getUsername());
        String refreshToken = jwtUtil.createRefreshToken(customUserAccount.getUsername());

        // refresh token 저장은 Service를 통해 처리
        refreshService.saveRefreshForLogin(refreshToken, customUserAccount.getUsername());

        // 토큰을 응답에 포함
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(
            Map.of("access_token", accessToken, "refresh_token", refreshToken)
        ));
    }

    // 로그인 실패 → config의 authenticationEntryPoint로
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed)
        throws IOException, ServletException {
        request.setAttribute("ERROR_CAUSE", "로그인실패");
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
```

---

## 10. 매 요청마다 토큰 검증 - JwtAccessTokenCheckAndSaveUserInfoFilter

![access token으로 인증된 요청](3refresh_token도%20있을%20때%20설명이미지/img_1.png)

로그인 후 클라이언트가 access token을 가지고 인증이 필요한 곳에 접근할 때,  
**매 요청마다** access token을 검증하고 SecurityContext에 사용자 정보를 저장해야 한다.  
(`JwtLoginFilter`는 `/login`일 때만 동작하므로, 일반 요청에는 이 필터가 담당)

처리 흐름:
1. **토큰 없음** → 그냥 통과 (인증 필요한 곳이면 security가 `authenticationEntryPoint`로 보냄)
2. **refresh 토큰** → 그냥 통과 (`/api/refresh/reissue`는 permitAll이므로 무사 통과)
3. **access 토큰 만료** → `ERROR_CAUSE` 세팅 후 통과 → `authenticationEntryPoint`로
4. **access 토큰 유효** → SecurityContext에 사용자 정보 저장 → 인증된 상태로 통과

```java
@RequiredArgsConstructor
public class JwtAccessTokenCheckAndSaveUserInfoFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        // refresh 토큰이면 이 필터에서는 검증하지 않고 통과
        String tokenType = jwtUtil.getTokenType(token);
        if (tokenType.equals("refresh")) {
            chain.doFilter(request, response);
            return;
        }

        // access token 만료 체크
        if (!jwtUtil.validateToken(token)) {
            request.setAttribute("ERROR_CAUSE", "토큰만료");
            chain.doFilter(request, response);
            return;
        }

        // 유효한 access token → SecurityContext에 인증정보 저장
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

## 11. Refresh Token 재발급 - RefreshController

![refresh token 재발급 흐름](3refresh_token도%20있을%20때%20설명이미지/img_2.png)

클라이언트가 access token 만료 응답을 받으면, refresh token으로 토큰 재발급을 요청한다.

![/api/refresh/reissue 흐름 1](3refresh_token도%20있을%20때%20설명이미지/img_3.png)
![/api/refresh/reissue 흐름 2](3refresh_token도%20있을%20때%20설명이미지/img_4.png)

`/api/refresh/reissue`는 `permitAll()`이므로 필터에서 refresh 토큰을 감지하면 그냥 통과시킨다.  
컨트롤러에서 refresh 토큰 검증을 수행한다.

### RefreshService

```java
@Service
@RequiredArgsConstructor
public class RefreshService {
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 토큰이 DB에 존재하는지 확인 (Controller에서 Entity 대신 boolean 사용)
    @Transactional(readOnly = true)
    public boolean existsByToken(String token) {
        return refreshRepository.findByToken(token) != null;
    }

    // 로그인 시 refresh token 저장 (Filter에서 Repository 직접 사용 대신 Service 사용)
    @Transactional
    public void saveRefreshForLogin(String refreshToken, String username) {
        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUserEntity(userRepository.findByUsername(username));
        refreshEntity.setToken(refreshToken);
        refreshRepository.save(refreshEntity);
    }

    // 토큰 재발급 시 새 refresh token 저장
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

### RefreshController

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

        // 1. 폐기된 토큰(로그아웃)인지 검증 = DB에 있냐 없냐
        if (!refreshService.existsByToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error", "Refresh Token discarded"));
        }

        // 2. 기존 refresh 토큰 삭제
        refreshService.deleteRefresh(token);

        // 3. Refresh Token 만료 검증
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error", "Refresh Token expired"));
        }

        // 4. 새 토큰 발급
        String username = jwtUtil.extractUsername(token);
        String newAccessToken = jwtUtil.createAccessToken(username);
        String newRefreshToken = jwtUtil.createRefreshToken(username);

        refreshService.saveRefresh(newRefreshToken);
        return ResponseEntity.ok(Map.of("access_token", newAccessToken, "refresh_token", newRefreshToken));
    }
}
```

---

## 12. 로그아웃 - LogoutController

![로그아웃 흐름](3refresh_token도%20있을%20때%20설명이미지/img_5.png)

`/api/logout`은 `.authenticated()`이므로 access token을 가지고 요청해야 필터를 통과한다.  
Authorization 헤더에 Bearer방식으로 access token을 보내고,  
별도의 헤더(`RefreshToken`)로 refresh 토큰을 보내야 한다.

```java
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LogoutController {
    private final RefreshService refreshService;

    @RequestMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("RefreshToken") String refreshToken) {
        refreshService.deleteRefresh(refreshToken);
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
```

> ※ refresh 토큰을 삭제했기 때문에 refresh 토큰으로 재발급은 안된다.  
> 하지만 access token이 만료되기 전까지 기존 access token으로 로그인은 가능하다.  
> 여기서는 클라이언트가 기존 access token을 사용안한다고 믿는 거고,  
> 보안을 강화하려면 access token도 못 쓰게하는 별도의 기능이 서버에 필요하다.

---

## 13. 실행결과

### 로그인전 /my/info
![로그인전 /my/info](3refresh_token도%20있을%20때%20설명이미지/img_6.png)

### 회원가입전 로그인시도
![회원가입전 로그인시도](3refresh_token도%20있을%20때%20설명이미지/img_7.png)

### 회원가입
![회원가입](3refresh_token도%20있을%20때%20설명이미지/img_8.png)

### 회원가입 후 로그인시도 (access + refresh 토큰 발급)
![회원가입 후 로그인시도](3refresh_token도%20있을%20때%20설명이미지/img_9.png)

### access token으로 /api/my/info 요청
![access token으로 요청](3refresh_token도%20있을%20때%20설명이미지/img_10.png)

### access token 만료시 /api/my/info
![access token 만료시](3refresh_token도%20있을%20때%20설명이미지/img_11.png)

### refresh 토큰 재발급 /api/refresh/reissue
![refresh 토큰 재발급](3refresh_token도%20있을%20때%20설명이미지/img_12.png)

### 로그아웃 시도
(Authorization Bearer에 access token, 별도 헤더에 refresh 토큰)

![로그아웃 시도](3refresh_token도%20있을%20때%20설명이미지/img_13.png)

### 로그아웃 후 재발급 신청 → discarded
![로그아웃 후 재발급 신청](3refresh_token도%20있을%20때%20설명이미지/img_14.png)

### refresh 토큰 만료 → expired
![refresh 토큰 만료](3refresh_token도%20있을%20때%20설명이미지/img_15.png)

