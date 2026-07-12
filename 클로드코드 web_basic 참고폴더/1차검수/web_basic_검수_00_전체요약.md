# web_basic 1차 검수 — 전체 요약

검수일: 2026-07-05
검수 대상: `docs/web_basic` 문서 전체(00_실습환경 ~ ch14 + index) + `web_basic/src` 소스(JSP / Servlet / MyBatis Mapper / SQL / web.xml / pom.xml)
검수 기준: **처음 개발을 시작하는 사람** 대상 강의. 설명이 실제 기술 동작과 맞는지, 문서와 소스가 일치하는지, 초보자에게 혼란을 주는 부분이 없는지.
검수 환경 전제: Tomcat 9 = Servlet 4.0(`javax.servlet.*`), JSTL 1.2, Java 8.

> 참고: 검수자도 틀릴 수 있음. 아래 지적 중 "확인 필요"로 표기한 항목은 단정이 아니라 재검토 요청임. 작업자는 맞는 것만 반영하면 됨.

---

## 총평

전반적으로 **기술 설명의 사실 정확성은 높은 편**이다. WAS/웹서버 구분, JSP 내장객체·스코프, 서블릿 생명주기, forward/redirect, 쿠키/세션, EL/JSTL, 필터, 리스너, MVC 등 핵심 개념 서술에 치명적 오류는 거의 없다. 다만 다음 **두 가지 공통 패턴**이 반복적으로 발견된다.

### 공통 이슈 1 — 문서 ↔ 실제 소스 불일치 (가장 많음)
문서가 "이렇게 구현되어 있다"고 설명하는 내용이 실제 소스와 다른 경우가 여러 챕터에서 반복된다.
- 문서는 MyBatis/DB 기반 게시판·페이징·검색(동적 SQL, count 쿼리)을 설명하지만, 실제 소스는 `InMemoryBoardDao`만 배선되어 있고 MyBatis/JDBC 의존성·`mybatis-config` environments·SqlSessionFactory가 전부 주석 처리됨. (ch10, ch11)
- 문서가 지목한 예시 파일에 실제로는 그 코드가 없음. (ch07 예외 발생 파일명, ch14 bad_example의 JDBC 스니펫)
- 문서 스니펫의 코드가 실제 소스 구현과 다름. (ch05 팝업 "세션 쿠키", ch13 SessionCount `int++` vs `AtomicInteger`)

→ 강의 자료 특성상 "개념 예시"와 "실제 실습 코드"가 갈리는 것은 자연스럽지만, 초보자는 문서를 그대로 따라 하다 파일을 못 찾거나 404/컴파일 오류를 만나면 크게 막힌다. **"이건 개념 예시", "실제 실습 파일은 OOO"임을 문서에 분명히 표기**하는 방향을 권장한다.

### 공통 이슈 2 — 이미지가 전혀 없음
검수 대상 **모든 문서에 이미지 참조(`![...]`)가 하나도 없다.** (깨진 링크는 없음 = 애초에 이미지가 없음.) 작업자 참고 지침에 "설명이 너무 글로만 되어 있다면 이미지를 만들어 쉽게 설명하라"고 되어 있는데, web_basic은 글 위주다. 특히 아래 개념은 그림이 있으면 초보자 이해도가 크게 오른다.
- WAS vs 웹서버 / 정적·동적 요청 흐름 (ch01)
- forward vs redirect 요청 흐름(브라우저-서버 화살표) (ch04)
- 쿠키/세션 저장 위치·JSESSIONID 흐름 (ch05)
- WEB-INF 접근 차단 구조 (ch06)
- 필터 체인 순서 (ch09)
- MVC(Model2) 요청 흐름 (ch14)

---

## 심각도별 핵심 목록

### 높음 (실습이 실제로 깨지거나 시연 목적이 성립하지 않는 것)
1. **ch06 WEB-INF 접근 차단 시연이 실제로 동작하지 않음.** `hidden.jsp`가 컨텍스트 루트 `/WEB-INF/`가 아니라 **중첩 경로 `/06_WEB-INF/WEB-INF/hidden.jsp`**에 있다. 톰캣은 URL이 `/WEB-INF/` 또는 `/META-INF/`로 **시작**할 때만 차단하므로, 이 파일은 브라우저로 직접 접근하면 **그대로 렌더링된다**. "직접 접근 불가"를 보여주려는 실습이 정반대로 동작한다. → 파일을 컨텍스트 루트 `WEB-INF/` 하위(예: `/WEB-INF/06_WEB-INF/hidden.jsp`)로 옮기고, 서블릿 forward 경로(`WebInfForwardServlet.java:14`)와 문서(`06_WEB-INF.md:44`)도 함께 수정. (검수자 직접 확인 완료)

### 중간 (사실 부정확 또는 문서↔소스 불일치)
- ch03 — `<jsp:include>`가 "독립적인 응답을 생성"(부정확: `RequestDispatcher.include`로 같은 request/response 공유), `pageEncoding`을 "MIME 타입"으로 설명(부정확: 소스 파일 인코딩).
- ch05 — 공지 팝업 "닫기(오늘만)"를 "세션 쿠키"로 설명하나 실제 코드는 쿠키를 아예 설정하지 않음 → 새로고침 즉시 다시 뜸.
- ch07 — "`07_error_demo.jsp`에서 예외 발생"이라 했으나 실제 예외는 `07_error_throw.jsp`에서 발생.
- ch10 — `#{}`를 "자동 이스케이프"로 설명(부정확: PreparedStatement 파라미터 **바인딩**). 인젝션 위험이 있는 `${}` 대비 설명 자체가 없음. 문서가 말하는 동적 SQL(`<if>`/`<where>`)이 실제 Mapper엔 없음.
- ch11 — 문서가 설명하는 count 쿼리·검색 WHERE·LIMIT 페이징이 실제 `BoardMapper.xml`에 없음.
- ch12 — enum 상태 추가 시 "컴파일 경고로 빠뜨릴 수 없음"은 부정확(Java 8 `switch` **문**은 enum case 누락 경고 없음. IDE 인스펙션 또는 Java 14+ switch **식**에서만 성립).
- ch14 — "`14_bad_example.jsp` 참고"라며 보여준 `DriverManager`/`ResultSet` JDBC 스니펫이 실제 파일엔 없음(파일은 하드코딩 `List<Map>`).

### 낮음 (경미한 오타·일관성·초보 배려·데모 안티패턴)
- 데모 여러 곳에서 사용자 입력을 `<%= %>`로 이스케이프 없이 출력 → 반사형 XSS 벡터(ch05 cookie/session demo, ch11 paging). 교육용이나 최소 주의 문구 권장.
- `Integer.parseInt(...)` 방어 코드 부재로 비정상 입력 시 500(ch05 CookieSessionServlet, ch11 paging_demo).
- 개념 스니펫의 URL이 contextPath/실제 매핑과 달라 그대로 쓰면 404(ch05).
- 미학습 선행 개념이 설명 없이 등장(ch02 EL 이스케이프 트릭, ch04 session, ch10 트랜잭션/락/HikariCP 등).
- `InMemoryBoardDao` subList 페이징 경계 잠재 예외(offset > size일 때) — 현재 미발현.
- 각 챕터 상세는 개별 파일 참고.

---

## 개별 검수 파일
- `web_basic_검수_ch00-01.md` — 실습환경 / index / WAS와웹서버
- `web_basic_검수_ch02-03.md` — JSP 필수객체 / page directive
- `web_basic_검수_ch04-05.md` — forward·redirect / cookie·session
- `web_basic_검수_ch06-07.md` — WEB-INF / error handling
- `web_basic_검수_ch08-09.md` — JSTL·EL / filter
- `web_basic_검수_ch10-11.md` — mybatis board / paging·search
- `web_basic_검수_ch12-14.md` — CODE enum / listener / MVC pattern
