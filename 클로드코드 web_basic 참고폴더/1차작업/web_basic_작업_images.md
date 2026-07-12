# web_basic 1차 작업 — 이미지 목록

작업일: 2026-07-05
저장 위치: `docs/web_basic/web_basic_images/chXX/`
형식: SVG (자체 완결, 외부 리소스 없음). java_basic과 동일한 폴더/참조 규칙.
참조 방식(Jekyll): `![alt]({{ '/web_basic/web_basic_images/chXX/name.svg' | relative_url }})`

| # | 파일 | 챕터 | 설명 |
|---|------|------|------|
| 1 | `ch01/ws-was-flow.svg` | ch01 | 클라이언트→WS→WAS. 정적이면 WS가 직접 응답, 동적이면 WAS로 위임, WAS→DB. |
| 2 | `ch04/forward-vs-redirect.svg` | ch04 | Forward=요청 1번·request 유지·URL 그대로 / Redirect=302 후 요청 2번·URL 변경. |
| 3 | `ch05/cookie-vs-session.svg` | ch05 | 쿠키=브라우저에 데이터 저장 / 세션=서버 Map에 저장, 브라우저는 JSESSIONID만 보유. |
| 4 | `ch06/web-inf-access.svg` | ch06 | 직접 접근 ✗(404) / 서블릿 forward ○. "/WEB-INF/로 시작할 때만 차단" 주석. |
| 5 | `ch09/filter-chain.svg` | ch09 | 요청이 Filter1→Filter2→Servlet, 응답은 역순 통과. doFilter 전후 처리. |
| 6 | `ch14/mvc-model2-flow.svg` | ch14 | Model2(브라우저→Controller→Model→View) vs Model1(JSP에 전부 혼재). |

## 삽입 위치(문서)
- ch01: "3) 요청 처리 흐름" 절 상단
- ch04: "2) 동작 원리" 절 상단
- ch05: "## 1. Cookie vs Session" 무상태 설명 직후
- ch06: "3) 왜 직접 접근이 차단되나?" 절
- ch09: "3) 필터 체인" 절
- ch14: "## 2) MVC 패턴이란" 표 위

## 검수의 이미지 제안 대응
검수가 이미지 제안한 6개 지점(ch01, ch04, ch05, ch06, ch09, ch14)에 모두 대응했다. 그 외 챕터는 표/코드 위주로 이미 이해가 충분하다고 판단해 텍스트 정정에 집중했다.

## 유효성
- 6개 SVG 모두 .NET XML 파서(`[xml]`)로 well-formed 확인 완료.
- viewBox 기반 반응형, 폰트는 'Malgun Gothic' 등 시스템 한글 폰트 fallback.
