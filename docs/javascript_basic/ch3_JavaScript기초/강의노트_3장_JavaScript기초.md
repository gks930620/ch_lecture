 # 3장: JavaScript 기초

## 📋 강의 개요
- **소요 시간**: 약 8-10시간
- **난이도**: ⭐⭐ 초급~중급
- **선수 지식**: HTML, CSS 기초
- **학습 목표**: JavaScript의 기본 문법을 이해하고 간단한 프로그램을 작성할 수 있다.

---

## 📚 3.1 JavaScript란?

### 강의 포인트
- JavaScript는 **프로그래밍 언어**입니다 (HTML, CSS는 아님!)
- 웹의 3요소 중 유일한 프로그래밍 언어
- 브라우저에서 실행되는 언어 (최근에는 Node.js로 서버에서도 사용)

### JavaScript의 역할
```
HTML     = 건물 뼈대 (구조)
CSS      = 인테리어 (디자인)
JavaScript = 가전제품, 엘리베이터 (동작, 기능)
```

### JavaScript로 할 수 있는 것
1. **사용자 인터랙션**
   - 버튼 클릭 처리
   - 입력값 검증
   - 애니메이션

2. **동적 콘텐츠**
   - HTML 내용 변경
   - 스타일 변경
   - 요소 추가/삭제

3. **데이터 통신**
   - 서버와 통신 (AJAX)
   - API 호출
   - 실시간 업데이트

4. **계산과 로직**
   - 계산기
   - 게임
   - 데이터 처리

### JavaScript의 특징
- **인터프리터 언어**: 컴파일 없이 바로 실행
- **동적 타입**: 변수의 타입을 자동으로 결정
- **프로토타입 기반**: 객체 지향 프로그래밍 지원
- **이벤트 기반**: 사용자 행동에 반응

---

## 📚 3.2 변수 (Variables)

### 강의 포인트
- 변수는 "데이터를 담는 상자"
- **var는 사용하지 마세요!** (ES6 이전 방식)
- 기본은 `const`, 변경이 필요하면 `let`

### 변수란?
```javascript
// 변수는 데이터를 저장하는 공간
let name = "홍길동";  // 이름 상자에 "홍길동" 저장
let age = 25;         // 나이 상자에 25 저장

console.log(name);    // "홍길동" 출력
console.log(age);     // 25 출력
```

---

### var vs let vs const 비교표

| 구분 | var | let | const |
|------|-----|-----|-------|
| **재할당** | ✅ 가능 | ✅ 가능 | ❌ 불가능 |
| **재선언** | ✅ 가능 | ❌ 불가능 | ❌ 불가능 |
| **스코프** | 함수 스코프 | 블록 스코프 | 블록 스코프 |
| **호이스팅** | ⚠️ 문제 있음 | ✅ 안전 | ✅ 안전 |
| **사용 권장** | ❌ 금지 | ✅ 변수용 | ✅ 상수용 |

---

### 1. let - 변수 선언 (값 변경 가능) ⭐

```javascript
// 선언과 동시에 값 할당
let userName = "홍길동";
let userAge = 25;
let isStudent = true;

// 값 변경 가능
userName = "김철수";
userAge = 30;

console.log(userName);  // "김철수"
console.log(userAge);   // 30

// 선언만 하고 나중에 값 할당도 가능
let score;
score = 95;
```

**사용 시기**:
```javascript
// 카운터 변수
let count = 0;
count = count + 1;  // 값이 변경됨

// 반복문
for (let i = 0; i < 5; i++) {
    console.log(i);  // i가 계속 변경됨
}

// 조건에 따라 값이 바뀌는 경우
let message;
if (age >= 18) {
    message = "성인";
} else {
    message = "미성년자";
}
```

---

### 2. const - 상수 선언 (값 변경 불가능) ⭐

```javascript
// 한번 할당하면 변경 불가
const PI = 3.14159;
const MAX_SIZE = 100;
const API_URL = "https://api.example.com";

// 에러 발생!
PI = 3.14;  // ❌ TypeError: Assignment to constant variable

// 선언과 동시에 반드시 초기화해야 함
const TAX_RATE;  // ❌ SyntaxError: Missing initializer
const TAX_RATE = 0.1;  // ✅ OK
```

**사용 시기**:
```javascript
// 설정값
const MAX_LOGIN_ATTEMPTS = 5;
const DEFAULT_LANGUAGE = "ko";

// API 엔드포인트
const API_BASE_URL = "https://api.myapp.com";

// 함수
const calculateTax = (price) => price * 0.1;

// 객체/배열 (참조는 변경 불가, 내용은 변경 가능)
const user = {
    name: "홍길동",
    age: 25
};
user.age = 26;  // ✅ OK (객체 내용 변경)
user = {};      // ❌ Error (재할당 불가)
```

**💡 중요한 개념**:
```javascript
// const는 "재할당"만 막음, "변경"은 가능
const colors = ["red", "blue"];
colors.push("green");  // ✅ OK (배열 내용 변경)
colors = [];           // ❌ Error (재할당)

const person = { name: "홍길동" };
person.name = "김철수";  // ✅ OK (속성 변경)
person = {};            // ❌ Error (재할당)
```

---

### 3. var - 구식 변수 선언 ❌ 사용 금지!

```javascript
// ES6 이전 방식 (2015년 이전)
var oldName = "홍길동";

// 문제점 1: 재선언 가능 (버그 유발!)
var oldName = "김철수";  // 에러 없이 덮어씀
console.log(oldName);    // "김철수"

// 문제점 2: 함수 스코프 (예상치 못한 동작)
if (true) {
    var x = 10;
}
console.log(x);  // 10 (블록 밖에서도 접근 가능!)

// 문제점 3: 호이스팅 문제
console.log(y);  // undefined (에러가 아님!)
var y = 5;
```

**var를 사용하면 안 되는 이유**:
```javascript
// 예제 1: 반복문에서의 문제
for (var i = 0; i < 3; i++) {
    setTimeout(() => console.log(i), 1000);
}
// 출력: 3, 3, 3 (예상: 0, 1, 2)

// let을 사용하면
for (let i = 0; i < 3; i++) {
    setTimeout(() => console.log(i), 1000);
}
// 출력: 0, 1, 2 (정상!)
```

---

### 변수 명명 규칙 (Naming Convention)

#### ✅ 올바른 변수명
```javascript
let userName = "홍길동";        // 카멜 케이스 (권장!)
let user_name = "홍길동";       // 스네이크 케이스
let age = 25;
let isLoggedIn = true;
let _privateVar = "값";
let $jquery = "값";
const MAX_SIZE = 100;           // 상수는 대문자 (관례)
```

#### ❌ 잘못된 변수명
```javascript
let 2name = "값";          // ❌ 숫자로 시작 불가
let user-name = "값";      // ❌ 하이픈 사용 불가
let user name = "값";      // ❌ 공백 사용 불가
let class = "값";          // ❌ 예약어 사용 불가
let let = "값";            // ❌ 예약어 사용 불가
```

#### 📌 명명 규칙 가이드

**1. 카멜 케이스 (camelCase) - 일반 변수/함수**
```javascript
let firstName = "홍";
let lastName = "길동";
let userAge = 25;
let isLoggedIn = true;

function getUserInfo() { }
function calculateTotalPrice() { }
```

**2. 파스칼 케이스 (PascalCase) - 클래스**
```javascript
class UserAccount { }
class ShoppingCart { }
```

**3. 대문자 + 언더스코어 - 상수**
```javascript
const MAX_LOGIN_ATTEMPTS = 5;
const API_BASE_URL = "https://api.com";
const TAX_RATE = 0.1;
```

**4. 의미있는 이름 사용**
```javascript
// ❌ Bad
let x = 25;
let a = "홍길동";
let temp = true;

// ✅ Good
let userAge = 25;
let userName = "홍길동";
let isLoggedIn = true;
```

---

### 스코프 (Scope) - 변수의 유효 범위

#### 1. 블록 스코프 (let, const)
```javascript
{
    let x = 10;
    const y = 20;
    console.log(x, y);  // 10, 20
}
console.log(x, y);  // ❌ ReferenceError (블록 밖에서 접근 불가)
```

#### 2. 함수 스코프
```javascript
function myFunction() {
    let localVar = "로컬 변수";
    console.log(localVar);  // ✅ OK
}
myFunction();
console.log(localVar);  // ❌ ReferenceError
```

#### 3. 전역 스코프
```javascript
let globalVar = "전역 변수";  // 어디서든 접근 가능

function test() {
    console.log(globalVar);  // ✅ OK
}
```

**⚠️ 전역 변수 남용 주의**:
```javascript
// ❌ Bad - 전역 변수 남용
let count = 0;
let userName = "";
let isLoggedIn = false;

// ✅ Good - 객체로 묶기
const app = {
    count: 0,
    userName: "",
    isLoggedIn: false
};
```

---

### 실무 베스트 프랙티스

#### 1. 기본은 const, 필요시 let
```javascript
// ✅ Good
const userName = "홍길동";        // 변경 안 됨
const API_URL = "https://...";   // 변경 안 됨
let counter = 0;                 // 변경 필요

// ❌ Bad
let userName = "홍길동";  // 변경 안 하는데 let 사용
```

#### 2. 변수는 사용 직전에 선언
```javascript
// ❌ Bad
let userName;
let userAge;
let userEmail;
// ... 100줄의 코드 ...
userName = "홍길동";

// ✅ Good
// ... 코드 ...
const userName = "홍길동";  // 사용 직전에 선언
console.log(userName);
```

#### 3. 의미있는 이름 사용
```javascript
// ❌ Bad
const d = new Date();
const t = d.getTime();
const u = users.filter(u => u.a > 18);

// ✅ Good
const currentDate = new Date();
const timestamp = currentDate.getTime();
const adults = users.filter(user => user.age > 18);
```

---

## 🎯 강의 진행 팁

### 1교시: JavaScript 소개 (30분)
- JavaScript란?
- 개발자 도구(F12) 사용법
- console.log() 실습

### 2교시: 변수 기초 (1시간)
- 변수란?
- let과 const
- **실습**: 변수 선언하고 출력하기

### 3교시: var와 스코프 (1시간)
- var를 사용하면 안 되는 이유
- 스코프 개념
- **실습**: 스코프 테스트

### 4교시: 명명 규칙과 실습 (30분)
- 변수 명명 규칙
- **종합 실습**: 사용자 정보 관리

---

## 💡 학생들이 자주 묻는 질문

### Q1: const로 선언했는데 배열/객체 내용은 왜 바뀌나요?
```javascript
const arr = [1, 2, 3];
arr.push(4);  // 가능! 왜?

// A: const는 "변수가 가리키는 참조"를 바꾸지 못하게 함
//    배열/객체의 "내용"은 바뀔 수 있음

arr = [5, 6];  // ❌ 이건 불가능 (재할당)
```

### Q2: let과 const 중 무엇을 써야 하나요?
```
A: 기본적으로 const를 사용하세요.
   값이 변경되어야 한다면 그때 let으로 바꾸세요.
   
   const를 사용하면:
   - 실수로 값을 변경하는 것을 방지
   - 코드를 읽는 사람이 이 값은 변하지 않는다는 것을 알 수 있음
```

### Q3: var는 정말 절대 사용하면 안 되나요?
```
A: 네, 사용하지 마세요!
   - 오래된 코드에서 볼 수는 있지만
   - 새로운 코드에서는 let/const만 사용
   - 회사에서도 var 사용을 금지하는 곳이 많습니다
```

---

## 📝 자주하는 실수

### 실수 1: 세미콜론 빠뜨림
```javascript
❌ let name = "홍길동"
   let age = 25

✅ let name = "홍길동";
   let age = 25;
```

### 실수 2: const 선언 시 초기화 안 함
```javascript
❌ const PI;
   PI = 3.14;

✅ const PI = 3.14;
```

### 실수 3: 변수명 오타
```javascript
let userName = "홍길동";
console.log(username);  // ❌ ReferenceError (대소문자 다름!)
```

---

## 📚 3.3 자료형 (Data Types)

### 강의 포인트
- JavaScript는 **동적 타입** 언어 (변수 선언 시 타입 지정 안 함)
- 원시 타입(Primitive)과 참조 타입(Reference)의 차이
- typeof 연산자로 타입 확인

### 원시 타입 (Primitive Types) - 7가지

#### 1. Number (숫자)
```javascript
let integer = 42;           // 정수
let float = 3.14;           // 소수
let negative = -10;         // 음수
let infinity = Infinity;    // 무한대
let nan = NaN;              // Not a Number

// 특수한 숫자
console.log(1 / 0);         // Infinity
console.log(-1 / 0);        // -Infinity
console.log("abc" * 2);     // NaN

// NaN 확인
console.log(isNaN("abc"));      // true
console.log(Number.isNaN(NaN)); // true (더 정확)
```

#### 2. String (문자열)
```javascript
let single = '작은따옴표';
let double = "큰따옴표";
let backtick = `백틱 (템플릿 리터럴)`;

// 문자열 연결
let name = "홍길동";
let greeting = "안녕, " + name + "!";           // 기존 방식
let greeting2 = `안녕, ${name}!`;               // 템플릿 리터럴 (권장!)

// 유용한 문자열 메소드
let str = "Hello, World!";
console.log(str.length);            // 13
console.log(str.toUpperCase());     // "HELLO, WORLD!"
console.log(str.toLowerCase());     // "hello, world!"
console.log(str.indexOf("World"));  // 7
console.log(str.includes("Hello")); // true
console.log(str.slice(0, 5));       // "Hello"
console.log(str.split(", "));       // ["Hello", "World!"]
console.log(str.trim());            // 양쪽 공백 제거
console.log(str.replace("World", "JavaScript")); // "Hello, JavaScript!"
```

#### 3. Boolean (불리언)
```javascript
let isTrue = true;
let isFalse = false;

// Falsy 값 (false로 평가됨)
// false, 0, -0, "", null, undefined, NaN

// Truthy 값 (true로 평가됨)
// 위 7개를 제외한 모든 값
// "0", "false", [], {}, function(){} 등
```

#### 4. null과 undefined
```javascript
let value1 = null;       // 의도적으로 "비어있음"
let value2 = undefined;  // 값이 할당되지 않음

// 차이
let user = null;          // 아직 사용자 정보가 없음 (의도적)
let name;                 // 선언만 하고 할당 안 함 (undefined)
console.log(typeof null);      // "object" (JavaScript의 유명한 버그!)
console.log(typeof undefined); // "undefined"
```

#### 5. Symbol (ES6)
```javascript
const id = Symbol('id');
const id2 = Symbol('id');
console.log(id === id2);  // false (항상 고유한 값)
```

#### 6. BigInt (ES2020)
```javascript
const bigNumber = 9007199254740991n;
const big2 = BigInt("9007199254740991");
```

### 참조 타입 (Reference Types)
```javascript
// Object, Array, Function 등
let obj = { name: "홍길동" };
let arr = [1, 2, 3];
let func = function() {};
```

### typeof 연산자
```javascript
console.log(typeof 42);          // "number"
console.log(typeof "hello");     // "string"
console.log(typeof true);        // "boolean"
console.log(typeof undefined);   // "undefined"
console.log(typeof null);        // "object" (⚠️ 버그)
console.log(typeof {});          // "object"
console.log(typeof []);          // "object" (배열도 object!)
console.log(typeof function(){}); // "function"

// 배열 확인법
console.log(Array.isArray([]));  // true
```

### 타입 변환
```javascript
// 문자열 → 숫자
Number("42");        // 42
parseInt("42px");    // 42
parseFloat("3.14");  // 3.14
+"42";               // 42 (단항 + 연산자)

// 숫자 → 문자열
String(42);          // "42"
(42).toString();     // "42"
42 + "";             // "42"

// 불리언 변환
Boolean(1);          // true
Boolean(0);          // false
Boolean("");         // false
Boolean("hello");    // true
!!1;                 // true (이중 부정)
```

---

## 📚 3.4-3.6 연산자 (Operators)

### 산술 연산자
```javascript
console.log(10 + 3);   // 13 (덧셈)
console.log(10 - 3);   // 7  (뺄셈)
console.log(10 * 3);   // 30 (곱셈)
console.log(10 / 3);   // 3.333... (나눗셈)
console.log(10 % 3);   // 1  (나머지)
console.log(2 ** 3);   // 8  (거듭제곱, ES7)

// 증감 연산자
let a = 5;
console.log(a++);   // 5 (후위: 사용 후 증가)
console.log(a);     // 6
console.log(++a);   // 7 (전위: 증가 후 사용)
```

### 비교 연산자
```javascript
console.log(5 > 3);    // true
console.log(5 < 3);    // false
console.log(5 >= 5);   // true
console.log(5 <= 4);   // false
```

### == vs === (⭐ 매우 중요!)
```javascript
// == (동등 연산자): 값만 비교 (타입 변환 발생!)
console.log(5 == "5");     // true  ⚠️
console.log(0 == false);   // true  ⚠️
console.log(null == undefined); // true ⚠️
console.log("" == false);  // true  ⚠️

// === (일치 연산자): 값과 타입 모두 비교 ✅ 권장!
console.log(5 === "5");    // false ✅
console.log(0 === false);  // false ✅
console.log(null === undefined); // false ✅
console.log("" === false); // false ✅

// 실무: 항상 === 사용!
```

### 논리 연산자
```javascript
// && (AND) - 모두 true여야 true
console.log(true && true);    // true
console.log(true && false);   // false

// || (OR) - 하나라도 true면 true
console.log(false || true);   // true
console.log(false || false);  // false

// ! (NOT) - 반대
console.log(!true);   // false
console.log(!false);  // true

// 단축 평가 (Short-circuit)
let name = user && user.name;           // user가 있으면 name
let defaultName = name || "기본이름";     // name이 없으면 기본이름

// ?? (Nullish Coalescing, ES2020)
let value = null ?? "기본값";  // "기본값" (null/undefined일 때만)
let value2 = 0 ?? "기본값";   // 0 (0은 null/undefined가 아님)
```

---

## 📚 3.7 조건문 (Conditional Statements)

### if문
```javascript
let age = 20;

if (age >= 18) {
    console.log("성인입니다");
} else if (age >= 14) {
    console.log("청소년입니다");
} else {
    console.log("어린이입니다");
}
```

### 삼항 연산자 (조건부 연산자)
```javascript
let age = 20;
let status = age >= 18 ? "성인" : "미성년자";
console.log(status);  // "성인"

// 중첩 (가독성 주의!)
let grade = score >= 90 ? "A" : score >= 80 ? "B" : score >= 70 ? "C" : "F";
```

### switch문
```javascript
let day = "월요일";

switch (day) {
    case "월요일":
    case "화요일":
    case "수요일":
    case "목요일":
    case "금요일":
        console.log("평일입니다");
        break;
    case "토요일":
    case "일요일":
        console.log("주말입니다");
        break;
    default:
        console.log("잘못된 요일");
}
```

**💡 강의 팁**: switch는 `===`로 비교합니다. break를 빠뜨리면 다음 case로 넘어갑니다 (fall-through).

---

## 📚 3.8 반복문 (Loops)

### for문
```javascript
for (let i = 0; i < 5; i++) {
    console.log(`${i}번째 반복`);
}
// 0번째 반복, 1번째 반복, ... 4번째 반복
```

### while문
```javascript
let count = 0;
while (count < 5) {
    console.log(`count: ${count}`);
    count++;
}

// do...while (최소 1번 실행)
let num = 10;
do {
    console.log(num);  // 10 (한 번은 실행)
} while (num < 5);
```

### for...in (객체 순회)
```javascript
const person = { name: "홍길동", age: 25, city: "서울" };

for (let key in person) {
    console.log(`${key}: ${person[key]}`);
}
// name: 홍길동, age: 25, city: 서울
```

### for...of (배열/이터러블 순회) ⭐ 권장
```javascript
const fruits = ["사과", "바나나", "체리"];

for (let fruit of fruits) {
    console.log(fruit);
}
// 사과, 바나나, 체리
```

### break와 continue
```javascript
// break: 반복문 종료
for (let i = 0; i < 10; i++) {
    if (i === 5) break;
    console.log(i);  // 0, 1, 2, 3, 4
}

// continue: 현재 반복 건너뛰기
for (let i = 0; i < 10; i++) {
    if (i % 2 === 0) continue;
    console.log(i);  // 1, 3, 5, 7, 9 (홀수만)
}
```

---

## 📚 3.9-3.10 함수 (Functions)

### 강의 포인트
- 함수 = 재사용 가능한 코드 블록
- 함수 선언문 vs 표현식의 차이 (호이스팅)
- 화살표 함수는 this 바인딩이 다름

### 함수 선언문 (Function Declaration)
```javascript
function greet(name) {
    return `안녕하세요, ${name}!`;
}

console.log(greet("홍길동"));  // "안녕하세요, 홍길동!"

// 호이스팅 됨 (선언 전에 호출 가능)
sayHello();  // ✅ 작동!
function sayHello() {
    console.log("Hello!");
}
```

### 함수 표현식 (Function Expression)
```javascript
const greet = function(name) {
    return `안녕하세요, ${name}!`;
};

console.log(greet("홍길동"));

// 호이스팅 안 됨
sayHi();  // ❌ ReferenceError!
const sayHi = function() {
    console.log("Hi!");
};
```

### 화살표 함수 (Arrow Function) ⭐
```javascript
// 기본 형태
const add = (a, b) => {
    return a + b;
};

// 본문이 한 줄이면 중괄호와 return 생략
const add2 = (a, b) => a + b;

// 매개변수가 하나면 괄호 생략
const double = x => x * 2;

// 매개변수가 없으면 빈 괄호
const hello = () => "Hello!";

// 객체 반환 시 소괄호로 감싸기
const makeUser = (name, age) => ({ name, age });
```

### 기본 매개변수 (Default Parameters)
```javascript
function greet(name = "Guest", greeting = "안녕하세요") {
    return `${greeting}, ${name}!`;
}

console.log(greet());           // "안녕하세요, Guest!"
console.log(greet("홍길동"));    // "안녕하세요, 홍길동!"
console.log(greet("홍길동", "Hello")); // "Hello, 홍길동!"
```

### 콜백 함수
```javascript
function processArray(arr, callback) {
    const result = [];
    for (let item of arr) {
        result.push(callback(item));
    }
    return result;
}

const numbers = [1, 2, 3, 4, 5];
const doubled = processArray(numbers, x => x * 2);
console.log(doubled);  // [2, 4, 6, 8, 10]
```

---

## 📚 3.11-3.12 배열 (Array)

### 강의 포인트
- 배열은 순서가 있는 데이터의 모음
- 인덱스는 0부터 시작
- 다양한 배열 메소드 활용이 핵심

### 배열 기본
```javascript
// 배열 생성
const fruits = ["사과", "바나나", "체리"];
const numbers = [1, 2, 3, 4, 5];
const mixed = [1, "hello", true, null];  // 다양한 타입 가능

// 접근
console.log(fruits[0]);    // "사과"
console.log(fruits[2]);    // "체리"
console.log(fruits.length); // 3

// 수정
fruits[1] = "포도";
console.log(fruits);  // ["사과", "포도", "체리"]
```

### 주요 배열 메소드

#### 추가/삭제
```javascript
const arr = [1, 2, 3];

arr.push(4);          // 끝에 추가 → [1, 2, 3, 4]
arr.pop();            // 끝에서 제거 → [1, 2, 3]
arr.unshift(0);       // 앞에 추가 → [0, 1, 2, 3]
arr.shift();          // 앞에서 제거 → [1, 2, 3]

// splice(시작, 삭제수, 추가요소들)
arr.splice(1, 1);       // 인덱스 1에서 1개 삭제 → [1, 3]
arr.splice(1, 0, 2);    // 인덱스 1에 2 삽입 → [1, 2, 3]
arr.splice(1, 1, 20);   // 인덱스 1을 20으로 교체 → [1, 20, 3]
```

#### 검색
```javascript
const fruits = ["사과", "바나나", "체리", "바나나"];

fruits.indexOf("바나나");     // 1 (첫 번째 위치)
fruits.lastIndexOf("바나나"); // 3 (마지막 위치)
fruits.includes("체리");      // true
fruits.find(f => f.length > 2);      // "바나나" (조건에 맞는 첫 번째)
fruits.findIndex(f => f === "체리");  // 2
```

#### 변환 (⭐ 가장 많이 사용!)
```javascript
const numbers = [1, 2, 3, 4, 5];

// map - 변환
const doubled = numbers.map(n => n * 2);
// [2, 4, 6, 8, 10]

// filter - 필터링
const evens = numbers.filter(n => n % 2 === 0);
// [2, 4]

// reduce - 누적
const sum = numbers.reduce((acc, cur) => acc + cur, 0);
// 15

// forEach - 순회 (반환값 없음)
numbers.forEach((n, i) => console.log(`${i}: ${n}`));

// sort - 정렬
const sorted = [...numbers].sort((a, b) => b - a);
// [5, 4, 3, 2, 1] (내림차순)

// slice - 자르기 (원본 변경 없음)
const sliced = numbers.slice(1, 3);  // [2, 3]

// join - 합치기
const str = ["Hello", "World"].join(" ");  // "Hello World"
```

#### 메소드 체이닝
```javascript
const users = [
    { name: "홍길동", age: 25 },
    { name: "김철수", age: 17 },
    { name: "이영희", age: 30 },
    { name: "박민수", age: 15 }
];

// 성인만 이름 추출
const adultNames = users
    .filter(user => user.age >= 18)
    .map(user => user.name);
// ["홍길동", "이영희"]
```

---

## 📚 3.13 객체 (Object)

### 강의 포인트
- 객체 = 키-값 쌍의 모음
- JavaScript에서 가장 중요한 자료구조
- 거의 모든 것이 객체

### 객체 기본
```javascript
// 객체 생성
const person = {
    name: "홍길동",
    age: 25,
    isStudent: true,
    hobbies: ["독서", "운동"],
    address: {
        city: "서울",
        zipcode: "12345"
    },
    greet() {
        console.log(`안녕, 나는 ${this.name}!`);
    }
};

// 접근
console.log(person.name);           // "홍길동" (점 표기법)
console.log(person["age"]);         // 25 (대괄호 표기법)
console.log(person.address.city);   // "서울"

// 수정
person.age = 26;
person.email = "hong@example.com";  // 새 속성 추가

// 삭제
delete person.isStudent;

// 메소드 호출
person.greet();  // "안녕, 나는 홍길동!"
```

### 객체 관련 메소드
```javascript
const user = { name: "홍길동", age: 25, city: "서울" };

// 키 목록
Object.keys(user);     // ["name", "age", "city"]

// 값 목록
Object.values(user);   // ["홍길동", 25, "서울"]

// 키-값 쌍
Object.entries(user);  // [["name","홍길동"], ["age",25], ["city","서울"]]

// 속성 존재 확인
"name" in user;              // true
user.hasOwnProperty("age");  // true

// 객체 합치기
const merged = Object.assign({}, user, { email: "hong@test.com" });
const merged2 = { ...user, email: "hong@test.com" };  // 스프레드 (권장)
```

### 단축 속성명 (Shorthand Properties)
```javascript
const name = "홍길동";
const age = 25;

// 기존
const person1 = { name: name, age: age };

// 단축 (키와 변수명이 같으면)
const person2 = { name, age };
```

---

## 📚 3.14 JSON (JavaScript Object Notation)

### 강의 포인트
- JSON은 데이터 교환 형식
- JavaScript 객체와 비슷하지만 다름
- 서버-클라이언트 통신의 표준

### JSON 형식
```json
{
    "name": "홍길동",
    "age": 25,
    "isStudent": true,
    "hobbies": ["독서", "운동"],
    "address": {
        "city": "서울"
    }
}
```

**⚠️ JSON vs 객체 차이**:
- JSON의 키는 반드시 큰따옴표(`"`)
- JSON에는 함수, undefined, 주석 불가
- JSON 문자열은 큰따옴표만 사용

### JSON.stringify() - 객체 → JSON 문자열
```javascript
const user = { name: "홍길동", age: 25 };
const jsonStr = JSON.stringify(user);
console.log(jsonStr);        // '{"name":"홍길동","age":25}'
console.log(typeof jsonStr); // "string"

// 보기 좋게 출력 (들여쓰기)
console.log(JSON.stringify(user, null, 2));
```

### JSON.parse() - JSON 문자열 → 객체
```javascript
const jsonStr = '{"name":"홍길동","age":25}';
const user = JSON.parse(jsonStr);
console.log(user.name);     // "홍길동"
console.log(typeof user);   // "object"
```

### 실무 활용 예
```javascript
// LocalStorage에 객체 저장
const settings = { theme: "dark", fontSize: 16 };
localStorage.setItem("settings", JSON.stringify(settings));

// LocalStorage에서 객체 불러오기
const loaded = JSON.parse(localStorage.getItem("settings"));
console.log(loaded.theme);  // "dark"
```

---

## 🎯 강의 진행 팁 (수정)

### 1교시: JavaScript 소개와 변수 (1.5시간)
- JavaScript란? + 개발자 도구
- let, const, var
- **실습**: 변수 선언과 출력

### 2교시: 자료형과 연산자 (1.5시간)
- 원시 타입 7가지
- typeof, ==vs===
- 산술/비교/논리 연산자
- **실습**: 타입 변환 테스트

### 3교시: 조건문과 반복문 (1.5시간)
- if, switch, 삼항 연산자
- for, while, for...of
- **실습**: 간단한 프로그램 만들기

### 4교시: 함수 (1.5시간)
- 함수 선언문, 표현식
- 화살표 함수
- 콜백 함수
- **실습**: 다양한 함수 작성

### 5교시: 배열과 객체 (2시간)
- 배열 생성과 메소드
- 객체 생성과 조작
- JSON
- **실습**: 데이터 가공하기

---

## 📝 평가 기준

### 이해도 체크리스트
- [ ] let, const, var의 차이를 설명할 수 있다
- [ ] 7가지 원시 타입을 알고 있다
- [ ] ==와 ===의 차이를 설명할 수 있다
- [ ] if문과 switch문을 상황에 맞게 사용할 수 있다
- [ ] for, while, for...of를 적절히 사용할 수 있다
- [ ] 함수 선언문, 표현식, 화살표 함수를 구분할 수 있다
- [ ] 배열 메소드(map, filter, reduce)를 사용할 수 있다
- [ ] 객체를 생성하고 조작할 수 있다
- [ ] JSON.parse/JSON.stringify를 사용할 수 있다

---

## 🎓 다음 단계

3장 JavaScript 기초를 마스터했다면:
1. **스코프와 호이스팅** (4장)
2. **클로저** (4장)
3. **this 바인딩** (4장)
4. **DOM 조작** (5장)

**3장 JavaScript 기초를 완료했습니다! 프로그래밍의 세계로! 🚀**

