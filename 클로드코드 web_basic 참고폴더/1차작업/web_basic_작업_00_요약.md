# web_basic 1차 작업 — 전체 요약

작업일: 2026-07-05
작업 대상: `docs/web_basic` 문서 전체 + `web_basic/src` 소스
작업 기준: `1차검수/` 폴더의 검수 지적을 검토하여 **맞는 것만** 반영. 검수자도 틀릴 수 있으므로 각 항목을 소스/문서로 직접 확인 후 반영/부분반영/미반영으로 분류했다.
기준 전제: Tomcat 9 = Servlet 4.0(`javax.servlet.*`), JSTL 1.2, Java 8.

---

## 처리 통계
- **소스 코드 수정**: ch06 파일 이동, ch05 세션 쿠키(+주석/라벨), ch10 InMemoryBoardDao subList 경계 수정
- **문서 수정**: 14개 챕터 중 12개 챕터에 반영 (ch01~ch14 중 실제 편집: 01,02,03,04,05,06,07,08,09,10,11,12,13,14 — 사실상 전 챕터)
- **이미지 신규 생성**: 6개 SVG (ch01, ch04, ch05, ch06, ch09, ch14)
- **미반영(사유 있음)**: 아래 "미반영 항목" 참조

---

## A. 소스 코드 수정 (검수 "높음"/"중간" 중 실제 결함)

### A-1. [높음] ch06 — WEB-INF 직접 접근 차단 데모가 실제로 동작하지 않던 문제 ✅ 수정
- **확인 결과 검수 지적이 정확함.** `hidden.jsp`가 컨텍스트 루트의 `/WEB-INF/`가 아니라 중첩 경로 `/06_WEB-INF/WEB-INF/hidden.jsp`에 있었다. 톰캣은 요청 경로가 `/WEB-INF/`로 **시작**할 때만 404 차단하므로, 중첩 WEB-INF는 차단되지 않고 브라우저로 그대로 열린다 → "직접 접근 불가" 시연이 정반대로 동작.
- 조치:
  - 파일 이동: `web_basic/.../06_WEB-INF/WEB-INF/hidden.jsp` → `web_basic/.../WEB-INF/06_WEB-INF/hidden.jsp` (컨텍스트 루트 WEB-INF 하위, 실제 보호됨). 기존 중첩 폴더 삭제.
  - `WebInfForwardServlet.java`: forward 경로를 `/WEB-INF/06_WEB-INF/hidden.jsp`로 수정.
  - 문서 `06_WEB-INF.md`: 실습 안내 경로 수정 + "차단은 경로가 `/WEB-INF/`로 시작할 때만" 규칙 명시 + 이미지 추가.
  - hidden.jsp 본문 문구도 "컨텍스트 루트의 /WEB-INF/ 안에 있어..."로 정확화.

### A-2. [중간] ch05 — 공지팝업 "닫기(오늘만)"가 쿠키를 전혀 안 설정하던 문제 ✅ 수정
- **확인 결과 검수 지적이 정확함.** `LoginServlet.handleNotice`의 `action=close` 분기가 쿠키를 아예 설정하지 않고 리다이렉트만 해서, 새로고침 즉시 팝업이 다시 떴다. 주석은 "세션 쿠키"라 했으나 실제로는 세션 쿠키조차 없었다.
- 조치(교육 의도를 살리는 방향으로 **코드를 수정**):
  - `close` 분기에서 `hideNotice=true` **세션 쿠키**(maxAge 미설정=-1)를 설정하도록 구현. → "1주일간 안보기"(7일 영속 쿠키)와 **maxAge만 다른** 대비 예제가 되어, 영속 쿠키 vs 세션 쿠키를 실습으로 보여줄 수 있게 됨.
  - `05_notice_popup.jsp` 버튼 라벨: `닫기 (오늘만)` → `닫기 (이번 세션만 - 세션 쿠키)`로 실제 동작에 맞춤.
  - `LoginServlet` javadoc 주석도 실제 동작에 맞게 갱신.
  - 문서 `05_cookie_session.md` 실습 파일 표에 두 쿠키 차이 설명 추가.

---

## B. 문서 수정 (사실 부정확 / 문서↔소스 불일치 / 초보 배려)

| 챕터 | 항목 | 조치 |
|------|------|------|
| ch01 | 실습 파일 4개 중 2개(`01_basic.jsp`,`01_dynamic_content.jsp`) 문서 누락 | 4개 전부 설명 추가. 서블릿 예제도 `LifecycleServlet` 명시 |
| ch01 | (이미지 없음) | WS/WAS 정적·동적 요청 흐름 SVG 추가 |
| ch02 | `AttributeSetterServlet`이 실제 없는 "직접 작성 과제"임 불명확 | "직접 작성" 연습 과제임 명시 + EL 이스케이프 트릭 한 줄 설명 |
| ch03 | `<jsp:include>`가 "독립적인 응답 생성" (부정확) | "같은 request/response 공유, `RequestDispatcher.include`" 로 정정 |
| ch03 | `pageEncoding`을 "MIME 타입"으로 설명 (부정확) | contentType(응답 MIME+charset) vs pageEncoding(JSP 소스 파일 인코딩) 구분 |
| ch03 | `@include` 재컴파일 서술 방향이 반대 | stale 출력 함정(변경 미반영) 취지로 정정 |
| ch03 | 헤더 request 접근이 jsp:include라야 된다는 뉘앙스 | 두 include 모두 request 공유 → 양쪽 접근 가능으로 정정, 실제 소스 구성 명시 |
| ch04 | (이미지 없음) | forward vs redirect 요청 흐름 SVG 추가 |
| ch04 | session 선행 참조 / "스택처럼" 모호 비유 / 결과 파일명 미명시 | ch05 참조 표기, 비유 제거, 결과 파일명 명시 |
| ch05 | (이미지 없음) | 쿠키 vs 세션 저장 구조 SVG 추가 |
| ch05 | 인증 예제 `sendRedirect("/login")` (contextPath/매핑 누락→404) | `request.getContextPath()+"/05_cookie_session/05_login.jsp"` 로 보완 |
| ch05 | 실습 파일 표: `CookieSessionServlet`이 `/session`도 처리 | 표 보완 |
| ch05 | 데모 XSS/`parseInt` 방어 부재 | 보안 고려사항에 주의 문구 추가 |
| ch06 | error-page 예시 경로가 실제와 다름 | "일반 예시"임과 실제 경로(`/07_error_handling/error/...`) 명시 |
| ch06 | (이미지 없음) | WEB-INF 차단 구조 SVG 추가 |
| ch07 | 예외 발생 파일명 오류(`07_error_demo.jsp`→`07_error_throw.jsp`) | 정정 (demo=메뉴, throw=예외 발생) |
| ch07 | "WEB-INF/web.xml 또는 web.xml" 중복 표현 | `WEB-INF/web.xml`로 통일 |
| ch08 | 삼항 연산자 "EL 2.2 이상" (부정확) | "EL 2.0(JSP 2.0)부터" + `empty` 사용 권장으로 정정 |
| ch08 | 데모가 스크립틀릿으로 바인딩 | "실무에선 서블릿에서 바인딩" 주석 추가 |
| ch09 | (이미지 없음) | 필터 체인 SVG 추가 |
| ch09 | 필터 등록 방식(@WebFilter/web.xml) 미설명 | 등록 방법 단락 + web.xml 예제 추가 |
| ch10 | `#{}` "자동 이스케이프" (부정확), `${}` 미설명 | `#{}`=파라미터 바인딩(PreparedStatement) vs `${}`=문자열 치환(인젝션 위험) 대비로 정정 |
| ch10 | 동적 SQL/DAO 교체가 실제 소스에 없음 | "현재 InMemory만 배선, MyBatis는 주석/직접 구현 과제"임 명시 + Board 모델에 statusCode 추가 + 계약 불일치(offset/limit) 주의 |
| ch11 | count 쿼리·검색 WHERE·LIMIT 페이징이 실제 매퍼에 없음 | "직접 추가할 예시/향후 구현"으로 표기 + parseInt/XSS 주의 |
| ch12 | "새 상태 추가 시 컴파일 경고" (부정확, Java8 switch문) | "IDE가 경고/제안" 완화 + javac 미경고/switch 식(14+) 정확화 |
| ch13 | 문서 `int++` vs 소스 `AtomicInteger` | 동시성 주석 + 실제 소스는 AtomicInteger임 명시. "접속자 수≠세션 수" 주석 |
| ch14 | (이미지 없음) | MVC(Model2) vs Model1 흐름 SVG 추가 |
| ch14 | `14_bad_example.jsp` 참고인데 JDBC 스니펫이 파일에 없음 | 실제 파일(하드코딩 Map) 스니펫으로 교체 + JDBC는 "예를 들어 이런 코드가 섞이면"으로 재표현 |

---

## C. 이미지 신규 생성 (SVG, `docs/web_basic/web_basic_images/chXX/`)
1. `ch01/ws-was-flow.svg` — WS/WAS 정적·동적 요청 흐름
2. `ch04/forward-vs-redirect.svg` — forward(요청 1번) vs redirect(요청 2번)
3. `ch05/cookie-vs-session.svg` — 쿠키(브라우저 저장) vs 세션(서버 저장 + JSESSIONID)
4. `ch06/web-inf-access.svg` — 직접 접근 ✗ / 서블릿 forward ○
5. `ch09/filter-chain.svg` — 요청→필터→서블릿, 응답 역순 통과
6. `ch14/mvc-model2-flow.svg` — Model2(MVC) vs Model1

> 참조 방식은 java_basic과 동일한 Jekyll 방식: `![alt]({{ '/web_basic/web_basic_images/chXX/name.svg' | relative_url }})`.

---

## D. 1차검수 "낮음"급 추가 지적의 처리 (사용자 검토 반영)

> 처음엔 미반영이었으나 **사용자 검토 후 ①②④는 추가 반영**, ③만 미반영으로 확정. 자세한 초보용 설명은 `web_basic_작업_미반영_쉬운설명.md` 참조.

- **① ch01 "서블릿은 싱글톤 인스턴스로 관리된다"** → **반영(문서)**. "엄밀히는 선언당 인스턴스 1개라 순수 싱글톤과 다르지만, 처음엔 하나를 공유한다 정도로 이해하면 충분"이라는 한 줄 언급을 괄호로 추가.
- **② ch10 `InMemoryBoardDao` subList 경계 예외(offset>size)** → **반영(소스 수정)**. `from = Math.min(list.size(), Math.max(0, offset))`, `to = Math.min(list.size(), from + limit)`로 상·하한 처리. `Math.max/Math.min`이 from/to 경계를 신경 쓰게 하는 학습 효과가 있어 수정하는 쪽을 택함.
- **③ 데모 JSP들의 `parseInt`(파라미터 예외) 방어 코드** → **미반영(유지)**. 처음 배우는 단계라 모든 예외 상황을 다 막는 코드는 어렵고 핵심을 흐림. "이렇게 파라미터를 받아 처리한다"는 흐름 위주로 두고, 실무 주의만 문서(ch05·ch11)에 유지. (XSS 주의 문구도 문서에 유지)
- **④ ch12 `INSERT INTO code VALUES(...)`가 실제 4컬럼 파일과 형태 불일치** → **반영(문서 수정)**. "설명과 실제 코드가 맞아야 한다"는 원칙에 따라 문서 예시를 실제 `code_table.sql`과 동일하게 맞춤(`id` 포함 CREATE TABLE + `INSERT INTO code(category, code, label) VALUES(...)` 컬럼 명시).

---

## E. 개별 작업 로그 파일
- `web_basic_작업_source_변경.md` — 소스 변경 상세(diff 요약)
- `web_basic_작업_docs_변경.md` — 문서 변경 상세(챕터별)
- `web_basic_작업_images.md` — 이미지 목록·설명
