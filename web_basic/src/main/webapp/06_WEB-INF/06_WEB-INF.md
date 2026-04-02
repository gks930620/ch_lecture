# 06 WEB-INF — 상세 강의노트

목차
- WEB-INF의 정의와 목적
- WEB-INF에 둘 자원(what belongs here)
- 직접 접근이 차단되는 이유와 내부 리소스 제공 방법
- web.xml(배포기술자) 개요와 주요 태그
- 실습: 내부 JSP를 서블릿으로 forward 제공, web.xml에 서블릿/필터/에러 매핑 예제

1) 정의와 목적
- `WEB-INF`는 웹 애플리케이션의 루트 디렉터리 아래 위치하는 특별한 디렉터리로, 외부(클라이언트)에서 직접 접근할 수 없는 리소스를 저장하는 공간입니다.
- 목적: 설정 파일, 내부 JSP, 라이브러리(JAR), 보안 민감 리소스를 외부로부터 보호.

2) WEB-INF에 두는 것
- `web.xml` (배포 기술자)
- 라이브러리 파일(`WEB-INF/lib/*.jar`)
- 클래스 파일(`WEB-INF/classes`), 리소스 파일
- 내부적으로만 사용될 JSP/Servlet fragment

3) 왜 직접 접근이 차단되나?
- 보안과 응용 구조상의 이유로 컨테이너가 WEB-INF 내부 자원에 대해 직접 URL 접근을 허용하지 않습니다. 대신 서블릿(또는 컨트롤러)이 내부 자원을 `RequestDispatcher.forward()`로 제공할 수 있습니다.

4) web.xml 개요(핵심 태그)
- `<servlet>` / `<servlet-mapping>`: 서블릿 클래스와 URL 매핑
- `<filter>` / `<filter-mapping>`: 필터 정의 및 매핑
- `<listener>`: 컨텍스트 리스너 등록
- `<context-param>`: 전역 초기화 파라미터
- `<error-page>`: 에러 처리 페이지 매핑

예시: `web.xml`에서 에러 페이지 매핑(간단)
```xml
<error-page>
  <error-code>404</error-code>
  <location>/error/404.jsp</location>
</error-page>
<error-page>
  <exception-type>java.lang.Throwable</exception-type>
  <location>/error/500.jsp</location>
</error-page>
```

5) 실습 안내
- `WEB-INF/hidden.jsp`는 직접 브라우저에서 접근 불가함을 확인한다.
- `WebInfForwardServlet`에서 `req.getRequestDispatcher("/06_WEB-INF/WEB-INF/hidden.jsp").forward(req, resp)`로 제공하는 흐름을 실습.
- `web.xml`에 서블릿/필터/에러 페이지 매핑을 추가하고 동작을 확인.

6) 운영 팁
- `WEB-INF/classes`와 `WEB-INF/lib`의 클래스패스 우선순위 이해
- 보안상 민감한 설정은 `WEB-INF` 내부에 두고 외부에 노출되는 자원은 루트에 둔다.

이 문서는 `WEB-INF`의 목적과 사용법, web.xml의 핵심 항목까지 포함한 강의 노트로 사용하기 적합하게 작성되었습니다.
