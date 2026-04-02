# 13. Listener — 서블릿 이벤트 리스너

## 핵심 개념
> 서블릿 컨테이너에서 발생하는 **이벤트**(컨텍스트 초기화, 세션 생성/소멸 등)를 감지하여 자동으로 코드를 실행하는 컴포넌트.

---

## 1) 서블릿 3대 컴포넌트

| 컴포넌트 | 역할 | 등록 방법 |
|----------|------|----------|
| **Servlet** | 요청 처리 (Controller) | `@WebServlet` 또는 `<servlet>` |
| **Filter** | 요청/응답 가로채기 (전처리/후처리) | `@WebFilter` 또는 `<filter>` |
| **Listener** | 이벤트 감지 (초기화, 세션, 속성 변경) | `@WebListener` 또는 `<listener>` |

→ Spring으로 가면 이 3가지가 Spring의 `DispatcherServlet`, `Filter`, `ApplicationListener`로 대응된다.

---

## 2) 주요 리스너 인터페이스

### ServletContextListener — 웹앱 시작/종료
```java
@WebListener
public class AppInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 웹앱 시작 시 1회 실행
        // DB 커넥션 풀 초기화, 설정 로드 등
        System.out.println("[AppInit] 웹앱 시작됨");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 웹앱 종료 시 1회 실행
        // 자원 정리
        System.out.println("[AppInit] 웹앱 종료됨");
    }
}
```
- **가장 많이 쓰는 리스너**
- Spring의 `ContextLoaderListener`가 바로 이것의 구현체

### HttpSessionListener — 세션 생성/소멸
```java
@WebListener
public class SessionCountListener implements HttpSessionListener {
    private static int activeSessions = 0;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        activeSessions++;
        System.out.println("[Session] 생성됨. 현재 세션 수: " + activeSessions);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        activeSessions--;
        System.out.println("[Session] 소멸됨. 현재 세션 수: " + activeSessions);
    }

    public static int getActiveSessions() { return activeSessions; }
}
```
- 실시간 접속자 수 추적, 세션 모니터링에 활용

### ServletRequestListener — 요청 시작/종료
```java
@WebListener
public class RequestTimingListener implements ServletRequestListener {
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        sre.getServletRequest().setAttribute("startTime", System.currentTimeMillis());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        long start = (long) sre.getServletRequest().getAttribute("startTime");
        long duration = System.currentTimeMillis() - start;
        System.out.println("[Request] 처리 시간: " + duration + "ms");
    }
}
```

---

## 3) Filter vs Listener 차이

| 구분 | Filter | Listener |
|------|--------|----------|
| 대상 | 요청/응답 | 이벤트 (컨텍스트, 세션, 요청) |
| 흐름 제어 | `chain.doFilter()`로 다음 단계 호출 | 이벤트 발생 시 자동 호출 (흐름 제어 불가) |
| 용도 | 인증, 인코딩, 로깅 | 초기화, 세션 추적, 자원 정리 |

---

## 4) 실습

### 실습 파일
- `AppInitListener.java` — 웹앱 시작/종료 로그
- `SessionCountListener.java` — 현재 활성 세션 수 추적
- `13_listener_demo.jsp` — 세션 수 확인

### 실습 포인트
1. 서버 시작 시 콘솔에서 `[AppInit] 웹앱 시작됨` 확인
2. 브라우저로 접속하면 세션 생성 로그 확인
3. 다른 브라우저로 접속하면 세션 수가 증가하는지 확인
4. 서버 종료 시 `[AppInit] 웹앱 종료됨` + 세션 소멸 로그 확인

---

## 5) Spring과의 연결

| 서블릿 | Spring |
|--------|--------|
| `ServletContextListener` | `ContextLoaderListener` (root ApplicationContext 생성) |
| `HttpSessionListener` | Spring Session, Spring Security 세션 관리 |
| `ServletRequestListener` | `RequestContextListener` |

→ Spring을 배우면 직접 리스너를 구현할 일은 줄지만, **Spring 내부가 이 리스너들로 동작**하므로 원리를 이해해야 한다.

