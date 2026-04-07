# Spring Security OAuth2 로그인 (세션 방식)

> 전체 코드: https://github.com/gks930620/spring_securty_all  
> 기본적인 OAuth2 로그인 설명은 **카카오**를 기본으로 설명합니다.  
> 동작방식의 각 동작에서 주소 등은 **설정파일(application.yml)**에서 설정하는 내용입니다.

---

## 동작 방식

### 1. 로그인 버튼 클릭

![로그인 버튼 클릭](seucirty%20이미지/img.png)

카카오 로그인 버튼 `<a>` 태그의 **href**는 `localhost:8080/oauth2/authorization/kakao` 고정이다.

Security에서 `localhost:8080/oauth2/authorization/{registrationId}`를 이용해 처음 OAuth2 로그인 과정을 시작.

`{registrationId}`는 `application.yml` 파일 설정에 따라 Security가 인식한다.

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          kakao:   # ← 이것이 registrationId
```

우리 서버는 브라우저에게 **응답코드 302**(redirect)로 응답한다.  
redirect할 주소는:

```
authorization-uri: https://kauth.kakao.com/oauth/authorize
```

---

### 2. 카카오 인증 서버로 요청

![카카오 인증 서버로 요청](seucirty%20이미지/img_1.png)

redirect를 받았기 때문에 브라우저는 바로 아래 URL로 요청한다:

```
https://kauth.kakao.com/oauth/authorize
  ?response_type=code
  &client_id=YOUR_KAKAO_REST_API_KEY
  &redirect_uri=http://localhost:8080/login/oauth2/code/kakao
  &scope=profile_nickname,account_email
  &state=xyz123
```

이때 파라미터에 있는 `redirect-uri`는 다음과 같고, 이 값을 카카오 서버가 기억하게 된다.

```yaml
redirect-uri: http://localhost:8080/login/oauth2/code/kakao  # 카카오 로그인 후 우리 서버에서 로그인하기 위한 주소 (카카오 developer와 일치)
```

---

### 3. 카카오 로그인 페이지에서 ID, PW 입력

![카카오 로그인 페이지](seucirty%20이미지/img_2.png)

카카오 로그인 페이지에서 카카오 ID, PW를 입력한다.

성공하면 카카오 인증서버로부터 **응답코드 302**의 response를 받는다.  
이때 redirect 주소는 아까 2번 과정의 `redirect-uri`.  
또 response에 **인가코드**가 포함된다.

참고로 인가코드가 포함되는 이유는 인가코드 방식으로 설정했기 때문:

```yaml
authorization-grant-type: authorization_code
```

---

### 4. redirect-uri로 요청

```yaml
redirect-uri: http://localhost:8080/login/oauth2/code/kakao
```

**카카오에서는 로그인 성공**해서 인가코드를 준 상태이고,  
브라우저는 인가코드를 포함해 redirect-uri로 우리 서버에 요청한다.

![redirect-uri로 요청](seucirty%20이미지/img_3.png)

이제 우리 서버에서 로그인을 진행하게 된다.  
이후 과정은 재요청 없이 현재 요청 진행 과정 중 로그인 과정을 설명한다.

---

### 5. 로그인 과정

![로그인 과정](seucirty%20이미지/img_4.png)

이미 카카오 서버에서는 로그인 성공한 것이고 그 결과로 요청에 인가코드가 포함된 상태이다.

우리 서버는 우리 서버에서의 로그인(유저정보가 필요한데 카카오에 있음)을 위해:

1. **인가코드 → access_token (userRequest)** — Security가 알아서 처리
2. **access_token → 유저정보 획득 후 우리 서버에 맞게 로그인유저정보를 만든다** — 개발자가 구현

여기서 토큰 요청 주소와 유저정보 요청 주소:

```yaml
token-uri: https://kauth.kakao.com/oauth/token       # 인증서버
user-info-uri: https://kapi.kakao.com/v2/user/me      # 리소스서버
```

#### Security 기본 로그인과 비교

![Security 기본 로그인 비교](seucirty%20이미지/img_5.png)

Security 기본 로그인과 비교해 보면 **UserDetails를 이용해 로그인 판단을 하지 않는다.**  
로그인 판단 여부는 카카오가 이미 했기 때문.

---

### 6. 세션 저장

우리 서버에 맞는 유저정보를 **session에 저장**한다. (Security 기본과 동일)

![세션 저장](seucirty%20이미지/img_6.png)

---

### ※ 카카오 공식 로그인 프로세싱 이미지 이해하기

[https://developers.kakao.com/docs/latest/ko/kakaologin/common](https://developers.kakao.com/docs/latest/ko/kakaologin/common) 에 있는 이미지이다.  
빨간줄은 생략된 부분.

![카카오 공식 이미지](seucirty%20이미지/img_7.png)

공식 이미지에서는 웹 환경뿐만 아니라 여러 환경에서의 사용을 고려해 생략하였지만,  
웹 환경에서는 브라우저가 직접 redirect를 진행하기 때문에 **빨간줄**과 같은 과정이 있다.  
1~6까지의 과정을 잘 이해했다면 공식 이미지의 과정도 잘 이해할 수 있을 것이다.

---

### ※ redirect-uri 참고

`redirect-uri`가 **`http://localhost:8080/login/oauth2/code/kakao`** 일 때는 yml에 설정하지 않아도 된다.  
Security에서 기본으로 인식하는 값이 `/login/oauth2/code/{registrationId}` 이기 때문이다.

`http://localhost:8080/login/my/redirect-uri` 등으로 설정했다면 카카오 개발자앱센터에서도 이렇게 등록하면 된다.

다만 이때는 내 서버가 기본 로그인 과정인  
인가코드로 카카오인증서버요청 + 토큰으로 카카오리소스서버 요청 + OAuth2UserDetailsService 등을 처리하지 않는다.

개발자가 따로 `/login/my/redirect-uri`에 해당하는 컨트롤러를 만들고  
RestTemplate 등으로 카카오인증서버요청 + 토큰으로 카카오리소스서버 요청 + 기타 등등의 과정을 만들어야 한다.

> **결론**: 카카오 개발자 앱 센터에는 왠만하면 `http://localhost:8080/login/oauth2/code/kakao`로 등록하자!

---

## 프로젝트 세팅

### 의존성

Spring Data JPA, Spring Web, Thymeleaf,  
Spring Boot Devtools, Lombok, Spring Security, **OAuth2 Client**

### application.yml (OAuth2 부분)

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          kakao:
            client-id: ${KAKAO_CLIENT_ID}  # 카카오 REST API 키
            redirect-uri: http://localhost:8080/login/oauth2/code/kakao
            authorization-grant-type: authorization_code
            # ※ client-secret 미설정 → Spring Boot 3.4+ 에서 자동으로 client-authentication-method: none 적용
            #    3.3.x 이하에서는 명시적으로 client-authentication-method: none 필요
            scope:
              - profile_nickname
              - account_email

          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}  # 구글은 secret 필요
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

### 개발자가 구현해야 할 것

| 파일 | 역할 |
|------|------|
| `SecurityConfig` | URL 접근 권한, 로그인/로그아웃 URL, OAuth2 로그인 설정 |
| `CustomUserAccount` | **UserDetails + OAuth2User 통합 구현** — SessionUserDTO 재사용 (composition) |
| `CustomUserDetailsService` | 일반 로그인 처리 — username으로 DB 조회 후 UserDetails 반환 |
| `CustomOAuth2UserService` | OAuth2 로그인 처리 — OAuth 유저정보로 DB 조회/저장 후 OAuth2User 반환 |
| `OAuthProvider` | Enum — 카카오/구글 등 provider별 attributes → UserEntity 변환 로직 |

### 통합 UserDetails (UserDetails + OAuth2User)

OAuth2 로그인과 일반 폼 로그인에서 **같은 타입**을 사용하기 위해  
`CustomUserAccount`가 `UserDetails`와 `OAuth2User`를 동시에 구현한다.

따로 구현하면 Controller에서 `instanceof`로 분기해야 하므로 통합이 편리하다.

```java
public class CustomUserAccount implements UserDetails, OAuth2User {
    private final SessionUserDTO sessionUser;  // 세션 사용자 정보
    private final String password;              // Security 인증용
    private final Map<String, Object> attributes; // OAuth2 전용 (폼 로그인 시 null)
    // ...
}
```

> **security_oauth2 프로젝트와의 차이점 (프로젝트_구조_정리.md 원칙 적용)**:
> - security_oauth2: Entity를 직접 세션에 저장 (`CustomUserAccount`가 `UserEntity` 직접 보유)
> - 이 프로젝트: **SessionUserDTO**를 composition으로 사용 (Entity 세션 저장 금지 원칙)
> - `CustomUserAccount.from(entity)` → 내부에서 `SessionUserDTO.from(entity)` 재사용 + password 추가

### OAuth2 로그인 흐름

```
사용자가 카카오 로그인 버튼 클릭 → /oauth2/authorization/kakao
  ↓ Security가 카카오 인증서버로 redirect (302)
  ↓ 카카오 로그인 페이지에서 ID/PW 입력
  ↓ 카카오가 인가코드와 함께 redirect-uri로 redirect
  ↓ Security가 인가코드 → access_token 교환 (자동)
  ↓ Security가 access_token으로 유저정보 요청 (자동)
  ↓ CustomOAuth2UserService.loadUser() 호출 (개발자 구현)
     - OAuth2 유저정보 → UserEntity로 변환 (OAuthProvider enum)
     - DB에 없으면 저장 (첫 로그인)
     - CustomUserAccount(SessionUserDTO + attributes) 반환
  ↓ Security가 세션에 저장 → 로그인 완료
```









### CustomOAuth2UserService 흐름
```
Security가 loadUser(userRequest) 자동 호출
  ↓ super.loadUser(userRequest) — user-info-uri에서 유저정보 가져옴 (Security 자동)
  ↓ registrationId 확인 ("kakao", "google")
  ↓ OAuthProvider.from(registrationId) — Enum으로 변환
  ↓ oAuthProvider.toUserEntity(attributes) — Provider별 유저정보 → UserEntity 변환
  ↓ DB에 없으면 save (첫 로그인 = 자동 회원가입)
  ↓ CustomUserAccount.of(entity, attributes) 반환 — SessionUserDTO로 변환하여 세션 저장
```

### OAuthProvider Enum
- `GOOGLE("google")`: attributes에서 sub, name, email 추출
- `KAKAO("kakao")`: attributes에서 id, kakao_account.email, properties.nickname 추출
- `from(registrationId)`: 문자열 → Enum 변환
- `toUserEntity(attributes)`: Provider별 추상 메서드 — UserEntity.builder()로 생성

### UserEntity 변경사항
- `provider` 필드 추가 (String: "kakao", "google" / 폼 로그인 시 null)
- OAuth2 사용자 username: `provider + id` (예: "kakao1234567890", "google112233...")
- OAuth2 사용자 password: `"oauth2user"` (비밀번호 불필요 — NOT NULL 제약조건 충족용 더미값)
