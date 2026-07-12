# web_basic 2차 검수 — 전체 요약 (1차 반영 검증)

검수일: 2026-07-05
검수 성격: **1차 검수 반영 결과의 재검증.** 작업자가 `1차작업/`에서 반영한 내용이 (1) 실제로 반영됐는지, (2) 기술적으로 올바르게 반영됐는지, (3) 수정·신규 추가(문구/이미지) 과정에서 **새 오류를 도입하지 않았는지**를 검증.
검수 환경 전제: Tomcat 9 = Servlet 4.0(`javax.servlet.*`), JSTL 1.2, Java 8.

> 참고: 검수자도 틀릴 수 있음. 아래 "잔여 항목"은 강의 진행에 지장 없는 경미한 수준이며 반영 여부는 작업자 판단.

---

## 결론: 반영 양호 ✅

1차 검수의 **높음 1건 + 중간 7건 + 다수 낮음**과 이미지 제안 6곳이 **정확하게 반영**되었고, 수정 문구·신규 SVG에서 **새로운 기술 오류는 발견되지 않았다.** 특히 과잉 교정 위험이 컸던 항목들(RequestDispatcher.include 설명, contentType vs pageEncoding, `#{}` vs `${}`, WEB-INF 차단 규칙, EL 삼항 버전)이 모두 이번엔 정확하게 서술됐다.

### 핵심 수정 직접 검증 (검수자 본인 확인)
- **[높음] ch06 WEB-INF 차단 시연** — ✅ 완전 해결. `hidden.jsp`가 `web_basic/src/main/webapp/WEB-INF/06_WEB-INF/hidden.jsp`로 이동됨(경로가 `/WEB-INF/`로 시작 → 톰캣이 실제로 직접 접근을 404 차단). `WebInfForwardServlet.java:14` forward 경로도 `/WEB-INF/06_WEB-INF/hidden.jsp`로 수정, 기존 중첩 폴더 삭제 확인, hidden.jsp 본문 문구도 정확화. 문서(`06_WEB-INF.md:22,47-49`)가 "차단은 경로가 `/WEB-INF/`로 시작할 때만, 중첩 WEB-INF는 보호 안 됨"까지 정확히 명시.
- **[중간] ch05 공지팝업 세션 쿠키** — ✅ 해결(개선). `LoginServlet.java:127-135` `close` 분기가 maxAge 미설정(-1) 세션 쿠키를 설정하도록 수정. 팝업 라벨(`05_notice_popup.jsp:35`)도 "닫기 (이번 세션만 - 세션 쿠키)"로 일치. "hide=7일 영속 vs close=세션" **maxAge만 다른 대비 예제**가 되어 교육 가치 상승.

### 문서 정정 검증 (에이전트 6팀 교차검증, 모두 정확)
- ch01 실습파일 4개 명시 / ch02 AttributeSetterServlet "직접 작성"+EL 이스케이프 설명 / ch03 `jsp:include` 공유·`pageEncoding`·`@include` 재컴파일 방향·헤더 request 뉘앙스 정정 → 전부 정확.
- ch04 session 선행참조·비유 제거·결과 파일명 / ch05 sendRedirect contextPath 보완·표 보완·XSS·parseInt 주의 → 정확.
- ch07 예외 발생 파일명(`07_error_throw.jsp`)·web.xml 표기 통일 → 정확.
- ch08 삼항 "EL 2.0부터"·empty 권장 / ch09 필터 등록 방식(@WebFilter/web.xml) 단락 → 정확.
- ch10 `#{}`=PreparedStatement 바인딩 / `${}`=치환·인젝션 위험 정정, "InMemory만 배선·MyBatis는 과제"임 정직 표기, Board 모델 statusCode / ch11 count·검색 WHERE를 "직접 구현 예시"로 표기 → 정확.
- ch12 "컴파일 강제"→"IDE 경고/제안"+switch문/식 정밀화 / ch13 AtomicInteger·"접속자≠세션" 주석 / ch14 bad_example 스니펫을 실제 파일(하드코딩 Map)로 교체 → 정확.

### 신규 이미지 검증 (SVG 6개, 모두 개념·방향 정확)
`ws-was-flow`, `forward-vs-redirect`, `cookie-vs-session`, `web-inf-access`, `filter-chain`, `mvc-model2-flow` — 화살표 방향·라벨·역할 배치에 개념 오류나 역전 표현 없음. java_basic과 동일한 Jekyll `relative_url` 참조 규칙, 링크 파일 모두 실존.

---

## 잔여 항목 (경미, 선택 반영)

| # | 위치 | 내용 | 심각도 |
|---|------|------|--------|
| 1 | `05_notice_popup.jsp:48` | 상태 표시 문구가 `hideNotice==true`면 무조건 "true (1주일간 안보기 설정됨)"로 출력 → "이번 세션만(close)"으로 닫아도 "1주일간 …"으로 표시돼 세션 쿠키와 구분 안 됨. 팝업 JSP는 쿠키 이름만 보고 maxAge를 알 수 없어 생기는 표시상 불일치. (문구를 "숨김 설정됨" 정도로 중립화하면 해소) | 낮음 |
| 2 | `web_basic_images/ch14/mvc-model2-flow.svg` | ④ Controller→View 화살표가 Model 박스를 시각적으로 관통(논리는 정확, 순수 배치 겹침). 화살표를 Model 박스 위/아래로 우회시키면 더 깔끔. | 낮음 |
| 3 | `06_WEB-INF.md:53` | 1차 '낮음' 권장이던 "클래스패스 우선순위" 한 줄 부연이 아직 없음. 오류 아님, 선택. | 낮음 |

## 미반영 항목 검토 (작업자 D절) — 타당함 ✅
작업자가 사유를 달아 미반영한 항목(ch01 "싱글톤" 단순화 유지, ch10 `InMemoryBoardDao` subList 경계 예외는 미발현이라 문서 주의로 갈음, 데모 XSS/parseInt는 소스 대신 문서 주의로, ch12 `INSERT` 3값 예시 유지)은 모두 **근거가 합리적이며 2차 검수도 동의**한다. 굳이 추가 수정 불필요.

## 오탐 정정
- 2차검증 중 한 에이전트가 `LoginServlet.java:128,138`이 `\`로 시작해 컴파일 오류 소지라 보고했으나, 검수자가 원본을 직접 확인한 결과 해당 줄들은 정상적인 `//` 주석이다. **컴파일 문제 없음(오탐).**

---

## 개별 검증 상세
- `web_basic_2차검수_반영검증_상세.md` — 챕터별 반영 확인/신규 문제/SVG 상세
