# web_basic 검수 — 02 JSP 필수객체 / 03 page directive

검수일: 2026-07-05
검수 범위: `docs/.../02_JSP_필수객체.md`, `03_page_directive.md` + `02_scope_demo.jsp`, `03_page_directives.jsp`, `03_include_header.jsp`, `03_include_footer.jsp`

정확성 확인(문제없음): 암시적 객체 9종(request/response/session/application/out/page/config/pageContext/exception) 타입·설명, 스코프(page/request/session/application) 생명주기와 EL 탐색 우선순위 서술 정확. **이미지 참조 없음.**

---

## 02_JSP_필수객체

### 낮음
- `web_basic/.../02_scope_demo.jsp:31` — `${"$"}{msg}` 는 `${"$"}`로 `$`만 출력하고 `{msg}`는 리터럴로 남기는 EL 이스케이프 트릭. 동작·결과(page 스코프 우선)와 32행 주석 설명은 옳으나, 이 기법이 초보자에게 **아무 설명 없이** 등장해 혼란을 줄 수 있음. 주석/문서에 한 줄 설명 추가 권장.
- `docs/.../02_JSP_필수객체.md:51` — 실습 지침의 `AttributeSetterServlet`은 "직접 작성해 보라"는 연습 과제로, 실제 파일이 없어도 무방. 초보자가 기존 파일로 오해하지 않도록 "직접 작성" 임을 명확히 하면 좋음. (정보성)

## 03_page_directive

### 확인 필요
- `docs/.../03_page_directive.md:12` — "@include는 포함 파일 변경 시 포함된 JSP가 함께 재컴파일 되어야 함". 정적 include의 고전적 함정은 오히려 반대(일부 컨테이너가 included 파일 변경을 감지 못해 **재컴파일이 안 되고** stale 출력이 남는 것). 현대 Tomcat은 변경을 추적하므로 명백한 오류라 단정하긴 어렵되, "included 파일만 바꾸면 반영이 안 될 수 있으니 주의" 취지로 다듬는 것을 검토 권장.

### 낮음
- `docs/.../03_page_directive.md:16` — `<jsp:include>`가 "독립적인 서블릿/응답을 생성"한다는 서술은 부정확. `<jsp:include>`는 `RequestDispatcher.include()` 기반으로 **같은 request/response를 공유**하며 대상의 출력을 현재 응답에 합침(별도 응답 생성 아님). "독립적인 서블릿을 실행" 정도로 정정 권장.
- `docs/.../03_page_directive.md:20` — "contentType, pageEncoding: 문자셋 및 MIME 타입 설정"에서 `pageEncoding`은 MIME 타입이 아니라 **JSP 소스 파일 자체의 인코딩**을 지정. `contentType`(응답 MIME+charset)과 역할이 다르므로 구분해 설명하는 것이 정확.
- `docs/.../03_page_directive.md:31` vs `web_basic/.../03_page_directives.jsp:10` — 문서는 "헤더에서 로그인 여부 표시 → `<jsp:include>`로 포함된 헤더에서 접근"을 제안하나, 실제 소스에서 헤더(`03_include_header.jsp`)는 `<%@ include %>`(compile-time)로 포함됨(footer만 runtime). request 속성은 compile-time/runtime include 모두 접근 가능하므로 "jsp:include라야 접근된다"는 뉘앙스는 오해 소지 + 현 소스 구성과 어긋남. 문구 조정 검토 권장.

소스(JSP) 코드 자체는 버그/오타/깨진 주석 없음. `@include`(header)·`<jsp:include>`(footer) 구성이 문서 30행 설명과 일치. EL `${pageContext.request.contextPath}`가 정적 include된 헤더에서도 정상 동작.
