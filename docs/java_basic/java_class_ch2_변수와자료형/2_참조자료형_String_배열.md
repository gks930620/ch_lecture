# 참조 자료형 - String과 배열

## 학습 목표
- 기본 자료형과 참조 자료형의 저장 방식 차이를 메모리 관점에서 설명할 수 있다.
- `String`의 불변성, 문자열 풀(String Pool), 비교 방식(`==`/`equals`)을 이해한다.
- 배열의 생성/초기화/순회/복사/예외를 실무 코드 기준으로 다룰 수 있다.

---

## 1. 참조 자료형이란 무엇인가

Java의 변수는 크게 두 가지를 담는다.

1. **값 자체** (primitive)
2. **객체를 가리키는 참조값** (reference)

참조 자료형(`String`, 배열, 클래스 인스턴스)의 변수에는 객체 본문이 직접 들어가지 않는다.  
대신 "객체가 있는 위치를 가리키는 값"이 저장된다.

예시:

```java
String a = "hello";
String b = a;
```

- `a`와 `b`는 같은 문자열 객체를 가리킬 수 있다.
- `b`를 다른 문자열로 재대입하면 `a`는 영향을 받지 않는다(참조값 복사이기 때문).

---

## 2. 메모리 관점: Stack과 Heap

간단하게 이해하면:
- 지역 변수(참조값 포함)는 스택 프레임에 놓이고
- 실제 객체 본문은 힙에 생성된다.

```java
String s = new String("Java");
```

- `s` 변수: 참조값 보관
- `new String(...)` 결과 객체: Heap에 존재

정확한 JVM 내부 구현은 VM마다 최적화가 다를 수 있지만,  
학습 단계에서는 "참조 변수는 객체를 가리킨다"를 확실히 이해하면 된다.

---

## 3. `null` 참조와 NPE

참조형 변수는 객체를 가리키지 않는 값 `null`을 가질 수 있다.

```java
String name = null;
System.out.println(name.length()); // NullPointerException
```

실무에서는 다음 습관이 중요하다.
- 객체 사용 전 null 가능성 점검
- API 설계 시 null 허용 여부 명확화
- 문자열 비교 시 `"상수".equals(변수)` 패턴 활용(변수가 null이어도 안전)

---

## 4. String: Java에서 가장 중요한 참조형

## 4.1 String은 클래스다

문자열은 primitive가 아니라 `java.lang.String` 객체다.

```java
String s1 = "abc";
String s2 = new String("abc");
```

두 표현 모두 문자열 객체를 다루지만 생성 경로가 다르다.

### 4.2 문자열 리터럴과 String Pool

문자열 리터럴은 JVM의 문자열 풀에 저장/재사용될 수 있다.

```java
String a = "hello";
String b = "hello";
System.out.println(a == b); // true 가능 (동일 풀 객체 공유)
```

반면 `new String("hello")`는 별도 객체를 만든다.

```java
String c = new String("hello");
System.out.println(a == c); // false
System.out.println(a.equals(c)); // true
```

핵심:
- `==` : 같은 객체를 가리키는지(참조 동일성)
- `equals` : 문자열 내용이 같은지(값 동등성)

### 4.3 String은 불변(immutable)

`String`은 한 번 생성되면 내용이 바뀌지 않는다.

```java
String s = "A";
s = s + "B";
```

`s`의 기존 객체를 수정하는 것이 아니라 `"AB"`라는 새 객체를 만들어 재대입한다.

불변성의 장점:
- 스레드 환경에서 안전성이 높음
- 해시 기반 컬렉션에서 안정적 동작
- 문자열 리터럴 재사용(String Pool) 가능

### 4.4 문자열 결합 성능: `+` vs `StringBuilder`

반복문에서 `+`를 계속 사용하면 중간 문자열 객체가 많이 생긴다.

```java
String result = "";
for (int i = 0; i < 10000; i++) {
    result += i; // 비효율
}
```

대신 가변 버퍼(`StringBuilder`)를 사용한다.

```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 10000; i++) {
    sb.append(i);
}
String result = sb.toString();
```

---

## 5. String 비교와 자주 발생하는 버그

1. 문자열 내용 비교를 `==`로 하는 실수  
2. null 가능 문자열에 바로 메서드 호출  
3. 대소문자/공백/인코딩 차이를 고려하지 않는 비교

안전한 기본 패턴:

```java
if ("ADMIN".equals(role)) {
    // null-safe
}
```

대소문자 무시 비교:

```java
"java".equalsIgnoreCase(input)
```

---

## 6. 배열(Array)의 본질

배열은 **같은 타입의 값을 고정 길이로 저장하는 객체**다.

```java
int[] scores = new int[3];
```

중요 포인트:
- 배열 자체도 참조 자료형 객체다.
- 길이(`length`)는 생성 후 변경 불가.
- 인덱스는 0부터 시작.

### 6.1 선언과 생성

```java
int[] a;          // 선언
a = new int[5];   // 생성

int[] b = {10, 20, 30}; // 선언+초기화
```

### 6.2 기본값

배열 요소는 자동 초기화된다.
- `int[]` -> 0
- `double[]` -> 0.0
- `boolean[]` -> false
- 참조형 배열(`String[]`) -> null

---

## 7. 배열 순회 방법

### 7.1 인덱스 기반 for

```java
for (int i = 0; i < scores.length; i++) {
    System.out.println(scores[i]);
}
```

- 인덱스 필요 작업(위치 교환, 부분 탐색)에 적합

### 7.2 향상된 for (for-each)

```java
for (int score : scores) {
    System.out.println(score);
}
```

- 읽기 전용 순회에 간결
- 인덱스 접근이 필요하면 일반 for 사용

---

## 8. 다차원 배열

Java의 2차원 배열은 "배열의 배열"이다.

```java
int[][] matrix = new int[2][3];
```

각 행 길이를 다르게 줄 수도 있다(가변 행, jagged array).

```java
int[][] jagged = {
    {1, 2},
    {3, 4, 5}
};
```

---

## 9. 배열 복사: 얕은 복사와 주의점

다음 코드는 "복사"가 아니라 참조 공유다.

```java
int[] a = {1, 2, 3};
int[] b = a; // 같은 배열 참조
b[0] = 99;
System.out.println(a[0]); // 99
```

실제 별도 배열이 필요하면 복사 API를 사용한다.

```java
int[] copy1 = java.util.Arrays.copyOf(a, a.length);
int[] copy2 = a.clone();
int[] copy3 = new int[a.length];
System.arraycopy(a, 0, copy3, 0, a.length);
```

참조형 배열에서는 "요소 객체"까지 깊게 복사되지 않을 수 있으므로  
깊은 복사 필요 여부를 항상 검토해야 한다.

---

## 10. 배열 관련 대표 예외

### 10.1 `ArrayIndexOutOfBoundsException`

```java
int[] arr = new int[3];
arr[3] = 10; // 인덱스 범위 초과
```

### 10.2 `NullPointerException`

```java
String[] names = new String[2];
System.out.println(names[0].length()); // 요소가 null
```

방지 포인트:
- 인덱스 경계 검사
- null 가능 요소 검증

---

## 11. `Arrays` 유틸리티 필수 메서드

`java.util.Arrays`는 배열 실무에서 자주 사용된다.

- `Arrays.toString(arr)` : 출력용 문자열
- `Arrays.sort(arr)` : 정렬
- `Arrays.binarySearch(arr, key)` : 이진 탐색(정렬 전제)
- `Arrays.equals(a, b)` : 내용 비교
- `Arrays.fill(arr, value)` : 일괄 대입

---

## 12. String과 배열 연결 지점

문자열도 내부적으로 문자 데이터 시퀀스를 다룬다.  
문자열 처리 시 자주 쓰는 메서드:

- `toCharArray()`
- `substring(begin, end)`
- `split(regex)`
- `join(delimiter, elements)`

대량 텍스트 처리에서는 불변 String만 반복 조합하지 말고  
`StringBuilder`와 배열/컬렉션 조합을 고려하는 것이 좋다.

---

## 13. 정리

- 참조형 변수는 객체 본문이 아니라 참조값을 저장한다.
- `String`은 불변 객체이며, 비교는 `equals`가 기본이다.
- 배열은 고정 길이 자료구조로, 인덱스/복사/null/경계 조건을 정확히 다뤄야 한다.
- 이 챕터를 정확히 이해하면 이후 컬렉션, 객체지향, 예외 처리 학습이 훨씬 쉬워진다.
