# 02 JSP 암시적 객체(Implicit Objects) — 상세 강의노트

이 문서는 JSP에서 제공하는 암시적 객체들의 개념, 내부 동작, 사용 시 주의사항을 깊게 설명합니다. 실습은 개념 이해 → 코드 예제 → 보안/동시성 이슈 탐구 순으로 진행하세요.

목차
- 암시적 객체의 목록과 설명
- 각 객체의 생명주기(scope)와 사용 사례
- request 속성 전달과 forward/redirect 차이(실습 연동)
- 동시성 및 보안 고려사항
- 실습: 서블릿에서 속성 설정 → JSP 출력

1) 암시적 객체 목록
- request(HttpServletRequest)
  - 클라이언트로부터 전달된 파라미터, 헤더, 세션 등을 접근.
  - request scope에 저장된 속성은 그 요청 안에서만 유효.
- response(HttpServletResponse)
  - 응답 상태 코드, 헤더, 쿠키 설정, 바이트 스트림/문자 스트림 조작.
- session(HttpSession)
  - 클라이언트별 서버 사이드 저장소. session id를 통해 동일 사용자를 구분.
- application(ServletContext)
  - 웹 애플리케이션 범위에서 공유되는 객체(리소스 로딩, 전역 속성 저장).
- out(JspWriter)
  - JSP 페이지로 HTML을 출력하는데 사용되는 Writer. 버퍼링과 flush 제어 가능.
- page(Object)
  - JSP 페이지 자체의 인스턴스(보통 사용 빈도 낮음).
- config(ServletConfig)
  - 해당 JSP가 속한 서블릿의 초기화 파라미터(주로 서블릿에서 사용).
- pageContext(PageContext)
  - 모든 스코프(request, session, application, page)의 통합 인터페이스. EL과 tag handler에서 중요.
- exception(Throwable)
  - 에러 페이지에서 사용. 발생한 예외 객체를 가리킴.

2) Scope(생명주기)
- page: 해당 JSP 인스턴스 내에서만 유효(가장 짧은 범위)
- request: 같은 요청에서 포워드한 대상까지 유효
- session: 클라이언트 세션 생명주기 동안 유효
- application: 웹 애플리케이션이 살아있는 동안 유효(서버 재시작 시 소멸)

3) 요청 속성 전달 기법
- Servlet -> JSP: `request.setAttribute("key", value)` 후 `request.getRequestDispatcher("/path.jsp").forward(req, resp)`
- Forward는 같은 request를 전달하므로 request scope 유지.
- Redirect는 클라이언트가 새 요청을 만들기 때문에 request scope의 데이터는 사라짐(대신 session 또는 query string 사용).

4) 동시성/보안 고려사항
- application scope에 mutable 객체를 저장하면 멀티스레드 환경에서 동기화 필요.
- session 하위 객체 또한 여러 요청이 병렬로 발생할 수 있으므로 동기화 고려.
- out(JspWriter) 사용 시 XSS에 주의 — 출력 전에 HTML escape 필요.

5) 실습 지침
- `02_scope_demo.jsp`를 열고 request/session/application 속성 설정 후 EL로 출력.
- 서블릿(`AttributeSetterServlet` 예제)을 작성해 request 속성을 설정하고 forward로 JSP를 호출해 보세요.
- 연습 문제: 같은 이름의 속성을 여러 스코프에 설정했을 때 EL이 어떤 값을 우선 참조하는지 실험하라.

6) 권장 코드 스타일
- JSP에 비즈니스 로직(복잡한 자바 코드)을 직접 두지 마세요. 서블릿/서비스에서 처리하고 JSP는 뷰로만 사용.
- 가능한 EL/JSTL을 사용해 표현식을 최소화하고 유지보수를 쉽게 하세요.

참고 자료
- JSP/Servlet 사양 문서
- OWASP XSS & Session 관리 권장 사항

이 파일은 강의에서 암시적 객체를 설명할 때 슬라이드 대체 자료로도 사용 가능한 수준으로 작성했습니다.
