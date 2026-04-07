# Spring Security OAuth2 로그인 (SSR, 세션 방식)

> **핵심 요약**: OAuth2는 **설정(yml)**만 하면 Security가 알아서 처리한다.  
> 개발자가 할 일은 **받은 유저정보를 내 서버의 Entity/DTO로 변환**하고,  
> 처음 로그인이면 **DB 저장 후 세션에 저장**, 이미 있으면 **세션에만 저장**하는 것.

---

## 1. 동작 방식 (카카오 기준)

### 전체 흐름 한눈에 보기

```
① 카카오 로그인 버튼 클릭 → /oauth2/authorization/kakao
② Security가 카카오 인증서버로 redirect (302)
③ 카카오 로그인 페이지에서 ID/PW 입력
④ 카카오가 인가코드와 함께 redirect-uri로 redirect
⑤ Security가 인가코드 → access_token 교환 (자동)
⑥ Security가 access_token으로 유저정보 요청 (자동)
⑦ CustomOAuth2UserService.loadUser() 호출 ← 여기만 개발자가 구현
   - 유저정보 → UserEntity 변환
   - DB에 없으면 저장 (첫 로그인 = 자동 회원가입)
   - CustomUserAccount 반환 → Security가 세션에 저장
```

> **①~⑥은 Security가 자동으로 처리**한다.  
> 개발자가 신경 쓸 건 **⑦번 — 받은 유저정보를 내 서버에 맞게 변환하는 것**뿐.

---

### 상세 과정

#### ① 로그인 버튼 클릭

![로그인 버튼 클릭](seucirty%20이미지/img.png)

카카오 로그인 버튼의 href는 **고정값**이다:

```html
<a href="/oauth2/authorization/kakao">카카오로 로그인</a>
```

Security가 `/oauth2/authorization/{registrationId}`를 인식해서 OAuth2 로그인을 시작한다.  
`registrationId`는 yml 설정에서 가져온다:

```yaml
spring.security.oauth2.client.registration:
  kakao:    # ← 이것이 registrationId
```

---

#### ② 카카오 인증서버로 redirect

![카카오 인증 서버로 요청](seucirty%20이미지/img_1.png)

우리 서버가 **302 redirect** 응답을 보내고, 브라우저가 카카오 인증서버로 요청한다:

```
https://kauth.kakao.com/oauth/authorize     ← authorization-uri
  ?client_id=카카오REST_API키
  &redirect_uri=http://localhost:8080/login/oauth2/code/kakao
  &response_type=code
  &scope=profile_nickname,account_email
```

---

#### ③ 카카오 로그인 페이지에서 ID/PW 입력

![카카오 로그인 페이지](seucirty%20이미지/img_2.png)

사용자가 카카오 ID/PW를 입력하고 로그인 성공하면,  
카카오 인증서버가 **인가코드**와 함께 `redirect-uri`로 302 redirect한다.

```yaml
authorization-grant-type: authorization_code  # 인가코드 방식
```

---

#### ④ redirect-uri로 우리 서버에 요청

![redirect-uri로 요청](seucirty%20이미지/img_3.png)

브라우저가 인가코드를 포함하여 우리 서버로 요청한다:

```
http://localhost:8080/login/oauth2/code/kakao?code=인가코드&state=xxx
```

**여기서부터는 브라우저 재요청 없이, 서버 내부에서 로그인 과정이 진행된다.**

---

#### ⑤⑥ Security가 자동 처리하는 부분

![로그인 과정](seucirty%20이미지/img_4.png)

| 단계 | 처리 | 요청 URL (yml 설정) |
|------|------|---------------------|
| 인가코드 → access_token | Security **자동** | `token-uri: https://kauth.kakao.com/oauth/token` |
| access_token → 유저정보 | Security **자동** | `user-info-uri: https://kapi.kakao.com/v2/user/me` |
| 유저정보 → 내 서버 로그인 | **개발자 구현** | `CustomOAuth2UserService.loadUser()` |

---

#### ⑦ CustomOAuth2UserService — 개발자가 구현하는 부분

```
Security가 loadUser(userRequest) 자동 호출
  ↓ super.loadUser(userRequest) — 유저정보 가져옴 (Security 자동)
  ↓ registrationId 확인 ("kakao" or "google")
  ↓ OAuthProvider.from(registrationId) — Enum으로 변환
  ↓ oAuthProvider.toUserEntity(attributes) — Provider별 유저정보 → UserEntity 변환
  ↓ DB에 없으면 save (첫 로그인 = 자동 회원가입)
  ↓ CustomUserAccount.of(entity, attributes) 반환 → 세션에 저장
```

---

#### 일반 로그인과 비교

![Security 기본 로그인 비교](seucirty%20이미지/img_5.png)

| | 일반 폼 로그인 | OAuth2 로그인 |
|---|---|---|
| 로그인 판단 | Security가 password 비교 | **카카오가 이미 판단** |
| 개발자 구현 | CustomUserDetailsService | CustomOAuth2UserService |
| 세션 저장 | CustomUserAccount (from) | CustomUserAccount (of + attributes) |

**OAuth2에서는 비밀번호 비교를 하지 않는다** — 로그인 성공/실패는 카카오가 이미 판단했기 때문.

> **참고 — 사용자가 직접 redirect-uri로 요청하면?**
>
> 서버 입장에서 redirect-uri(`/login/oauth2/code/kakao`)로 오는 요청도 그냥 하나의 HTTP 요청일 뿐이다.  
> 그렇다면 카카오 로그인을 거치지 않고 **브라우저에 직접 주소를 입력**하면 어떻게 될까?
>
> **결론: 에러가 발생하고 로그인 페이지로 튕겨 나간다.**
>
> Security는 URI만 보고 판단하지 않는다.  
> ①~② 단계(카카오로 보내는 단계)에서 서버 세션에 **인증 요청 정보(`OAuth2AuthorizationRequest`)**를 저장해두고,  
> redirect-uri로 돌아왔을 때 **"내가 보냈던 인증 요청의 응답인가?"**를 검증한다.
>
> | 상황 | 결과 |
> |------|------|
> | 직접 URL 입력 (code, state 없음) | 세션에 인증 시도 정보 없음 → `AuthenticationException` → 로그인 실패 |
> | 가짜 code 포함해서 접근 | 서버가 생성한 `state` 값과 불일치 → **CSRF 공격으로 간주** → 로그인 실패 |
> | 정상 흐름 (카카오 redirect) | 세션의 state와 일치 + 유효한 code → 토큰 교환 → 로그인 성공 |
>
> 즉, Security가 검증하는 것은 access_token이 아니라 카카오가 준 **인가 코드(code)**와  
> ② 단계에서 서버가 생성한 **state 값의 일치 여부**이다.

---

#### 세션 저장

![세션 저장](seucirty%20이미지/img_6.png)

일반 로그인과 동일하게 Security가 **세션에 Authentication(CustomUserAccount)을 저장**한다.

---

### ※ 카카오 공식 프로세스 이미지

![카카오 공식 이미지](seucirty%20이미지/img_7.png)

[카카오 공식 문서](https://developers.kakao.com/docs/latest/ko/kakaologin/common)의 이미지.  
빨간줄은 웹 환경에서 브라우저가 redirect를 진행하는 과정 (공식 이미지에서는 생략됨).

---

### ※ redirect-uri 참고

| redirect-uri 값 | yml 설정 | 동작 |
|---|---|---|
| `/login/oauth2/code/kakao` (기본값) | 생략 가능 | Security가 자동 처리 (인가코드→토큰→유저정보→세션) |
| `/login/my/custom-uri` (커스텀) | 명시 필요 | 개발자가 직접 Controller + RestTemplate 구현 필요 |

> **결론**: 카카오 개발자센터에는 `http://localhost:8080/login/oauth2/code/kakao`로 등록하자.

---

## 2. 프로젝트 세팅

### 의존성

```
Spring Data JPA, Spring Web, Thymeleaf, Spring Boot Devtools, Lombok,
Spring Security, OAuth2 Client, thymeleaf-extras-springsecurity6
```

### application.yml (OAuth2 부분)

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          kakao:
            client-id: ${KAKAO_CLIENT_ID}
            client-authentication-method: none   # ★ 카카오는 secret 불필요 → none
                                                  # 기본값 client_secret_basic은 401 에러 발생
            redirect-uri: http://localhost:8080/login/oauth2/code/kakao
            authorization-grant-type: authorization_code
            scope:
              - profile_nickname
              - account_email

          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}   # 구글은 secret 필요
            scope:
              - profile
              - email
            authorization-grant-type: authorization_code
            redirect-uri: http://localhost:8080/login/oauth2/code/google

        provider:
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-name-attribute: id
          # google은 Security가 기본 제공하므로 provider 설정 불필요
```

#### ★ client-authentication-method 설명

| 값 | 동작 | 용도 |
|---|---|---|
| `client_secret_basic` (기본값) | client_id:secret을 **Authorization 헤더**로 전송 | 구글 등 |
| `client_secret_post` | client_id, secret을 **POST body**로 전송 | - |
| `none` | 인증 없이 client_id만 **POST body**로 전송 | **카카오** (secret 불필요) |

> 카카오에 `client_secret_basic`(기본값)이 적용되면 **401 에러** 발생.  
> 카카오 토큰 엔드포인트는 Authorization 헤더 인증을 지원하지 않기 때문.

---

## 3. 개발자가 구현해야 할 것

| 파일 | 역할 |
|------|------|
| `SecurityConfig` | URL 접근 권한, 폼/OAuth2 로그인 설정, PasswordEncoder Bean |
| `CustomUserAccount` | **UserDetails + OAuth2User 통합** — SessionUserDTO composition |
| `CustomUserDetailsService` | 일반 폼 로그인 처리 — username으로 DB 조회 후 UserDetails 반환 |
| `CustomOAuth2UserService` | OAuth2 로그인 처리 — 유저정보 → Entity 변환 → DB 저장 → 세션 저장 |
| `OAuthProvider` | Enum — Provider별(카카오/구글) attributes → UserEntity 변환 |

---

## 4. 통합 UserDetails (UserDetails + OAuth2User)

OAuth2 로그인과 일반 폼 로그인에서 **같은 타입**을 사용하기 위해  
`CustomUserAccount`가 `UserDetails`와 `OAuth2User`를 동시에 구현한다.

따로 구현하면 Controller에서 `instanceof`로 분기해야 하므로 **통합이 편리**하다.

```java
public class CustomUserAccount implements UserDetails, OAuth2User {
    private final SessionUserDTO sessionUser;          // 세션 사용자 정보 (composition)
    private final String password;                      // Security 인증용
    private final Map<String, Object> attributes;       // OAuth2 전용 (폼 로그인 시 null)

    // 일반 로그인: CustomUserAccount.from(entity)        → attributes = null
    // OAuth2:    CustomUserAccount.of(entity, attributes) → attributes 포함
}
```

> **프로젝트_구조_정리.md 원칙 적용**:
> - Entity를 직접 세션에 저장하지 않고 **SessionUserDTO**를 composition으로 사용
> - `CustomUserAccount.from(entity)` 내부에서 `SessionUserDTO.from(entity)` 재사용

---

## 5. OAuthProvider Enum

Provider마다 유저정보(attributes) 구조가 다르므로, Enum으로 변환 로직을 분리한다.

```
구글 attributes:          카카오 attributes:
{                          {
  "sub": "112233...",        "id": 1234567890,
  "name": "John",           "kakao_account": {
  "email": "j@g.com"          "email": "u@e.com"
}                            },
                             "properties": {
                               "nickname": "홍길동"
                             }
                           }
```

| Provider | username | password | email/nickname |
|---|---|---|---|
| GOOGLE | `"google" + sub` | `"oauth2user"` (더미값) | attributes에서 직접 추출 |
| KAKAO | `"kakao" + id` | `"oauth2user"` (더미값) | kakao_account, properties에서 추출 |

---

## 6. UserEntity 변경사항 (OAuth2 추가)

| 필드 | 설명 | 예시 |
|---|---|---|
| `provider` | OAuth2 provider 이름 (폼 로그인 시 null) | "kakao", "google" |
| `username` | OAuth2: provider + id | "kakao1234567890" |
| `password` | OAuth2: `"oauth2user"` (비밀번호 불필요) | NOT NULL 제약조건 충족용 더미값 |

---

## 7. SecurityConfig 핵심 설정

```java
// 일반 폼 로그인
http.formLogin(auth -> auth
    .loginPage("/login")
    .loginProcessingUrl("/loginProc")
    .defaultSuccessUrl("/")
    .failureUrl("/login?error=true")
    .permitAll()
);

http.userDetailsService(customUserDetailsService);

// OAuth2 로그인
http.oauth2Login(oauth2 -> oauth2
    .loginPage("/login")         // 폼 로그인과 같은 페이지
    .defaultSuccessUrl("/")
    .userInfoEndpoint(userInfo -> userInfo
        .userService(customOAuth2UserService)
    )
);
```

> 폼 로그인과 OAuth2 로그인의 `loginPage`를 같게 설정하면  
> 한 페이지에서 ID/PW 입력 + 카카오/구글 버튼을 모두 제공할 수 있다.
