# web_basic 검수 — 08 JSTL·EL / 09 filter

검수일: 2026-07-05
검수 범위: `docs/.../08_JSTL_EL.md`, `09_filter.md` + `08_jstl_demo.jsp`, `09_filter_test.jsp`, `LoggingFilter.java`, `web.xml`, `pom.xml`

정확성 확인(문제없음): EL `${}` 스코프 탐색 순서(pageScope→requestScope→sessionScope→applicationScope, md:12), taglib uri `http://java.sun.com/jsp/jstl/core`(md:29)는 Tomcat9/JSTL1.2 기준 정확. `pom.xml:50-54`에 `javax.servlet:jstl:1.2`가 compile 스코프로 선언되어 uri 정상 동작. 필터 생명주기(`init`/`doFilter`/`destroy`, md:14-17), `chain.doFilter()` 필요성, 체인 순서·전후 처리 서술 정확. `LoggingFilter`의 `@WebFilter("/*")` ↔ 문서 "모든 요청" 서술 일치, web.xml의 중복 필터 매핑은 주석 처리라 충돌 없음. **이미지 참조 없음.** 링크(`/02_JSP_필수객체/02_scope_demo.jsp`, `/board/list`) 유효.

---

## 08_JSTL_EL

### 낮음
- `docs/.../08_JSTL_EL.md:18` — 기본값 예시 `${param.page ne null ? param.page : 1}`에 붙은 `(EL 2.2 이상 또는 JSTL로 처리)` 주석이 부정확. 삼항 연산자 `? :`는 **EL 2.0(JSP 2.0)부터** 지원되며 "EL 2.2 이상"이 필요하지 않음. 또한 `ne null`은 파라미터가 아예 없을 때만 걸러지고 빈 문자열은 통과하므로, 데모(`08_jstl_demo.jsp:13`)처럼 `${empty param.q ? ...}` 패턴이 초보자에게 더 안전. → "삼항 연산자는 EL 2.0+ 지원" 및 `empty` 사용 권장으로 정정.
- `docs/.../08_JSTL_EL.md:49` vs `08_jstl_demo.jsp:39-46` — 문서는 "로직은 서블릿/비즈니스 레이어, JSP는 표시만"이라 안내하나 실제 데모는 스크립틀릿 `<% ... request.setAttribute(...) %>`로 데이터를 만든다. 데모 편의상 트레이드오프지만 초보자 혼동 소지 → "실무에선 서블릿에서 바인딩" 주석 한 줄 보강 권장(사실 오류는 아님).

## 09_filter

### 낮음
- `docs/.../09_filter.md`(문서 전반) — 필터 "개념/생명주기/체인"만 설명하고, 실제 소스가 쓰는 **등록 방식(`@WebFilter` 애노테이션 vs `web.xml` `<filter>`/`<filter-mapping>`)을 전혀 언급하지 않음.** 문서만 읽으면 `LoggingFilter`가 어떻게 등록·매핑되는지 알 수 없음(소스 `LoggingFilter.java:8`은 `@WebFilter("/*")`, web.xml은 주석 예시). → "필터 등록은 `@WebFilter` 또는 web.xml `<filter>`/`<filter-mapping>`" 한 단락 추가 권장.

### 이미지 제안
- 필터 체인(요청 → Filter1 → Filter2 → Servlet → 응답 역순 통과) 흐름도.
