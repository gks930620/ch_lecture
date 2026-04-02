# 2.4 상수 (final), 2.5 리터럴, 2.6 var 키워드

## 학습 목표
- 상수(final)의 개념을 이해한다
- 리터럴의 의미를 안다
- var 키워드를 사용할 수 있다

---

## 1. 상수 (final)

### 1.1 상수란?
- **변경할 수 없는 값**
- `final` 키워드 사용
- 관례적으로 **대문자와 언더스코어(_)** 사용

```java
final int MAX_COUNT = 100;
final double PI = 3.141592;
final String COMPANY_NAME = "MyCompany";

// MAX_COUNT = 200;  // 에러! 상수는 변경 불가
```

### 1.2 상수의 장점
- 코드 가독성 향상
- 값 변경 방지
- 유지보수 용이

```java
// 상수 사용 안 함 (나쁜 예)
if (score >= 90) {
    return "A";
}

// 상수 사용 (좋은 예)
final int A_GRADE = 90;
if (score >= A_GRADE) {
    return "A";
}
```

---

## 2. 리터럴 (Literal)

### 2.1 리터럴이란?
- **소스 코드에 직접 입력된 값**
- 변수가 아닌 값 자체

```java
int num = 100;        // 100이 정수 리터럴
double pi = 3.14;     // 3.14가 실수 리터럴
char ch = 'A';        // 'A'가 문자 리터럴
String str = "Hello"; // "Hello"가 문자열 리터럴
boolean flag = true;  // true가 논리 리터럴
```

### 2.2 리터럴 종류

#### 정수 리터럴
```java
int decimal = 100;      // 10진수
int octal = 0144;       // 8진수 (0으로 시작)
int hex = 0x64;         // 16진수 (0x로 시작)
int binary = 0b1100100; // 2진수 (0b로 시작, Java 7+)

long longNum = 100L;    // long 리터럴 (L 또는 l)
```

#### 실수 리터럴
```java
double d1 = 3.14;       // 기본형 (double)
float f1 = 3.14f;       // float (f 또는 F 필수)

double d2 = 3.14e2;     // 과학적 표기법 (314.0)
double d3 = 3.14E-2;    // 0.0314
```

#### 문자 리터럴
```java
char c1 = 'A';          // 일반 문자
char c2 = '\n';         // 이스케이프 시퀀스 (줄바꿈)
char c3 = '\t';         // 탭
char c4 = '\\';         // 백슬래시
char c5 = '\'';         // 작은따옴표
char c6 = '\u0041';     // 유니코드 (A)
```

#### 문자열 리터럴
```java
String str1 = "Hello";
String str2 = "Hello\nWorld";  // 줄바꿈 포함
String str3 = "\"인용문\"";     // 큰따옴표 포함
```

### 2.3 언더스코어 (_) 사용 (Java 7+)
```java
int million = 1_000_000;        // 가독성 향상
long creditCard = 1234_5678_9012_3456L;
double pi = 3.141_592_653_589_793;
```

---

## 3. var 키워드 (Java 10+)

### 3.1 var란?
- **타입 추론** 기능
- 컴파일러가 자동으로 타입 결정
- **지역 변수**에만 사용 가능

```java
// 기존 방식
String message = "Hello";
int count = 10;
ArrayList<String> list = new ArrayList<>();

// var 사용
var message = "Hello";          // String으로 추론
var count = 10;                 // int로 추론
var list = new ArrayList<String>();  // ArrayList<String>으로 추론
```

### 3.2 var 사용 가능한 경우
```java
// 지역 변수
var name = "홍길동";

// 반복문
for (var i = 0; i < 10; i++) {
    System.out.println(i);
}

for (var item : list) {
    System.out.println(item);
}

// 복잡한 타입
var map = new HashMap<String, List<Integer>>();
```

### 3.3 var 사용 불가능한 경우
```java
// 필드 변수 (에러!)
class Example {
    // var name = "홍길동";  // 에러!
}

// 초기화 없이 선언 (에러!)
// var x;  // 에러!

// null 초기화 (에러!)
// var obj = null;  // 에러!

// 메소드 매개변수 (에러!)
// public void method(var x) { }  // 에러!

// 반환 타입 (에러!)
// public var method() { }  // 에러!
```

### 3.4 var 사용 가이드
```java
// 좋은 예: 타입이 명확할 때
var name = "홍길동";
var count = 10;
var list = new ArrayList<String>();

// 나쁜 예: 타입이 불명확할 때
var result = calculate();  // 무슨 타입인지 모름
var value = getValue();     // 가독성 저하
```

---

## 4. 정리

### 핵심 개념
✅ **final**: 상수 선언 (변경 불가)  
✅ **리터럴**: 코드에 직접 쓴 값  
✅ **언더스코어**: 숫자 가독성 향상 (1_000_000)  
✅ **var**: 타입 추론 (Java 10+)  
✅ **var 제한**: 지역 변수만, 초기화 필수  

---

## 다음 학습
- 3장. 연산자와 제어문

