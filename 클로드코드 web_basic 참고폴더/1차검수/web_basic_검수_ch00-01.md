# web_basic 검수 — 00 실습환경 / index / 01 WAS와웹서버

검수일: 2026-07-05
검수 범위: `docs/web_basic/00_실습환경.md`, `docs/web_basic/index.md`, `docs/.../01_WAS와웹서버/01_WAS와웹서버.md` + `web_basic/.../01_WAS와웹서버/*.jsp`, `index.jsp`, `LifecycleServlet.java`, `web.xml`, `pom.xml`

정확성 확인(문제없음): WS vs WAS 구분, 정적/동적 요청 흐름, HTTP 메서드·상태코드·헤더, 서블릿 생명주기(init→service→doGet/doPost→destroy), JSP 스크립트 요소 구분 서술은 기술적으로 정확. `javax.servlet-api 4.0.1 provided`(00:31-34) ↔ `pom.xml:27-32` 일치, `web.xml` version 4.0(`javaee` 네임스페이스)로 Servlet 4.0 규격 정합. index.md의 14개 챕터 링크 + 00/기타 문서 전부 실제 존재(깨진 링크 없음). **모든 문서에 이미지 참조 없음.**

---

## 00_실습환경 / index

### 낮음
- `docs/web_basic/00_실습환경.md:56-59` — 예제 `HelloServlet.doGet` 시그니처가 `throws IOException`만 선언. 컴파일은 정상(부모 메서드의 checked 예외를 좁히는 것은 합법)이라 버그는 아니나, 실제 강의 소스(`LifecycleServlet.java:37`은 `throws ServletException, IOException`)와 관례가 달라 초보자가 혼동할 수 있음. 강의 전반과 통일하려면 `throws ServletException, IOException`으로 맞추는 것 권장. (경미)

## 01_WAS와웹서버

### 낮음
- `docs/.../01_WAS와웹서버.md:84-85, 96` — "참고 예제 코드"/실습 안내가 `01_intro.jsp`, `01_server_info.jsp`, `ForwardRedirectServlet.java`만 언급하고, 같은 폴더의 `01_basic.jsp`(JSP 3대 스크립트 요소 실습)·`01_dynamic_content.jsp`(동/정적 비교 실습)는 문서에서 전혀 언급하지 않음. 실습 파일 4개 중 2개 누락 → 학생이 존재를 모를 수 있음. 문서에 링크/설명 추가 권장. (누락, 오류 아님)
- `docs/.../01_WAS와웹서버.md:50` — "서블릿은 싱글톤 인스턴스로 관리된다"는 표현은 엄밀히는 부정확(스펙상 GoF 싱글톤이 아니라 "선언당 인스턴스 1개"이며, 같은 클래스를 다른 url-pattern으로 여러 번 선언하면 인스턴스가 여러 개일 수 있음). 다만 초보 강의에서 널리 쓰이는 단순화이고 소스 주석(`LifecycleServlet.java:21`)과도 일관됨. **오류로 단정하지 않음(확인 필요)** — 그대로 둬도 무방.

### 이미지 제안
- WS vs WAS 역할 분담, 정적 요청(WS가 직접 응답) vs 동적 요청(WAS로 위임) 흐름을 화살표 그림으로 넣으면 초보자 이해에 큰 도움.
