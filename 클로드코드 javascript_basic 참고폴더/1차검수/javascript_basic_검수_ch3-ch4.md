# javascript_basic 검수사항 — ch3 JavaScript기초 / ch4 JavaScript심화

검수일: 2026-07-03
검수 범위: `docs/javascript_basic/ch3_JavaScript기초/강의노트_3장_JavaScript기초.md`, `ch4_JavaScript심화/강의노트_4장_JavaScript심화.md` + 각 소스 HTML(ch3 part01~05, ch4 part01~05)

전제: 예제 코드를 머릿속으로 실행해 결과값을 검증함. **확실한 것만** 기재.

---

## ch3_JavaScript기초

총평: 기술적 정확성은 전반적으로 매우 양호. `==`/`===`, truthy/falsy, `typeof null`, 호이스팅, `const` 참조/값, 배열·객체·JSON 설명 및 예상 출력값을 코드로 따라가 본 결과 **명백한 사실 오류나 콘솔 에러를 유발하는 코드는 없음.** 주 문제는 제목 장번호 잔재와 소스가 강의노트보다 앞서 나가는 문서↔소스 불일치.

### 높음
- 해당 없음 (정밀 검증 결과 확실한 사실 오류/버그 없음. 오탐 방지를 위해 비워 둠)

### 중간
- `javascript_basic/ch3_JavaScript기초/part01_JavaScript소개와변수.html:91` — 페이지 제목이 `🚀 28.1-28.2: …`로, 다른 파트(`3장 Part 2`~`Part 5`)와 달리 옛 챕터 번호(28.x) 잔재. 학생에게 노출됨.
- `javascript_basic/ch3_JavaScript기초/part04_함수.html:116-130, 346-383` ↔ `강의노트_3장_JavaScript기초.md:768-849` — 소스 part04는 **고차 함수(함수를 반환하는 함수)**와 **IIFE/모듈 패턴**을 별도 섹션으로 다루지만 강의노트 함수 파트(3.9-3.10)에는 없음. 특히 고차함수 예제(`multiplyBy`)는 사실상 클로저인데, 클로저는 노트에서 4장 주제로 예고(`:1136`)되어 입문자 기준 다소 이르고 노트-소스 커버리지가 어긋남.
- `javascript_basic/ch3_JavaScript기초/part05_배열과객체와JSON.html:376-381` — **구조 분해 할당 + rest**(`const { name, age, ...rest } = user;`)를 사용/설명하지만 강의노트에는 구조분해 개념이 없음. 소스가 노트보다 앞서 나감.

### 낮음
- `javascript_basic/ch3_JavaScript기초/part02_자료형과연산자.html:337-338` 및 `강의노트_3장_JavaScript기초.md:513` — "Falsy 7가지만 외우면 나머지는 모두 Truthy"라 단정하나, BigInt `0n`(그리고 `document.all`)도 falsy. 강의에서 BigInt를 소개(`:536`)하므로 `0n`을 truthy로 오해할 여지.
- `강의노트_3장_JavaScript기초.md` 전체 — 대응 실습 소스(part01~05)를 한 번도 링크/참조하지 않음. 강사가 노트만 보면 대응 데모 존재를 알기 어려움.
- `강의노트_3장_JavaScript기초.md` 전체 — 이미지/다이어그램 전무. 스코프·호이스팅·참조 vs 값(스택/힙) 등은 글·표만으로 어려운 개념이라 시각 자료 보강 여지(사실 오류 아님).
- `javascript_basic/ch3_JavaScript기초/part03_조건문과반복문.html:424` — `listPrimes()` 헤더가 `=== 1~100 사이의 소수 ===`인데 루프는 2부터(`:427`). 결과는 정확하나 라벨 표기 부정확.

정확성 확인(이상 없음): `5=="5"`, `0==false`, `""==false`, `null==undefined`, `[]==false`, `"0"==false`, `typeof null→"object"`, `NaN===NaN→false`, `const` 객체 mutation 허용·재할당 금지, splice 순차, `fruits.find(f=>f.length>2)→"바나나"`, do-while 최소 1회, var setTimeout `3,3,3`, 소수 판별 모두 실제 JS와 일치.

---

## ch4_JavaScript심화

총평: 개념 설명·예제는 입문자 수준에서 잘 정리됨. 다만 **part05 스크립트 전체를 죽이는 문법 오류가 하나 있어 5장 실습 페이지의 모든 버튼이 동작하지 않음(최우선).** 또 `window.name` 출력값, `Array.prototype.toString` 상속 출처 등 이 챕터 특유의 오개념 몇 군데. 스코프 체인·클로저·this·프로토타입 체인 다이어그램이 전무.

### 높음
- `javascript_basic/ch4_JavaScript심화/part05_ES6+최신문법.html:407` — `` log(`');`` 는 `log('');` 오타로, 백틱이 열린 채 다음 줄까지 이어져 **템플릿 리터럴 파싱 오류(SyntaxError)**. 이 `<script>` 블록 전체가 파싱 실패하므로 **part05의 모든 버튼(구조분해·스프레드·Map·Set·최신연산자 등)이 전혀 동작하지 않음.** `log('');`로 수정 필요.

### 중간
- `javascript_basic/ch4_JavaScript심화/part03_this바인딩과call_apply_bind.html:253`(및 `:242`) — 화살표/내부 일반 함수에서 `this===window`는 맞으나 주석 `// undefined (window.name)`는 부정확. 브라우저 `window.name`은 항상 문자열이며 기본값이 **빈 문자열 `""`** 라 실제 출력은 `undefined`가 아니라 공백. `강의노트_4장_JavaScript심화.md:348-353`(`person.arrowFunc(); // undefined`), `:369`(`greet(); // "안녕, undefined"`)도 동일하게 실제로는 `""` → `"안녕, "`로 출력.
- `강의노트_4장_JavaScript심화.md:511` — `arr.toString()`의 결과 `"1,2,3"`은 맞지만 `(Object.prototype에서 상속)` 설명이 틀림. 배열 `toString`은 **`Array.prototype.toString`이 오버라이드**한 메소드(내부적으로 `join` 호출)로 `Array.prototype`에서 옴. 소스 `part04_프로토타입과클래스.html:214-215`도 `toString`을 "Object.prototype 상속" 항목 아래 배치해 같은 오개념(같은 항목의 `hasOwnProperty`는 Object.prototype 상속이 맞음).
- `강의노트_4장_JavaScript심화.md` 전체 — 스코프 체인·클로저(렉시컬 환경)·this 바인딩·프로토타입 체인 등 그림이 특히 중요한 주제인데 **이미지가 하나도 없음.** 입문자 대상이므로 프로토타입 체인/스코프 체인 다이어그램 보강 권장.

### 낮음
- `part01~05` 모든 HTML 파일 끝(`part01:293`, `part02:369`, `part03:445`, `part04:500`, `part05:498`) — `</html>` 뒤에 마크다운 코드펜스 ` ``` `가 남아 있음. 렌더링엔 무해하나 마크다운 추출 잔재이므로 제거 권장.
- `강의노트_4장_JavaScript심화.md:291` — `console.log(this); // window (브라우저) / global (Node.js)`에서 Node.js **모듈 스코프 `this`는 `global`이 아니라 `module.exports`(빈 객체 `{}`)**(REPL에서만 global). 입문 단순화지만 엄밀히 부정확.
- `part03_this바인딩과call_apply_bind.html:440` — 결과 `10`은 정확하나 주석 `(10 + (-5) * 2 = 10)`이 오해 유발. 실제는 체이닝 순차 `((0+10)-5)*2 = 10`이며, 적힌 수식은 연산자 우선순위로는 `10 + (-10) = 0`이 되어 혼동 가능.
- `강의노트_4장_JavaScript심화.md:502-512` — 프로토타입 체인을 ```javascript 블록 안에 화살표 다이어그램으로 넣음(실행 불가 의사코드). 일반 텍스트/그림으로 구분 처리 권장.
