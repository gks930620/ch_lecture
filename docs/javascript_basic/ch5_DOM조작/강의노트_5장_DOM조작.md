# 5장: DOM 조작

## 📋 강의 개요
- **소요 시간**: 약 8-10시간
- **난이도**: ⭐⭐ 초급~중급
- **선수 지식**: HTML, CSS, JavaScript 기초
- **학습 목표**: JavaScript로 HTML 요소를 동적으로 제어하고 이벤트를 처리할 수 있다.

---

## 📚 5.1 DOM이란?

### 강의 포인트
- DOM = Document Object Model (문서 객체 모델)
- HTML을 JavaScript로 조작할 수 있게 해주는 인터페이스
- 트리 구조로 표현됨

### DOM이란?
```
DOM은 HTML 문서를 프로그래밍적으로 조작할 수 있게 해주는 API입니다.

HTML 문서 → 브라우저 → DOM 트리 → JavaScript로 조작 가능
```

### DOM 트리 구조
```html
<!DOCTYPE html>
<html>
  <head>
    <title>제목</title>
  </head>
  <body>
    <h1>안녕하세요</h1>
    <p>문단입니다.</p>
  </body>
</html>
```

```
Document
  └─ html
      ├─ head
      │   └─ title
      │       └─ "제목"
      └─ body
          ├─ h1
          │   └─ "안녕하세요"
          └─ p
              └─ "문단입니다."
```

### DOM의 종류
- **Document**: 문서 전체
- **Element**: HTML 태그 (`<div>`, `<p>` 등)
- **Attribute**: 요소의 속성 (id, class 등)
- **Text**: 텍스트 내용

---

## 📚 5.2 요소 선택 (Selecting Elements)

### 강의 포인트
- querySelector/querySelectorAll이 가장 범용적
- getElementById는 빠르지만 제한적
- getElementsBy~ 시리즈는 Live Collection 반환

### 요소 선택 메소드

#### 1. getElementById() - ID로 선택
```javascript
// <div id="header">Header</div>
const header = document.getElementById('header');
console.log(header);  // <div id="header">...</div>

// ⚠️ ID는 페이지에 하나만 존재해야 함
// ⚠️ #을 붙이지 않음!
```

**특징**:
- 가장 빠름
- 하나만 반환 (배열 아님)
- 없으면 null 반환

#### 2. getElementsByClassName() - Class로 선택
```javascript
// <div class="box">Box 1</div>
// <div class="box">Box 2</div>
const boxes = document.getElementsByClassName('box');
console.log(boxes.length);  // 2

// HTMLCollection 반환 (배열 같지만 배열 아님!)
console.log(boxes[0]);  // 첫 번째 요소
```

**특징**:
- HTMLCollection 반환 (Live Collection)
- 배열처럼 보이지만 배열 아님
- forEach 사용 불가 (변환 필요)

#### 3. getElementsByTagName() - 태그로 선택
```javascript
// 모든 p 태그 선택
const paragraphs = document.getElementsByTagName('p');
console.log(paragraphs.length);

// 모든 요소 선택
const allElements = document.getElementsByTagName('*');
```

#### 4. querySelector() - CSS 선택자로 선택 ⭐ 권장
```javascript
// ID
const header = document.querySelector('#header');

// Class
const box = document.querySelector('.box');  // 첫 번째만

// 태그
const firstP = document.querySelector('p');

// 복잡한 선택자
const link = document.querySelector('div.container > a.external[href^="https"]');
```

**특징**:
- **CSS 선택자 그대로 사용 가능**
- 첫 번째 요소만 반환
- 없으면 null 반환
- 가장 유연하고 강력

#### 5. querySelectorAll() - 모든 요소 선택 ⭐ 권장
```javascript
// 모든 .box 요소
const boxes = document.querySelectorAll('.box');

// NodeList 반환 (forEach 사용 가능!)
boxes.forEach((box, index) => {
    console.log(`Box ${index + 1}:`, box.textContent);
});

// 배열로 변환
const boxArray = Array.from(boxes);
const boxArray2 = [...boxes];  // 스프레드 연산자
```

**특징**:
- CSS 선택자 사용
- NodeList 반환 (Static)
- forEach 사용 가능
- 없으면 빈 NodeList 반환

### HTMLCollection vs NodeList 비교

```javascript
// HTMLCollection (Live) - getElementsBy~
const liveBoxes = document.getElementsByClassName('box');
console.log(liveBoxes.length);  // 2

// 요소 추가하면 자동으로 반영됨!
const newBox = document.createElement('div');
newBox.className = 'box';
document.body.appendChild(newBox);
console.log(liveBoxes.length);  // 3 (자동 업데이트!)

// NodeList (Static) - querySelectorAll
const staticBoxes = document.querySelectorAll('.box');
console.log(staticBoxes.length);  // 2

// 요소 추가해도 반영 안 됨
const newBox2 = document.createElement('div');
newBox2.className = 'box';
document.body.appendChild(newBox2);
console.log(staticBoxes.length);  // 2 (그대로)
```

### 선택자 선택 가이드

```javascript
// ✅ 권장: querySelector/querySelectorAll
const element = document.querySelector('#myId');
const elements = document.querySelectorAll('.myClass');

// 👌 OK: getElementById (성능이 중요한 경우)
const element2 = document.getElementById('myId');

// ⚠️ 주의: getElementsByClassName (Live Collection)
const elements2 = document.getElementsByClassName('myClass');
```

---

## 📚 5.3 요소 생성/추가/삭제

### 강의 포인트
- createElement로 요소 생성
- appendChild, insertBefore로 추가
- remove, removeChild로 삭제
- 실제 DOM에 추가해야 화면에 보임

### 요소 생성
```javascript
// 1. 요소 생성
const div = document.createElement('div');
const p = document.createElement('p');
const a = document.createElement('a');

// 2. 내용/속성 설정
div.textContent = '새로운 div';
div.className = 'box';
div.id = 'newBox';

// 3. DOM에 추가 (이제 화면에 보임!)
document.body.appendChild(div);
```

### 요소 추가 메소드

#### appendChild() - 마지막 자식으로 추가
```javascript
const container = document.querySelector('#container');
const newP = document.createElement('p');
newP.textContent = '새 문단';

container.appendChild(newP);  // container의 마지막에 추가
```

#### insertBefore() - 특정 위치에 추가
```javascript
const container = document.querySelector('#container');
const newP = document.createElement('p');
newP.textContent = '새 문단';
const referenceNode = document.querySelector('#reference');

container.insertBefore(newP, referenceNode);  // reference 앞에 추가
```

#### append() - 여러 개 추가 (최신)
```javascript
const container = document.querySelector('#container');

// 여러 요소 한번에 추가
container.append(
    document.createElement('p'),
    document.createElement('div'),
    '텍스트도 추가 가능'
);
```

#### prepend() - 첫 번째 자식으로 추가
```javascript
const container = document.querySelector('#container');
const newP = document.createElement('p');

container.prepend(newP);  // 맨 앞에 추가
```

#### insertAdjacentHTML() - HTML 문자열로 추가
```javascript
const container = document.querySelector('#container');

// beforebegin: 요소 앞
// afterbegin: 첫 번째 자식
// beforeend: 마지막 자식
// afterend: 요소 뒤

container.insertAdjacentHTML('beforeend', '<p>새 문단</p>');
```

### 요소 삭제

#### remove() - 자신을 삭제 (최신)
```javascript
const element = document.querySelector('#myElement');
element.remove();  // 요소 삭제
```

#### removeChild() - 자식 삭제
```javascript
const parent = document.querySelector('#parent');
const child = document.querySelector('#child');

parent.removeChild(child);  // 자식 삭제
```

#### 모든 자식 삭제
```javascript
const container = document.querySelector('#container');

// 방법 1: innerHTML
container.innerHTML = '';

// 방법 2: 반복문
while (container.firstChild) {
    container.removeChild(container.firstChild);
}

// 방법 3: replaceChildren (최신)
container.replaceChildren();
```

### 요소 복제
```javascript
const original = document.querySelector('#original');

// 얕은 복제 (자식 포함 안 함)
const clone1 = original.cloneNode(false);

// 깊은 복제 (자식 포함)
const clone2 = original.cloneNode(true);

document.body.appendChild(clone2);
```

---

## 📚 5.4 속성 조작

### 강의 포인트
- getAttribute/setAttribute로 속성 제어
- 직접 속성 접근도 가능 (element.id)
- data-* 속성은 dataset으로 접근

### 속성 가져오기/설정하기

#### getAttribute() / setAttribute()
```javascript
const link = document.querySelector('a');

// 속성 가져오기
const href = link.getAttribute('href');
const target = link.getAttribute('target');

// 속성 설정하기
link.setAttribute('href', 'https://google.com');
link.setAttribute('target', '_blank');
link.setAttribute('rel', 'noopener noreferrer');
```

#### 직접 접근 (권장)
```javascript
const link = document.querySelector('a');

// 가져오기
console.log(link.href);
console.log(link.id);
console.log(link.className);  // class는 className

// 설정하기
link.href = 'https://google.com';
link.id = 'myLink';
link.className = 'btn btn-primary';
```

#### hasAttribute() / removeAttribute()
```javascript
const element = document.querySelector('#myElement');

// 속성 존재 확인
if (element.hasAttribute('data-id')) {
    console.log('data-id 속성이 있습니다');
}

// 속성 제거
element.removeAttribute('data-id');
```

### 클래스 조작 (classList)

#### classList.add() - 클래스 추가
```javascript
const element = document.querySelector('#myElement');

element.classList.add('active');
element.classList.add('box', 'highlighted');  // 여러 개 추가
```

#### classList.remove() - 클래스 제거
```javascript
element.classList.remove('active');
element.classList.remove('box', 'highlighted');
```

#### classList.toggle() - 클래스 토글
```javascript
// 있으면 제거, 없으면 추가
element.classList.toggle('active');

// 조건부 토글
element.classList.toggle('active', true);   // 무조건 추가
element.classList.toggle('active', false);  // 무조건 제거
```

#### classList.contains() - 클래스 확인
```javascript
if (element.classList.contains('active')) {
    console.log('active 클래스가 있습니다');
}
```

#### classList.replace() - 클래스 교체
```javascript
element.classList.replace('old-class', 'new-class');
```

### data-* 속성 (dataset)
```html
<div id="user" 
     data-user-id="123" 
     data-user-name="홍길동"
     data-role="admin">
</div>
```

```javascript
const user = document.querySelector('#user');

// 가져오기 (카멜케이스로 변환됨!)
console.log(user.dataset.userId);    // "123"
console.log(user.dataset.userName);  // "홍길동"
console.log(user.dataset.role);      // "admin"

// 설정하기
user.dataset.email = 'hong@example.com';

// 삭제하기
delete user.dataset.role;
```

---

## 📚 5.5 스타일 조작

### 강의 포인트
- style 속성으로 인라인 스타일 제어
- classList로 CSS 클래스 제어 (권장)
- getComputedStyle로 계산된 스타일 가져오기

### style 속성
```javascript
const element = document.querySelector('#myElement');

// 단일 스타일
element.style.color = 'red';
element.style.fontSize = '20px';  // CSS는 font-size, JS는 fontSize
element.style.backgroundColor = 'yellow';

// 여러 스타일
element.style.cssText = 'color: red; font-size: 20px; background: yellow;';

// 제거
element.style.color = '';
```

**CSS 속성명 변환 규칙**:
```javascript
// CSS        →  JavaScript
'font-size'    →  fontSize
'background-color' → backgroundColor
'margin-top'   →  marginTop
'z-index'      →  zIndex
```

### 계산된 스타일 가져오기
```javascript
const element = document.querySelector('#myElement');

// 현재 적용된 모든 스타일 (CSS 포함)
const styles = window.getComputedStyle(element);

console.log(styles.color);
console.log(styles.fontSize);
console.log(styles.display);
```

### 스타일 제어 권장 방법

```css
/* CSS 파일 */
.hidden {
    display: none;
}

.active {
    color: red;
    font-weight: bold;
}

.highlight {
    background: yellow;
}
```

```javascript
// ✅ 권장: classList 사용
element.classList.add('active');
element.classList.toggle('hidden');

// ⚠️ 비권장: 직접 style 조작
element.style.display = 'none';
element.style.color = 'red';
```

**이유**:
- CSS와 JavaScript 분리 (관심사 분리)
- 재사용 가능
- 유지보수 용이
- 성능 향상

---

## 📚 5.6 innerHTML vs textContent vs innerText

### 강의 포인트
- 각각의 차이를 명확히 이해
- 보안 위험 (XSS) 주의
- 성능 차이

### innerHTML - HTML 포함
```javascript
const div = document.querySelector('#myDiv');

// 가져오기 (HTML 태그 포함)
console.log(div.innerHTML);
// "<p>안녕하세요 <strong>홍길동</strong>입니다.</p>"

// 설정하기 (HTML로 해석됨)
div.innerHTML = '<p>새로운 <strong>내용</strong></p>';

// ⚠️ 보안 위험! (XSS 공격 가능)
const userInput = '<img src=x onerror="alert(\'해킹!\')">';
div.innerHTML = userInput;  // 위험!
```

**장점**: HTML 태그 사용 가능
**단점**: XSS 공격 위험, 느림

### textContent - 순수 텍스트
```javascript
const div = document.querySelector('#myDiv');

// 가져오기 (텍스트만, 모든 공백 포함)
console.log(div.textContent);
// "안녕하세요 홍길동입니다."

// 설정하기 (HTML 태그가 문자열로 취급됨)
div.textContent = '<p>새로운 내용</p>';
// 화면에 "<p>새로운 내용</p>" 그대로 표시

// ✅ XSS 안전
const userInput = '<script>alert("해킹")</script>';
div.textContent = userInput;  // 안전! (텍스트로 표시됨)
```

**장점**: 안전, 빠름
**단점**: HTML 사용 불가

### innerText - 보이는 텍스트만
```javascript
const div = document.querySelector('#myDiv');

// 가져오기 (화면에 보이는 텍스트만)
console.log(div.innerText);
// "안녕하세요 홍길동입니다." (공백 정리됨)

// 설정하기
div.innerText = '새로운 내용';
```

**차이점**:
- CSS에 영향을 받음 (display:none 요소는 제외)
- 성능이 느림 (레이아웃 계산 필요)

### 비교표

| 속성 | HTML 태그 | 공백/줄바꿈 | CSS 영향 | 성능 | 보안 |
|------|-----------|-------------|----------|------|------|
| **innerHTML** | ✅ 해석 | ✅ 유지 | ❌ 무관 | 느림 | ⚠️ 위험 |
| **textContent** | ❌ 문자열 | ✅ 유지 | ❌ 무관 | 빠름 | ✅ 안전 |
| **innerText** | ❌ 문자열 | ❌ 정리 | ✅ 영향 | 매우 느림 | ✅ 안전 |

### 사용 가이드
```javascript
// ✅ 사용자 입력 표시 (안전)
element.textContent = userInput;

// ✅ HTML 생성 (신뢰할 수 있는 소스)
element.innerHTML = '<p>안전한 내용</p>';

// ✅ 성능 중요 (빠른 텍스트 조작)
element.textContent = '내용';

// ❌ 피하기 (느림)
element.innerText = '내용';
```

---

## 📚 5.7 이벤트 리스너 (addEventListener)

### 강의 포인트
- addEventListener가 표준 방법
- 여러 이벤트 핸들러 등록 가능
- removeEventListener로 제거

### 기본 사용법
```javascript
const button = document.querySelector('#myButton');

// 이벤트 리스너 등록
button.addEventListener('click', function() {
    console.log('버튼 클릭!');
});

// 화살표 함수
button.addEventListener('click', () => {
    console.log('버튼 클릭!');
});

// 함수 분리 (권장)
function handleClick() {
    console.log('버튼 클릭!');
}
button.addEventListener('click', handleClick);
```

### 여러 이벤트 핸들러
```javascript
const button = document.querySelector('#myButton');

// 여러 개 등록 가능!
button.addEventListener('click', function() {
    console.log('첫 번째 핸들러');
});

button.addEventListener('click', function() {
    console.log('두 번째 핸들러');
});

// 둘 다 실행됨!
```

### 이벤트 제거
```javascript
const button = document.querySelector('#myButton');

function handleClick() {
    console.log('버튼 클릭!');
}

// 등록
button.addEventListener('click', handleClick);

// 제거 (같은 함수 참조를 전달해야 함!)
button.removeEventListener('click', handleClick);

// ❌ 안 됨 (다른 함수)
button.addEventListener('click', function() {
    console.log('클릭');
});
button.removeEventListener('click', function() {
    console.log('클릭');
});
```

### once 옵션
```javascript
// 한 번만 실행
button.addEventListener('click', function() {
    console.log('한 번만 실행!');
}, { once: true });
```

---

## 📚 5.8 이벤트 객체 (event)

### 강의 포인트
- 이벤트 핸들러는 자동으로 이벤트 객체를 받음
- 이벤트에 대한 모든 정보 포함
- target, currentTarget, preventDefault 등

### 이벤트 객체 기본
```javascript
button.addEventListener('click', function(event) {
    console.log(event);  // 이벤트 객체
    console.log(event.type);  // "click"
    console.log(event.target);  // 클릭된 요소
    console.log(event.currentTarget);  // 이벤트가 등록된 요소
});

// 짧게 e로 많이 씀
button.addEventListener('click', (e) => {
    console.log(e.type);
});
```

### 주요 속성

#### target vs currentTarget
```javascript
// <div id="parent">
//   <button id="child">클릭</button>
// </div>

const parent = document.querySelector('#parent');

parent.addEventListener('click', function(e) {
    console.log('target:', e.target);  // button (실제 클릭된 요소)
    console.log('currentTarget:', e.currentTarget);  // div (리스너 등록된 요소)
    console.log('this:', this);  // div (currentTarget과 같음)
});
```

#### preventDefault() - 기본 동작 막기
```javascript
// 링크 클릭 막기
const link = document.querySelector('a');
link.addEventListener('click', function(e) {
    e.preventDefault();  // 페이지 이동 막기
    console.log('링크 클릭했지만 이동 안 함');
});

// 폼 제출 막기
const form = document.querySelector('form');
form.addEventListener('submit', function(e) {
    e.preventDefault();  // 제출 막기
    console.log('폼 검증 중...');
});
```

#### stopPropagation() - 이벤트 전파 막기
```javascript
child.addEventListener('click', function(e) {
    e.stopPropagation();  // 부모로 전파 안 됨
    console.log('자식 클릭');
});

parent.addEventListener('click', function(e) {
    console.log('부모 클릭');  // 실행 안 됨!
});
```

---

## 📚 5.9 이벤트 버블링과 캡처링

### 강의 포인트
- 이벤트는 부모 요소로 전파됨 (버블링)
- 캡처링은 반대 방향 (거의 안 씀)
- 이벤트 위임의 기반

### 이벤트 버블링
```javascript
// <div id="outer">
//   <div id="inner">
//     <button id="btn">클릭</button>
//   </div>
// </div>

document.querySelector('#btn').addEventListener('click', () => {
    console.log('1. 버튼 클릭');
});

document.querySelector('#inner').addEventListener('click', () => {
    console.log('2. inner 클릭');
});

document.querySelector('#outer').addEventListener('click', () => {
    console.log('3. outer 클릭');
});

// 버튼 클릭 시 출력:
// 1. 버튼 클릭
// 2. inner 클릭
// 3. outer 클릭
// → 아래에서 위로 전파 (버블링)
```

### 이벤트 캡처링
```javascript
// 세 번째 인자를 true로 설정
document.querySelector('#outer').addEventListener('click', () => {
    console.log('1. outer 클릭');
}, true);  // 캡처링 단계에서 실행

document.querySelector('#inner').addEventListener('click', () => {
    console.log('2. inner 클릭');
}, true);

document.querySelector('#btn').addEventListener('click', () => {
    console.log('3. 버튼 클릭');
});

// 버튼 클릭 시 출력:
// 1. outer 클릭
// 2. inner 클릭
// 3. 버튼 클릭
// → 위에서 아래로 전파 (캡처링)
```

---

## 📚 5.10 이벤트 위임 (Event Delegation)

### 강의 포인트
- 부모 요소에 이벤트를 등록하여 자식 요소 처리
- 동적으로 추가된 요소도 자동으로 처리
- 성능 향상 (이벤트 리스너 수 감소)

### 기본 개념
```javascript
// ❌ 비효율적: 각 항목에 이벤트 등록
const items = document.querySelectorAll('.item');
items.forEach(item => {
    item.addEventListener('click', function() {
        console.log(this.textContent);
    });
});

// ✅ 효율적: 부모에 한 번만 등록
const list = document.querySelector('#list');
list.addEventListener('click', function(e) {
    if (e.target.classList.contains('item')) {
        console.log(e.target.textContent);
    }
});
```

### 실전 예제: 동적 항목 처리
```html
<ul id="todo-list">
    <li>항목 1 <button class="delete">삭제</button></li>
    <li>항목 2 <button class="delete">삭제</button></li>
</ul>
<button id="add">항목 추가</button>
```

```javascript
const todoList = document.querySelector('#todo-list');
const addBtn = document.querySelector('#add');

// 이벤트 위임: ul에 등록
todoList.addEventListener('click', function(e) {
    if (e.target.classList.contains('delete')) {
        e.target.parentElement.remove();
    }
});

// 동적으로 추가된 항목도 자동으로 처리됨!
addBtn.addEventListener('click', function() {
    const li = document.createElement('li');
    li.innerHTML = `항목 ${Date.now()} <button class="delete">삭제</button>`;
    todoList.appendChild(li);
});
```

### 이벤트 위임 패턴
```javascript
// 여러 버튼 종류 처리
const container = document.querySelector('#container');

container.addEventListener('click', function(e) {
    const target = e.target;
    
    // 삭제 버튼
    if (target.classList.contains('btn-delete')) {
        handleDelete(target);
    }
    
    // 수정 버튼
    if (target.classList.contains('btn-edit')) {
        handleEdit(target);
    }
    
    // 완료 버튼
    if (target.classList.contains('btn-complete')) {
        handleComplete(target);
    }
});
```

---

## 📚 5.11-5.12 주요 이벤트 종류

### 마우스 이벤트
```javascript
element.addEventListener('click', () => {});        // 클릭
element.addEventListener('dblclick', () => {});     // 더블클릭
element.addEventListener('mousedown', () => {});    // 마우스 버튼 누름
element.addEventListener('mouseup', () => {});      // 마우스 버튼 뗌
element.addEventListener('mousemove', () => {});    // 마우스 이동
element.addEventListener('mouseenter', () => {});   // 마우스 진입 (버블링 X)
element.addEventListener('mouseleave', () => {});   // 마우스 떠남 (버블링 X)
element.addEventListener('mouseover', () => {});    // 마우스 진입 (버블링 O)
element.addEventListener('mouseout', () => {});     // 마우스 떠남 (버블링 O)
```

### 키보드 이벤트
```javascript
element.addEventListener('keydown', (e) => {        // 키 누름
    console.log(e.key);        // 눌린 키 이름 ("a", "Enter", "Shift")
    console.log(e.code);       // 물리적 키 코드 ("KeyA", "Enter")
    console.log(e.keyCode);    // 키 코드 (Deprecated)
    console.log(e.ctrlKey);    // Ctrl 키가 눌렸는지
    console.log(e.shiftKey);   // Shift 키가 눌렸는지
    console.log(e.altKey);     // Alt 키가 눌렸는지
});

element.addEventListener('keyup', () => {});        // 키 뗌
element.addEventListener('keypress', () => {});     // 키 입력 (Deprecated)
```

### 폼 이벤트
```javascript
input.addEventListener('input', () => {});          // 값 변경 (실시간)
input.addEventListener('change', () => {});         // 값 변경 완료
input.addEventListener('focus', () => {});          // 포커스 받음
input.addEventListener('blur', () => {});           // 포커스 잃음
form.addEventListener('submit', (e) => {            // 폼 제출
    e.preventDefault();  // 제출 막기
});
```

### 기타 이벤트
```javascript
window.addEventListener('load', () => {});          // 페이지 로드 완료
window.addEventListener('DOMContentLoaded', () => {}); // DOM 준비 완료
window.addEventListener('resize', () => {});        // 창 크기 변경
window.addEventListener('scroll', () => {});        // 스크롤
```

---

## 🎯 강의 진행 팁

### 1-2교시: DOM 기초 (2시간)
- DOM이란?
- 요소 선택
- **실습**: 선택자 테스트

### 3-4교시: DOM 조작 (2시간)
- 요소 생성/추가/삭제
- 속성 조작
- **실습**: 동적 리스트 만들기

### 5-6교시: 이벤트 기초 (2시간)
- addEventListener
- 이벤트 객체
- **실습**: 버튼 클릭 이벤트

### 7-8교시: 이벤트 고급 (2시간)
- 이벤트 버블링
- 이벤트 위임
- **실습**: Todo List 앱

---

## 📝 종합 프로젝트: Todo List 앱

```javascript
// 완전한 Todo List 구현
const todoForm = document.querySelector('#todo-form');
const todoInput = document.querySelector('#todo-input');
const todoList = document.querySelector('#todo-list');

// 할 일 추가
todoForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const text = todoInput.value.trim();
    if (text === '') return;
    
    addTodoItem(text);
    todoInput.value = '';
});

// 이벤트 위임으로 삭제/완료 처리
todoList.addEventListener('click', (e) => {
    if (e.target.classList.contains('delete')) {
        e.target.parentElement.remove();
    }
    
    if (e.target.classList.contains('todo-text')) {
        e.target.classList.toggle('completed');
    }
});

function addTodoItem(text) {
    const li = document.createElement('li');
    li.innerHTML = `
        <span class="todo-text">${text}</span>
        <button class="delete">삭제</button>
    `;
    todoList.appendChild(li);
}
```

**5장 DOM 조작을 완료했습니다! 🎯**

