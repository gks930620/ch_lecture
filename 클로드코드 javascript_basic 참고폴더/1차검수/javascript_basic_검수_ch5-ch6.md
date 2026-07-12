# javascript_basic 검수사항 — ch5 DOM조작 / ch6 비동기처리

검수일: 2026-07-03
검수 범위: `docs/javascript_basic/ch5_DOM조작/강의노트_5장_DOM조작.md`, `ch6_비동기처리/강의노트_6장_비동기처리.md` + 각 소스 HTML(ch5 part01~02, ch6 part01)

---

## ch5_DOM조작

총평: 강의노트의 DOM 선택·조작·이벤트 이론(HTMLCollection Live vs NodeList Static, target vs currentTarget, 버블링/캡처링, mouseenter vs mouseover)은 모두 정확하고 입문자 수준에 적절. 기능(추가/삭제/이벤트 위임/LocalStorage)도 정상 동작. 다만 소스 주석의 개념 오라벨링, 코드펜스 잔재, 강의노트 종합예제↔실제 소스 구조 불일치가 확인됨.

### 높음
- 해당 없음 (실행을 막는 치명적 오류 없음)

### 중간
- `javascript_basic/ch5_DOM조작/part02_이벤트와종합실습_TodoList.html:230` — 필터 버튼 처리에 `// 필터 버튼 (이벤트 위임)` 주석이 있으나, 실제 코드(231~241줄)는 `filterBtns.forEach(btn => btn.addEventListener(...))`로 **각 버튼에 개별 리스너를 등록**(이벤트 위임이 아니라 반대). 5.10에서 가르치는 핵심 개념을 소스 주석이 잘못 설명 → 입문자 혼동.
- `javascript_basic/ch5_DOM조작/part01_DOM선택과조작.html:345` 및 `part02_...html:376` — `</html>` 뒤에 마크다운 코드펜스 ` ``` `가 그대로 남아 페이지 하단에 "```" 문자가 노출됨.
- `docs/javascript_basic/ch5_DOM조작/강의노트_5장_DOM조작.md:961-996` — 종합 프로젝트 코드가 `#todo-form`, `#todo-input`, `#todo-list`(form submit 기반)인데, 실제 소스 part02는 `#todoInput`, `#addBtn`, `#todoList`(form 없음, keypress 기반). 선택자·구조·기능(필터/LocalStorage 유무)이 모두 달라 강의노트 코드를 실제 파일과 대조하면 동작하지 않고 혼동.
- `docs/javascript_basic/ch5_DOM조작/강의노트_5장_DOM조작.md:911` vs `part02_...html:224` — 강의노트는 `keypress`를 "Deprecated"로 명시하는데, 정작 실제 Todo 소스는 Enter 처리를 `keypress`로 구현. 노트 권고와 소스가 상충(`keydown` 권장).

### 낮음
- `part01_DOM선택과조작.html:281` — `document.createDocumentFragment()`를 "성능 최적화" 주석과 함께 쓰나 강의노트 5.3 어디에서도 DocumentFragment를 설명하지 않음(예고 없는 개념).
- `part02_이벤트와종합실습_TodoList.html:252` — 이벤트 위임에서 `e.target.closest('.todo-item')`를 쓰는데, 강의노트 위임 예제는 `parentElement`만 소개하고 `closest()`는 안 다룸(설명 없는 메소드).
- `강의노트_5장_DOM조작.md:990` 및 `part02_...html:330` — 5.6에서 "사용자 입력은 textContent로 표시(안전)"라며 XSS 위험을 강조한 직후, 종합 프로젝트·실제 소스 모두 사용자 입력 `${todo.text}`를 `innerHTML` 템플릿으로 렌더링. 방금 가르친 안전 원칙과 상충 → 최소한 주석으로 짚기 권장.
- `강의노트_5장_DOM조작.md:928` — `window.addEventListener('DOMContentLoaded', ...)` 안내. 버블링되어 동작은 하지만 관례상 `document`에 등록이 표준적이라 입문자 오해 소지.

참고: 강의노트에 이미지 링크(`![]()`) 자체가 없어 깨진 링크 검증 대상 없음.

---

## ch6_비동기처리

총평: 코드 실습(HTML)은 JSONPlaceholder 기반 GET/POST/PUT/DELETE 및 순차·병렬 예제가 모두 정상 동작하며, `fetch`의 HTTP 에러 미거부 설명 등 핵심 개념은 대체로 정확. 다만 **도입부에서 비동기를 "병렬 실행"으로 정의한 부분은 싱글 스레드 설명과 모순되는 사실 오류**이며, 이벤트 루프 설명에 마이크로태스크 큐가 빠져 Promise 챕터임에도 실행 순서 개념이 약함. 깨진 이미지 링크 없음(이미지 참조 자체가 없음).

### 높음
- `docs/javascript_basic/ch6_비동기처리/강의노트_6장_비동기처리.md:15` — "동기는 순차 실행, **비동기는 병렬 실행**"은 사실 오류. 바로 위 14번 줄 "JavaScript는 싱글 스레드"와 모순. 비동기는 병렬(parallel)이 아니라 논블로킹(non-blocking)/동시성(concurrency). `Promise.all`도 동시 대기일 뿐 코드가 여러 스레드에서 도는 것이 아님. 입문자에게 가장 흔한 핵심 오개념을 정의로 각인시킬 위험 → 수정 필요.

### 중간
- `docs/javascript_basic/ch6_비동기처리/강의노트_6장_비동기처리.md:75-88` — 이벤트 루프가 ASCII 텍스트로만 설명되고(입문자 기준 그림 필수 영역), **마이크로태스크 큐 누락.** 다이어그램에 `Task Queue`만 있어 Promise `.then` 콜백이 `setTimeout`(매크로태스크)보다 먼저 실행된다는 우선순위를 전달하지 못함. 챕터 주제가 Promise인데 정작 Promise 콜백의 큐 처리 설명·실행 순서 예제("동기 → 마이크로태스크 → 매크로태스크")가 부재.

### 낮음
- `docs/javascript_basic/ch6_비동기처리/강의노트_6장_비동기처리.md:194,224-227` — `reject('실패!')` 등 Promise를 문자열로 거부. 표준/실무는 `reject(new Error('실패!'))` 권장(스택 추적·`error.message` 일관성). 뒤의 `catch(error)`/`error.message` 예제와 톤이 어긋남.
- `javascript_basic/ch6_비동기처리/part01_Promise와async_await_종합실습.html:424-427` — 주석은 "첫 번째 게시글의 댓글"이라며 순차 로딩을 강조하지만, 실제로는 받은 `posts[0].id`가 아니라 `/posts/1/comments`로 **id를 하드코딩.** userId=1의 게시글이 마침 id 1부터라 결과가 우연히 일치할 뿐, "이전 결과를 다음 요청에 넘긴다"는 순차 의존 실습 취지와 어긋남.
- `docs/javascript_basic/ch6_비동기처리/강의노트_6장_비동기처리.md:515-516` — Top-level await 예제에서 `const data = await fetch(...)` 후 `console.log(data)`는 파싱된 데이터가 아니라 `Response` 객체를 출력(변수명 `data`가 오해 유발). `.json()` 한 단계가 빠짐.
