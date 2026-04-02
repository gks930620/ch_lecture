# 4장: JavaScript 심화

## 📋 강의 개요
- **소요 시간**: 약 10-12시간
- **난이도**: ⭐⭐⭐ 중급
- **선수 지식**: JavaScript 기초 (변수, 함수, 배열, 객체)
- **학습 목표**: JavaScript의 고급 개념을 이해하고 실무에서 활용할 수 있다.

---

## 📚 4.1 스코프 (Scope)

### 강의 포인트
- 스코프는 "변수가 유효한 범위"
- var, let, const에 따라 스코프가 다름
- 렉시컬 스코프 개념이 중요

### 스코프의 종류

#### 1. 전역 스코프 (Global Scope)
```javascript
// 전역 변수 - 어디서든 접근 가능
let globalVar = "전역 변수";

function test() {
    console.log(globalVar);  // ✅ 접근 가능
}

console.log(globalVar);  // ✅ 접근 가능
```

**특징**:
- 코드 어디서든 접근 가능
- 파일 전체에서 살아있음
- ⚠️ 전역 변수 남용은 위험 (이름 충돌, 메모리 낭비)

#### 2. 함수 스코프 (Function Scope)
```javascript
function myFunction() {
    let localVar = "지역 변수";
    console.log(localVar);  // ✅ 접근 가능
}

myFunction();
console.log(localVar);  // ❌ ReferenceError: localVar is not defined
```

**var는 함수 스코프**:
```javascript
function test() {
    var x = 10;
    if (true) {
        var x = 20;  // 같은 변수!
    }
    console.log(x);  // 20
}
```

#### 3. 블록 스코프 (Block Scope)
```javascript
{
    let blockVar = "블록 변수";
    const BLOCK_CONST = "블록 상수";
    console.log(blockVar);  // ✅ 접근 가능
}
console.log(blockVar);  // ❌ ReferenceError
```

**let/const는 블록 스코프**:
```javascript
function test() {
    let x = 10;
    if (true) {
        let x = 20;  // 다른 변수!
        console.log(x);  // 20
    }
    console.log(x);  // 10
}
```

### 렉시컬 스코프 (Lexical Scope)
```javascript
let name = "전역";

function outer() {
    let name = "outer";
    
    function inner() {
        console.log(name);  // "outer" (정의된 위치 기준)
    }
    
    inner();
}

outer();  // "outer"
```

**핵심**: 함수는 **정의된 위치**를 기준으로 상위 스코프를 결정

---

## 📚 4.2 호이스팅 (Hoisting)

### 강의 포인트
- 호이스팅 = "끌어올림"
- 선언이 스코프의 최상단으로 올라감
- var와 let/const의 호이스팅 차이

### var 호이스팅
```javascript
console.log(x);  // undefined (에러 아님!)
var x = 5;
console.log(x);  // 5

// 실제 동작 (내부적으로)
var x;           // 선언이 위로
console.log(x);  // undefined
x = 5;           // 할당은 제자리
console.log(x);  // 5
```

### let/const 호이스팅
```javascript
console.log(y);  // ❌ ReferenceError: Cannot access 'y' before initialization
let y = 5;

// let/const도 호이스팅 되지만 TDZ(Temporal Dead Zone)에 있음
```

### 함수 호이스팅
```javascript
// 함수 선언문 - 호이스팅 됨
sayHello();  // ✅ "Hello!" (선언 전에 호출 가능)

function sayHello() {
    console.log("Hello!");
}

// 함수 표현식 - 호이스팅 안 됨
sayHi();  // ❌ ReferenceError

const sayHi = function() {
    console.log("Hi!");
};
```

**실무 팁**: 
- 항상 변수를 사용 전에 선언
- let/const 사용 (var 금지)
- 함수는 사용 전에 선언

---

## 📚 4.3 클로저 (Closure)

### 강의 포인트
- 클로저는 JavaScript의 핵심 개념
- "함수 + 렉시컬 환경"
- 데이터 은닉, 모듈 패턴에 사용

### 클로저란?
```javascript
function outer() {
    let count = 0;  // outer 함수의 지역 변수
    
    function inner() {
        count++;
        console.log(count);
    }
    
    return inner;
}

const counter = outer();
counter();  // 1
counter();  // 2
counter();  // 3

// inner 함수가 outer 함수의 변수(count)를 기억!
```

**클로저의 3가지 조건**:
1. 내부 함수가 외부 함수의 변수에 접근
2. 외부 함수가 내부 함수를 반환
3. 외부 함수 실행이 끝나도 내부 함수가 외부 변수를 기억

### 실용적인 클로저 예제

#### 1. 카운터
```javascript
function makeCounter() {
    let count = 0;
    
    return {
        increment() { return ++count; },
        decrement() { return --count; },
        getCount() { return count; }
    };
}

const counter = makeCounter();
console.log(counter.increment());  // 1
console.log(counter.increment());  // 2
console.log(counter.decrement());  // 1
console.log(counter.getCount());   // 1

// count 변수는 외부에서 직접 접근 불가 (데이터 은닉)
```

#### 2. 프라이빗 변수
```javascript
function createPerson(name) {
    let _name = name;  // private 변수
    
    return {
        getName() {
            return _name;
        },
        setName(newName) {
            _name = newName;
        }
    };
}

const person = createPerson("홍길동");
console.log(person.getName());  // "홍길동"
person.setName("김철수");
console.log(person.getName());  // "김철수"
console.log(person._name);      // undefined (직접 접근 불가)
```

#### 3. 함수 팩토리
```javascript
function multiplyBy(factor) {
    return function(number) {
        return number * factor;
    };
}

const double = multiplyBy(2);
const triple = multiplyBy(3);

console.log(double(5));  // 10
console.log(triple(5));  // 15
```

### 클로저 주의사항

#### 반복문에서의 클로저
```javascript
// ❌ 잘못된 예
for (var i = 0; i < 3; i++) {
    setTimeout(function() {
        console.log(i);
    }, 1000);
}
// 출력: 3, 3, 3 (예상: 0, 1, 2)

// ✅ 올바른 예 (let 사용)
for (let i = 0; i < 3; i++) {
    setTimeout(function() {
        console.log(i);
    }, 1000);
}
// 출력: 0, 1, 2

// ✅ 올바른 예 (IIFE 사용)
for (var i = 0; i < 3; i++) {
    (function(j) {
        setTimeout(function() {
            console.log(j);
        }, 1000);
    })(i);
}
// 출력: 0, 1, 2
```

---

## 📚 4.4 this 바인딩

### 강의 포인트
- this는 "호출 방법"에 따라 결정
- 가장 헷갈리는 개념 중 하나
- 화살표 함수의 this는 다름

### this 결정 규칙

#### 1. 전역 컨텍스트
```javascript
console.log(this);  // window (브라우저) / global (Node.js)
```

#### 2. 함수 호출
```javascript
function test() {
    console.log(this);
}

test();  // window (strict mode에서는 undefined)
```

#### 3. 메소드 호출
```javascript
const person = {
    name: "홍길동",
    sayName() {
        console.log(this.name);
    }
};

person.sayName();  // "홍길동" (this = person)
```

#### 4. 생성자 함수
```javascript
function Person(name) {
    this.name = name;
}

const person1 = new Person("홍길동");
console.log(person1.name);  // "홍길동" (this = 새 객체)
```

#### 5. 명시적 바인딩 (call, apply, bind)
```javascript
function greet() {
    console.log(`Hello, ${this.name}`);
}

const person = { name: "홍길동" };

greet.call(person);   // "Hello, 홍길동"
greet.apply(person);  // "Hello, 홍길동"

const boundGreet = greet.bind(person);
boundGreet();  // "Hello, 홍길동"
```

#### 6. 화살표 함수
```javascript
const person = {
    name: "홍길동",
    regularFunc: function() {
        console.log(this.name);  // "홍길동"
    },
    arrowFunc: () => {
        console.log(this.name);  // undefined (this = 외부 스코프)
    }
};

person.regularFunc();  // "홍길동"
person.arrowFunc();    // undefined
```

### this 실전 문제

```javascript
const user = {
    name: "홍길동",
    greet: function() {
        console.log(`안녕, ${this.name}`);
    }
};

user.greet();  // "안녕, 홍길동" ✅

const greet = user.greet;
greet();  // "안녕, undefined" ❌ (this = window)

// 해결 방법 1: bind
const boundGreet = user.greet.bind(user);
boundGreet();  // "안녕, 홍길동" ✅

// 해결 방법 2: 화살표 함수
const user2 = {
    name: "김철수",
    greet: function() {
        setTimeout(() => {
            console.log(`안녕, ${this.name}`);
        }, 1000);
    }
};

user2.greet();  // "안녕, 김철수" ✅ (화살표 함수는 상위 this 사용)
```

---

## 📚 4.5 call, apply, bind

### 강의 포인트
- this를 명시적으로 바인딩하는 방법
- call과 apply는 즉시 실행, bind는 새 함수 반환
- apply는 인자를 배열로 전달

### call()
```javascript
function greet(greeting, punctuation) {
    console.log(`${greeting}, ${this.name}${punctuation}`);
}

const person = { name: "홍길동" };

greet.call(person, "안녕하세요", "!");
// "안녕하세요, 홍길동!"
```

### apply()
```javascript
function greet(greeting, punctuation) {
    console.log(`${greeting}, ${this.name}${punctuation}`);
}

const person = { name: "홍길동" };

greet.apply(person, ["안녕하세요", "!"]);
// "안녕하세요, 홍길동!" (인자를 배열로)
```

### bind()
```javascript
function greet(greeting) {
    console.log(`${greeting}, ${this.name}`);
}

const person = { name: "홍길동" };

const boundGreet = greet.bind(person);
boundGreet("안녕하세요");  // "안녕하세요, 홍길동"

// 부분 적용
const sayHello = greet.bind(person, "Hello");
sayHello();  // "Hello, 홍길동"
```

### 실무 예제

#### 배열 메소드 활용
```javascript
const numbers = [5, 6, 2, 3, 7];

// Math.max는 배열을 받지 않음
console.log(Math.max(5, 6, 2, 3, 7));  // 7

// apply로 배열 전달
console.log(Math.max.apply(null, numbers));  // 7

// 최신 방식 (스프레드 연산자)
console.log(Math.max(...numbers));  // 7
```

#### 배열 메소드 빌려쓰기
```javascript
const arrayLike = {
    0: "a",
    1: "b",
    2: "c",
    length: 3
};

// 유사 배열에 배열 메소드 적용
const arr = Array.prototype.slice.call(arrayLike);
console.log(arr);  // ["a", "b", "c"]

// 최신 방식
const arr2 = Array.from(arrayLike);
console.log(arr2);  // ["a", "b", "c"]
```

---

## 📚 4.6 프로토타입 (Prototype)

### 강의 포인트
- JavaScript는 프로토타입 기반 언어
- 모든 객체는 프로토타입을 가짐
- 상속을 프로토타입 체인으로 구현

### 프로토타입이란?
```javascript
function Person(name) {
    this.name = name;
}

// 프로토타입에 메소드 추가
Person.prototype.greet = function() {
    console.log(`Hello, ${this.name}`);
};

const person1 = new Person("홍길동");
const person2 = new Person("김철수");

person1.greet();  // "Hello, 홍길동"
person2.greet();  // "Hello, 김철수"

// 두 객체가 같은 메소드를 공유
console.log(person1.greet === person2.greet);  // true
```

### 프로토타입 체인
```javascript
const arr = [1, 2, 3];

// arr의 프로토타입 체인
arr 
  → Array.prototype (push, pop 등)
    → Object.prototype (toString, hasOwnProperty 등)
      → null

console.log(arr.toString());  // "1,2,3" (Object.prototype에서 상속)
```

### __proto__ vs prototype
```javascript
function Person(name) {
    this.name = name;
}

const person = new Person("홍길동");

console.log(person.__proto__ === Person.prototype);  // true
console.log(Person.prototype.constructor === Person);  // true
```

---

## 📚 4.7 클래스 (ES6 class)

### 강의 포인트
- ES6부터 class 문법 추가
- 내부적으로는 프로토타입 사용
- 더 직관적이고 읽기 쉬움

### 기본 클래스
```javascript
class Person {
    // 생성자
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }
    
    // 메소드
    greet() {
        console.log(`안녕하세요, ${this.name}입니다.`);
    }
    
    // getter
    get info() {
        return `${this.name} (${this.age}세)`;
    }
    
    // setter
    set info(value) {
        [this.name, this.age] = value.split(',');
    }
    
    // 정적 메소드
    static create(name, age) {
        return new Person(name, age);
    }
}

const person = new Person("홍길동", 25);
person.greet();  // "안녕하세요, 홍길동입니다."
console.log(person.info);  // "홍길동 (25세)"

const person2 = Person.create("김철수", 30);
```

### 상속
```javascript
class Animal {
    constructor(name) {
        this.name = name;
    }
    
    speak() {
        console.log(`${this.name}가 소리를 냅니다.`);
    }
}

class Dog extends Animal {
    constructor(name, breed) {
        super(name);  // 부모 생성자 호출
        this.breed = breed;
    }
    
    speak() {
        console.log(`${this.name}가 멍멍 짖습니다.`);
    }
    
    fetch() {
        console.log(`${this.name}가 공을 가져옵니다.`);
    }
}

const dog = new Dog("바둑이", "진돗개");
dog.speak();  // "바둑이가 멍멍 짖습니다."
dog.fetch();  // "바둑이가 공을 가져옵니다."
```

### Private 필드 (#)
```javascript
class BankAccount {
    #balance = 0;  // private 필드
    
    deposit(amount) {
        this.#balance += amount;
    }
    
    getBalance() {
        return this.#balance;
    }
}

const account = new BankAccount();
account.deposit(1000);
console.log(account.getBalance());  // 1000
console.log(account.#balance);      // ❌ SyntaxError
```

---

## 📚 4.8-4.15 기타 고급 개념

### 4.8 모듈 (import/export)
```javascript
// math.js
export const PI = 3.14159;
export function add(a, b) {
    return a + b;
}
export default function multiply(a, b) {
    return a * b;
}

// main.js
import multiply, { PI, add } from './math.js';
console.log(PI);           // 3.14159
console.log(add(2, 3));    // 5
console.log(multiply(2, 3));  // 6
```

### 4.9 구조 분해 할당
```javascript
// 배열
const [a, b, c] = [1, 2, 3];

// 객체
const { name, age } = { name: "홍길동", age: 25 };

// 기본값
const { x = 10, y = 20 } = { x: 5 };
console.log(x, y);  // 5, 20

// 중첩 구조
const user = {
    name: "홍길동",
    address: {
        city: "서울",
        zipcode: "12345"
    }
};
const { address: { city } } = user;
console.log(city);  // "서울"
```

### 4.10 스프레드 연산자
```javascript
// 배열
const arr1 = [1, 2, 3];
const arr2 = [...arr1, 4, 5, 6];

// 객체
const obj1 = { a: 1, b: 2 };
const obj2 = { ...obj1, c: 3 };

// 함수 인자
function sum(a, b, c) {
    return a + b + c;
}
const numbers = [1, 2, 3];
console.log(sum(...numbers));  // 6
```

### 4.11 Rest 파라미터
```javascript
function sum(...numbers) {
    return numbers.reduce((a, b) => a + b, 0);
}

console.log(sum(1, 2, 3, 4, 5));  // 15
```

### 4.12 템플릿 리터럴
```javascript
const name = "홍길동";
const age = 25;

// 기본
const greeting = `안녕하세요, ${name}입니다.`;

// 여러 줄
const message = `
이름: ${name}
나이: ${age}
`;

// 표현식
const result = `2 + 2 = ${2 + 2}`;
```

### 4.13 Symbol
```javascript
const id1 = Symbol('id');
const id2 = Symbol('id');

console.log(id1 === id2);  // false (항상 고유)

// 객체의 숨겨진 속성
const user = {
    name: "홍길동",
    [id1]: 123
};
```

### 4.14 Map과 Set
```javascript
// Map
const map = new Map();
map.set('name', '홍길동');
map.set('age', 25);
console.log(map.get('name'));  // "홍길동"

// Set
const set = new Set([1, 2, 2, 3, 3, 3]);
console.log([...set]);  // [1, 2, 3] (중복 제거)
```

---

## 🎯 강의 진행 팁

### 1-2교시: 스코프와 호이스팅 (2시간)
- 실습: 다양한 스코프 테스트

### 3-4교시: 클로저 (2시간)
- 실습: 카운터 만들기

### 5-6교시: this 바인딩 (2시간)
- 실습: this 문제 풀기

### 7-8교시: 프로토타입과 클래스 (2시간)
- 실습: 클래스 상속 구현

### 9-10교시: ES6+ 문법 (2시간)
- 실습: 모던 JavaScript 문법 활용

---

## 📝 평가 및 실습

### 종합 프로젝트: Todo List 앱
- 클래스 사용
- 클로저로 데이터 은닉
- 모듈 패턴
- ES6+ 문법 활용

**4장 JavaScript 심화를 완료했습니다! 🚀**

