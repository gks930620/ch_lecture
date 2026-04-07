# 04 Forward vs Redirect — 상세 강의노트

목차
- 기본 개념 정리
- 동작 원리(서버/클라이언트 관점)
- 장단점 및 사용 사례
- PRG(Post-Redirect-Get) 패턴
- 실습: 폼 제출 예제와 검증 오류 처리

1) 기본 개념
- Forward
  - 서버 내부에서 요청을 다른 리소스로 전달. 클라이언트는 URL 변경을 인지하지 못함.
  - `RequestDispatcher.forward(req, resp)` 호출 시 request/response 객체가 유지됨.
- Redirect
  - 서버가 302 응답과 Location 헤더를 보냄으로써 클라이언트가 새로운 URL을 요청하도록 지시.
  - `HttpServletResponse.sendRedirect(url)` 사용. 요청이 새로 생성되므로 request 속성은 유지되지 않음.

2) 동작 원리
- Forward: 같은 서블릿 컨테이너 내에서 내부적으로 호출. 서버 내부에서 스택처럼 처리됨.
- Redirect: 서버가 클라이언트에게 새 URL을 알려 클라이언트가 별도의 GET 요청을 보냄(브라우저 주소창 변경).

3) 장단점 및 사용 사례
- Forward 사용 시
  - 장점: request scope 데이터 유지, 클라이언트가 URL을 변경하지 않아 내부 흐름 숨김.
  - 단점: 브라우저 새로고침 시 폼 재전송 문제 발생 가능(POST 재전송).
- Redirect 사용 시
  - 장점: PRG 패턴으로 중복 제출 방지, 브라우저 주소창이 변경되어 사용자 경험 일관.
  - 단점: 클라이언트가 새 요청을 하기 때문에 request 데이터 유실 — 필요한 경우 session이나 쿼리스트링으로 전달.

4) PRG 패턴(Post-Redirect-Get)
- 목적: 폼 제출 후 사용자가 새로고침으로 인해 중복 제출되는 문제 방지.
- 흐름: 클라이언트 POST -> 서버 처리 -> 서버가 리다이렉트 응답(302) -> 클라이언트가 GET 요청 -> 결과 페이지 표시.
- 실습: 폼 제출 후 DB/저장소에 쓰는 동작은 POST에서 수행하고, 완료 후 목록 페이지로 redirect 하도록 구현.

5) 실습 지침
- `04_form.jsp`에서 mode를 입력해 forward/redirect를 실습.
- `ForwardRedirectServlet`을 통해 request 속성 설정 후 forward/redirect 동작 비교.
- 연습 문제: 폼 검증(예: 제목 필수) 실패 시 에러 메시지를 보여주려면 어떤 전략(Forward vs Redirect+Flash Attribute)을 사용할지 설명하라.

6) 고급: Flash Attributes 패턴
- Redirect 시 일회성 메시지(성공/실패 알림)를 전달할 때 session에 임시로 저장하고 리다이렉트 후 제거하는 방식(예: Spring의 FlashAttribute).
- 직접 구현 시 세션에 메시지 넣고, GET 처리 시 꺼내서 제거하는 방식으로 구현 가능.

이 문서는 Forward/Redirect의 내부 동작과 실무에서의 적절한 사용법, PRG 패턴까지 포함해 강의에서 핵심으로 다뤄야 할 모든 요소를 설명합니다.
