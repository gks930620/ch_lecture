# 2.2 참조 자료형 (Reference Types) - String과 배열

## 학습 목표
- 참조 자료형의 개념을 이해한다
- String 클래스를 사용할 수 있다
- 배열의 기본 개념을 이해한다

---

## 1. 참조 자료형이란?

### 1.1 기본 자료형 vs 참조 자료형

| 구분 | 기본 자료형 | 참조 자료형 |
|------|------------|-----------|
| **종류** | byte, short, int, long, float, double, char, boolean | String, 배열, 클래스, 인터페이스 등 |
| **저장 위치** | Stack 메모리 | Heap 메모리 (참조값은 Stack) |
| **저장 값** | 실제 값 | 객체의 주소(참조) |
| **기본값** | 0, false 등 | null |
| **비교** | == 사용 (값 비교) | equals() 사용 권장 (내용 비교) |

```java
// 기본 자료형
int a = 10;  // 실제 값 10이 저장됨

// 참조 자료형
String s = "Hello";  // "Hello" 객체의 주소가 저장됨
```

---

## 2. String 클래스

### 2.1 String이란?
- **문자열**을 다루는 참조 자료형
- Java에서 가장 많이 사용되는 클래스
- 큰 따옴표("")로 표현

```java
String str1 = "Hello";        // 문자열 리터럴
String str2 = new String("Hello");  // 객체 생성
```

### 2.2 주요 메소드

```java
String str = "Hello World";

// 길이
str.length();           // 11

// 문자 추출
str.charAt(0);          // 'H'

// 부분 문자열
str.substring(0, 5);    // "Hello"

// 대소문자 변환
str.toLowerCase();      // "hello world"
str.toUpperCase();      // "HELLO WORLD"

// 포함 여부
str.contains("World");  // true

// 시작/끝 확인
str.startsWith("Hello"); // true
str.endsWith("World");   // true

// 문자열 교체
str.replace("World", "Java");  // "Hello Java"

// 공백 제거
"  Hello  ".trim();     // "Hello"

// 분리
str.split(" ");         // ["Hello", "World"]
```

### 2.3 String 연결
```java
String s1 = "Hello";
String s2 = "World";

// + 연산자
String s3 = s1 + " " + s2;  // "Hello World"

// concat 메소드
String s4 = s1.concat(" ").concat(s2);  // "Hello World"  ..   보통은 + 씀 
```

---

## 3. 배열 (Array)

### 3.1 배열이란?
- **같은 타입**의 데이터를 여러 개 저장하는 자료구조
- 인덱스는 **0부터 시작**
- 크기는 **고정**됨 (한 번 선언하면 변경 불가)

### 3.2 배열 선언과 초기화

```java
// 방법 1: 선언 후 크기 지정
int[] arr1 = new int[5];  // 크기 5인 배열 (기본값 0으로 초기화)

// 방법 2: 선언과 동시에 값 초기화
int[] arr2 = {1, 2, 3, 4, 5};

// 방법 3: new 키워드 사용
int[] arr3 = new int[] {1, 2, 3, 4, 5};

// 배열 타입 표기 (둘 다 가능)
int[] arr4;   // 권장
int arr5[];   // C 스타일
```

### 3.3 배열 사용

```java
int[] scores = {90, 85, 88, 92, 78};

// 인덱스로 접근
int first = scores[0];   // 90
scores[1] = 95;          // 값 변경

// 배열 길이
int length = scores.length;  // 5

// 배열 순회
for (int i = 0; i < scores.length; i++) {
    System.out.println(scores[i]);
}

// 향상된 for문
for (int score : scores) {
    System.out.println(score);
}
```

---

## 4. 참조와 null

### 4.1 null이란?
- 참조 자료형의 기본값
- "아무것도 참조하지 않음"을 의미
- 기본 자료형은 null을 가질 수 없음

```java
String str = null;      // OK (참조 자료형)
// int num = null;      // 에러! (기본 자료형)

if (str == null) {
    System.out.println("문자열이 없습니다.");
}
```

### 4.2 NullPointerException
```java
String str = null;
// str.length();  // NullPointerException 발생!

// 안전한 사용
if (str != null) {
    int len = str.length();
}
```

---

## 5. 실습 예제

### 예제 1: String 다루기
```java
package ch2_변수와자료형.part2_참조자료형;

public class StringExample {
    public static void main(String[] args) {
        String name = "홍길동";
        String email = "hong@example.com";
        
        System.out.println("=== String 메소드 실습 ===");
        System.out.println("이름: " + name);
        System.out.println("이름 길이: " + name.length());
        System.out.println("이메일: " + email);
        System.out.println("대문자: " + email.toUpperCase());
        
        // 문자열 연결
        String greeting = "안녕하세요, " + name + "님!";
        System.out.println(greeting);
        
        // 포함 여부 확인
        if (email.contains("@")) {
            System.out.println("올바른 이메일 형식입니다.");
        }
        
        // 문자열 분리
        String[] parts = email.split("@");
        System.out.println("아이디: " + parts[0]);
        System.out.println("도메인: " + parts[1]);
    }
}
```

### 예제 2: 배열 사용하기
```java
package ch2_변수와자료형.part2_참조자료형;

public class ArrayExample {
    public static void main(String[] args) {
        // 학생 점수 배열
        int[] scores = {88, 92, 85, 90, 78};
        
        System.out.println("=== 학생 점수 관리 ===");
        System.out.println("학생 수: " + scores.length);
        
        // 모든 점수 출력
        System.out.println("\n전체 점수:");
        for (int i = 0; i < scores.length; i++) {
            System.out.println((i + 1) + "번 학생: " + scores[i] + "점");
        }
        
        // 합계와 평균
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        double average = (double) sum / scores.length;
        
        System.out.println("\n합계: " + sum);
        System.out.println("평균: " + average);
        
        // 최고점 찾기
        int max = scores[0];
        for (int score : scores) {
            if (score > max) {
                max = score;
            }
        }
        System.out.println("최고점: " + max);
    }
}
```

### 예제 3: null 처리
```java
package ch2_변수와자료형.part2_참조자료형;

public class NullExample {
    public static void main(String[] args) {
        String str1 = "Hello";
        String str2 = null;
        
        System.out.println("=== null 처리 예제 ===");
        
        // str1은 정상 동작
        System.out.println("str1 길이: " + str1.length());
        
        // str2는 null 체크 필요
        if (str2 != null) {
            System.out.println("str2 길이: " + str2.length());
        } else {
            System.out.println("str2는 null입니다.");
        }
        
        // 배열도 참조 자료형
        int[] arr = null;
        if (arr != null) {
            System.out.println("배열 길이: " + arr.length);
        } else {
            System.out.println("배열이 초기화되지 않았습니다.");
        }
    }
}
```

---

## 6. 정리

### 핵심 개념
✅ 참조 자료형은 **객체의 주소**를 저장  
✅ String은 **문자열**을 다루는 클래스  
✅ 배열은 **같은 타입의 데이터**를 연속으로 저장  
✅ 참조 자료형의 기본값은 **null**  
✅ null인 객체의 메소드를 호출하면 **NullPointerException** 발생  

### 자주 사용하는 것들
- **String**: 문자열 처리
- **배열**: 여러 값을 한 번에 관리
- **null 체크**: 안전한 프로그래밍

---

## 다음 학습
- 3장. 연산자와 제어문

