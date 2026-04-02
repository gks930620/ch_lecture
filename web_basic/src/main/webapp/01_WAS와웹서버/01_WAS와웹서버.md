# 01 WAS vs WS (상세 강의노트)

이 문서는 WAS(Web Application Server)와 WS(Web Server)의 개념을 심층적으로 설명합니다. 수업은 이론(정의·구성요소) → 내부 동작(요청-응답 흐름) → 관련 기술(서블릿/JSP) → 운영/배포 사례 순으로 진행합니다.

목차
- 정의와 역할
- 구조와 구성 요소
- 요청 처리 흐름(정적/동적)
- 서블릿(Servlet)과 JSP의 위치와 역할
- 실습: 간단한 요청 흐름 추적
- 배포/운영 고려사항

1) 정의와 역할
- Web Server(WS)
  - 정적 리소스(HTML, CSS, JS, 이미지)를 클라이언트에게 전달하는 소프트웨어.
  - 주로 HTTP 프로토콜의 요청을 받아 정적 파일을 응답으로 돌려준다.
  - 예: Nginx, Apache HTTP Server.
- Web Application Server(WAS)
  - 동적 자원(서블릿, JSP, 웹 애플리케이션 컨테이너)을 실행하고 관리하는 서버.
  - 요청을 받아 서블릿 컨테이너(예: Tomcat)가 자바 서블릿을 실행해 동적 응답을 생성.
  - WAS는 서블릿 API, JSP 규격을 구현하고 앱 라이프사이클(배포, 초기화, 멀티스레드 처리 등)을 담당.

2) 구조와 구성 요소
- 프론트엔드 웹서버(Reverse Proxy): 정적 처리, TLS 종료, 로드밸런싱, 정적 캐싱을 담당.
- WAS(애플리케이션 컨테이너): 서블릿 엔진, JSP 컴파일러, 세션 관리, 리소스(jdbc, jndi) 연결 관리.
- 데이터베이스, 캐시(예: Redis), 외부 API 등의 백엔드 서비스.

3) 요청 처리 흐름(정적 vs 동적)
- 정적 요청 흐름
  1. 클라이언트가 URL로 요청
  2. 웹서버(Nginx 등)가 정적 파일인지 확인
  3. 정적이면 웹서버가 파일을 찾아 응답(빠르고 효율적)
- 동적 요청 흐름
  1. 클라이언트가 동적 리소스 URL 요청
  2. 웹서버가 프록시 설정에 의해 WAS로 요청 전달(또는 WAS가 직접 수신)
  3. WAS 서블릿 컨테이너가 해당 서블릿/JSP를 실행
  4. 서블릿이 비즈니스 로직 처리 후 Response를 생성
  5. WAS가 응답을 반환하고, 웹서버(프록시)가 클라이언트로 전달

4) 서블릿(Servlet)과 JSP의 관계
- Servlet
  - Java 기반의 서버측 컴포넌트로 HTTP 요청을 처리하는 표준 API.
  - HttpServlet 클래스를 상속받아 doGet/doPost 등 메서드를 구현.
  - 컨트롤러 역할(요청 파싱, 서비스 호출, 응답 생성/포워드/리다이렉트)에 적합.
- JSP
  - 서버에서 실행되어 HTML을 생성하는 템플릿 언어. 내부적으로 서블릿으로 변환되어 동작.
  - 뷰(화면) 생성에 적합하며, JSTL/EL을 이용해 자바 코드 의존을 줄이는 것이 권장됨.

5) 서블릿 생명주기(Lifecycle) — Spring 전 반드시 이해할 것
- 서블릿은 싱글톤 인스턴스로 관리된다. 최초 요청 시 1회만 생성.
- 생명주기 순서:
  1. 클래스 로딩 + 인스턴스 생성 (최초 요청 시 또는 load-on-startup)
  2. `init(ServletConfig)` 호출 — 초기화 (1번만)
  3. `service()` → `doGet()`/`doPost()` — 매 요청마다 호출. 멀티스레드로 동시 처리!
  4. `destroy()` — 서버 종료 시 호출. 자원 정리.
- ⚠ 핵심: 인스턴스 변수(필드)에 사용자별 데이터 저장 금지! (모든 스레드가 공유)
- 실습: `/lifecycle` 서블릿에서 요청 횟수, 스레드명, 인스턴스 해시코드를 확인.

6) HTTP 기본 — 서블릿이 처리하는 프로토콜
- HTTP 메서드:
  - GET: 리소스 조회. 파라미터는 URL 쿼리스트링에 포함 (`?key=value&...`)
  - POST: 데이터 전송. 파라미터는 요청 본문(body)에 포함 (URL에 노출 안됨)
  - PUT/DELETE/PATCH: RESTful API에서 사용 (서블릿에서는 doGet/doPost만 주로 사용)
- HTTP 상태 코드:
  - 200 OK, 301/302 Redirect, 304 Not Modified
  - 400 Bad Request, 403 Forbidden, 404 Not Found
  - 500 Internal Server Error
- HTTP 헤더:
  - 요청: Host, User-Agent, Accept, Cookie, Content-Type
  - 응답: Content-Type, Set-Cookie, Location(redirect), Cache-Control
- HttpServletRequest 주요 메서드:
  - `getParameter("name")` — 파라미터 조회
  - `getHeader("User-Agent")` — 요청 헤더 조회
  - `getMethod()` — GET/POST 등
  - `getRequestURI()`, `getContextPath()`, `getServletPath()`
  - `setAttribute()`, `getAttribute()` — request scope 속성
- HttpServletResponse 주요 메서드:
  - `setContentType("text/html;charset=UTF-8")` — 응답 타입
  - `setStatus(int)` — 상태 코드 설정
  - `sendRedirect(url)` — 리다이렉트
  - `getWriter()` — 응답 출력 스트림

7) 실습: 요청 흐름 추적(수업 활동)
- 목적: index.jsp -> 서블릿 -> JSP 흐름을 손으로 따라가며 log/출력으로 확인.
- 활동: `01_intro.jsp`에서 현재 시간 출력(동적), `01_server_info.jsp`에서 application/request 정보를 출력.
- 활동2: `/lifecycle`에 접속하여 GET/POST 차이, HTTP 헤더, 서블릿 생명주기를 직접 확인.
- 토론: 프록시가 필요할 때와 불필요할 때, 캐시 적용 위치, TLS 종료 위치 등.

8) 배포·운영 고려사항
- 성능: 정적 자원은 웹서버에서 서빙하고, 동적 자원은 WAS에서 처리하는 아키텍처를 권장.
- 확장성: WAS는 수평 확장(인스턴스 추가)과 세션 공유(Sticky session vs distributed session) 문제 고려.
- 보안: TLS, HTTP 헤더(Strict-Transport-Security, X-Frame-Options 등), 세션/쿠키 설정.
- 로깅/모니터링: 접속 로그, 애플리케이션 로그, APM(예: NewRelic) 연동.

참고 예제 코드
- 간단한 서블릿 예제와 JSP 연동은 프로젝트의 `src/main/java/com/example/chlecture/forwardredirect/ForwardRedirectServlet.java`와 `src/main/webapp/01_WAS와웹서버/01_server_info.jsp`를 참고하세요.

추가 자료(권장 읽기)
- 서블릿 규격 문서(Servlet Specification)
- Tomcat 아키텍처 문서
- Nginx + Tomcat 연동(Reverse Proxy) 구성 예제

이 파일만 읽어도 WAS/WS의 차이, 서블릿과 JSP의 역할, 요청 흐름의 기본을 이해할 수 있도록 작성했습니다.
