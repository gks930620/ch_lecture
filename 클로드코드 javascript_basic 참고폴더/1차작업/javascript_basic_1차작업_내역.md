# javascript_basic 1차 작업 내역

작업일: 2026-07-03
작업 기준: `1차검수/` 폴더의 검수 지적사항 (검수자도 틀릴 수 있으므로 **맞는 지적만** 반영)
대상: `docs/javascript_basic/` 강의노트 + 리포 최상위 `javascript_basic/` 소스 HTML + 이미지 폴더

---

## ✅ 완료한 작업

### 1. [높음] 치명적 오류 수정
| 파일 | 수정 내용 |
|------|-----------|
| `javascript_basic/ch4_JavaScript심화/part05_ES6+최신문법.html:407` | `` log(`'); `` → `log('');` — 백틱 미종료 SyntaxError로 죽던 part05 스크립트 전체 복구 (node로 파싱 OK 검증) |
| `docs/.../ch2_CSS/강의노트_2장_CSS.md:39` + `javascript_basic/ch2_CSS/part01...html` 비교표 | **"인라인 > 내부 > 외부" 사실오류 정정.** 내부/외부는 가중치 동일, specificity 같으면 나중 선언이 우선. 표·주석·오개념 정정 |
| `javascript_basic/ch2_CSS/part01_external.css` **(신규 생성)** | 404였던 외부 CSS 실제 생성. 내부 `<style>`의 `.method-external` 제거 → "외부 CSS 적용" 데모가 진짜 외부 파일로 동작 |
| `docs/.../ch6_비동기처리/강의노트:15` | **"비동기는 병렬 실행" 사실오류 정정** → 논블로킹/동시성으로 수정, 싱글스레드와의 관계 명시 |
| `javascript_basic/ch1_HTML/part05_이미지태그.html` (12곳) | 종료된 `via.placeholder.com` → 인라인 `data:image/svg+xml` SVG로 교체 (docs 세트와 동일 방식) |
| `javascript_basic/ch7_jQuery/part02_선택자와DOM조작.html` (3곳) | 동일 placeholder 교체 |

### 2. [중간] 개념 오류·불일치 수정
- **소스 H1 장번호 잔재 정정** (타 커리큘럼 잔재): ch1 26.x→1.x(10개), ch2 27.x→2.x(2개), ch3 28.x→3.x(1개), ch7 32.x→7.x(3개). 남은 잔재 0 확인.
- **ch4 `window.name` 오개념**: `this===window`일 때 `this.name`은 `undefined`가 아니라 빈 문자열 `""`. 강의노트(348/353/369)·소스 part03(242/253) 주석 정정.
- **ch4 `Array.prototype.toString`**: "Object.prototype 상속"은 틀림 → Array.prototype이 오버라이드(내부 join). 강의노트:511·소스 part04:212-218 정정.
- **ch5 이벤트 위임 오라벨**: part02:230 `forEach`로 개별 리스너 등록인데 "(이벤트 위임)" 주석 → "각 버튼에 개별 리스너"로 정정. `keypress`(deprecated)→`keydown`.
- **ch7 jQuery 유사배열**: 강의노트:620 "항상 배열 형태" → 유사배열(array-like) jQuery 객체로 정정.
- **ch8**: HTTP/1.1 Pipelining "동시 전송" 오해 정정(HOL blocking 잔존), HTTP/2 Server Push 폐기 표기, "30-50% 향상" 완화, CORS `Allow-Origin` 중복 설정 → 택일 명시.
- **ch9 리플로우 서술**: "매번 appendChild=리플로우" 뉘앙스 → "라이브 DOM 조작이 레이아웃을 무효화(invalidate), 계산은 지연/배치"로 다듬음(소스 part01 + 강의노트 561/569/695/712). 스타일 측정 실습에 `void offsetWidth` 강제 리플로우 추가해 측정이 유의미해지도록 수정. V8 "인터프리터"→"엔진(JIT)", transform/opacity "리플로우❌리페인트❌"에 "별도 레이어일 때" 각주.
- **ch10 선수지식 과소안내**: 입문/초급 밴드에 실제 필요한 5장 DOM·이벤트, 8.12 LocalStorage 명시.
- **PART3_웹기초.md**: 존재하지 않는 PART2/PART4 링크 → index.md로 교체.
- **ch5/ch7 커버리지 불일치**: 강의노트 종합예제↔실제 소스 선택자 상이(ch5), part03(이벤트/AJAX) 미설명(ch7) → 실습 파일을 가리키는 안내 문구 추가.

### 3. [낮음] 정리
- ch4 part01~05 / ch5 part01~02 `</html>` 뒤 마크다운 코드펜스 ` ``` ` 잔재 제거(7개 파일).
- `00_완성현황.md`: `가이드---` 오타 수정 + 문서/소스 이원화 파일 위치 안내 추가.
- ch10: 랜덤색상 `padStart(6,'0')` 보정, `substr`→`slice`.
- ch2 우선순위 코드 주석 "1~5순위" 방향 혼동 → 명확화.

### 4. 입문자용 이미지 보강 (신규 SVG 6종, 모두 XML 검증·경로 확인 완료)
| 파일 | 삽입 위치 |
|------|-----------|
| `ch6/event-loop.svg` | 6.1 이벤트 루프 (마이크로/매크로태스크 우선순위 포함) |
| `ch4/prototype-chain.svg` | 4.6 프로토타입 체인 |
| `ch4/scope-closure.svg` | 4.3 클로저 |
| `ch2/specificity.svg` | 2.x CSS 우선순위 |
| `ch9/rendering-pipeline.svg` | 9.2 렌더링 엔진 동작 과정 |
| `ch8/cors-preflight.svg` | 8.13 CORS |
- ch6 강의노트 이벤트 루프에 "동기→마이크로태스크→매크로태스크" 실행순서 예제 추가.

---

## ⚠️ 반영하지 않은 지적 (판단 근거)

(아래 4건은 최초엔 보류했으나, 사용자 지침을 받고 **모두 후속 처리 완료** — 다음 절 참고)

---

## 🔄 사용자 지침 반영 (후속 작업)

### 1. docs = md 전용 정책 + ch1 세트 이관
- **docs는 문서(md)+이미지(svg)만** 두기로 확정. docs 안 실습 HTML 제거.
- 확인 결과 **docs의 ch1 HTML이 강의노트와 정합하는 최신본**이고 `part10_종합실습_블로그레이아웃.html`은 docs에만 존재 → 사용자 결정(**docs→source 이관 후 삭제**)에 따라:
  - 구버전 `javascript_basic/ch1_HTML/*.html`(폼요소기초/Input타입상세/… 26.x 계열) **삭제**
  - `docs/.../ch1_HTML/*.html` 10개를 `javascript_basic/ch1_HTML/`로 **이관**(실습 0X·주석 포함 최신본)
  - `docs/.../ch1_HTML/`엔 `강의노트_1장_HTML.md`만 남김
- 최종 확인: **docs 내 .html/.css/.js = 0개**. 이관본은 `없는파일.jpg`(의도된 깨짐 데모) 외 외부 이미지 의존 없음.

### 2. ch5 종합예제 → 안내형 + 강의 진행 팁 삭제
- `강의노트_5장_DOM조작.md`의 Todo 종합 프로젝트 **풀이 코드 전면 제거** → "이런 Todo를 만들어보자" **요구사항·힌트 안내형**으로 교체(정답 미제공, 배운 개념 5.2~5.10 연결).
- **"## 🎯 강의 진행 팁"(교시별 계획) 섹션 전체 삭제.**
- 소스 `part02_이벤트와종합실습_TodoList.html`은 **이벤트 실습(5.7~5.10)이 함께 들어있어 유지**(사용자 결정).

### 3. ch3 심화 내용 — 지금 실력으로 설명/구분
- **구조 분해(part05)**: 객체 지식만으로 이해 가능 → 소스에 "무슨 문법인지" 평이한 설명 추가(정답 제공 아님, 개념 설명).
- **IIFE(part04)**: 기본 형태는 지금 이해 가능 → 설명 추가. 단 "모듈 패턴이 count를 기억하는 원리(클로저)는 4장" 주석으로 명시.
- **고차함수(part04)**: 이미 "이 패턴을 클로저라 함(4장에서 자세히)" 처리되어 있어 유지.
- 두 소스 파일 JS 파싱 정상 확인.

### 4. 사소한 항목 — 가벼운 메모만
- ch8 `part01_HTTP실습...html`: HTTP/2에선 `statusText`가 빈 문자열일 수 있음(정상) 주석 추가.
- ch1 `type=url` 스킴 엄밀성 메모: 옛 소스(part08_Input타입상세)에 넣었으나 해당 파일이 이관 과정에서 삭제되고, **새 정본 폼 파일엔 url input 자체가 없어 자연 해소**.

---

## 다음 세션(2차) 참고
1. **ch2~ch10 md ↔ 소스 일치** 전수 점검(이번엔 ch1·ch5·ch7 위주로 정리). 각 장 강의노트가 실제 소스 파일과 선택자·구성이 맞는지 확인.
2. ch3 커리큘럼 순서(고차함수·구조분해·IIFE를 몇 장에서 정식 도입할지) 노트 반영 여부 결정.
3. 나머지 ASCII 다이어그램(ch3 스택/힙, ch8 요청/응답 구조 등) 이미지화 여부.
