# CORS & CSRF 개념 정리
## 🌐 CORS (Cross-Origin Resource Sharing)
### CORS란?
**다른 도메인에서 API를 호출할 수 있도록 허용하는 보안 정책**
브라우저는 기본적으로 **다른 도메인**의 API 호출을 차단합니다 (보안 목적).
---
## 현재 vs 미래 아키텍처
### 현재 (CORS 불필요)
```
[브라우저]
     ↓
[Spring Boot 서버] ─── HTML + API 모두 제공
localhost:8080
     │
     ├── /community (HTML 페이지)
     └── /api/communities (REST API)
→ 같은 도메인이라 CORS 불필요!
```
### 나중에 (CORS 필요!) ⭐
```
[브라우저]
     ↓
[웹 서버 (Nginx)]     [API 서버 (Spring Boot)]
https://my-app.com    https://api.my-app.com
     │                       │
     └── HTML/CSS/JS         └── @RestController만
         정적 파일                (ResponseBody)
→ 다른 도메인이라 CORS 필요!
```

### 왜 분리하나?
| 장점 | 설명 |
|------|------|
| **확장성** | API 서버만 스케일 아웃 가능 |
| **CDN** | 정적 파일을 CDN에서 제공 |
| **보안** | API 서버 숨기기 가능 |
| **유연성** | 프론트/백엔드 독립 배포 |

---

### 예시 상황
```
[프론트엔드]                    [백엔드 서버]
https://my-app.com     →→→     https://api.my-app.com
     ↑                              ↑
   도메인 A                       도메인 B
   ❌ 브라우저가 차단! (CORS 에러)
```
---
### 🔄 CORS 동작 흐름 (핵심!)
**브라우저가 먼저 백엔드에게 "허용해?"라고 물어본다!**
```
브라우저 (3000에서 받은 페이지)          백엔드 API (8080)
         │                                    │
         │  1️⃣ "야 8080, 3000에서 온         │
         │     요청 받아줄 수 있어?"          │
         │  ─────────────────────────────────→│
         │     (OPTIONS 요청 = Preflight)     │
         │                                    │
         │  2️⃣ 백엔드 응답:                  │
         │     "응, 3000은 허용해"            │
         │  ←─────────────────────────────────│
         │     (Access-Control-Allow-Origin)  │
         │                                    │
         │  3️⃣ 그제서야 진짜 요청 전송       │
         │  ─────────────────────────────────→│
         │     (GET /api/shops)               │
         │                                    │
         │  4️⃣ 데이터 응답                   │
         │  ←─────────────────────────────────│
         │     { shops: [...] }               │
```

### ❌ 백엔드가 허용 안 했으면?

```
브라우저 (3000)                          백엔드 API (8080)
         │                                    │
         │  1️⃣ "3000에서 온 요청 허용해?"    │
         │  ─────────────────────────────────→│
         │                                    │
         │  2️⃣ 백엔드: (CORS 설정 없음)      │
         │     "몰라, 안돼"                   │
         │  ←─────────────────────────────────│
         │                                    │
         │  ❌ 브라우저: "CORS 에러!"          │
         │     진짜 요청 안 보냄!              │
         │     (프론트 콘솔에 빨간 에러)       │
```

### 🎯 누가 뭘 하나?

| 질문 | 답변 |
|------|------|
| 누가 물어봐? | **브라우저**가 자동으로 (프론트 코드와 무관) |
| 누가 대답해? | **백엔드 서버** (CORS 설정으로) |
| 누가 차단해? | **브라우저** (백엔드가 허용 안 하면) |
| 프론트는? | **아무것도 못 함** (브라우저가 막으니까) |

### 👨‍💻 프론트 vs 백엔드 역할

| 구분 | 프론트엔드 | 백엔드 |
|------|-----------|--------|
| **할 일** | 그냥 fetch/axios로 요청 | **CORS 허용 설정** |
| **코드** | `fetch('/api/...')` | `@CrossOrigin` 또는 CorsConfig |
| **권한** | 없음 (브라우저가 차단함) | **허용 여부 결정** |
> 💡 **결론: CORS 설정은 백엔드 개발자가 해야 한다!**
---
### 언제 CORS 에러가 발생하나?
| 상황 | CORS 필요 |
|------|----------|
| `localhost:3000` (React) → `localhost:8080` (Spring) | ✅ 필요 |
| `my-app.com` → `api.my-app.com` | ✅ 필요 |
| Flutter 앱 → `api.my-app.com` | ❌ 앱은 브라우저가 아니라 불필요 |
| 같은 도메인에서 호출 | ❌ 불필요 |

### 현재 프로젝트는?

```
[브라우저]
localhost:8080/community  →  localhost:8080/api/communities
       ↑                            ↑
    같은 도메인이라 CORS 불필요!
```
**하지만 앱(Flutter)에서 호출하거나, 프론트엔드를 분리하면 CORS 필요!**

### CORS 설정 방법

> ⚠️ **중요**: Spring Security 사용 시 `WebMvcConfigurer` 방식은 Security 필터보다 늦게 동작하므로,
> 반드시 `CorsConfigurationSource` Bean으로 등록해야 함!

```java
// ❌ WebMvcConfigurer 방식 — Security 필터 이후에 동작 → 무용지물!
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) { ... }
}

// ✅ CorsConfigurationSource Bean 방식 — Security 필터에서 바로 사용됨
@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",     // React 개발 서버
            "http://localhost:8080",     // 로컬 개발
            "https://my-app.com"         // 운영 도메인
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);  // 쿠키 허용 (JWT 쿠키용)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```
### SecurityConfig에서 CORS 활성화
```java
// CorsConfigurationSource Bean을 자동으로 찾아서 사용
http.cors(Customizer.withDefaults());
```

### 왜 Bean 방식이어야 하나?
```
요청 → [Security 필터 체인] → [DispatcherServlet] → Controller
              ↑                        ↑
        CorsFilter 여기!         WebMvcConfigurer 여기!
        (먼저 실행됨)            (너무 늦음)

cors(Customizer.withDefaults())는 CorsConfigurationSource Bean을 찾음
→ Bean 없으면 기본 설정(모든 요청 거부) → "Invalid CORS request" 에러!
```



### CORS 에러 메시지 예시

```
Access to XMLHttpRequest at 'http://localhost:8080/api/...' 
from origin 'http://localhost:3000' has been blocked by CORS policy
```
---







## 🛡️ CSRF (Cross-Site Request Forgery)

### CSRF란?
**사용자가 의도하지 않은 요청을 보내게 만드는 공격**

### 공격 시나리오

```
1. 사용자가 은행 사이트에 로그인 (세션 쿠키 저장됨)
2. 사용자가 악성 사이트 방문
3. 악성 사이트에서 숨겨진 폼이 자동 제출됨:
   <form action="https://bank.com/transfer" method="POST">
     <input name="to" value="해커계좌">
     <input name="amount" value="1000000">
   </form>
4. 브라우저가 쿠키를 자동으로 포함시켜 요청 전송!
5. 서버는 정상 요청으로 인식하고 송금 처리 😱
```

### CSRF 방어 방법

| 방법 | 설명 |
|------|------|
| **CSRF 토큰** | 폼에 랜덤 토큰 포함, 서버에서 검증 |
| **SameSite 쿠키** | 다른 사이트에서 쿠키 전송 차단 |
| **JWT 사용** | 쿠키 대신 헤더로 토큰 전송 |

### 현재 프로젝트에서 CSRF 설정

```java
// SecurityConfig.java
http.csrf(csrf -> csrf.disable());  // CSRF 비활성화
```

**왜 비활성화?**
- 일반적으로 JWT를 `Authorization` 헤더로 전송하면 CSRF 공격에 안전함
  (악성 사이트에서 헤더를 조작할 수 없으므로)
- **하지만 이 프로젝트는 HttpOnly 쿠키에 JWT를 저장**하므로, 쿠키는 자동 전송됨
- 따라서 **SameSite=Lax** 설정으로 방어 (현재 적용됨)
  - SameSite=Lax: GET 요청만 다른 사이트에서 쿠키 전송 허용
  - POST/PUT/DELETE 같은 상태 변경 요청은 차단됨
- 결론: CSRF 토큰 없이도 SameSite 쿠키로 충분히 안전

---
### CSRF 상태

| 항목 | 상태 |
|------|------|
| CSRF 토큰 | ❌ 비활성화 |
| JWT 사용 | ✅ 헤더/쿠키 |   위조요청 방어를 jwt 해결  
| SameSite 쿠키 | ✅ Lax 설정 |
| **결론** | ✅ 안전함 |

---

