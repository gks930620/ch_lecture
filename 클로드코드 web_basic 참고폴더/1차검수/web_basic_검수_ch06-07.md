# web_basic 검수 — 06 WEB-INF / 07 error handling

검수일: 2026-07-05
검수 범위: `docs/.../06_WEB-INF.md`, `07_error_handling.md` + `06_WEB-INF/WEB-INF/hidden.jsp`, `WebInfForwardServlet.java`, `07_*.jsp`, `error/404.jsp`, `error/500.jsp`, `web.xml`

정확성 확인(문제없음): 에러 처리 속성명(`javax.servlet.error.status_code/exception/message`)·EL 접근·`isErrorPage="true"`·`exception`은 Throwable 서술 정확. `web.xml:75-86`의 error-page 매핑 location 실제 파일 존재 확인, RuntimeException→`java.lang.Throwable` 매핑으로 500.jsp 이동/404 error-code 매핑 정상. **이미지 참조 없음.**

---

## 06_WEB-INF

### 높음
- `web_basic/.../06_WEB-INF/WEB-INF/hidden.jsp:10` + `docs/.../06_WEB-INF.md:43` + `WebInfForwardServlet.java:14` — **데모의 핵심 전제가 실제로 성립하지 않음.** hidden.jsp는 "이 페이지는 /WEB-INF 내부에 있어서 직접 접근할 수 없습니다"라고 주장하지만, 실제 위치는 컨텍스트 루트의 `/WEB-INF/`가 아니라 **중첩된 `/06_WEB-INF/WEB-INF/`** 다. Tomcat(StandardContextValve)의 접근 차단은 요청 경로가 문자열 **시작부터** `/WEB-INF/` 또는 `/META-INF/`인 경우에만 404를 반환한다. `/06_WEB-INF/WEB-INF/hidden.jsp`는 `/WEB-INF/`로 시작하지 않으므로 **차단되지 않고, 브라우저로 직접 접근하면 그대로 렌더링된다.** 즉 "직접 접근 불가"를 보여주려는 실습이 정반대로 동작해 교육 목적이 깨진다.
  - 수정방향: hidden.jsp를 진짜 보호되는 컨텍스트 루트 WEB-INF 하위로 이동(예: `/WEB-INF/06_WEB-INF/hidden.jsp`). 서블릿 forward 경로(`WebInfForwardServlet.java:14`)와 문서(`06_WEB-INF.md:44`)도 동일 경로로 함께 수정.
  - 참고: forward 자체는 서블릿 코드라 어떤 경로든 동작하므로 서블릿 경유 접근(`/06_WEB-INF/hidden`)은 지금도 성공한다. 문제는 "직접 접근 차단" 시연이 실패한다는 점. **(검수자 직접 파일 확인 완료)**

### 낮음
- `docs/.../06_WEB-INF.md:34,38` — 예시 `<location>/error/404.jsp</location>`, `/error/500.jsp`는 실제 프로젝트 에러 페이지 경로(`/07_error_handling/error/404.jsp`, `/500.jsp`)와 다름. "간단 예시"로 명시돼 오류는 아니나, 초보자가 그대로 따라 하면 존재하지 않는 경로가 됨. 실제 경로와 맞추거나 "예시일 뿐"임을 더 분명히.
- `06_WEB-INF.md:48` — "클래스패스 우선순위"가 설명 없이 등장. 초보 대상이면 한 줄 부연 권장.

## 07_error_handling

### 중간
- `docs/.../07_error_handling.md:28` — 문서↔소스 불일치. "`07_error_demo.jsp`에서 ... `throw new RuntimeException("테스트 예외")`를 발생"이라 했으나, 실제 예외를 던지는 파일은 `07_error_throw.jsp:4`다. `07_error_demo.jsp`는 링크만 있는 메뉴 페이지(`07_error_demo.jsp:9`가 `07_error_throw.jsp`로 링크). 파일명을 `07_error_throw.jsp`로 정정 필요.

### 낮음
- `07_error_handling.md:29` — "`WEB-INF/web.xml` 또는 `web.xml`에 추가"는 사실상 같은 파일을 둘로 표현(web.xml은 WEB-INF 안에만 존재). "`WEB-INF/web.xml`"로 통일 권장.
- `07_error_handling.md:34` — "Sentry" 등 미학습 외부 도구가 예시로 등장. "운영 관점" 맥락이라 큰 문제는 아님.

### 이미지 제안
- WEB-INF 차단 구조(브라우저 직접 접근 ✗ / 서블릿 forward ○) 도식.
