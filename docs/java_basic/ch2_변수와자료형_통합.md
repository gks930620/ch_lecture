---
layout: default
title: ch2 변수와 자료형 통합
description: 기본자료형, 참조자료형, 형변환, 상수/리터럴/var, 문제를 한 파일로 통합
---

# ch2 변수와 자료형 통합

이 문서는 아래 내용을 하나의 파일로 합친 통합 노트입니다.

1. 기본 자료형
2. 참조 자료형 (String, 배열)
3. 형변환
4. 상수, 리터럴, var
5. 문제

---

## 1) 기본 자료형

### 1-1. 기본 자료형 8가지

- 정수형: `byte`, `short`, `int`, `long`
- 실수형: `float`, `double`
- 문자형: `char`
- 논리형: `boolean`

| 타입 | 크기 | 의미/범위 |
|---|---:|---|
| `byte` | 8bit | -128 ~ 127 |
| `short` | 16bit | -32,768 ~ 32,767 |
| `int` | 32bit | 일반 정수 기본 타입 |
| `long` | 64bit | 큰 정수 처리 |
| `float` | 32bit | 단정밀도 실수 |
| `double` | 64bit | 배정밀도 실수(기본) |
| `char` | 16bit | UTF-16 코드 유닛 |
| `boolean` | 구현 의존 | `true` / `false` |

### 1-2. 정수 표현 핵심

- Java signed 정수는 2의 보수 기반
- 오버플로우 시 예외 없이 래핑됨

```java
int max = Integer.MAX_VALUE;
System.out.println(max + 1); // -2147483648
```

### 1-3. 실수 표현 핵심

- `float`, `double`은 IEEE 754 부동소수점 근사값
- `0.1 + 0.2 == 0.3`은 `false`가 될 수 있음

```java
double x = 0.1 + 0.2;
System.out.println(x); // 0.30000000000000004
```

### 1-4. 타입 선택 실무 포인트

- 일반 정수: `int`, 큰 범위: `long`
- 일반 실수: `double`
- 금액 계산: `BigDecimal` 검토
- 경계값 테스트(`MIN_VALUE`, `MAX_VALUE`) 습관화

---

## 2) 참조 자료형 - String과 배열

### 2-1. 참조 자료형 기본

- 변수에는 객체 본문이 아닌 “참조값” 저장
- `null` 가능성 고려 필수

```java
String name = null;
// name.length(); // NullPointerException
```

### 2-2. String 핵심

- `String`은 객체이며 불변(immutable)
- 내용 비교는 `equals`, 참조 비교는 `==`

```java
String a = new String("java");
String b = new String("java");

System.out.println(a == b);      // false
System.out.println(a.equals(b)); // true
```

- 반복 문자열 결합은 `StringBuilder` 권장

### 2-3. 배열 핵심

- 배열은 같은 타입 고정 길이 자료구조(객체)
- 인덱스 0부터 시작, `length`로 길이 확인
- 범위 초과 시 `ArrayIndexOutOfBoundsException`

```java
int[] arr = {1, 2, 3};
for (int i = 0; i < arr.length; i++) {
    System.out.println(arr[i]);
}
```

### 2-4. 배열 복사 주의

- `b = a`는 복사가 아니라 참조 공유
- 실제 복사는 `clone`, `Arrays.copyOf`, `System.arraycopy`

---

## 3) 형변환

### 3-1. 자동 형변환 (Widening)

- 작은 타입 -> 큰 타입은 자동 변환

```java
int i = 100;
long l = i;
double d = l;
```

### 3-2. 강제 형변환 (Narrowing)

- 큰 타입 -> 작은 타입은 캐스팅 필요
- 값 손실 가능

```java
long big = 3_000_000_000L;
int small = (int) big;
```

### 3-3. 연산 시 승격 규칙

- `byte`, `short`, `char`는 산술 연산 시 `int` 승격

```java
byte a = 10;
byte b = 20;
int c = a + b; // 결과 타입 int
```

### 3-4. 참조형 캐스팅

- 업캐스팅: 자동
- 다운캐스팅: 명시적 + 타입 확인 필요

```java
Object obj = "hello";
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

### 3-5. 오토박싱/언박싱 주의

```java
Integer x = null;
// int y = x; // NullPointerException
```

---

## 4) 상수, 리터럴, var

### 4-1. `final` 상수

```java
public static final int MAX_RETRY = 3;
```

- 재대입 금지
- 의미 있는 고정값은 상수화 권장

### 4-2. 리터럴 표기

- 정수: `10`, `0xFF`, `0b1010`, `1_000_000`, `3_000_000_000L`
- 실수: 기본 `double`, `float`는 `F` 필요
- 문자/문자열: `'A'`, `"A"`

### 4-3. `var` 타입 추론

```java
var name = "kim"; // String
var count = 10;   // int
```

- 지역 변수에서만 사용
- `var x = null;` 불가
- 가독성 기준으로 선택

---

## 5) 문제

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
3. `String[]` 5개 중 null 아닌 값만 출력
4. `int[]`의 합계/평균/최대/최소 출력
5. 문자열 내용 비교 (`==` 금지, `equals` 사용)

### C. 형변환 집중

1. `long -> int`, `double -> int`, `int -> byte` 손실 예제 작성
2. `char ch='A'` 코드값/다음 문자/소문자 변환 출력
3. 정수 나눗셈 vs 실수 나눗셈 비교 출력
4. 언박싱 NPE 재현 + 방어 코드 작성

### D. String/배열 심화

1. `String +` 와 `StringBuilder` 성능 비교
2. 가변 행 2차원 배열 합계 계산
3. 배열 복사 3방식 구현 및 비교
4. 참조 복사 vs 값 복사 설명

### E. 상수/리터럴/var

1. `static final` 상수 5개 선언
2. 같은 수를 2/8/10/16진으로 표현
3. `var` 가독성 좋은/나쁜 사례 코드 작성
4. `final` 참조 변수와 객체 불변성 차이 설명

### F. 챌린지

1. 문자열 숫자 파싱 합계 + 예외 처리
2. 문자열 배열 중복 제거 + 길이 내림차순 정렬
3. 점수 배열 평균/표준편차 계산(타입 선택 이유 설명)

---

## 제출 체크리스트

1. 경계값 테스트를 했는가?
2. 문자열 비교에 `equals`를 사용했는가?
3. 축소 형변환 손실을 확인했는가?
4. `var` 사용이 가독성을 해치지 않는가?
