# web_basic 2차 검수 — 챕터별 반영 검증 상세

검수일: 2026-07-05
표기: [반영확인] 1차 지적이 정확히 반영됨 / [신규문제] 수정 과정에서 새로 발견 / [SVG] 이미지 검증 / [잔여] 경미·선택

---

## ch01 WAS와웹서버
- [반영확인] `01_WAS와웹서버.md:99-104` — 실습 파일 4개(`01_intro`/`01_basic`/`01_dynamic_content`/`01_server_info.jsp`) 용도와 함께 전부 명시. 1차 누락 해소.
- [SVG] `ws-was-flow.svg` — 정적=WS 직접 응답 / 동적=WAS 위임 / WAS→DB / WS→클라이언트 응답. 화살표 방향·라벨·경로 정확, 오탈자 없음. 삽입 경로 유효.
- [반영확인] "싱글톤" 표현(`:53`)은 1차에서 "유지 무방"으로 판단 → 미변경 유지, 문제없음.
- 신규 문제 없음.

## ch02 JSP 필수객체
- [반영확인] `02_JSP_필수객체.md:51` — `AttributeSetterServlet`이 "프로젝트에 없는 파일, 직접 작성 연습 과제"임 명시.
- [반영확인] `:54` — `${"$"}{msg}` EL 이스케이프 트릭(`$`만 출력, `{msg}`는 리터럴) 설명 추가. 소스 `02_scope_demo.jsp:31`과 일치, EL 동작상 정확.
- 신규 문제 없음. (이미지 없음)

## ch03 page directive
- [반영확인] `:12` — `@include` 재컴파일 서술을 정반대의 정확한 방향(포함 파일만 수정 시 stale 출력 함정)으로 정정.
- [반영확인] `:15-16` — `<jsp:include>`를 "`RequestDispatcher.include()`, 같은 request/response 공유, 별도 독립 응답 아님"으로 정정. 과잉 교정 아님.
- [반영확인] `:20-21` — contentType(응답 MIME+charset) vs pageEncoding(JSP 소스 파일 인코딩) 역할 분리.
- [반영확인] `:31-32` — request 속성은 compile-time/runtime include 양쪽 접근 가능으로 정정. 실제 소스 구성(header=@include, footer=jsp:include)과 일치.
- 신규 문제 없음. (이미지 없음)

## ch04 forward·redirect
- [반영확인] `:31` session 선행참조(ch05 학습 명시) / `:22` "스택처럼" 모호 비유 제거·정확 재서술 / `:40` 결과 파일명 명시.
- [SVG] `forward-vs-redirect.svg` — Forward=요청 1번·request 유지·URL 유지 / Redirect=요청1→302+Location→새 요청(2번)·URL 변경. 화살표 개수·방향·순서 정확, 302→브라우저→재요청 흐름 정확.
- 신규 문제 없음.

## ch05 cookie·session
- [반영확인] `:151-153` 인증 예제 `sendRedirect`에 `getContextPath()`+실제 경로 포함(404 방지 주석까지) / `:112` `CookieSessionServlet`이 `/session`도 처리함을 표 보완 / `:177` XSS·parseInt 주의 문구 / `:113` 팝업 "닫기=세션 쿠키, 1주일=7일 영속" 정정(소스 수정과 정합).
- [SVG] `cookie-vs-session.svg` — 쿠키=브라우저 저장 / 세션=서버 Map 저장, 브라우저는 JSESSIONID만. 라벨·화살표 정확, 역전 표현 없음.
- [잔여/낮음] `05_notice_popup.jsp:48` — 상태 문구가 세션 쿠키(close)에도 "1주일간 안보기 설정됨"으로 표시. 팝업 JSP가 쿠키 이름만 보고 maxAge를 알 수 없어 생기는 표시상 불일치. 문구 중립화 권장.

## ch06 WEB-INF (1차 높음)
- [반영확인] `:47-49` 실습 경로 `/WEB-INF/06_WEB-INF/hidden.jsp`로 정정, 서블릿 forward 경로(`WebInfForwardServlet.java:14`)와 일치.
- [반영확인] `:22` "차단은 경로가 `/WEB-INF/` 또는 `/META-INF/`로 시작할 때만, 중첩 WEB-INF는 보호 안 됨" 규칙 정확 서술(톰캣 동작 부합).
- [반영확인] `:44` error-page 예시가 "일반 예시 vs 실제 경로(`/07_error_handling/error/...`)" 구분, `web.xml:75-86`과 정합.
- [SVG] `web-inf-access.svg` — 직접 접근 ✗404 / 서블릿 forward ○, 하단에 "/WEB-INF/로 시작할 때만 차단, 중첩은 안 됨" 주석. 개념 정확.
- [잔여/낮음] `:53` 클래스패스 우선순위 한 줄 부연 미반영(선택).

## ch07 error handling
- [반영확인] `:28` 예외 발생 파일명을 `07_error_throw.jsp`로 정정(demo=메뉴, throw=예외 발생, 소스와 일치) / `:29` `WEB-INF/web.xml`로 표기 통일.
- 에러 속성명(`javax.servlet.error.*`)·Throwable·EL 접근 서술 정확 유지. 신규 문제 없음.

## ch08 JSTL·EL
- [반영확인] `:18` 삼항 `? :`를 "EL 2.0(JSP 2.0)부터 지원"으로 정정(1차 "EL 2.2 이상" 오류 해소) + `empty` 사용 권장(null·빈문자열·빈컬렉션 처리) / `:49` "실무는 서블릿에서 바인딩" 주석.
- taglib uri `http://java.sun.com/jsp/jstl/core` 유지(정확). 신규 문제 없음.

## ch09 filter
- [반영확인] `:24-38` 필터 등록 방식 단락 신설(@WebFilter / web.xml `<filter>`·`<filter-mapping>`). web.xml 예시의 `filter-class`가 실제 `LoggingFilter` 패키지·클래스명과 일치. "@WebFilter는 순서 보장 약함 → 순서 필요 시 web.xml 선언 순서" 안내 정확.
- [SVG] `filter-chain.svg` — 요청: 브라우저→F1→F2→Servlet, 응답: 역순(Servlet→F2→F1→브라우저), doFilter 전/후처리·미호출 시 체인 중단 명시. 방향·순서 정확.
- 신규 문제 없음.

## ch10 mybatis board
- [반영확인] `:56-59` `#{}`=PreparedStatement `?` 파라미터 바인딩 / `${}`=문자열 치환·인젝션 위험으로 정확 정정, "자동 이스케이프" 표현 제거, `${}`는 컬럼명·정렬처럼 값 아닌 부분에 화이트리스트 검증 후만.
- [반영확인] `:24` 동적 SQL 부재 명시(직접 구현 과제) / `:46-47` "InMemory만 배선, MyBatis/JDBC·SqlSessionFactory 주석 처리, 직접 구현 과제" 정직 표기 / `:21` Board 모델 statusCode 추가(소스 `Board.java:12`와 일치) / `:49` offset·limit 계약 불일치 주의.
- 신규 문제 없음. 문서↔소스 정합.

## ch11 paging·search
- [반영확인] `:34` count 쿼리를 "현재 매퍼에 없음 → 직접 추가" 표기 / `:21` 검색 WHERE+LIMIT SQL을 "직접 작성할 목표 예시"로 표기(실제 selectList SQL 정확 인용) / `:43` parseInt·XSS 주의.
- offset 계산 `(page-1)*size` 정확. 신규 문제 없음.

## ch12 CODE enum
- [반영확인] `:114` "컴파일 경고로 강제"→"IDE가 경고/제안"으로 완화 / `:117` "Java 8 switch **문**은 javac가 case 누락 강제 안 함, IDE 인스펙션 또는 Java 14+ switch **식** exhaustiveness에서만 성립" 정밀 보충(기술적으로 정확).
- 신규 문제 없음. (이미지 없음)

## ch13 listener
- [반영확인] `:65` 문서 `int++`는 단순화이고 실제 소스는 `AtomicInteger` incrementAndGet/decrementAndGet 사용(경쟁 조건 회피)임 명시, 소스와 일치 / `:66` "접속자 수 ≠ 세션 수"(한 사용자 다중 세션, 타임아웃까지 유지) 주석.
- 신규 문제 없음. (이미지 없음)

## ch14 MVC pattern
- [반영확인] `:81-96` bad_example "참고" 스니펫을 실제 파일 내용(하드코딩 `List<Map<String,String>>`)으로 교체, `14_bad_example.jsp`와 일치 / `:98-105` JDBC 예시는 "예를 들어 이런 코드가 섞이면(실제 파일엔 없는 더 나쁜 형태)"로 재표현 / 역할표 정확.
- [SVG] `mvc-model2-flow.svg` — Model2(브라우저→Controller→Model→View→응답) vs Model1(JSP에 전부 혼재) 대비. 역할 배치·화살표 정확.
- [잔여/낮음] `mvc-model2-flow.svg` — ④ Controller→View 화살표가 Model 박스를 시각적으로 관통(논리 정확, 배치 겹침만). 우회 배치 권장.
