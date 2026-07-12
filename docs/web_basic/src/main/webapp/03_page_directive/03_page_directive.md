# 03 Page Directive & Include — 상세 강의노트

목차
- JSP include의 두 가지 방식: compile-time(@include) vs runtime(<jsp:include>)
- `page` 디렉티브의 주요 속성
- 실제로 어떤 경우에 어느 방식을 선택할지 결정하는 기준
- 실습: 헤더/푸터 분리, 런타임 포함 시 서블릿 파라미터 전달

1) `@include` (compile-time include)
- 동작: 번역(translate) 단계에서 포함 파일의 내용을 원본 JSP에 병합.
- 장점: 성능상 이점(번역 후에는 하나의 클래스처럼 동작), 간단한 HTML 조각 포함에 적합.
- 단점: 포함 파일만 수정했을 때 컨테이너가 이를 감지하지 못하고 재번역(재컴파일)을 하지 않아, **변경이 반영되지 않은 이전(stale) 출력**이 남을 수 있음(고전적 함정). 현대 Tomcat은 대체로 변경을 추적하지만, 안 되는 경우 원본 JSP까지 다시 저장/재배포해야 반영됨.

2) `<jsp:include page="..." />` (runtime include)
- 동작: 요청 처리 시점에 `RequestDispatcher.include()`로 대상 리소스를 실행하고, 그 출력을 현재 응답에 합침. (별도의 독립 응답을 새로 만드는 것이 아니라 **같은 request/response를 공유**한다.)
- 장점: 포함 대상을 독립적으로 실행(대상 JSP가 컴파일 단위상 분리)하며, 변경 시 대상만 재번역되면 되어 재컴파일 부담이 적음.
- 단점: 런타임 호출 오버헤드가 있음.

3) `page` 디렉티브 주요 속성
- contentType: **응답(Response)의 MIME 타입 + 문자셋**을 지정 (예: `text/html;charset=UTF-8` → 브라우저가 받는 Content-Type 헤더)
- pageEncoding: **JSP 소스 파일 자체의 인코딩**을 지정 (컨테이너가 .jsp 파일을 어떤 문자셋으로 읽을지). contentType과 역할이 다르므로 구분할 것.
- import: Java 클래스를 import
- isErrorPage/isELIgnored: 에러 페이지 설정 및 EL 사용 여부 제어

4) 선택 기준(실무 가이드)
- 정적인 HTML 조각(로고, 고정 메뉴): `@include` 사용 고려
- 동적 컨텐츠(사용자별 권한에 따라 변경되는 헤더): `<jsp:include>` 사용
- 프래그먼트 재사용성과 독립성이 중요하면 runtime include 또는 태그파일 사용

5) 실습 제안
- 실제 소스 구성: `03_page_directives.jsp`는 헤더를 `<%@ include %>`(compile-time)로, 푸터를 `<jsp:include>`(runtime)로 포함합니다. 두 방식을 바꿔보며 차이를 관찰하세요.
- 참고: request 속성(`request.setAttribute(...)`)은 compile-time include와 runtime include **양쪽 모두에서 접근 가능**합니다(둘 다 같은 request를 공유). 따라서 "헤더에서 로그인 여부 표시"는 어느 include 방식이든 동작합니다. 두 방식의 진짜 차이는 include 대상이 별도 컴파일 단위인지, 변경 반영/재번역 시점, 런타임 오버헤드 등입니다.

6) 고급 주제(확장)
- Tag File(.tag) 및 커스텀 태그 라이브러리 사용으로 재사용성 향상
- JSP Fragment와 EL 함수(custom function) 설계 패턴

이 문서는 학생이 include 방식의 차이와 실제 선택 기준을 이해하고, 수업 중 즉시 실습할 수 있도록 예제와 실무 팁을 제공합니다.
