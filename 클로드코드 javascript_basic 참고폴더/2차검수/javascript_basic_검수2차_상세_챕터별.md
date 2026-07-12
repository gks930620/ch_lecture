# javascript_basic 2차 검수 — 챕터별 상세 근거

검수일: 2026-07-03. 요약은 `javascript_basic_검수2차_00_전체요약.md` 참고.
표기: **검증 OK** = 1차 작업이 올바르게 유지됨 / **[등급]** = 신규·잔존 지적.

---

## ch1 HTML
- **검증 OK** H1 장번호 잔재 없음(소스 10개 grep 0건). 코드펜스·TODO 잔재 0.
- **검증 OK** part05 이미지: `via.placeholder.com` 완전 제거, 정상 이미지는 `data:image/svg+xml`로 렌더. `없는파일.jpg`는 의도된 alt 대체 데모(주석 명시). part10 배너도 data URI.
- **검증 OK** 이미지 링크 4종(web-trio-house/html-document-tree/semantic-layout/block-vs-inline .svg) 실재·유효, 노트 참조 경로와 일치.
- **검증 OK** 강의노트 1.2~1.12 ↔ part01~10 순서·내용 대응. 테이블 rowspan/colspan, form name 그룹핑, 시맨틱, data-* 예고(3장) 정합.
- [낮음] `part09:41` 유일한 JS(onclick)는 "3장에서 자세히" 예고 있음 → 문제 아님(참고).
- [낮음] `part07:21-22` "action 비워둔다" 주석이나 실제로 속성 부재 → "생략합니다"로.
- [낮음] `강의노트:149` 상대/절대경로 언급 vs part04에 상대경로 예시 없음.
- [낮음] `강의노트:93` strong "(SEO 영향)" 다소 과장 — 입문 단순화 범위 내, 유지 가능.

## ch2 CSS
- **검증 OK** 우선순위: 노트 39-40 "인라인 > (내부=외부), 같으면 나중 선언, !important 최우선, 내부>외부는 흔한 오해"까지 정확. 소스 part01:220/228 비교표 "내부·외부 동일*" + 232-237 각주. specificity.svg:40도 동일.
- **검증 OK** 외부 CSS 데모: `part01_external.css` 실재·`.method-external` 정의, part01:8 link, 내부 style에 `.method-external` 없음 → 진짜 외부파일로 동작.
- **검증 OK** specificity.svg 실재, 노트:347 경로 일치.
- **검증 OK** part02 선택자/명시도 점수 예시(111/30/2점) 정확.
- 신규 지적 없음.

## ch3 JavaScript기초
- **검증 OK** 고차함수/IIFE/구조분해 입문 설명: part04:350-353·382-383(IIFE, 클로저는 4장 유예), :338(고차함수), part05:376-379(구조분해) 모두 개념 설명형(정답 나열 아님).
- **검증 OK** window.name 오개념 잔재 없음(`let name`은 함수 스코프 한정, 전역 name 미사용).
- **검증 OK** 사실관계: 원시타입 7종, `typeof null==="object"`, `NaN===NaN` false, `==` 강제변환 예시, `??` vs `||`, `for...in`/`for...of`, switch fall-through, `sort()` 문자열 함정 모두 정확.
- [낮음] `강의노트:함수/객체 섹션` 노트가 고차함수·IIFE·구조분해 미언급 → 데모 범위 안내 한 줄씩.
- [낮음] `강의노트:1` H1 앞 선행 공백.
- [낮음] `part02:318,322` falsy `-0` 화면상 `0`과 구분 안 됨 → 라벨 `-0` 별도 표기.

## ch4 JavaScript심화
- **검증 OK** part05 백틱: 5개 part 스크립트 블록 `node --check` 전부 파싱 성공. `log('');` 정정 확인, 다른 미종료 없음.
- **검증 OK** window.name `""`: 노트 350/355/371, 소스 part03:242/253 모두 빈 문자열로 정정.
- **검증 OK** Array.toString 오버라이드: 노트:516, part04:212-213, prototype-chain.svg("오버라이드"/"원본" 구분)까지 정정.
- **검증 OK** 코드펜스 잔재 0(part01~05 마지막 라인 `</html>`).
- **검증 OK** prototype-chain.svg / scope-closure.svg 실재, 노트:156/505 경로 일치.
- **검증 OK** 스코프/호이스팅/TDZ, 클로저(var/let 반복문, 모듈 패턴), 프로토타입·클래스(#private, static, super), ES6 문법 정확.
- [낮음] `part03:440` 체이닝 주석 `(10 + (-5) * 2 = 10)` → 순차계산이면 `((10-5)×2=10)`.
- [낮음] `강의노트:293` Node.js 최상위 this는 `global` 아니라 `module.exports`.
- [낮음] 비-strict 전제(302/371, part03:198) — ES 모듈/strict에선 TypeError, 캐비엇 권장(오류 아님).

## ch5 DOM 조작
- **검증 OK** 이벤트 위임 오라벨: part02:230-231 "각 버튼에 개별 리스너"로 수정, 실제 위임은 250-251 `todoList.addEventListener`+`closest`에 정확히 적용.
- **검증 OK** keypress→keydown: part02:223-224 주석+사용. 노트:911도 deprecated 표기.
- **검증 OK** 종합예제 요구사항·힌트형 전환(노트:935-958), 교시별 팁 섹션 없음(삭제 확인).
- **검증 OK** 노트↔소스 선택자 정합(getElementById/ClassName/TagName/querySelector(All), HTMLCollection forEach 미지원 ↔ `Array.from`).
- 신규 지적 없음. (단 ch6에는 동일 개선 미적용 — ch6 항목 참고.)

## ch6 비동기처리
- **검증 OK** 비동기=병렬 정정: 노트:14-16 싱글스레드+"병렬 아님(동시성)" 명시.
- **검증 OK** 이벤트 루프: 노트:92-115 "스택 빔→마이크로태스크 전부→매크로태스크 하나", 예제 출력순서(동기→Promise→setTimeout0) 정확. event-loop.svg 실재(4013B)·본문 일치.
- **[중간]** `강의노트:440-458` 하단 소제목 `순차 vs 병렬`, 주석 `✅ 병렬 실행` — 앞의 "병렬 아님"과 충돌. `동시 진행(concurrent)`로 통일 또는 보강. (소스 part01:114/191/278 UI 라벨도 함께.)
- **[낮음]** `part01:459` `</html>` 뒤 스트레이 코드펜스 ` ``` ` → 삭제.
- **[낮음]** `강의노트:798-864` 교시별 팁 + 정답코드 종합프로젝트 잔존 → ch5식으로 정리 검토.

## ch7 jQuery
- **검증 OK** H1 장번호: part01:122(7.1-7.4)/part02:114(7.5-7.9)/part03:135(7.10-7.13). 32.x 잔재 없음.
- **검증 OK** 유사배열: 노트:620 "유사배열(array-like) jQuery 객체(진짜 Array 아님)"로 정정.
- **검증 OK** part03 안내: 노트:781-788 "이벤트/효과/AJAX는 part03에서" 추가.
- **[높음]** `part02:516, 528` — placeholder→인라인 SVG 교체분이 `'src': 'data:...xmlns='http://...''` 작은따옴표 중첩으로 `SyntaxError: Unexpected identifier 'http'`. `$(function(){...})` 전체 로드 실패 → **이 페이지 모든 데모 버튼 동작 불가**. `node`로 재현 확인. 바깥 따옴표를 큰따옴표로 교체 필요. (line 414 정적 img는 HTML 속성이라 정상.)
- **[중간]** `강의노트:444` `$('#agree').val() // checkbox 값` → `.val()`은 체크 여부 무관 `value`("on")만 반환. `.prop('checked')`/`.is(':checked')` 주석 추가.

## ch8 HTTP통신
- **검증 OK** Pipelining: 노트:79/84-85 "응답 안 기다리고 연속 전송, 응답은 순서대로, '동시' 아님, HOL Blocking 잔존" 정정.
- **검증 OK** HTTP/2 Server Push: 노트:93 "사실상 폐기(Chrome 2022 제거), 2026 기준 권장 안 함".
- **검증 OK** "30-50% 향상" → 노트:96 수치 제거·완화.
- **검증 OK** CORS Allow-Origin 중복: 노트:843-846 "둘 중 하나만, 두 번 시 덮어씀" + (A)/(B) 택일.
- **검증 OK** cors-preflight.svg 실재(2943B), 노트:801 경로 정상.
- **검증 OK** part01:322 statusText HTTP/2 빈 문자열 주석 — 정확(좋은 보완).
- [낮음] `강의노트:176` "GET Body 불가" → "URL로 전달, 본문 안 씀"이 더 정확.

## ch9 웹브라우저 동작원리
- **검증 OK** 리플로우 서술: 노트:564/574-576·692-720, 소스 part01:172/182 "라이브 DOM 조작이 레이아웃 무효화, 계산은 지연·배치"로 정정.
- **검증 OK** 강제 리플로우: part01:245/264/282 세 함수 모두 `void target.offsetWidth;` 후 `performance.now()` → 측정 유의미(toFixed(4)).
- **검증 OK** V8 "엔진(JIT)": 노트:30, "인터프리터" 오표현 0건.
- **검증 OK** transform/opacity "별도 레이어일 때" 각주: 노트:327-330 `❌*`+각주, part01:121 팁 언급, rendering-pipeline.svg:46 각주.
- **검증 OK** rendering-pipeline.svg 실재(3.7KB), 노트:52 경로 일치.
- [낮음] `part01:74-75` 소스 비교표에 "별도 레이어일 때만" 각주 미반영(노트엔 있음) → 소스 표에도 각주 추가.

## ch10 프로젝트 가이드 / index
- **검증 OK** 선수지식: 노트:84/86(입문 밴드 5장 DOM·5.7 이벤트), 195/197·224(초급 밴드 8.12 LocalStorage) 명시.
- **검증 OK** 랜덤색상 `padStart(6,'0')`(노트:184, 자리수 부족 설명 포함), `substr→slice`(노트:978).
- **검증 OK** `javascript_basic/ch10_.../` 소스 디렉터리 비어있음 = 가이드 문서만 존재, 대조 대상 없음(정상).
- **검증 OK** index.md 10개 링크 전부 실재. PART2/PART4 깨진 링크 0건. ch7 part03 참조는 소스 실재로 유효.
- 신규 지적 없음.
