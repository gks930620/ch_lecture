# OAuth2 소셜 로그인 (카카오 / 구글) — SSR, 세션 방식

> **핵심 한 줄 요약**
> OAuth2는 **설정(application.yml)** 만 해두면 인가코드 교환·토큰 요청·유저정보 조회까지 **Spring Security가 알아서 처리**한다.
> 개발자가 실제로 짜는 코드는 딱 하나 —
> "**소셜에서 받은 유저정보를 내 서버의 `UserEntity`로 바꾸고, 처음 로그인이면 DB에 저장한 뒤 세션에 담는 것**"뿐이다.

이 문서는 이 프로젝트(`spring_ssr_security_oauth2_chat`)의 **OAuth2 로그인 흐름**을 초심자 기준으로 정리한다.
폼 로그인(아이디/비밀번호)은 `프로젝트_구조_정리.md`의 "로그인" 절을, 채팅은 `스프링채팅설명.md`를 참고하면 된다.

---

## 1. OAuth2 로그인이란?

**OAuth2 로그인**(소셜 로그인)은 카카오·구글 같은 **외부 인증서버**가 "이 사람 맞다"고 대신 확인해 주는 방식이다.

- 우리 서버는 **비밀번호를 직접 받지도, 저장하지도 않는다.**
- 사용자가 카카오/구글 화면에서 로그인하면, 그쪽이 "인증됨"을 증명하는 정보를 우리 서버에 넘겨준다.
- 우리는 그 정보로 **회원을 식별**하고, 처음 보는 사용자면 **자동으로 회원가입**시킨다.

이 프로젝트가 사용하는 방식은 **인가 코드 그랜트(Authorization Code Grant)** 이다.

---

## 2. 전체 흐름 한눈에 보기 (카카오 기준)

```
[브라우저]                 [우리 서버(Spring)]              [카카오 인증서버/리소스서버]
    │                            │                                  │
    │ ① "카카오로 로그인" 클릭   │                                  │
    │  /oauth2/authorization/kakao                                  │
    │ ──────────────────────────>│                                  │
    │                            │ ② 카카오로 가라고 302 redirect   │
    │ <──────────────────────────│  (client_id, redirect_uri, state 포함)
    │                            │                                  │
    │ ③ 카카오 로그인 페이지로 이동 → ID/PW 입력                     │
    │ ─────────────────────────────────────────────────────────────>│
    │                            │                                  │
    │ ④ 로그인 성공 → 인가코드(code) 들고 redirect_uri로 302        │
    │ <─────────────────────────────────────────────────────────────│
    │                            │                                  │
    │ ⑤ /login/oauth2/code/kakao?code=인가코드&state=xxx            │
    │ ──────────────────────────>│                                  │
    │                            │ ⑥ code → access_token 교환 (자동)│
    │                            │ ─────────────────────────────────>│
    │                            │ <─────────────────────────────────│
    │                            │ ⑦ access_token으로 유저정보 요청 │
    │                            │ ─────────────────────────────────>│
    │                            │ <──────── 유저정보(attributes) ───│
    │                            │                                  │
    │        ⑧ CustomOAuth2UserService.loadUser() ← 여기만 개발자가 구현
    │           - 유저정보 → UserEntity 변환 (OAuthProvider)
    │           - DB에 없으면 저장 (첫 로그인 = 자동 회원가입)
    │           - CustomUserAccount 반환 → Security가 세션에 저장
    │                            │                                  │
    │ ⑨ 로그인 완료, "/"로 redirect (세션에 로그인 정보 저장됨)     │
    │ <──────────────────────────│                                  │
```

> **①~⑦은 Security가 자동으로 처리한다.**
> 개발자가 실제로 신경 쓰는 부분은 **⑧번 — 받은 유저정보를 내 서버에 맞게 변환하는 것**뿐이다.

| 단계 | 처리 주체 | 사용하는 yml 설정 |
|------|-----------|-------------------|
| ② 인증서버로 redirect | Security **자동** | `authorization-uri` |
| ⑥ 인가코드 → access_token | Security **자동** | `token-uri` |
| ⑦ access_token → 유저정보 | Security **자동** | `user-info-uri` |
| ⑧ 유저정보 → 내 서버 로그인 | **개발자 구현** | `CustomOAuth2UserService.loadUser()` |

---

## 3. 왜 폼 로그인과 OAuth2 로그인을 "같은 타입"으로 합치는가?

이 프로젝트는 **아이디/비밀번호 로그인**과 **소셜 로그인**을 둘 다 지원한다.
그런데 로그인 이후 Controller/화면에서는 "누가 로그인했는지"를 매번 두 가지로 나눠 처리하면 번거롭다.

그래서 **`CustomUserAccount` 하나로 통합**한다. 이 클래스는 `UserDetails`(폼 로그인용)와 `OAuth2User`(소셜 로그인용)를 **동시에 구현**한다.

```
           폼 로그인(UserDetails)        OAuth2 로그인(OAuth2User)
                  │                │
                  ▼                ▼
         ┌──────────────────────────────────┐
         │        CustomUserAccount         │
         │implements UserDetails, OAuth2User│
         │                                  │
         │  - SessionUserDTO sessionUser    │  ← 세션 사용자 정보 (공통)
         │  - String password               │  ← 폼 로그인 검증용
         │  - Map attributes                │  ← OAuth2 전용 (폼 로그인이면 null)
         └──────────────────────────────────┘
```

- **통합하지 않으면** Controller에서 `instanceof`로 "폼 로그인이야? 소셜이야?"를 매번 분기해야 한다.
- 통합하면 `@AuthenticationPrincipal CustomUserAccount user` 하나로 어느 경로로 로그인했든 동일하게 쓴다.

> **프로젝트 원칙(`프로젝트_구조_정리.md`) 적용**:
> Entity를 직접 세션에 저장하지 않고 **`SessionUserDTO`를 composition**으로 갖는다.
> - 폼 로그인: `CustomUserAccount.from(entity)` → `attributes = null`
> - OAuth2:  `CustomUserAccount.of(entity, attributes)` → `attributes` 포함
> 두 경우 모두 내부에서 `SessionUserDTO.from(entity)`를 재사용한다.

| 구분 | 폼 로그인 | OAuth2 로그인 |
|------|-----------|---------------|
| 로그인 성공/실패 판단 | **우리 서버**가 BCrypt로 password 비교 | **카카오/구글**이 이미 판단 |
| 개발자가 구현하는 서비스 | `CustomUserDetailsService` | `CustomOAuth2UserService` |
| 세션 저장 객체 생성 | `CustomUserAccount.from(entity)` | `CustomUserAccount.of(entity, attributes)` |

---

## 4. Provider마다 유저정보 구조가 다르다 — `OAuthProvider` Enum

카카오와 구글은 유저정보(attributes)의 **JSON 구조가 서로 다르다.** 그래서 Provider별 변환 로직을 `OAuthProvider` Enum으로 분리한다.

```
구글 attributes                     카카오 attributes
{                                   {
  "sub":  "112233445566...",          "id": 1234567890,          ← Long 타입
  "name": "John Doe",                 "kakao_account": {
  "email":"john@gmail.com"              "email": "user@example.com"
}                                     },
                                      "properties": {
                                        "nickname": "홍길동"
                                      }
                                    }
```

| Provider | `username`(로그인 식별자) | `nickname` / `email` 위치 | 식별 attribute (yml `user-name-attribute`) |
|----------|--------------------------|---------------------------|--------------------------------------------|
| GOOGLE | `"google" + sub` | `name` / `email` (최상위) | `sub` (Security 기본 제공) |
| KAKAO  | `"kakao" + id`   | `properties.nickname` / `kakao_account.email` | `id` |

- `username`을 `provider + id`로 만들기 때문에 **카카오 회원과 구글 회원의 식별자가 겹치지 않는다.**
- 카카오는 동의 항목에 따라 email·nickname이 **null일 수 있어** null-safe 처리를 한다(닉네임 없으면 "카카오사용자" 기본값).

---

## 5. `UserEntity`의 `password` — OAuth2 사용자는 왜 더미값인가?

OAuth2 사용자는 우리 서버에 비밀번호를 만들지 않는다. 하지만 `UserEntity.password` 컬럼은 **NOT NULL**이라 뭐라도 채워야 한다.
그래서 `OAuthProvider`에서 더미 문자열을 넣는다.

```java
.password("{noop}oauth2user")   // OAuth2 사용자는 폼 로그인 비밀번호 검증을 하지 않음 → 이 값은 실제로 비교되지 않는 더미값
```

> ⚠️ **`{noop}` 접두사에 대한 오해 주의**
> `{noop}`은 원래 Spring Security의 `DelegatingPasswordEncoder`에서 "이 비밀번호는 인코딩하지 않았다"는 표시로 쓰는 관례다.
> 하지만 **이 프로젝트의 `PasswordEncoder` Bean은 `BCryptPasswordEncoder` 단독**이라 `{noop}`이 특별한 의미를 갖지 않는다(그냥 문자열의 일부일 뿐이다).
> - **버그는 아니다.** OAuth2 사용자는 폼 로그인(`/loginProc`)의 password 비교 과정을 아예 타지 않으므로, 이 값이 검증에 쓰이는 일이 없다.
> - 다만 "`{noop}` 덕분에 안전하게 처리된다"고 오해하지 말 것. 정확히는 **"NOT NULL 제약을 채우기 위한, 사용되지 않는 더미값"** 이다.

---

## 6. 카카오 설정에서 자주 막히는 지점 — `client-authentication-method: none`

```yaml
kakao:
  client-id: ${KAKAO_CLIENT_ID}
  client-authentication-method: none   # ★ 카카오는 client_secret이 없음 → none
  redirect-uri: http://localhost:8080/login/oauth2/code/kakao
  authorization-grant-type: authorization_code
```

| 값 | client_id/secret 전송 위치 | 용도 |
|----|----------------------------|------|
| `client_secret_basic` (secret이 있을 때의 기본값) | **Authorization 헤더** | 구글 등 |
| `client_secret_post` | POST body | - |
| `none` | client_id만 **POST body**로 전송 (secret 없음) | **카카오** |

> client-secret이 설정돼 있으면 지금도 기본 인증 방식은 `client_secret_basic`이다(구글이 이 경우).
> 카카오 토큰 엔드포인트는 secret이 없어서 Authorization 헤더 인증을 지원하지 않는다.
> 만약 `client_secret_basic`이 적용되면 카카오가 **401 에러**를 낸다.
> Spring Boot 3.4+ 에서는 secret이 없으면 자동으로 `none`이 잡히기도 하지만, 버전과 무관하게 안전하도록 **명시**하는 것을 권장한다.

구글은 인가코드 → access_token 과정에서 `client-secret`이 **필요**하며, provider 정보(authorization/token/user-info URI)는 Security가 기본 제공하므로 yml에 따로 적지 않아도 된다.

---

## 7. 개발자가 만든 파일 정리

| 파일 | 역할 |
|------|------|
| `SecurityConfig` | URL 접근 권한, 폼/OAuth2 로그인 설정, `PasswordEncoder` Bean |
| `CustomUserAccount` | **UserDetails + OAuth2User 통합** — `SessionUserDTO` composition |
| `CustomUserDetailsService` | 폼 로그인 처리 — username으로 DB 조회 후 `UserDetails` 반환 |
| `CustomOAuth2UserService` | OAuth2 로그인 처리 — 유저정보 → Entity 변환 → DB 저장 → 세션 저장 |
| `OAuthProvider` (Enum) | Provider별(카카오/구글) attributes → `UserEntity` 변환 |

### SecurityConfig 핵심 (폼 + OAuth2 동시 설정)

```java
// ── 폼 로그인 ──
http.formLogin(auth -> auth
        .loginPage("/login")
        .loginProcessingUrl("/loginProc")   // Controller 불필요 — Security가 처리
        .defaultSuccessUrl("/")
);
http.userDetailsService(customUserDetailsService);

// ── OAuth2 로그인 ──
http.oauth2Login(oauth2 -> oauth2
        .loginPage("/login")                // 폼 로그인과 같은 페이지 → 한 화면에 ID/PW + 소셜 버튼
        .defaultSuccessUrl("/")
        .userInfoEndpoint(userInfo -> userInfo
                .userService(customOAuth2UserService)   // ⑧번 처리 지점
        )
);
```

> 폼 로그인과 OAuth2 로그인의 `loginPage`를 같게 지정하면 **한 페이지**에서 아이디/비밀번호 입력과 카카오/구글 버튼을 함께 제공할 수 있다.

---

## 8. 로그인 버튼은 어떻게 시작되나?

소셜 로그인 시작 URL은 **Security가 자동으로 만들어 주는 고정 형식**이다. 직접 Controller를 만들 필요가 없다.

```html
<a href="/oauth2/authorization/kakao">카카오로 로그인</a>
<a href="/oauth2/authorization/google">구글로 로그인</a>
```

- `/oauth2/authorization/{registrationId}` 형식이며, `{registrationId}`는 yml의 `registration:` 아래 이름(`kakao`, `google`)과 일치해야 한다.
- 이 URL로 요청이 오면 Security가 위 §2의 ②단계(인증서버로 redirect)를 시작한다.

---

## 9. 요약

1. **설정(yml)이 대부분**이다. 인가코드 교환·토큰 요청·유저정보 조회는 Security가 자동으로 한다.
2. 개발자는 **`CustomOAuth2UserService.loadUser()`** 에서 받은 유저정보를 `UserEntity`로 바꾸고, 없으면 DB에 저장한 뒤 세션에 담는다.
3. Provider마다 유저정보 구조가 다르니 **`OAuthProvider` Enum**으로 변환을 분리한다.
4. 폼 로그인과 OAuth2 로그인을 **`CustomUserAccount` 하나로 통합**해 이후 처리를 단순화한다.
5. 카카오는 `client-authentication-method: none`, 구글은 `client-secret` 필요 — 이 차이만 주의하면 된다.
