# 09 Filter — 상세 강의노트

목차
- Filter의 개념과 역할
- 생명주기와 메서드 설명
- 필터 체인 동작 원리
- 실습: 로깅 필터, 인코딩 필터, 인증 필터 구현
- 고급: 필터 매핑 전략, 성능 고려사항

1) Filter의 개념
- 서블릿 필터는 요청과 응답을 가로채 전처리/후처리를 수행하는 컴포넌트.
- 로깅, 인증, 인코딩 처리, 응답 압축, 캐시 제어 등을 구현할 때 사용.

2) 생명주기 및 핵심 메서드
- `init(FilterConfig)` : 필터 초기화(리소스 로드 등)
- `doFilter(ServletRequest, ServletResponse, FilterChain)` : 요청/응답 처리의 핵심. `chain.doFilter()`를 호출해야 다음 필터 또는 타깃 서블릿으로 이동.
- `destroy()` : 필터가 제거될 때 자원 정리.

3) 필터 체인
- 여러 필터가 등록되면 지정된 순서로 체인을 형성. 각 필터는 다음 필터를 호출하거나, 특정 조건에서 체인을 중단하고 응답을 작성 가능.

![필터 체인: 요청이 Filter → Servlet을 지나 응답이 역순으로 통과]({{ '/web_basic/web_basic_images/ch09/filter-chain.svg' | relative_url }})

3-1) 필터 등록 방법
- 필터는 두 가지로 등록할 수 있습니다.
  1. **애노테이션**: 필터 클래스에 `@WebFilter("/*")`처럼 URL 패턴을 지정 (이 프로젝트의 `LoggingFilter`가 이 방식, `@WebFilter("/*")` → 모든 요청 대상).
  2. **web.xml**: `<filter>`로 필터를 정의하고 `<filter-mapping>`으로 URL 패턴에 매핑.
     ```xml
     <filter>
       <filter-name>loggingFilter</filter-name>
       <filter-class>com.example.chlecture.filter.LoggingFilter</filter-class>
     </filter>
     <filter-mapping>
       <filter-name>loggingFilter</filter-name>
       <url-pattern>/*</url-pattern>
     </filter-mapping>
     ```
- 여러 필터의 실행 순서: `@WebFilter`는 보통 클래스명(또는 필터명) 순서에 의존해 순서 보장이 약하므로, **순서가 중요하면 web.xml의 `<filter-mapping>` 선언 순서**로 제어합니다.

4) 실습 예제
- `LoggingFilter`(프로젝트에 포함): 모든 요청 URI를 콘솔에 출력.
- 인코딩 필터: `request.setCharacterEncoding("UTF-8")`, `response.setCharacterEncoding("UTF-8")`를 설정해 한글 깨짐 방지.
- 인증 필터: 세션에서 로그인 여부를 체크하고 미인증 시 로그인 페이지로 리다이렉트.

5) 매핑 전략
- 전역 매핑(`/*`) vs 특정 패턴(`/board/*`)
- 정적 리소스(이미지/CSS)는 필터 대상에서 제외해 성능 최적화.

6) 성능과 보안 고려
- 필터 내 무거운 처리 금지(특히 동기 I/O 등).
- 인증/권한 필터는 가능한 한 빠르게 인증 체크를 수행하도록 설계.

7) 확장 실습
- 필터에서 응답 바디를 래핑하여 응답을 조작(예: HTML 미니파이)하는 방법 실습.
- 필터에 메트릭(처리시간) 수집을 추가해 모니터링 연동.

이 문서는 필터의 개념·동작·실습·운영 고려사항 등을 포함해 강의에 바로 활용할 수 있도록 구성했습니다.
