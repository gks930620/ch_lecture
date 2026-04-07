# 07 에러 처리(예외와 에러 페이지) — 상세 강의노트

목차
- 에러의 분류(클라이언트 에러 vs 서버 에러)
- `web.xml`의 `<error-page>` 동작 원리
- 예외 전달과 암시적 객체 `exception`
- 실습: 의도적 예외 발생 → 에러 페이지로 이동
- 모니터링/로깅과의 연계

1) 에러의 분류
- 클라이언트 에러(4xx): 잘못된 요청, 존재하지 않는 리소스(404)
- 서버 에러(5xx): 서버 측 예외/오류(500)

2) `<error-page>` 동작 원리
- 상태 코드 기반 매핑
  - `<error-page><error-code>404</error-code><location>/error/404.jsp</location></error-page>`
- 예외 타입 기반 매핑
  - 특정 예외 타입이 발생하면 해당 예외 타입에 매핑된 페이지로 포워드됨.
- 컨테이너가 예외를 감지하면 요청/응답을 지정된 에러 페이지로 포워드하고, 에러 페이지는 `javax.servlet.error.*` 속성으로 추가 정보를 얻을 수 있음.

3) 에러 페이지에서 사용 가능한 정보
- `javax.servlet.error.status_code` — HTTP 상태 코드
- `javax.servlet.error.exception` — Throwable 인스턴스
- `javax.servlet.error.message` — 예외 메시지
- 이 정보는 JSP에서 `${requestScope['javax.servlet.error.exception']}` 등으로 접근 가능

4) 실습 지침
- `07_error_demo.jsp`에서 인위적으로 `throw new RuntimeException("테스트 예외")`를 발생시켜 에러 페이지로 이동시키기.
- `WEB-INF/web.xml` 또는 `web.xml`에 `<error-page>`를 추가하고, 에러 발생 시 커스텀 페이지가 나타나는지 확인.
- 에러 페이지에서 예외 메시지와 스택트레이스를 로그로 남기고, 사용자에게는 친절한 안내문을 보여줄 것.

5) 운영 관점
- 프로덕션에서는 예외의 스택트레이스를 사용자에게 그대로 노출하지 말 것(정보 노출 위험).
- 에러 발생 시 자동 알림 체계(이메일, Slack, Sentry 등)로 통합하면 빠른 대응이 가능.

6) 확장 실습
- 특정 예외(예: NotFoundException)를 던지고 해당 예외에 대해 맞춤형 에러 페이지를 제공하도록 구현.
- 국제화된 에러 페이지(다국어 지원) 구현.

이 문서는 에러 처리의 이론과 실습을 모두 포함해 강의 노트로 활용할 수 있도록 구성했습니다.
