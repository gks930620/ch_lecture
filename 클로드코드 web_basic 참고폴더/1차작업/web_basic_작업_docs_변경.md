# web_basic 1차 작업 — 문서 변경 상세 (챕터별)

작업일: 2026-07-05
대상: `docs/web_basic/src/main/webapp/*/​*.md`

각 항목은 검수 지적을 소스로 확인한 뒤, 초보자가 문서를 그대로 따라 해도 막히지 않도록 정정/보강했다.

---

## ch01 (01_WAS와웹서버.md)
- 요청 처리 흐름 절 상단에 **WS/WAS 흐름 이미지** 삽입.
- "참고 예제 코드"가 실습 파일 4개 중 2개만 언급하던 것을 **4개 전부**(`01_intro`, `01_basic`, `01_dynamic_content`, `01_server_info`) 설명으로 교체. 서블릿 예제도 `LifecycleServlet` 명시.
- "서블릿은 싱글톤" 표현은 검수도 "확인 필요"라 **유지**(초보 단순화).

## ch02 (02_JSP_필수객체.md)
- 실습 지침에서 `AttributeSetterServlet`이 프로젝트에 없는 **"직접 작성 연습 과제"**임을 명시.
- `02_scope_demo.jsp`의 `${"$"}{msg}` **EL 이스케이프 트릭** 한 줄 설명 추가.

## ch03 (03_page_directive.md)
- `@include` 단점: "재컴파일 되어야 함" → **"included 파일만 수정 시 반영 안 될 수 있는 stale 함정"** 취지로 정정.
- `<jsp:include>`: "독립적인 서블릿/응답 생성" → **`RequestDispatcher.include()`로 같은 request/response 공유**로 정정.
- page 디렉티브: `contentType`(응답 MIME+charset) vs `pageEncoding`(**JSP 소스 파일 인코딩**) 구분.
- 실습: header=@include(compile-time)/footer=jsp:include(runtime) 실제 구성 반영, request 속성은 **두 방식 모두 접근 가능**함을 명시.

## ch04 (04_forward_redirect.md)
- 동작 원리 절에 **forward vs redirect 이미지** 삽입.
- "서버 내부에서 스택처럼 처리됨" 모호 비유 제거.
- redirect 설명의 session은 **ch05에서 학습**임을 표기.
- 실습 결과 파일명(`04_result_forward.jsp`, `04_result_redirect.jsp`) 명시.

## ch05 (05_cookie_session.md)
- 개념 절에 **쿠키 vs 세션 구조 이미지** 삽입.
- 인증 예제 `sendRedirect("/login")` → `request.getContextPath() + "/05_cookie_session/05_login.jsp"`로 정정(404 방지).
- 실습 파일 표: `CookieSessionServlet`이 `/cookie`뿐 아니라 **`/session`도 처리**함을 반영. 공지팝업 두 버튼의 쿠키 차이 설명.
- 보안 절에 데모의 **XSS/`parseInt` 방어 부재** 주의 문구.

## ch06 (06_WEB-INF.md)
- "왜 차단되나" 절에 **차단 규칙(경로가 `/WEB-INF/`로 시작할 때만)** 정확 서술 + **차단 구조 이미지**.
- error-page 예시가 실제 프로젝트 경로와 다름을 "일반 예시"로 명시.
- 실습 안내: hidden.jsp 위치를 `WEB-INF/06_WEB-INF/hidden.jsp`로 수정, 직접 접근 404 / 서블릿 forward ○ 대비 실습으로 재작성(소스 이동과 정합).

## ch07 (07_error_handling.md)
- 예외 발생 파일명 정정: `07_error_demo.jsp`(메뉴) → **`07_error_throw.jsp`(실제 throw)**.
- "WEB-INF/web.xml 또는 web.xml" → `WEB-INF/web.xml`로 통일.

## ch08 (08_JSTL_EL.md)
- 삼항 연산자 "EL 2.2 이상" → **"EL 2.0(JSP 2.0)부터"** 정정 + `empty` 사용 권장.
- 데모가 스크립틀릿 바인딩임을 인지시키고 "실무에선 서블릿에서 바인딩" 주석.

## ch09 (09_filter.md)
- 필터 체인 절에 **필터 체인 이미지** 삽입.
- **필터 등록 방법**(@WebFilter vs web.xml `<filter>`/`<filter-mapping>`) 단락 + web.xml 예제 + 순서 제어 팁 추가.

## ch10 (10_mybatis_board.md)
- 보안 절: `#{}` "자동 이스케이프"(부정확) → **`#{}`=PreparedStatement 파라미터 바인딩 vs `${}`=문자열 치환(인젝션 위험)** 대비로 재작성.
- Board 모델에 **`statusCode`** 필드 추가(Ch12 반영).
- Mapper의 동적 SQL이 실제로 없음 → "기본 CRUD만, 동적 SQL은 ch11 확장 과제"로 명시.
- 실습 전략: **현재 InMemory만 배선**, MyBatis 의존성/설정은 주석 상태 → "직접 구현 과제"임 명시. `selectList` offset/limit **계약 불일치** 주의.

## ch11 (11_paging_search.md)
- 검색+페이징 SQL 예제를 "직접 작성할 목표 예시"로 표기(실제 매퍼엔 WHERE/LIKE 없음).
- count 쿼리도 실제 매퍼에 없음 → **직접 추가**해야 함 명시.
- `11_paging_demo.jsp`의 `parseInt`/XSS 한계 주의 문구.

## ch12 (12_CODE_enum.md)
- switch 주석 "컴파일 경고 → 빠뜨릴 수 없음" → **"IDE가 경고/제안"**으로 완화.
- 보충 note: javac(Java8 switch 문)은 case 누락 미경고, switch 식(14+)에서만 exhaustiveness 강제임을 정확화.

## ch13 (13_listener.md)
- 문서 `int++` vs 실제 `AtomicInteger` → **동시성 note + 실제 소스는 AtomicInteger** 사용 명시.
- "실시간 접속자 수" → **접속자 수 ≠ 세션 수** 보충.

## ch14 (14_MVC_pattern.md)
- MVC 절에 **Model2 vs Model1 흐름 이미지** 삽입.
- `14_bad_example.jsp` 참고 스니펫을 **실제 파일 내용(하드코딩 `List<Map>`)**으로 교체하고, JDBC 스니펫은 "예를 들어 이런 코드가 섞이면(실제 파일엔 없는 더 나쁜 형태)"로 재표현.
