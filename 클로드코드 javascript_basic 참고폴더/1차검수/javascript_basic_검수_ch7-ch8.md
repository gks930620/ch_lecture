# javascript_basic 검수사항 — ch7 jQuery / ch8 HTTP통신

검수일: 2026-07-03
검수 범위: `docs/javascript_basic/ch7_jQuery/강의노트_7장_jQuery.md`, `ch8_HTTP통신/강의노트_8장_HTTP통신.md` + 각 소스 HTML(ch7 part01~03, ch8 part01)

---

## ch7_jQuery

총평: 기술 설명(선택자·DOM조작·이벤트·AJAX)은 입문자 수준에서 대체로 정확하고 jQuery CDN 버전(3.7.1)도 최신. `$.ajax/$.get/$.post`, `$(document).ready`, 이벤트 위임, `preventDefault()`는 정확하며 JSONPlaceholder AJAX 예제는 인터넷 연결 시 실제 작동. 다만 이미지 데모가 종료된 외부 서비스에 의존해 깨지고, 강의노트↔소스 간 범위 불일치와 챕터 번호(32.x) 잔재가 있음.

### 높음
- `javascript_basic/ch7_jQuery/part02_선택자와DOM조작.html:414, :516, :528` — 속성 조작 데모가 `https://via.placeholder.com/...` 이미지를 사용하는데 이 서비스는 종료되어(DNS는 응답하나 TLS 핸드셰이크 실패, 이미지 로드 불가) 초기 이미지·`changeAttr()`·`resetAttr()` 모두 깨진 이미지로 표시됨. attr() 실습의 핵심 시각 효과가 동작하지 않음.

### 중간
- `javascript_basic/ch7_jQuery/part01...html:122`, `part02...html:114`, `part03...html:135` — 화면 H1 제목이 각각 "32.1-32.4", "32.5-32.9", "32.12-32.15". 강의노트는 "7장"인데 소스는 32장 번호(문서↔소스 불일치, part03은 32.10~32.11 건너뜀 → 타 커리큘럼 잔재).
- `docs/javascript_basic/ch7_jQuery/강의노트_7장_jQuery.md:781-788` — 강의노트는 7.8(DOM 조작)까지만 다루고 이벤트·애니메이션·AJAX를 "다음 단계 예고"로 처리. 그러나 소스 `part03_이벤트와AJAX.html`은 이벤트(on/off, hover, 폼)와 AJAX($.ajax/$.get/$.post)를 ch7 실습으로 포함 → 노트에 part03 대응 설명이 통째로 빠짐(범위 불일치).
- `docs/javascript_basic/ch7_jQuery/강의노트_7장_jQuery.md:620-621` — `$('.box'); // 항상 배열 형태로 반환` 주석은 부정확. jQuery는 배열이 아니라 유사배열(array-like)인 jQuery 객체를 반환. 입문자가 실제 Array로 오해 가능.

### 낮음
- `docs/javascript_basic/ch7_jQuery/강의노트_7장_jQuery.md:16` — "한때 90% 이상의 웹사이트에서 사용"은 과장. W3Techs 기준 약 70%대이며 "90% 이상" 근거 불명확.
- `docs/...강의노트_7장_jQuery.md:327-341`, `part02...html:161-194` — `:first :last :eq :even :odd :gt :lt` 등 위치 기반 필터 선택자는 jQuery 3.4부터 deprecated(3.7.1에서 동작하나 표준 CSS 선택자 아님). "CSS와 동일"이라 소개되지만 jQuery 전용·제거 대상이라는 안내 없음.
- `docs/...강의노트_7장_jQuery.md:443-444` — `$('#agree').val();`를 "checkbox 값"으로 소개하나, 체크박스 `val()`은 체크 상태와 무관하게 항상 value 속성을 반환(체크 상태는 `.prop('checked')`). 초보자 오해 소지.
- `part01...html:411-414, :425-430`의 "강사 노트/강의 TIP" 주석 — 학생 배포용 소스에 남은 강사용 메모(의도된 것이면 무방).

참고: jQuery 3.7.1 CDN(HTTP 200), JSONPlaceholder(`/posts/1`, `/posts`, `/users` HTTP 200) 정상 동작 확인.

---

## ch8_HTTP통신

총평: 상태코드(401/403, 301/302), 메서드 멱등성·안전성, 쿠키/LocalStorage/SessionStorage 용량·생명주기·전송여부, CORS/Preflight 개념이 정확히 서술되어 입문자 자료로 완성도 높음. **명백한 사실 오류(높음) 없음.** 다만 HTTP/1.1 파이프라이닝↔멀티플렉싱 혼동, HTTP/2 Server Push가 현행 기능처럼 서술된 점, CORS 예제의 헤더 중복 설정이 보완 필요. 소스 실습(Storage/Fetch/상태코드/CORS)은 실제 동작.

### 높음
- 없음 (핵심 기술 사실에 치명적 오류 없음)

### 중간
- `docs/javascript_basic/ch8_HTTP통신/강의노트_8장_HTTP통신.md:79` — HTTP/1.1 Pipelining을 "요청 여러 개 **동시** 전송"으로 설명. 파이프라이닝은 응답을 기다리지 않고 연속 전송할 뿐 응답은 순서대로 와야 하며(HOL blocking 잔존), '동시'는 HTTP/2 Multiplexing과 혼동. 브라우저에서 사실상 미사용. 89-90행 Multiplexing 설명과 구분되게 수정 필요.
- `docs/javascript_basic/ch8_HTTP통신/강의노트_8장_HTTP통신.md:92` — HTTP/2 Server Push를 현행 개선점으로 서술. Server Push는 실효성 문제로 Chrome에서 2022년 제거되는 등 사실상 폐기(deprecated). 2026년 기준 "폐기됨" 주석 필요.
- `docs/javascript_basic/ch8_HTTP통신/강의노트_8장_HTTP통신.md:839-840` — CORS 해결 예제에서 `res.header('Access-Control-Allow-Origin', '*')`와 `res.header(..., 'https://mysite.com')`를 연속 호출. 실제로 두 번 설정하면 헤더가 중복/덮어써져 오히려 CORS가 깨질 수 있음. "둘 중 하나만 사용"임이 코드상 명확하지 않음(주석뿐).

### 낮음
- `part01_HTTP실습_Storage와FetchAPI.html:322,342,397,427,447,473` — `response.statusText`를 출력하지만 JSONPlaceholder는 HTTP/2로 응답해 statusText가 빈 문자열("")로 반환(스펙상 HTTP/2는 reason phrase 없음). 화면에 "상태: 200 "처럼 보일 수 있음.
- `part01_HTTP실습_Storage와FetchAPI.html:284-309` — `file://` 프로토콜로 직접 열면 Chrome에서 `document.cookie` 설정/조회가 동작하지 않음(쿠키 데모만 빈 결과). Live Server 등 http로 열어야 정상 → 실습 안내에 서빙 방식 명시 권장.
- `part01_HTTP실습_Storage와FetchAPI.html:502-506` — CORS 데모의 `error.message`는 브라우저에서 "Failed to fetch"라는 일반 메시지로 표시(순수 CORS 문구 아님). 네트워크 오류와 구분 안 됨 → "이것이 CORS 에러"라는 단정이 다소 부정확. 데모 목적(catch 유도)은 달성.
- `docs/javascript_basic/ch8_HTTP통신/강의노트_8장_HTTP통신.md:569` — Content-Type 응답 헤더 예시에 `multipart/form-data`(주로 요청/파일 업로드용)를 나열. 응답 타입 예시로 부적절.
- `docs/javascript_basic/ch8_HTTP통신/강의노트_8장_HTTP통신.md:95` — "성능: 30-50% 향상"은 근거 없는 특정 수치. "상황에 따라" 정도로 완화 권장.
- `docs/javascript_basic/ch8_HTTP통신/강의노트_8장_HTTP통신.md:116-155, 798-858` — HTTP 요청/응답 구조·CORS/Preflight 흐름이 모두 ASCII 텍스트 다이어그램(깨진 이미지 링크는 없음). 입문자 이해를 위해 실제 그림 도입 권장.
