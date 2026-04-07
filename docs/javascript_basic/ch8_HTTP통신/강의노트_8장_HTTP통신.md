# 8장: HTTP 통신 이해

## 📋 강의 개요
- **소요 시간**: 약 6-8시간
- **난이도**: ⭐⭐ 초급~중급
- **선수 지식**: 웹 기초, JavaScript 비동기 처리
- **학습 목표**: HTTP 프로토콜을 이해하고 웹 통신의 원리를 파악할 수 있다.

---

## 📚 8.1 HTTP 프로토콜이란?

### 강의 포인트
- HTTP = HyperText Transfer Protocol
- 클라이언트와 서버 간의 통신 규약
- Stateless (무상태성) 특징

### HTTP란?
```
HTTP (HyperText Transfer Protocol)
= 웹에서 데이터를 주고받기 위한 프로토콜(규약)

클라이언트 (브라우저) ↔ HTTP ↔ 서버
```

### HTTP의 특징

#### 1. Stateless (무상태성)
```
서버는 클라이언트의 상태를 저장하지 않음

요청 1: 로그인 → 응답: 성공
요청 2: 데이터 조회 → 서버는 로그인 상태를 모름!

해결: Cookie, Session, Token
```

#### 2. Connectionless (비연결성)
```
요청 → 응답 → 연결 종료

장점: 서버 자원 효율적 사용
단점: 매번 연결/종료 오버헤드

해결: Keep-Alive (HTTP/1.1부터 기본)
```

#### 3. 클라이언트-서버 구조
```
클라이언트: 요청 (Request)
서버: 응답 (Response)

역할 분리 → 독립적 개발 가능
```

---

## 📚 8.2 HTTP 버전

### 강의 포인트
- HTTP/1.1이 가장 널리 사용됨
- HTTP/2는 성능 향상
- HTTP/3는 QUIC 프로토콜 사용

### HTTP/1.0 (1996년)
```
특징:
- 연결 → 요청 → 응답 → 종료 (매번)
- 하나의 연결에 하나의 요청

문제:
- 느림 (연결 비용 높음)
```

### HTTP/1.1 (1997년) ⭐ 현재 가장 널리 사용
```
개선:
✅ Keep-Alive (연결 재사용)
✅ Pipelining (요청 여러 개 동시 전송)
✅ Host 헤더 (가상 호스팅 지원)
✅ 캐시 제어 강화

문제:
❌ Head-of-Line Blocking (앞 요청이 느리면 뒤도 대기)
```

### HTTP/2 (2015년)
```
개선:
✅ Multiplexing (하나의 연결로 여러 요청/응답 동시 처리)
✅ Header 압축
✅ Server Push (서버가 먼저 전송)
✅ Binary Protocol (텍스트 → 바이너리)

성능: 30-50% 향상
```

### HTTP/3 (2022년)
```
개선:
✅ QUIC 프로토콜 사용 (UDP 기반)
✅ 더 빠른 연결 수립
✅ 패킷 손실에 강함

아직 도입 초기 단계
```

---

## 📚 8.3-8.4 HTTP 메시지 구조

### 강의 포인트
- 요청과 응답 모두 동일한 구조
- 시작 라인 + 헤더 + 빈 줄 + 본문

### HTTP 요청 (Request) 구조
```
GET /users/123 HTTP/1.1              ← 요청 라인 (Request Line)
Host: api.example.com                ← 헤더 (Headers)
Content-Type: application/json
Authorization: Bearer token123
                                      ← 빈 줄
{"name": "홍길동"}                    ← 본문 (Body, 선택)
```

#### 요청 라인 (Request Line)
```
메서드  경로  HTTP버전
  ↓     ↓     ↓
GET /users HTTP/1.1

메서드: GET, POST, PUT, DELETE 등
경로: 요청 대상 리소스
HTTP버전: HTTP/1.1, HTTP/2 등
```

### HTTP 응답 (Response) 구조
```
HTTP/1.1 200 OK                      ← 상태 라인 (Status Line)
Content-Type: application/json       ← 헤더 (Headers)
Content-Length: 50
Set-Cookie: session=abc123
                                      ← 빈 줄
{"id": 123, "name": "홍길동"}         ← 본문 (Body)
```

#### 상태 라인 (Status Line)
```
HTTP버전  상태코드  상태메시지
   ↓       ↓        ↓
HTTP/1.1 200     OK

상태코드: 200, 404, 500 등
상태메시지: OK, Not Found, Internal Server Error 등
```

---

## 📚 8.5 HTTP 메서드

### 강의 포인트
- GET, POST, PUT, DELETE가 가장 많이 사용됨
- CRUD에 대응됨
- 각 메서드의 의미를 정확히 이해

### GET - 조회 (Read)
```javascript
// 데이터 조회 (쿼리 파라미터 사용)
fetch('https://api.example.com/users?page=1&size=10')

특징:
✅ 안전 (Safe): 서버 상태를 변경하지 않음
✅ 멱등성 (Idempotent): 여러 번 실행해도 같은 결과
✅ 캐시 가능
❌ Body 사용 불가 (URL에 데이터 포함)
```

**예시**:
```
GET /users              → 사용자 목록 조회
GET /users/123          → 사용자 123 조회
GET /users?name=홍길동   → 이름으로 검색
```

### POST - 생성 (Create)
```javascript
// 새 데이터 생성
fetch('https://api.example.com/users', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
        name: '홍길동',
        email: 'hong@example.com'
    })
})

특징:
✅ Body에 데이터 전송
❌ 안전하지 않음 (서버 상태 변경)
❌ 멱등성 없음 (실행할 때마다 새 리소스 생성)
❌ 캐시 불가
```

**예시**:
```
POST /users             → 새 사용자 생성
POST /posts             → 새 게시글 작성
POST /login             → 로그인
```

### PUT - 전체 수정 (Update)
```javascript
// 리소스 전체를 새 데이터로 교체
fetch('https://api.example.com/users/123', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
        name: '김철수',        // 전체 데이터
        email: 'kim@example.com',
        age: 30
    })
})

특징:
✅ 멱등성 있음 (여러 번 실행해도 같은 결과)
❌ 안전하지 않음
```

**예시**:
```
PUT /users/123          → 사용자 123 전체 수정
PUT /posts/456          → 게시글 456 전체 수정
```

### PATCH - 부분 수정 (Partial Update)
```javascript
// 리소스의 일부만 수정
fetch('https://api.example.com/users/123', {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
        age: 31                 // 나이만 수정
    })
})

특징:
✅ PUT보다 효율적 (변경할 부분만 전송)
❌ 멱등성 보장 안 됨 (구현에 따라 다름)
```

**PUT vs PATCH**:
```
PUT: 전체 교체
  기존: {name: "홍길동", age: 30, email: "hong@..."}
  요청: {name: "홍길동", age: 31}
  결과: {name: "홍길동", age: 31}  ← email 사라짐!

PATCH: 부분 수정
  기존: {name: "홍길동", age: 30, email: "hong@..."}
  요청: {age: 31}
  결과: {name: "홍길동", age: 31, email: "hong@..."}  ← email 유지
```

### DELETE - 삭제 (Delete)
```javascript
// 리소스 삭제
fetch('https://api.example.com/users/123', {
    method: 'DELETE'
})

특징:
✅ 멱등성 있음 (여러 번 삭제해도 결과 같음)
❌ 안전하지 않음
```

**예시**:
```
DELETE /users/123       → 사용자 123 삭제
DELETE /posts/456       → 게시글 456 삭제
```

### OPTIONS - 지원 메서드 확인
```javascript
// 서버가 지원하는 메서드 확인
fetch('https://api.example.com/users', {
    method: 'OPTIONS'
})

// 응답 헤더
Allow: GET, POST, PUT, DELETE
```

### HEAD - 헤더만 조회
```javascript
// Body 없이 헤더만 가져옴
fetch('https://api.example.com/users/123', {
    method: 'HEAD'
})

// 사용 예: 리소스 존재 확인, 메타데이터 조회
```

### CRUD 대응표
```
CRUD      HTTP Method
-------   -----------
Create    POST
Read      GET
Update    PUT / PATCH
Delete    DELETE
```

---

## 📚 8.6 멱등성과 안전성

### 강의 포인트
- 안전 = 서버 상태를 변경하지 않음
- 멱등 = 여러 번 실행해도 같은 결과

### 안전 (Safe)
```
서버의 상태를 변경하지 않는가?

✅ 안전: GET, HEAD, OPTIONS
❌ 불안전: POST, PUT, PATCH, DELETE
```

### 멱등성 (Idempotent)
```
여러 번 실행해도 결과가 같은가?

✅ 멱등:
  GET /users/123     → 항상 같은 결과
  PUT /users/123     → 몇 번을 실행해도 같은 상태
  DELETE /users/123  → 이미 삭제되었으면 404 (상태 같음)

❌ 비멱등:
  POST /users        → 실행할 때마다 새 사용자 생성
```

### 비교표

| 메서드 | 안전 | 멱등 | 캐시 가능 |
|--------|------|------|-----------|
| GET    | ✅   | ✅   | ✅        |
| POST   | ❌   | ❌   | ❌        |
| PUT    | ❌   | ✅   | ❌        |
| PATCH  | ❌   | ❌   | ❌        |
| DELETE | ❌   | ✅   | ❌        |
| HEAD   | ✅   | ✅   | ✅        |
| OPTIONS| ✅   | ✅   | ❌        |

---

## 📚 8.7-8.8 HTTP 상태 코드

### 강의 포인트
- 3자리 숫자로 응답 상태 표현
- 첫 번째 자리로 카테고리 구분
- 주요 코드는 반드시 암기

### 상태 코드 분류
```
1xx: 정보 (Information)       - 거의 사용 안 함
2xx: 성공 (Success)           - 요청 성공
3xx: 리다이렉션 (Redirection)  - 추가 조치 필요
4xx: 클라이언트 에러           - 클라이언트 잘못
5xx: 서버 에러                - 서버 잘못
```

### 2xx - 성공

#### 200 OK ⭐
```javascript
// 요청 성공
GET /users → 200 OK + 사용자 목록
POST /login → 200 OK + 로그인 정보
```

#### 201 Created
```javascript
// 리소스 생성 성공
POST /users → 201 Created + Location: /users/123
```

#### 204 No Content
```javascript
// 성공했지만 반환할 내용 없음
DELETE /users/123 → 204 No Content
PUT /users/123 → 204 No Content
```

### 3xx - 리다이렉션

#### 301 Moved Permanently
```
영구 이동 (URL이 바뀜)
GET /old-url → 301 + Location: /new-url

검색 엔진도 URL 업데이트
```

#### 302 Found
```
임시 이동
GET /login → 302 + Location: /dashboard

원래 URL은 유효함
```

#### 304 Not Modified
```
캐시된 리소스 사용 (변경 없음)
GET /image.jpg → 304 Not Modified

브라우저 캐시에서 가져옴
```

### 4xx - 클라이언트 에러

#### 400 Bad Request ⭐
```javascript
// 잘못된 요청 (문법 오류, 유효하지 않은 데이터)
POST /users
Body: { "email": "잘못된 이메일" }
→ 400 Bad Request
```

#### 401 Unauthorized ⭐
```javascript
// 인증 필요 (로그인 안 함)
GET /my-profile
→ 401 Unauthorized
→ "인증이 필요합니다. 로그인하세요."
```

#### 403 Forbidden ⭐
```javascript
// 권한 없음 (로그인은 했지만 접근 불가)
DELETE /users/999  (다른 사용자 삭제 시도)
→ 403 Forbidden
→ "권한이 없습니다."
```

#### 404 Not Found ⭐⭐⭐
```javascript
// 리소스를 찾을 수 없음
GET /users/99999  (존재하지 않는 사용자)
→ 404 Not Found
→ "사용자를 찾을 수 없습니다."
```

#### 409 Conflict
```javascript
// 충돌 (중복 등)
POST /users
Body: { "email": "existing@example.com" }
→ 409 Conflict
→ "이미 사용 중인 이메일입니다."
```

### 5xx - 서버 에러

#### 500 Internal Server Error ⭐
```javascript
// 서버 내부 오류 (코드 버그, 예외 등)
GET /users
→ 500 Internal Server Error
→ "서버 오류가 발생했습니다."
```

#### 502 Bad Gateway
```
게이트웨이/프록시 서버 오류
서버가 유효하지 않은 응답을 받음
```

#### 503 Service Unavailable
```
서비스 이용 불가 (서버 다운, 유지보수)
GET /api/data
→ 503 Service Unavailable
→ Retry-After: 3600 (1시간 후 재시도)
```

### 주요 상태 코드 암기

**반드시 외워야 할 코드**:
```
200 OK              - 성공
201 Created         - 생성 성공
204 No Content      - 성공 (본문 없음)

301 Moved Permanently - 영구 이동
302 Found           - 임시 이동
304 Not Modified    - 캐시 사용

400 Bad Request     - 잘못된 요청
401 Unauthorized    - 인증 필요
403 Forbidden       - 권한 없음
404 Not Found       - 없음
409 Conflict        - 충돌

500 Internal Server Error - 서버 오류
502 Bad Gateway     - 게이트웨이 오류
503 Service Unavailable - 서비스 불가
```

---

## 📚 8.9 HTTP 헤더

### 강의 포인트
- 헤더는 메타데이터 전달
- 요청/응답 모두 헤더 사용
- 주요 헤더는 반드시 이해

### 일반 헤더 (General)
```
Date: Tue, 15 Jan 2024 10:00:00 GMT   # 메시지 생성 시간
Connection: keep-alive                 # 연결 유지
```

### 요청 헤더 (Request)

#### Host ⭐ (필수)
```
Host: api.example.com
→ 요청 대상 서버 (가상 호스팅)
```

#### User-Agent
```
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)...
→ 클라이언트 정보 (브라우저, OS 등)
```

#### Accept ⭐
```
Accept: application/json, text/html, */*
→ 클라이언트가 받을 수 있는 타입
```

#### Authorization ⭐
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
→ 인증 토큰
```

#### Cookie
```
Cookie: session=abc123; user_id=456
→ 쿠키 전송
```

### 응답 헤더 (Response)

#### Content-Type ⭐⭐⭐
```
Content-Type: application/json; charset=UTF-8
→ 응답 본문의 타입

주요 타입:
- application/json      # JSON
- text/html             # HTML
- text/plain            # 텍스트
- image/png             # PNG 이미지
- multipart/form-data   # 파일 업로드
```

#### Content-Length
```
Content-Length: 1234
→ 본문 크기 (바이트)
```

#### Set-Cookie ⭐
```
Set-Cookie: session=abc123; Path=/; HttpOnly; Secure
→ 쿠키 설정
```

#### Location
```
Location: /users/123
→ 리다이렉션 대상 URL (3xx에서 사용)
```

#### Allow
```
Allow: GET, POST, PUT, DELETE
→ 지원하는 메서드 (OPTIONS 응답)
```

### 캐시 헤더

#### Cache-Control ⭐
```
Cache-Control: no-cache               # 캐시 사용 전 재검증
Cache-Control: no-store               # 캐시 저장 안 함
Cache-Control: max-age=3600           # 3600초 동안 캐시
Cache-Control: public                 # 모든 캐시 가능
Cache-Control: private                # 브라우저만 캐시
```

#### ETag
```
ETag: "33a64df551425fcc55e4d42a148795d9f25f89d4"
→ 리소스 버전 (변경 감지)
```

### CORS 헤더

#### Access-Control-Allow-Origin ⭐
```
Access-Control-Allow-Origin: *
Access-Control-Allow-Origin: https://example.com
→ CORS 허용 출처
```

#### Access-Control-Allow-Methods
```
Access-Control-Allow-Methods: GET, POST, PUT, DELETE
→ CORS 허용 메서드
```

#### Access-Control-Allow-Headers
```
Access-Control-Allow-Headers: Content-Type, Authorization
→ CORS 허용 헤더
```

---

## 📚 8.10-8.12 쿠키, 세션, 스토리지

### 강의 포인트
- HTTP는 무상태(Stateless)
- 상태 유지를 위한 방법들
- 각각의 특징과 용도

### 쿠키 (Cookie)
```javascript
// 쿠키 설정 (서버)
Set-Cookie: user_id=123; Max-Age=3600; Path=/; HttpOnly; Secure

// 쿠키 전송 (클라이언트 → 서버)
Cookie: user_id=123

// JavaScript로 쿠키 설정
document.cookie = "name=홍길동; max-age=3600; path=/";

// JavaScript로 쿠키 읽기
console.log(document.cookie);  // "name=홍길동; session=abc123"
```

**쿠키 속성**:
```
Max-Age=3600        # 3600초 후 만료
Expires=날짜        # 특정 날짜에 만료
Path=/              # 경로 지정
Domain=.example.com # 도메인 지정
Secure              # HTTPS에서만 전송
HttpOnly            # JavaScript 접근 불가 (보안)
SameSite=Strict     # CSRF 방지
```

**사용 예**:
- 로그인 상태 유지
- 사용자 설정 저장
- 장바구니
- 추적/분석

### 세션 (Session)
```
세션 = 서버에 저장되는 사용자 상태

1. 클라이언트 로그인
2. 서버가 세션 생성 (session_id=abc123)
3. 서버가 쿠키로 session_id 전송
4. 클라이언트는 이후 요청마다 session_id 쿠키 전송
5. 서버는 session_id로 사용자 식별
```

**쿠키 vs 세션**:
```
쿠키:
✅ 클라이언트에 저장
✅ 빠름 (서버 요청 불필요)
❌ 보안 취약 (노출 가능)
❌ 용량 제한 (4KB)

세션:
✅ 서버에 저장
✅ 보안 좋음
❌ 서버 부하
❌ 서버 재시작 시 초기화
```

### 로컬 스토리지 (Local Storage)
```javascript
// 저장 (영구 보관)
localStorage.setItem('name', '홍길동');
localStorage.setItem('settings', JSON.stringify({ theme: 'dark' }));

// 읽기
const name = localStorage.getItem('name');
const settings = JSON.parse(localStorage.getItem('settings'));

// 삭제
localStorage.removeItem('name');
localStorage.clear();  // 전체 삭제

// 키 목록
for (let i = 0; i < localStorage.length; i++) {
    const key = localStorage.key(i);
    console.log(key, localStorage.getItem(key));
}
```

**특징**:
- 용량: 5-10MB
- 만료 없음 (영구)
- 도메인별로 격리
- 동기 API

### 세션 스토리지 (Session Storage)
```javascript
// 저장 (탭 닫으면 삭제)
sessionStorage.setItem('temp', 'temporary data');

// 읽기
const temp = sessionStorage.getItem('temp');

// API는 localStorage와 동일
```

**특징**:
- 탭 닫으면 삭제
- 탭 간 공유 안 됨
- 새로고침 시 유지

### 비교표

| 항목 | Cookie | Local Storage | Session Storage |
|------|--------|---------------|-----------------|
| 용량 | 4KB | 5-10MB | 5-10MB |
| 만료 | 설정 가능 | 영구 | 탭 닫을 때 |
| 서버 전송 | ✅ 자동 | ❌ 수동 | ❌ 수동 |
| 접근 | HTTP + JS | JS만 | JS만 |
| 보안 | HttpOnly 설정 가능 | 보통 | 보통 |

### 사용 가이드
```
Cookie:
- 로그인 세션 (HttpOnly, Secure)
- 추적/분석

Local Storage:
- 사용자 설정
- 오프라인 데이터
- 캐시

Session Storage:
- 폼 입력 임시 저장
- 페이지 간 데이터 전달 (같은 탭)
```

---

## 📚 8.13-8.14 HTTPS와 CORS

### HTTPS (HTTP Secure)
```
HTTPS = HTTP + SSL/TLS (암호화)

HTTP:  클라이언트 ↔ 서버 (평문 전송) ❌
HTTPS: 클라이언트 ↔ SSL/TLS ↔ 서버 (암호화) ✅
```

**SSL/TLS 역할**:
1. 암호화 (도청 방지)
2. 인증 (위장 방지)
3. 무결성 (변조 방지)

**HTTPS 인증서**:
```
인증 기관 (CA)이 발급
- Let's Encrypt (무료)
- DigiCert, Comodo 등 (유료)

브라우저가 인증서 검증
✅ 유효 → 자물쇠 표시
❌ 무효 → 경고 표시
```

### CORS (Cross-Origin Resource Sharing)
```
CORS = 다른 출처(도메인)의 리소스에 접근하기 위한 메커니즘

출처 = 프로토콜 + 도메인 + 포트

https://example.com:443/api/users
  ↓         ↓        ↓
프로토콜   도메인    포트
```

**Same-Origin (같은 출처)**:
```
https://example.com/page1
https://example.com/page2
→ 같은 출처 ✅

https://example.com
https://api.example.com
→ 다른 출처 ❌ (서브도메인 다름)

http://example.com
https://example.com
→ 다른 출처 ❌ (프로토콜 다름)
```

**CORS 에러**:
```javascript
// https://mysite.com에서
fetch('https://api.example.com/data')
// ❌ CORS Error!

// 콘솔 에러:
Access to fetch at 'https://api.example.com/data' from origin 
'https://mysite.com' has been blocked by CORS policy
```

**CORS 해결 (서버 설정)**:
```javascript
// Node.js + Express
app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');  // 모든 출처 허용
    res.header('Access-Control-Allow-Origin', 'https://mysite.com');  // 특정 출처만
    res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE');
    res.header('Access-Control-Allow-Headers', 'Content-Type, Authorization');
    next();
});
```

**Preflight 요청**:
```
복잡한 요청 전에 OPTIONS 요청으로 허용 여부 확인

1. Preflight (OPTIONS)
   브라우저: "POST 요청 보내도 되나요?"
   서버: "네, 허용합니다"

2. 실제 요청 (POST)
   브라우저: 데이터 전송
   서버: 응답
```

---

## 🎯 강의 진행 팁

### 1-2교시: HTTP 기초 (2시간)
- HTTP란?
- 메시지 구조
- 메서드

### 3-4교시: 상태 코드와 헤더 (2시간)
- 상태 코드 암기
- 주요 헤더
- **실습**: Postman으로 API 테스트

### 5-6교시: 쿠키, 세션, 스토리지 (2시간)
- 상태 유지 방법
- **실습**: LocalStorage 활용

### 7-8교시: HTTPS와 CORS (2시간)
- 보안
- CORS 문제 해결
- **실습**: CORS 에러 체험

---

## 📝 실습: REST API 클라이언트 만들기

```javascript
class APIClient {
    constructor(baseURL) {
        this.baseURL = baseURL;
    }
    
    async request(endpoint, options = {}) {
        const url = `${this.baseURL}${endpoint}`;
        
        const config = {
            ...options,
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            }
        };
        
        try {
            const response = await fetch(url, config);
            
            if (!response.ok) {
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }
            
            return await response.json();
            
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    }
    
    get(endpoint) {
        return this.request(endpoint);
    }
    
    post(endpoint, data) {
        return this.request(endpoint, {
            method: 'POST',
            body: JSON.stringify(data)
        });
    }
    
    put(endpoint, data) {
        return this.request(endpoint, {
            method: 'PUT',
            body: JSON.stringify(data)
        });
    }
    
    delete(endpoint) {
        return this.request(endpoint, {
            method: 'DELETE'
        });
    }
}

// 사용
const api = new APIClient('https://jsonplaceholder.typicode.com');
api.get('/users').then(users => console.log(users));
```

**8장 HTTP 통신을 완료했습니다! 🌐**

