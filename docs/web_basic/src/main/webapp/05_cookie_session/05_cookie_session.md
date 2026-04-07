# 05 Cookie와 Session

## 목차
1. Cookie vs Session 개념
2. 쿠키 구조와 주요 속성
3. 세션 동작 원리
4. 실습 파일 목록
5. 쿠키 실용 예제: 1주일간 공지 안보기
6. 세션 기반 로그인/로그아웃
7. 보안 고려사항
8. 세션 심화

---

## 1. Cookie vs Session

| 구분 | Cookie | Session |
|------|--------|---------|
| 저장 위치 | 클라이언트(브라우저) | 서버 메모리 |
| 보안 | 취약 (탈취 가능) | 상대적으로 안전 |
| 용량 | 4KB 제한 | 서버 메모리 한도 |
| 만료 | maxAge 설정 | 브라우저 종료 or 타임아웃 |
| 사용 예 | 1주일간 안보기, 자동로그인 토큰 | 로그인 상태 유지 |

HTTP는 무상태(Stateless) 프로토콜이다.
각 요청은 독립적이며 서버는 이전 요청을 기억하지 못한다.
Cookie와 Session은 이 무상태 문제를 해결하기 위한 수단이다.

---

## 2. 쿠키 구조와 주요 속성

```
Set-Cookie: name=value; Path=/; Max-Age=604800; HttpOnly; Secure; SameSite=Lax
```

| 속성 | 설명 |
|------|------|
| Max-Age | 유지 시간(초). 0이면 즉시 삭제, 없으면 세션 쿠키 |
| HttpOnly | JS에서 document.cookie 접근 불가 → XSS 방어 |
| Secure | HTTPS에서만 전송 |
| SameSite | Lax/Strict/None → CSRF 방어 |
| Path | 해당 경로 요청에만 쿠키 포함 |

```java
// 쿠키 생성
Cookie c = new Cookie("hideNotice", "true");
c.setPath("/");
c.setMaxAge(7 * 24 * 60 * 60); // 7일
c.setHttpOnly(true);
resp.addCookie(c);

// 쿠키 삭제 → 같은 이름으로 maxAge=0 설정
Cookie del = new Cookie("hideNotice", "");
del.setPath("/");
del.setMaxAge(0);
resp.addCookie(del);

// 쿠키 읽기
Cookie[] cookies = request.getCookies(); // null 가능
if (cookies != null) {
    for (Cookie cookie : cookies) {
        String name = cookie.getName();
        String value = cookie.getValue();
    }
}
```

---

## 3. 세션 동작 원리

```
1. 최초 요청 → 서버: 세션 생성, JSESSIONID 쿠키 응답
2. 이후 요청 → 브라우저: JSESSIONID 쿠키 자동 포함
3. 서버: JSESSIONID로 세션 저장소 조회 → 사용자 상태 복원
```

```java
// 세션 생성 or 가져오기
HttpSession session = request.getSession();         // 없으면 새로 생성
HttpSession session = request.getSession(true);     // 동일
HttpSession session = request.getSession(false);    // 없으면 null 반환 ← 권장

// 값 저장/조회/삭제
session.setAttribute("loginUser", "admin");
String user = (String) session.getAttribute("loginUser");
session.removeAttribute("loginUser");

// 세션 소멸 (로그아웃)
session.invalidate();
```

> ⚠️ **getSession(false) 를 써야 하는 이유**
> 인증이 필요한 페이지에서 `getSession(true)`를 쓰면 미로그인 사용자도 세션이 생성된다.
> 10만 명이 접속하면 서버 메모리가 폭발한다.
> 세션 존재 여부 확인은 항상 `getSession(false)`로.

---

## 4. 실습 파일 목록

| 파일 | 설명 |
|------|------|
| `05_cookie_demo.jsp` | 쿠키 생성/삭제/목록 기본 실습 |
| `05_notice_popup.jsp` | 실용 예제: 1주일간 공지 안보기 |
| `05_session_demo.jsp` | 세션 정보 확인 기본 실습 |
| `05_login.jsp` | 세션 기반 로그인 폼 |
| `05_dashboard.jsp` | 로그인 후 대시보드 (미로그인 시 자동 리다이렉트) |
| `CookieSessionServlet` | 쿠키 생성/삭제 처리 (`/05_cookie_session/cookie`) |
| `LoginServlet` | 로그인/로그아웃/공지팝업 처리 |

---

## 5. 쿠키 실용 예제: 1주일간 공지 안보기

쿠키의 가장 흔한 실제 사용 사례다.

**흐름:**
```
사용자가 공지 팝업 "1주일간 안보기" 클릭
→ POST /notice?action=hide
→ 서버: hideNotice=true 쿠키 설정 (maxAge=7일)
→ 리다이렉트
→ 다음 요청부터 JSP에서 쿠키 확인 → 팝업 숨김
```

핵심: 서버가 쿠키를 **읽어서** 팝업 노출 여부를 결정한다.
프론트(JS)에서 처리하는 방식도 있지만, 서버에서 처리하면 JS가 꺼져도 동작한다.

---

## 6. 세션 기반 로그인/로그아웃

**Spring Security를 배우기 전에 원리를 이해하는 것이 목표다.**

**로그인 흐름:**
```
로그인 폼 제출 (POST /login)
→ 서블릿: ID/PW 검증
→ 성공: session.setAttribute("loginUser", username) → 대시보드 리다이렉트
→ 실패: 로그인 페이지로 리다이렉트 (?error=1)
```

**인증 확인 (모든 보호 페이지에서):**
```java
HttpSession session = request.getSession(false);  // 없으면 null
if (session == null || session.getAttribute("loginUser") == null) {
    response.sendRedirect("/login");  // 미인증 → 로그인으로
    return;
}
```

**로그아웃:**
```java
session.invalidate();  // 서버의 세션 데이터 전부 삭제
```

**세션 고정 공격(Session Fixation) 방어:**
- 로그인 성공 시 기존 세션을 `invalidate()`하고 새 세션을 발급한다.
- 공격자가 미리 심어둔 세션 ID를 무력화한다.
- Spring Security는 이를 자동으로 처리한다.

---

## 7. 보안 고려사항

- 민감 정보(비밀번호, 카드번호)는 절대 쿠키에 저장하지 말 것
- 세션에는 최소한의 정보만 저장 (userId, role 정도)
- `HttpOnly` 쿠키로 XSS 방어
- 로그인 후 세션 ID 재발급으로 세션 고정 공격 방어
- HTTPS 환경에서는 `Secure` 쿠키 속성 사용

---

## 8. 세션 심화

### JSP에서 세션이 자동 생성되는 이유
```java
// Tomcat이 JSP를 변환할 때 자동으로 getSession(true) 호출
HttpSession session = pageContext.getSession();
```
해결: `<%@ page session="false" %>` 디렉티브로 자동 생성 방지

### 서버 내부에서 세션은 Map이다
```java
// 개념적 코드
public class StandardSession implements HttpSession {
    private String id; // JSESSIONID
    private Map<String, Object> attributes = new HashMap<>();
}
```

### getSession(true) vs getSession(false)
- `getSession(true)` : 없으면 새로 생성
- `getSession(false)` : 없으면 null 반환 ← **인증 확인 시 반드시 사용**

### Spring에서 미리 알아두기
- `@GetMapping` 파라미터에 `HttpSession session` 선언 → getSession(true) (항상 생성)
- `HttpServletRequest`를 받아서 `request.getSession(false)` 호출 → 필요할 때만 (권장)
