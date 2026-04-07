# Spring WebSocket + STOMP 채팅 서버 (spring_csr_chat)

## 이 글의 목적

Spring WebSocket + STOMP로 기본 채팅서버를 만들고,  
Spring Security와 JWT를 이용해 인증 후  
**웹, 앱 모두 같은 채팅방에서 채팅**을 하는 기능을 구현한다.

웹에서는 ajax를 통해 `/api` 요청을 해서 화면을 구성한다.  
*(따로 웹 서버 만들기 귀찮아서 채팅서버에 WebController → return 단순화면 추가)*

즉 seucirty jwt (oatuh2제외) + stomp

---

## Spring WebSocket

웹 소켓은 HTML5에 등장한 실시간 웹 애플리케이션을 위해 설계된 통신 프로토콜이며, TCP 기반으로 연결한다.  
웹 소켓은 신뢰성 있는 데이터 전송을 보장하며, 메시지 경계를 존중하고, 순서가 보장된 양방향 통신을 제공한다.

클라이언트-서버 간 최초 연결이 이루어지면, 이 연결을 통해 양방향 통신을 지속적으로 할 수 있다.  
이 때 데이터는 패킷형태로 전달되며, 전송은 **연결 중단과 추가 HTTP 요청없이** 양방향으로 이뤄진다.

> 첫 연결은 HTTP를 통해 이루어지고, 이후 WS 프로토콜로 중단없이 통신이 이루어진다.  
> 이 때 HTTP 첫 연결을 **HTTP 핸드쉐이크**라고 한다.  
> TCP 기반 위에 HTTP 핸드쉐이크 후, TCP 기반 위에 WS 프로토콜 통신.

---

## STOMP

STOMP는 **Simple Text Oriented Messaging Protocol**의 약자로  
메시징 시스템 간에 데이터를 교환하기 위한 간단하면서도 유연한 텍스트 기반 프로토콜이다.  
웹 소켓 기반으로 동작하며, 메시징 애플리케이션에서 표준 프로토콜로 채택되어 있다.

STOMP는 WebSocket에는 없는 **Pub/Sub 구조**를 가지고 있다.
- **발행자(Publisher)** 가 특정 토픽이나 큐에 메시지를 생성하고 발행한다.
- **메시지 브로커(Message Broker)** 가 발행된 메시지를 관리한다.
- **구독자(Subscriber)** 는 특정 주제나 큐에 구독할 수 있고, 브로커는 등록된 모든 구독자에게 해당 주제의 메시지를 전달한다.

WebSocket은 서버-클라이언트 1:1 통신이고, 간혹 연결이 끊기면 메세지가 사라질 수 있다.  
반면 STOMP는 메세지 브로커로 **1:N 통신**을 지원하고, 메세지를 서버에 저장했다가 클라이언트에 송신하므로 더 안전하다.

> 여기서 발행자는 메세지를 보내는 사람이고,  
> 구독자가 구독하는 '특정 주제'는 **채팅방** 정도로 생각하면 된다.  
> STOMP 없이 웹 소켓만 사용하면 채팅방 기능을 직접 코딩해야 하지만,  
> STOMP를 이용하면 채팅방 기능이 이미 만들어져있다고 생각하면 된다.

---

## Spring WebSocket 채팅 동작 과정

### 1. WebSocket 서버 생성

![WebSocket 서버 생성](stomp설명이미지들/img.png)

`WebSocketMessageBrokerConfigurer`를 구현한 `WebSocketConfig`를 Spring에 등록하면  
Spring 서버 안에 WebSocket 서버가 생긴다.

### 2. HTTP 핸드쉐이크 (물리적 연결)

![HTTP 핸드쉐이크](stomp설명이미지들/img_1.png)

클라이언트가 `new SockJS("/ws-chat")`을 하는 순간 웹 소켓 서버와 **HTTP 핸드쉐이크**를 한다.  
이 때 `localhost:8080/ws-chat` 요청은 HTTP 요청이기 때문에 **Spring Security의 HTTP 설정**의 영향을 받는다.  
여기까지하면 클라이언트와 서버가 **물리적인 연결만** 된 것이다.

### 3. STOMP 프로토콜 선언

![STOMP over](stomp설명이미지들/img_2.png)

물리적인 연결이 된 후에 STOMP 방식으로 메시지를 주고받겠다고 선언한다. (`Stomp.over`)

### 4. STOMP Connect + JWT 인증

![STOMP Connect](stomp설명이미지들/img_3.png)

Connect를 하는 순간부터 **웹 소켓 인터셉터(ChannelInterceptor)** 가 작동하는데, 여기서는 Connect 때 **JWT 인증**을 한다.

HttpSession처럼 웹 소켓 서버도 각각의 클라이언트를 식별할 수 있는 **Session**이 있는데,  
인증 후 이 Session에 `UsernamePasswordAuthenticationToken`을 저장한다.  
이후에는 클라이언트가 유저 이름을 메시지에 보내지 않아도 서버에서 유저이름을 알 수 있다.

또 연결에 성공하면 `WebSocketEventListener`에 의해 **연결성공 이벤트**가 실행된다.  
이 때 같은 방에 있는 사람들한테만 메세지를 전송한다. ("~~님이 입장했습니다.")

> **참고:** 웹 소켓 서버의 인터셉터는 HTTP 핸드쉐이크부터 동작할 수 있다.  
> 이 때의 인터셉터는 `HandshakeInterceptor`이고,  
> CONNECT, SUBSCRIBE, SEND, DISCONNECT 등에서 동작하는 인터셉터는 `ChannelInterceptor`이다.  
> 여기서는 `ChannelInterceptor`를 통해 **Connect일 때만 JWT 인증**, 나머지는 그냥 통과하도록 했다.

### 5. Subscribe (방 입장)

![Subscribe](stomp설명이미지들/img_4.png)

그 후 `subscribe("/sub/room/1")`을 통해 1번 방에 입장한다.

### 6. Send (메시지 전송)

![Send 1](stomp설명이미지들/img_5.png) ![Send 2](stomp설명이미지들/img_6.png)

이후에는 `send("/pub/room/1")`을 통해 C1이 메세지를 보내면  
웹 소켓 서버는 room/1에 있는 c1, c2한테 메세지를 전부 보낸다.

연결할 때 유저정보를 웹 소켓 세션에 저장했기 때문에  
msg를 서버가 받은 다음 유저정보를 같이 보낸다. (가공)  
클라이언트는 유저정보를 굳이 보내지 않는다.  
(인증 없이 Spring WebSocket만 할 때 `{user: "user1", msg: "내용"}`, 이 프로젝트에서는 `{content: "내용"}`만 보냄)

> **참고:**
> - `/ws-chat` 순간이나 매 메시지마다 JWT 인증을 할 수도 있지만, 여기서는 **Connect 때만 인증**을 했다.
> - 인터셉터는 Connect 뿐만 아니라 매번 메시지 보낼 때마다 거치게 된다. (다만 Connect가 아닐 때는 그냥 통과)
> - 채팅과 별개로 방 정보(`/api/rooms`, `/api/room/**`)도 Spring Security 인증이 필요하다.

---

## 동작 이미지

![웹 채팅방 입장](stomp설명이미지들/img_7.png)

![앱에서도 참여](stomp설명이미지들/img_8.png)

user1(왼쪽 브라우저), user3(앱), user2(오른쪽 브라우저) 순으로 입장했다.

![채팅 진행](stomp설명이미지들/img_9.png)

![퇴장](stomp설명이미지들/img_10.png)

user1(왼쪽 브라우저), user3(앱)이 퇴장한 모습.

---

## Spring 채팅 코드 설명

채팅과 관련된 핵심 코드만 설명한다.  
그 외의 코드(방 관리, 유저, JWT 등)는 소스 코드 참고.

간단히 설명하면:
- 웹의 화면은 단순 html을 반환하는 `WebController`가 있고,
- 실제 화면 데이터(방 정보 등)는 html에서 jQuery ajax로 `/api/rooms`, `/api/room/**`로 요청한다.
- JWT 토큰은 `localStorage`에 저장하고, 서버에 인증할 때 `Authorization: Bearer {token}` 형식으로 보낸다.

---

## Spring Security

Spring Security JWT 기본 기능은 별도 참고.  
(이 프로젝트의 `security/` 패키지에 구현되어 있음)

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
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(form -> form.disable())
            .logout(logout -> logout.disable())
            .httpBasic(basic -> basic.disable());

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/login", "/loginPage", "/api/refresh/reissue", "/home", "/", "/rooms", "/room/**",
                    "/ws-chat", "/ws-chat/**",
                    "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
                ).permitAll()
                .requestMatchers("/api/me").authenticated()
                .requestMatchers("/api/rooms", "/api/room/**").authenticated()
                .requestMatchers("/api/logout").authenticated()
            );

        http
            .userDetailsService(customUserDetailsService)
            .addFilterAt(
                new JwtLoginFilter(authenticationConfiguration.getAuthenticationManager(), jwtUtil, refreshService),
                UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(
                new JwtAccessTokenCheckAndSaveUserInfoFilter(jwtUtil, customUserDetailsService),
                UsernamePasswordAuthenticationFilter.class);

        http
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    // ... 에러 원인에 따라 분기 처리 (토큰만료, 로그인실패 등)
                })
            );
        return http.build();
    }
}
```

**핵심 포인트:**
- `/ws-chat`, `/ws-chat/**`는 `permitAll` → 웹 소켓 **HTTP 핸드쉐이크 자체는 인증 불필요**
- `/api/rooms`, `/api/room/**` → `authenticated` → 채팅방 정보는 **인증 필요**
- 웹 소켓 인증은 STOMP Connect 단계에서 하며, 이는 **Spring Security가 아니라 WebSocket ChannelInterceptor에서 처리**

**필터 흐름:**
1. `JwtLoginFilter` — `/login` 요청 시 username/password 검증 → Access Token + Refresh Token 발급 → `RefreshService`를 통해 Refresh Token DB 저장
2. `JwtAccessTokenCheckAndSaveUserInfoFilter` — 매 HTTP 요청마다 Access Token 검증 → `SecurityContext`에 인증정보 저장

---

## Spring WebSocket + STOMP 채팅서버 구현

### WebSocketConfig

```java
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")     // 웹소켓 엔드포인트
            .setAllowedOriginPatterns("*")   // 모든 출처 허용 (CORS)
            .withSockJS();                   // SockJS fallback 허용
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");  // 클라이언트 → 서버
        registry.enableSimpleBroker("/sub");                 // 서버 → 클라이언트 (구독)
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new JwtChannelInterceptor(jwtUtil, customUserDetailsService));
    }
}
```

- `/pub` — 클라이언트가 메시지를 **보낼 때** 사용하는 prefix. `send("/pub/room/1")`
- `/sub` — 클라이언트가 메시지를 **받기 위해 구독**할 때 사용하는 prefix. `subscribe("/sub/room/1")`
- `SockJS` — WebSocket 연결 실패 시 HTTP Streaming, Long Polling 등으로 전환해 연결을 시도

---

### JwtChannelInterceptor

```java
@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (!StompCommand.CONNECT.equals(accessor.getCommand())) {
            return message;  // CONNECT가 아니면 토큰검증 안함
        }

        // CONNECT 요청에 대해 JWT 토큰 검증
        String token = accessor.getFirstNativeHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return null;  // 메시지 전송 차단 → 연결 거부
        }

        token = token.substring(7);
        String username = jwtUtil.validateAndExtractUsername(token);
        if (username == null) {
            return null;  // 검증실패
        }

        // 사용자 인증 정보 설정 → 웹소켓 세션에 저장
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        accessor.getSessionAttributes().put("user", authenticationToken);
        accessor.getSessionAttributes().put("roomId", accessor.getFirstNativeHeader("roomId"));
        accessor.setUser(authenticationToken);
        return message;
    }
}
```

**동작:**
1. STOMP `CONNECT` 요청이 아니면 → 그냥 통과
2. `Authorization` 헤더에서 JWT 토큰 추출
3. 토큰 검증 실패 시 → `return null` (연결 거부)
4. 검증 성공 시 → `UsernamePasswordAuthenticationToken`을 **웹소켓 세션**에 저장
5. `roomId`도 세션에 저장 (프론트에서 Connect 시 헤더로 보냄)

---

### ChatController

```java
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    // 클라이언트 → 서버 (send일 때만 옴)
    @MessageMapping("/room/{roomId}")
    public void sendMessage(
        @DestinationVariable String roomId,
        ChatMessage message,
        Message<?> msg
    ) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(msg);
        Object sessionUser = accessor.getSessionAttributes().get("user");
        Authentication auth = (Authentication) sessionUser;
        String username = auth.getName();
        message.setSender(username);
        messagingTemplate.convertAndSend("/sub/room/" + roomId, message);
    }
}
```

**동작:**
1. 클라이언트가 `/pub/room/{roomId}`로 메시지를 보내면 이 메소드가 실행됨
2. 웹소켓 세션에서 인증정보(user)를 꺼내서 sender로 설정
3. `convertAndSend`로 해당 방 구독자들에게 메시지 전송

---

### WebSocketEventListener

```java
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    // 연결됐을 때 한번 실행 (순서: JwtChannelInterceptor → 연결 → 여기)
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Authentication auth = (Authentication) accessor.getSessionAttributes().get("user");
        String roomId = (String) accessor.getSessionAttributes().get("roomId");

        if (auth != null) {
            String username = auth.getName();
            messagingTemplate.convertAndSend("/sub/room/" + roomId, username + "님이 입장했습니다.");
        }
    }

    // 연결 해제 (뒤로가기, 브라우저 종료 등 모두 감지)
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Authentication auth = (Authentication) accessor.getSessionAttributes().get("user");
        String roomId = (String) accessor.getSessionAttributes().get("roomId");

        if (auth != null && roomId != null) {
            String username = auth.getName();
            messagingTemplate.convertAndSend("/sub/room/" + roomId, username + "님이 퇴장했습니다.");
        }
    }
}
```

**동작:**
- **입장 이벤트:** STOMP Connect 성공 시 해당 방에 "~~님이 입장했습니다." 메시지 전송
- **퇴장 이벤트:** Disconnect 시 (브라우저 종료, 뒤로가기 등 모두 감지) "~~님이 퇴장했습니다." 메시지 전송

---

### room.html (클라이언트)

```javascript
$(document).ready(function() {
    const token = localStorage.getItem("accessToken");
    const roomId = window.location.pathname.split("/").pop();

    // 현재 로그인한 사용자 이름 가져오기
    let currentUser = null;
    $.ajax({
        url: `/api/me`,
        method: "GET",
        headers: { "Authorization": "Bearer " + token },
        async: false,
        success: function(res) { currentUser = res.username; }
    });

    // STOMP 연결
    const socket = new SockJS('/ws-chat');          // 1. HTTP 핸드쉐이크
    const stompClient = Stomp.over(socket);         // 2. STOMP 프로토콜 선언

    stompClient.connect(
        { Authorization: "Bearer " + token, "roomId": roomId },  // 3. Connect (JWT 인증)
        function(frame) {
            // 4. 구독 (방 입장)
            stompClient.subscribe(`/sub/room/${roomId}`, function(message) {
                let msg = JSON.parse(message.body);

                if (!msg.sender) {
                    // 시스템 메시지 (입장/퇴장)
                    $('#chatList').append(`<li class="system-msg">${msg.content}</li>`);
                } else if (msg.sender === currentUser) {
                    // 내가 보낸 메시지
                    $('#chatList').append(`<li class="my-msg"><b>${msg.sender}</b>: ${msg.content}</li>`);
                } else {
                    // 남이 보낸 메시지
                    $('#chatList').append(`<li class="other-msg"><b>${msg.sender}</b>: ${msg.content}</li>`);
                }
            });
        }
    );

    // 5. 메시지 보내기
    $('#sendBtn').click(function() {
        const msg = $('#msgInput').val();
        stompClient.send(`/pub/room/${roomId}`, {}, JSON.stringify({ content: msg }));
        $('#msgInput').val('');
    });
});
```

**클라이언트 흐름 요약:**
1. `new SockJS('/ws-chat')` → HTTP 핸드쉐이크 (Security `permitAll`)
2. `Stomp.over(socket)` → STOMP 프로토콜 선언
3. `stompClient.connect(헤더)` → JWT 토큰 + roomId를 헤더에 담아 Connect (ChannelInterceptor에서 인증)
4. `stompClient.subscribe('/sub/room/{roomId}')` → 채팅방 구독
5. `stompClient.send('/pub/room/{roomId}')` → 메시지 전송

---

## 프로젝트 구조

```
com.chat/
├── DemoApplication.java          # Spring Boot 메인
├── WebController.java            # 단순 화면 반환 (home, loginPage, rooms, room)
├── DataInitializer.java          # 초기 사용자 3명 생성
├── RoomDataInitializer.java      # 초기 채팅방 3개 생성
├── OpenApiConfig.java            # Swagger 설정
├── security/                     # JWT + Spring Security
│   ├── config/SecurityConfig.java
│   ├── filter/JwtLoginFilter.java
│   ├── filter/JwtAccessTokenCheckAndSaveUserInfoFilter.java
│   ├── JwtUtil.java
│   ├── entity/UserEntity.java
│   ├── entity/RefreshEntity.java
│   ├── model/CustomUserAccount.java
│   ├── repository/UserRepository.java
│   ├── repository/RefreshRepository.java
│   ├── service/CustomUserDetailsService.java
│   ├── service/RefreshService.java
│   ├── controller/UserController.java
│   ├── controller/RefreshController.java
│   └── controller/LogoutController.java
└── stomp/                        # WebSocket + STOMP 채팅
    ├── config/WebSocketConfig.java
    ├── config/WebSocketEventListener.java
    ├── interceptor/JwtChannelInterceptor.java
    ├── controller/ChatController.java
    ├── controller/RoomController.java
    ├── service/RoomService.java
    ├── entity/RoomEntity.java
    ├── model/ChatMessage.java
    ├── model/RoomDTO.java
    └── repository/RoomRepository.java
```

---

## 전체 흐름 요약

```
[클라이언트]                                   [서버]
    │                                            │
    │── POST /login (username, password) ──────→ │ JwtLoginFilter
    │←── {access_token, refresh_token} ─────────│
    │                                            │
    │── new SockJS("/ws-chat") ────────────────→ │ HTTP 핸드쉐이크 (Security permitAll)
    │                                            │
    │── Stomp.over(socket) ────────────────────→ │ STOMP 프로토콜 선언
    │                                            │
    │── stompClient.connect(JWT, roomId) ──────→ │ JwtChannelInterceptor (JWT 인증)
    │                                            │ → 웹소켓 세션에 user, roomId 저장
    │                                            │ → WebSocketEventListener (입장 메시지)
    │                                            │
    │── subscribe("/sub/room/1") ──────────────→ │ 1번 방 구독
    │                                            │
    │── send("/pub/room/1", {content}) ────────→ │ ChatController.sendMessage()
    │                                            │ → 세션에서 user 꺼내서 sender 설정
    │←── {sender, content} ─────────────────────│ → convertAndSend → 구독자들에게 전달
    │                                            │
    │── (브라우저 종료/뒤로가기) ───────────────→ │ WebSocketEventListener (퇴장 메시지)
```

