---
layout: default
title: ch2 변수와 자료형
description: 변수 개념, 기본/참조 자료형, 형변환, 상수와 리터럴, var
---

# ch2 변수와 자료형

## 학습 목표
- 변수가 무엇인지 설명하고, 선언/할당/재할당을 코드로 작성할 수 있다.
- 기본 자료형 8가지의 용도를 구분하고 상황에 맞는 타입을 선택할 수 있다.
- 기본형과 참조형의 차이(값 저장 vs 주소 저장)를 그림으로 설명할 수 있다.
- 형변환(자동/강제)과 연산 시 타입 승격 규칙을 이해한다.
- `final` 상수, 리터럴 표기, `var`를 상황에 맞게 사용할 수 있다.

---

## 1. 변수란 무엇인가

프로그램은 결국 **데이터를 저장하고, 꺼내서, 가공하는 일**의 반복이다.
이때 데이터를 담아두는 "이름 붙은 저장 공간"이 **변수(variable)**다.

```java
int age;        // 1. 선언: int 값을 담을 공간을 만들고 age라는 이름을 붙임
age = 25;       // 2. 할당: 공간에 25를 저장 (= 는 "같다"가 아니라 "넣는다")
age = 30;       // 3. 재할당: 기존 값 25는 사라지고 30으로 교체
```

![변수는 이름 붙은 저장 공간]({{ '/java_basic/java_basic_images/ch2/variable-memory-box.svg' | relative_url }})

선언과 할당을 한 줄로 합칠 수도 있고, 실무에서는 이 형태를 가장 많이 쓴다.

```java
int age = 25;   // 선언 + 초기화
```

주의할 점: **지역 변수(메소드 안의 변수)는 값을 넣기 전에는 사용할 수 없다.**

```java
int count;
// System.out.println(count); // 컴파일 오류: 초기화되지 않은 변수
```

### 1.1 변수 이름 규칙

- 문자로 시작 (숫자로 시작 불가: `1st` ❌)
- 대소문자 구분 (`age`와 `Age`는 다른 변수)
- 예약어 사용 불가 (`int`, `class` 같은 자바 키워드)
- 관례: **camelCase** — 첫 단어는 소문자, 이후 단어 첫 글자만 대문자 (`userName`, `totalPrice`)

이름은 문법만 지키면 되는 것이 아니라 **의미가 드러나게** 짓는 것이 중요하다.
`a`, `temp` 보다 `age`, `totalPrice`가 좋은 이름이다.

---

## 2. 기본 자료형 8가지

변수를 선언할 때는 "어떤 종류의 값을 담을지"를 타입으로 지정한다.
타입이 있어야 컴퓨터가 **공간을 얼마나 확보할지, 그 값으로 어떤 연산이 가능한지** 알 수 있다.

자바가 언어 차원에서 제공하는 기본 자료형(primitive type)은 8가지다.

- 정수형: `byte`, `short`, `int`, `long`
- 실수형: `float`, `double`
- 문자형: `char`
- 논리형: `boolean`

| 타입 | 크기 | 의미/범위 |
|---|---:|---|
| `byte` | 8bit | -128 ~ 127 |
| `short` | 16bit | -32,768 ~ 32,767 |
| `int` | 32bit | 약 ±21억, **일반 정수 기본 타입** |
| `long` | 64bit | 매우 큰 정수 처리 |
| `float` | 32bit | 단정밀도 실수 |
| `double` | 64bit | 배정밀도 실수, **실수 기본 타입** |
| `char` | 16bit | 문자 1개 (UTF-16 코드 유닛) |
| `boolean` | 구현 의존 | `true` / `false` |

8가지나 있지만 실무에서 주로 쓰는 것은 **`int`, `long`, `double`, `boolean`, `char`** 정도다.
일단 "정수는 `int`, 실수는 `double`"로 시작하면 된다.

### 2.1 정수형: int와 long

```java
int population = 51_000_000;        // _ 는 자릿수 구분용 (컴파일 시 무시됨)
long worldPopulation = 8_000_000_000L; // int 범위(±21억)를 넘으면 long, 뒤에 L 필수
```

`L`을 빼면 컴파일러가 `8_000_000_000`을 일단 int 리터럴로 해석하려다 범위 초과로 컴파일 오류가 난다.
**int 범위를 넘는 숫자를 쓸 때는 반드시 `L`을 붙인다.**

### 2.2 정수의 한계: 오버플로우

int는 32bit 공간이므로 담을 수 있는 최댓값이 정해져 있다(`Integer.MAX_VALUE` = 2,147,483,647).
최댓값에서 1을 더하면 어떻게 될까? 오류가 날 것 같지만, 자바는 **예외 없이 최솟값으로 되돌아간다(wrap-around)**.

```java
int max = Integer.MAX_VALUE;
System.out.println(max + 1); // -2147483648 (최솟값으로 래핑!)
```

이런 일이 생기는 이유는 자바의 정수가 **2의 보수(two's complement)** 방식으로 저장되기 때문이다.
비트가 한 바퀴 돌아 부호 비트를 침범하면서 음수가 된다.

![2의 보수 표현 예시]({{ '/java_basic/java_basic_images/ch2/twos-complement-example.svg' | relative_url }})

지금 단계에서는 "정수에는 한계가 있고, 넘어가면 조용히 이상한 값이 된다"는 것만 확실히 기억하자.
큰 값을 다룰 가능성이 있으면 처음부터 `long`을 쓴다.

### 2.3 실수형: double과 float

```java
double pi = 3.141592;   // 실수 기본 타입
float rate = 0.05F;     // float는 뒤에 F 필수 (실수 리터럴의 기본은 double이므로)
```

실수는 정수와 달리 **근사값**으로 저장된다. `float`, `double`은 IEEE 754 부동소수점 방식을 쓰는데,
0.1 같은 값을 2진수로 정확히 표현할 수 없어서 아주 작은 오차가 생긴다.

```java
double x = 0.1 + 0.2;
System.out.println(x);          // 0.30000000000000004
System.out.println(x == 0.3);   // false!
```

![IEEE 754 비트 구조]({{ '/java_basic/java_basic_images/ch2/ieee754-layout.svg' | relative_url }})

그래서 두 가지 실무 규칙이 나온다.

- 실수를 `==`로 직접 비교하지 않는다 (오차 허용 범위로 비교)
- **돈 계산처럼 오차가 허용되지 않는 곳에는 `double`을 쓰지 않는다** (`BigDecimal` 사용 — ch10에서 배움)

### 2.4 문자형: char

`char`는 **문자 딱 1개**를 담는다. 작은따옴표를 쓴다는 점이 문자열(`"..."`, 큰따옴표)과 다르다.

```java
char grade = 'A';     // 문자 1개, 작은따옴표
String name = "Ann";  // 문자열(여러 글자), 큰따옴표 — 참조형(아래에서 설명)
```

char는 내부적으로 문자의 유니코드 번호(숫자)로 저장되기 때문에 연산도 가능하다.

```java
char ch = 'A';
System.out.println((int) ch);      // 65 (문자 'A'의 코드값)
System.out.println((char)(ch + 1)); // B
```

### 2.5 논리형: boolean

`true` 또는 `false` 두 가지 값만 가진다. 조건문(ch4)에서 핵심적으로 쓰인다.

```java
boolean isAdult = age >= 20;   // 비교 결과가 boolean
System.out.println(isAdult);   // age가 25라면 true
```

### 2.6 타입 선택 기준 정리

- 일반 정수: `int` / 21억 넘을 수 있으면: `long`
- 일반 실수: `double`
- 금액 계산: `BigDecimal` 검토 (ch10)
- `byte`, `short`, `float`는 파일/네트워크 포맷 등 특수한 경우에만

---

## 3. 기본형 vs 참조형

자바의 타입은 크게 두 부류다. 이 구분은 앞으로 모든 챕터에서 계속 등장하는 **가장 중요한 개념**이다.

- **기본형(primitive)**: 변수 공간에 **값 자체**가 들어있다 — 위의 8가지
- **참조형(reference)**: 변수 공간에는 **객체가 있는 곳의 주소(참조)**만 들어있다 — `String`, 배열, 그리고 앞으로 배울 모든 클래스

![기본형 vs 참조형]({{ '/java_basic/java_basic_images/ch2/primitive-vs-reference.svg' | relative_url }})

참조형 변수에는 "아직 아무것도 가리키지 않음"을 뜻하는 `null`을 넣을 수 있다.
그리고 null인 상태에서 객체를 사용하려고 하면 그 유명한 **NullPointerException(NPE)**이 발생한다.

```java
String name = null;
// name.length(); // NullPointerException: 가리키는 객체가 없는데 사용하려고 함
```

기본형에는 null이 없다. `int x = null;`은 컴파일 자체가 안 된다.

---

## 4. String — 가장 많이 쓰는 참조형

문자열을 다루는 `String`은 참조형이지만, 워낙 많이 쓰여서 리터럴 표기(`"..."`)를 지원한다.

```java
String greeting = "hello";
System.out.println(greeting.length());        // 5 (글자 수)
System.out.println(greeting.toUpperCase());   // HELLO
System.out.println(greeting + " java");       // hello java (+ 는 문자열 연결)
```

`String`의 다양한 메소드는 ch10(자바 API)에서 자세히 배운다. 여기서는 두 가지 성질만 확실히 잡자.

### 4.1 String은 불변(immutable)이다

`toUpperCase()` 같은 메소드는 원본을 바꾸는 게 아니라 **새 문자열을 만들어 돌려준다**.

```java
String s = "hello";
s.toUpperCase();
System.out.println(s);  // hello (원본 그대로!)

s = s.toUpperCase();    // 결과를 다시 받아야 바뀐 값을 쓸 수 있다
System.out.println(s);  // HELLO
```

### 4.2 내용 비교는 equals, == 금지

`==`는 "같은 객체를 가리키는가(주소 비교)"이고, 내용 비교는 `equals()`다.

```java
String a = new String("java");
String b = new String("java");

System.out.println(a == b);      // false (서로 다른 객체)
System.out.println(a.equals(b)); // true  (내용은 같음)
```

문자열 리터럴(`"java"`)은 **문자열 풀(String Pool)**이라는 공간에 저장되어 같은 리터럴끼리는 같은 객체를 공유한다.
반면 `new String()`은 항상 새 객체를 만들기 때문에 `==` 비교가 `false`가 된다.
리터럴끼리는 풀을 공유해서 `==`도 `true`가 나오기 때문에 오히려 "== 로 비교해도 되네?"라고 착각하기 쉽다.
**어떤 경우든 문자열 내용 비교는 `equals`로 통일**하면 안전하다.

![String Pool과 참조 비교]({{ '/java_basic/java_basic_images/ch2/string-pool-reference.svg' | relative_url }})

### 4.3 반복 결합은 StringBuilder

문자열은 불변이라 `+`로 이어붙일 때마다 새 객체가 생긴다.
반복문에서 수백 번 이어붙이는 상황이라면 `StringBuilder`를 쓴다.

```java
StringBuilder sb = new StringBuilder();
sb.append("hello");
sb.append(" java");
String result = sb.toString(); // "hello java"
```

---

## 5. 배열 — 같은 타입 여러 개를 한 번에

배열(array)은 **같은 타입의 값 여러 개를 순서대로 담는** 참조형이다. 배열 자체는 ch5에서 깊게 다루고, 여기서는 기본 사용법과 "참조형답게 동작하는" 특징만 본다.

```java
int[] scores = new int[3];      // 길이 3짜리 int 배열 생성 (값은 전부 0으로 자동 초기화)
scores[0] = 90;                 // 인덱스는 0부터 시작
scores[1] = 85;
scores[2] = 77;

int[] arr = {1, 2, 3};          // 선언과 동시에 값 지정
for (int i = 0; i < arr.length; i++) {   // length: 배열 길이
    System.out.println(arr[i]);
}
```

> `for` 반복문은 ch4에서 배운다. 지금은 "arr의 값을 처음부터 끝까지 하나씩 출력한다" 정도로만 읽으면 된다.

- 인덱스는 **0부터** 시작한다. 길이 3이면 유효 인덱스는 0, 1, 2
- 범위를 벗어나면 `ArrayIndexOutOfBoundsException` 발생 (`arr[3]` ❌)
- 한 번 만든 배열의 길이는 바꿀 수 없다

### 5.1 배열 복사 주의 — `b = a`는 복사가 아니다

배열은 참조형이므로, 변수를 대입하면 **배열이 복사되는 게 아니라 같은 배열을 둘이 가리키게 된다**.

```java
// 파일 상단에 import java.util.Arrays; 필요
int[] a = {1, 2, 3};
int[] b = a;            // 복사 아님! 같은 배열을 가리킴
b[0] = 99;
System.out.println(a[0]); // 99 — a도 바뀐 것처럼 보임 (사실 같은 배열)

int[] c = Arrays.copyOf(a, a.length); // 진짜 복사
c[0] = 1;
System.out.println(a[0]); // 99 (c를 바꿔도 a는 영향 없음)
```

실제 복사가 필요하면 `Arrays.copyOf`, `clone`, `System.arraycopy`를 쓴다.

![배열 참조 공유와 실제 복사 비교]({{ '/java_basic/java_basic_images/ch2/array-copy-reference.svg' | relative_url }})

---

## 6. 형변환

타입이 다른 값끼리 대입하거나 연산할 때는 **형변환(casting)**이 일어난다.

![타입 승격과 캐스팅 흐름]({{ '/java_basic/java_basic_images/ch2/type-promotion-casting.svg' | relative_url }})

### 6.1 자동 형변환 (Widening)

작은 타입 → 큰 타입은 값 손실이 없으므로 자동으로 변환된다.

```java
int i = 100;
long l = i;     // int(32bit) -> long(64bit): 자동
double d = l;   // long -> double: 자동
```

### 6.2 강제 형변환 (Narrowing)

큰 타입 → 작은 타입은 **값이 잘릴 수 있으므로** `(타입)`을 붙여 "손실을 감수하겠다"고 명시해야 한다.

```java
long big = 3_000_000_000L;
int small = (int) big;
System.out.println(small);  // -1294967296 (int 범위를 넘어 값이 깨짐!)

double pi = 3.99;
int n = (int) pi;
System.out.println(n);      // 3 (반올림이 아니라 소수부를 버림)
```

### 6.3 연산 시 승격 규칙

`byte`, `short`, `char`는 산술 연산을 하는 순간 `int`로 승격된다.
그래서 아래 코드는 직관과 달리 컴파일 오류다.

```java
byte a = 10;
byte b = 20;
// byte c = a + b; // 컴파일 오류: a + b의 결과 타입은 int
int c = a + b;     // OK
```

### 6.4 참조형 캐스팅 (맛보기)

참조형에도 형변환이 있다.

```java
Object obj = "hello";
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

업캐스팅/다운캐스팅은 상속(ch7)에서 자세히 배운다. 지금은 "참조형에도 형변환이 있다" 정도만 알아두면 된다.

### 6.5 오토박싱/언박싱 주의

`Integer`는 `int`를 객체로 감싼 래퍼(wrapper) 클래스다.
자바가 `int ↔ Integer` 변환을 자동으로 해주는데(오토박싱/언박싱), `Integer`가 null이면 언박싱 순간 NPE가 터진다.

```java
Integer x = null;
// int y = x; // NullPointerException: null을 int로 풀 수 없음
```

---

## 7. 상수, 리터럴, var

### 7.1 `final` 상수

`final`을 붙인 변수는 한 번 값을 넣으면 재대입할 수 없다.
"바뀌면 안 되는 의미 있는 고정값"은 상수로 만들고, 이름은 대문자+언더스코어 관례를 쓴다.

```java
public static final int MAX_RETRY = 3;
// MAX_RETRY = 5; // 컴파일 오류: final 변수 재대입 불가
```

### 7.2 리터럴 표기

리터럴(literal)은 코드에 직접 적는 값 자체를 말한다 (`10`, `3.14`, `'A'`, `"hi"`, `true`).

- 정수: `10`(10진), `0xFF`(16진), `0b1010`(2진), `1_000_000`(자릿수 구분), `3_000_000_000L`(long)
- 실수: 기본이 `double`, `float`는 `0.5F`처럼 `F` 필요
- 문자/문자열: `'A'`(char, 1글자), `"A"`(String)

### 7.3 `var` 타입 추론

`var`를 쓰면 컴파일러가 우변을 보고 타입을 추론한다 (Java 10+).
타입이 없어지는 게 아니라 **컴파일 시점에 확정**되는 것이므로, 동적 타입 언어와는 다르다.

```java
var name = "kim"; // String으로 추론
var count = 10;   // int로 추론
```

- 지역 변수에서만 사용 가능
- `var x = null;` 불가 (추론할 근거가 없음)
- 우변만 봐도 타입이 명확할 때만 사용 — 가독성이 기준

---

## 8. 입력 받기 맛보기 — Scanner

지금까지는 값을 코드에 직접 적었지만, 실행 중에 사용자에게 입력받을 수도 있다.
실습 소스(`P7변수사용범위와입력.java`, `P8Scanner.java`, `quest/Q2~Q4`)에서 사용하므로 기본 형태만 익혀두자.

```java
import java.util.Scanner;   // 파일 상단에 필요

Scanner sc = new Scanner(System.in);
System.out.println("나이를 입력하세요");
int age = sc.nextInt();        // 정수 입력
String name = sc.next();       // 문자열(공백 전까지) 입력
double height = sc.nextDouble(); // 실수 입력
```

> Scanner를 이용한 다양한 입력 문제는 ch3부터 본격적으로 다룬다.

---

## 9. 자주 하는 실수 모음

1. **문자열을 `==`로 비교** → `equals` 사용
2. **정수끼리 나눗셈** `5 / 2`는 `2.5`가 아니라 `2` (소수부 버림, ch3에서 자세히)
3. **int 범위 넘는 리터럴에 `L` 누락** → 컴파일 오류
4. **초기화 안 한 지역 변수 사용** → 컴파일 오류
5. **`(int)` 캐스팅이 반올림이라고 착각** → 소수부를 버린다
6. **실수를 `==`로 비교** → 부동소수점 오차 때문에 실패할 수 있음

---

## 정리

- 변수 = 타입이 정해진, 이름 붙은 저장 공간. 선언 → 할당 → 사용.
- 기본형 8가지 중 실전 기본값은 `int`, `long`, `double`, `boolean`, `char`.
- 기본형은 값을, 참조형은 주소를 담는다. 참조형은 `null`이 될 수 있다 → NPE 주의.
- String은 불변이며, 내용 비교는 반드시 `equals`.
- 배열 대입(`b = a`)은 복사가 아니라 참조 공유.
- 축소 형변환은 값이 깨질 수 있고, `byte`/`short`/`char`는 연산 시 `int`로 승격된다.

---

## 문제

> 실습 소스 `src/quest`의 Q1~Q4는 B 유형의 예시 답안입니다 — Q1: 두 정수 합/곱(B1 일부), Q2: 직사각형 넓이/둘레(B2 변형), Q3: 원기둥 계산(B2 변형), Q4: 동전 합계(응용).
> 전체 정답 예시: [ch2 문제 답안](문제답안/ch2_문제답안.md)

### A. 기초 확인

1. `byte`, `short`, `int`, `long`의 크기와 범위를 표로 정리하시오.
2. `float`와 `double`의 정밀도 차이를 설명하시오.
3. 아래 코드 오류 이유를 설명하시오.

```java
byte a = 10;
byte b = 20;
byte c = a + b;
```

4. `String`이 primitive가 아닌 이유를 설명하시오.
5. 배열이 참조 자료형임을 보여주는 예제를 작성하시오.

### B. 코드 작성

1. 정수 2개 합/차/곱/몫/나머지 출력 프로그램 작성
2. 반지름(`double`)으로 원의 넓이/둘레 출력
3. `String[]` 5개 중 null 아닌 값만 출력 (반복문 필요 — ch4 학습 후)
4. `int[]`의 합계/평균/최대/최소 출력 (반복문 필요 — ch4 학습 후)
5. 문자열 내용 비교 (`==` 금지, `equals` 사용)

### C. 형변환 집중

1. `long -> int`, `double -> int`, `int -> byte` 손실 예제 작성
2. `char ch='A'` 코드값/다음 문자/소문자 변환 출력
3. 정수 나눗셈 vs 실수 나눗셈 비교 출력
4. 언박싱 NPE 재현 + 방어 코드 작성 (조건문 필요 — ch4 학습 후)

### D. String/배열 심화

1. `String +` 와 `StringBuilder` 성능 비교 (반복문 필요 — ch4 학습 후)
2. 가변 행 2차원 배열 합계 계산 (2차원 배열 — ch5 학습 후)
3. 배열 복사 3방식 구현 및 비교
4. 참조 복사 vs 값 복사 설명

### E. 상수/리터럴/var

1. `static final` 상수 5개 선언
2. 같은 수를 2/8/10/16진으로 표현
3. `var` 가독성 좋은/나쁜 사례 코드 작성
4. `final` 참조 변수와 객체 불변성 차이 설명

### F. 챌린지

(1번은 예외 처리(ch9), 2번은 배열 심화(ch5), 3번은 반복문(ch4) 학습 후 도전하세요.)

1. 문자열 숫자 파싱 합계 + 예외 처리
2. 문자열 배열 중복 제거 + 길이 내림차순 정렬
3. 점수 배열 평균/표준편차 계산(타입 선택 이유 설명)

---

## 제출 체크리스트

1. 경계값 테스트를 했는가?
2. 문자열 비교에 `equals`를 사용했는가?
3. 축소 형변환 손실을 확인했는가?
4. `var` 사용이 가독성을 해치지 않는가?
