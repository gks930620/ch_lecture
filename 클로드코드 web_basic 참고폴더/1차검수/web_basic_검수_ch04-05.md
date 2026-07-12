# web_basic 검수 — 04 forward·redirect / 05 cookie·session

검수일: 2026-07-05
검수 범위: `docs/.../04_forward_redirect.md`, `05_cookie_session.md` + `04_*.jsp`, `ForwardRedirectServlet.java`, `05_*.jsp`, `CookieSessionServlet.java`, `LoginServlet.java`, `web.xml`

정확성 확인(문제없음): forward(RequestDispatcher 내부 이동·request 유지·URL 유지) vs redirect(302·새 요청·request 소멸) 서술 정확(`ForwardRedirectServlet.java:17-21`과 일치). 쿠키 Max-Age(0=즉시삭제, 미설정=세션쿠키)·HttpOnly/Secure/SameSite/Path, 세션 원리·JSESSIONID·`getSession(true/false)`·`invalidate`, 세션 고정 공격 방어(로그인 시 기존 세션 invalidate 후 재발급, `md:159-162` ↔ `LoginServlet.java:66-74`) 모두 정확. 서블릿 URL 매핑이 JSP form action·문서와 일치. **이미지 참조 없음.**

---

## 04_forward_redirect

### 낮음
- `docs/.../04_forward_redirect.md:28` — "필요한 경우 session이나 쿼리스트링으로 전달": session은 ch05에서 처음 배우는데 여기서 설명 없이 등장. 경미한 선행 참조.
- `docs/.../04_forward_redirect.md:19` — "서버 내부에서 스택처럼 처리됨": 근거가 모호한 비유. 초보자 오해 소지, 없어도 무방. (확인 필요/경미)
- `docs/.../04_forward_redirect.md:35-38` — 실습 지침에 실제 결과 파일명(`04_result_forward.jsp`, `04_result_redirect.jsp`) 미명시. 경미.

## 05_cookie_session

### 중간
- `LoginServlet.java:106-107, 127-129` + `05_notice_popup.jsp:35` — 공지 팝업의 action=close를 "오늘만 (세션 쿠키, maxAge 설정 안 함)"으로 설명하지만 실제 코드는 **쿠키를 전혀 설정하지 않는다**(주석 128-129: "쿠키를 설정하지 않고 그냥 리다이렉트"). 따라서 "세션 쿠키"라는 서술이 부정확하고, 버튼 라벨 "닫기(오늘만)"도 실제로는 다음 요청/새로고침 즉시 팝업이 다시 뜨므로 "오늘만"조차 성립하지 않음. 동작과 설명 불일치 → 코드가 세션 쿠키를 실제로 설정하도록 고치거나, 라벨/설명을 실제 동작에 맞게 수정.

### 낮음
- `docs/.../05_cookie_session.md:149` — 인증 확인 예제 `response.sendRedirect("/login");`: 실제 매핑은 `/05_cookie_session/login`이고 contextPath도 빠짐(실제 파일들은 `request.getContextPath() + ...` 사용, 예: `05_dashboard.jsp:7`). 개념용 스니펫이나 초보자가 그대로 쓰면 404. contextPath 포함으로 보완 권장.
- `docs/.../05_cookie_session.md:110` — 실습 파일 표에 `CookieSessionServlet`을 "쿠키 생성/삭제(`/05_cookie_session/cookie`)"로만 표기했으나 실제로는 `/05_cookie_session/session`도 처리(`CookieSessionServlet.java:12, 39-43`). 문서 불완전.
- `05_cookie_demo.jsp:30`, `05_session_demo.jsp:20` — 사용자 입력값(쿠키 값/세션 저장값)을 `<%= %>`로 이스케이프 없이 출력 → 반사형 XSS 벡터. 교육용 데모지만 명백한 안티패턴이므로 최소한 주의 문구 권장. (`05_dashboard.jsp:19`의 loginUser는 하드코딩 "admin"이라 위험 없음.)
- `CookieSessionServlet.java:34` — `Integer.parseInt(maxAgeParam)`에 숫자 아닌 값 입력 시 `NumberFormatException` → 500. 초보 실습에서 쉽게 밟는 지점. try/catch 또는 검증 부재.
- 쿠키 path 표기 불일치(확인 필요, 경미) — 문서 예제는 `setPath("/")`(md:48,56), `CookieSessionServlet`은 `setPath(getContextPath())`(23,32), `LoginServlet`은 `setPath(getContextPath()+"/")`(122,134). contextPath가 비어있지 않으면 셋 다 정상 동작하나 표기 방식이 달라 초보자 혼동 가능.

### 이미지 제안
- forward vs redirect의 브라우저↔서버 요청 흐름(화살표 개수·URL 변화), 쿠키(브라우저 저장) vs 세션(서버 저장 + JSESSIONID 쿠키) 구조도.
