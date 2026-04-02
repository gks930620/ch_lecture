# 03 Page Directive & Include — 상세 강의노트

목차
- JSP include의 두 가지 방식: compile-time(@include) vs runtime(<jsp:include>)
- `page` 디렉티브의 주요 속성
- 실제로 어떤 경우에 어느 방식을 선택할지 결정하는 기준
- 실습: 헤더/푸터 분리, 런타임 포함 시 서블릿 파라미터 전달

1) `@include` (compile-time include)
- 동작: 번역(translate) 단계에서 포함 파일의 내용을 원본 JSP에 병합.
- 장점: 성능상 이점(재컴파일 후에는 하나의 클래스처럼 동작), 간단한 HTML 조각 포함에 적합.
- 단점: 포함 파일 변경 시 포함된 JSP가 함께 재컴파일 되어야 함.

2) `<jsp:include page="..." />` (runtime include)
- 동작: 요청 처리 시점에 대상 리소스를 호출하여 그 출력을 현재 응답에 포함.
- 장점: 포함 리소스가 독립적인 서블릿/응답을 생성할 수 있고, 변경 시 재컴파일 불필요.
- 단점: 런타임 호출 오버헤드가 있으며, 포함 대상에서 별도의 request/response 처리가 필요할 수 있음.

3) `page` 디렉티브 주요 속성
- contentType, pageEncoding: 문자셋 및 MIME 타입 설정
- import: Java 클래스를 import
- isErrorPage/isELIgnored: 에러 페이지 설정 및 EL 사용 여부 제어

4) 선택 기준(실무 가이드)
- 정적인 HTML 조각(로고, 고정 메뉴): `@include` 사용 고려
- 동적 컨텐츠(사용자별 권한에 따라 변경되는 헤더): `<jsp:include>` 사용
- 프래그먼트 재사용성과 독립성이 중요하면 runtime include 또는 태그파일 사용

5) 실습 제안
- `03_page_directives.jsp`를 수정해 `03_include_header.jsp`(compile-time)와 `03_include_footer.jsp`(runtime)를 번갈아 가며 포함해보고 차이를 관찰.
- 헤더에서 로그인 여부를 표시하려면 서블릿이 request 속성을 설정하고 `<jsp:include>`로 포함된 헤더에서 접근해보세요.

6) 고급 주제(확장)
- Tag File(.tag) 및 커스텀 태그 라이브러리 사용으로 재사용성 향상
- JSP Fragment와 EL 함수(custom function) 설계 패턴

이 문서는 학생이 include 방식의 차이와 실제 선택 기준을 이해하고, 수업 중 즉시 실습할 수 있도록 예제와 실무 팁을 제공합니다.
