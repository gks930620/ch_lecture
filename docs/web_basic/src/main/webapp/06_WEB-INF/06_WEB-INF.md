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
- ⚠️ **차단 규칙은 "경로가 `/WEB-INF/` 또는 `/META-INF/`로 시작할 때"만 적용됩니다.** 톰캣은 요청 경로가 이 접두어로 **시작**하는 경우에만 404로 막습니다. 따라서 `/WEB-INF/hidden.jsp`는 직접 접근이 차단되지만, `/something/WEB-INF/hidden.jsp`처럼 **중첩된 WEB-INF**는 경로가 `/WEB-INF/`로 시작하지 않아 **차단되지 않고 그대로 열립니다.** 보호하려면 반드시 컨텍스트 루트 바로 아래의 `WEB-INF/`에 두어야 합니다.

![WEB-INF 직접 접근 차단 구조 (직접 접근 ✗ / 서블릿 forward ○)]({{ '/web_basic/web_basic_images/ch06/web-inf-access.svg' | relative_url }})

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
> 위 `<location>`은 형식을 보여주는 **일반 예시**입니다. 이 프로젝트의 실제 에러 페이지 경로는 `/07_error_handling/error/404.jsp`, `/07_error_handling/error/500.jsp`이므로, 실제 web.xml에는 그 경로가 들어갑니다(ch07 참고).

5) 실습 안내
- 실습 파일은 컨텍스트 루트의 `WEB-INF/06_WEB-INF/hidden.jsp`에 있습니다(컨텍스트 루트 바로 아래 `WEB-INF/`이므로 실제로 보호됨).
- 브라우저로 `.../WEB-INF/06_WEB-INF/hidden.jsp`에 직접 접근하면 **404**가 나는 것을 확인한다(직접 접근 불가).
- 반면 서블릿 경로 `/06_WEB-INF/hidden`으로 접근하면, `WebInfForwardServlet`이 `req.getRequestDispatcher("/WEB-INF/06_WEB-INF/hidden.jsp").forward(req, resp)`로 내부 forward 하여 **정상적으로 렌더링**된다. → "직접 접근은 막히지만 서블릿 forward로는 제공된다"를 대비 실습.
- `web.xml`에 서블릿/필터/에러 페이지 매핑을 추가하고 동작을 확인.

6) 운영 팁
- `WEB-INF/classes`와 `WEB-INF/lib`의 클래스패스 우선순위 이해
- 보안상 민감한 설정은 `WEB-INF` 내부에 두고 외부에 노출되는 자원은 루트에 둔다.

이 문서는 `WEB-INF`의 목적과 사용법, web.xml의 핵심 항목까지 포함한 강의 노트로 사용하기 적합하게 작성되었습니다.
