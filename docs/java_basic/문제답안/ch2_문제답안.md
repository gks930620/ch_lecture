---
layout: default
title: ch2 문제 답안
description: ch2 변수와 자료형 문제 예시 답안
---

# ch2 변수와 자료형 — 문제 예시 답안

> 답은 한 가지가 아닙니다. 아래는 예시 답안이며, 스스로 푼 뒤에 비교해 보세요.

## A. 기초 확인

### A-1. 정수형 4가지의 크기와 범위

| 타입 | 크기 | 범위 |
|---|---:|---|
| `byte` | 8bit (1byte) | -128 ~ 127 |
| `short` | 16bit (2byte) | -32,768 ~ 32,767 |
| `int` | 32bit (4byte) | -2,147,483,648 ~ 2,147,483,647 (약 ±21억) |
| `long` | 64bit (8byte) | 약 -922경 ~ 922경 (-2^63 ~ 2^63-1) |

**핵심 포인트**: 비트 수가 n이면 표현 범위는 -2^(n-1) ~ 2^(n-1)-1. 일반 정수는 `int`, 21억을 넘을 수 있으면 처음부터 `long`을 쓴다.

### A-2. float와 double의 정밀도 차이

둘 다 IEEE 754 부동소수점 방식으로 실수를 **근사값**으로 저장하지만, 값을 담는 비트 수가 다르다.

| 타입 | 크기 | 유효 자릿수(10진 기준) |
|---|---:|---|
| `float` (단정밀도) | 32bit | 약 6~7자리 |
| `double` (배정밀도) | 64bit | 약 15~16자리 |

`double`이 소수점 아래를 두 배 이상 정밀하게 표현할 수 있어서, 자바에서 실수 리터럴의 기본 타입도 `double`이다. 특별한 이유(메모리 절약이 중요한 대량 데이터 등)가 없으면 실수는 `double`을 쓴다.

**핵심 포인트**: 둘 다 "근사값"이라는 본질은 같다. 정밀도가 높다는 것이지 오차가 없다는 뜻이 아니다.

### A-3. `byte c = a + b;`가 컴파일 오류인 이유

```java
byte a = 10;
byte b = 20;
byte c = a + b; // 컴파일 오류!
```

자바는 `byte`, `short`, `char`끼리 산술 연산을 하는 순간 피연산자를 **int로 승격(promotion)** 시킨다. 따라서 `a + b`의 결과 타입은 `byte`가 아니라 `int`이고, 큰 타입(int)을 작은 타입(byte)에 그대로 대입할 수 없어서 오류가 난다.

```java
int c = a + b;          // 방법 1: 결과를 int로 받는다 (권장)
byte c2 = (byte)(a + b); // 방법 2: 손실을 감수하고 강제 캐스팅
```

**핵심 포인트**: "byte + byte = int". 값이 30이라 손실이 없어 보여도, 컴파일러는 타입만 보고 판단한다.

### A-4. String이 primitive가 아닌 이유

- 자바의 기본 자료형(primitive)은 `byte, short, int, long, float, double, char, boolean` **8가지로 고정**되어 있고, `String`은 여기에 포함되지 않는다.
- `String`은 여러 문자를 담는 **클래스(객체)** 다. 변수에는 문자열 값 자체가 아니라 **객체가 있는 곳의 주소(참조)** 가 저장된다.
- 그래서 참조형의 특징을 그대로 가진다: `null`을 넣을 수 있고, `length()`, `toUpperCase()` 같은 **메소드를 호출**할 수 있으며, 내용 비교는 `==`가 아닌 `equals()`로 해야 한다.

**핵심 포인트**: 리터럴 표기(`"hello"`)를 지원해서 기본형처럼 보이지만, 저장 방식(값 vs 주소) 기준으로는 명백한 참조형이다.

### A-5. 배열이 참조 자료형임을 보여주는 예제

```java
public class ArrayReferenceDemo {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int[] b = a;              // 배열이 복사되는 게 아니라 "같은 배열의 주소"가 복사됨

        b[0] = 99;                // b를 통해 값 변경

        System.out.println(a[0]); // 99  <- a도 바뀐 것처럼 보임 (사실 같은 배열)
        System.out.println(b[0]); // 99
        System.out.println(a == b); // true (같은 객체를 가리킴)
    }
}
```

**핵심 포인트**: `b = a`는 값 복사가 아니라 주소 복사다. `b[0]`을 바꿨는데 `a[0]`이 함께 바뀐다는 것이 "변수에 주소가 들어있다"는 증거다.

---

## B. 코드 작성

### B-1. 정수 2개 합/차/곱/몫/나머지

```java
public class ArithmeticDemo {
    public static void main(String[] args) {
        int a = 7;
        int b = 3;

        System.out.println("합: " + (a + b));      // 합: 10
        System.out.println("차: " + (a - b));      // 차: 4
        System.out.println("곱: " + (a * b));      // 곱: 21
        System.out.println("몫: " + (a / b));      // 몫: 2  (정수 나눗셈: 소수부 버림)
        System.out.println("나머지: " + (a % b));  // 나머지: 1
    }
}
```

**핵심 포인트**: `7 / 3`은 2.33...이 아니라 2다. 정수끼리의 나눗셈은 소수부를 버린다. 또한 `"합: " + a + b`로 쓰면 `합: 73`이 되므로 괄호가 필요하다.

### B-2. 반지름으로 원의 넓이/둘레 출력

```java
public class CircleDemo {
    public static void main(String[] args) {
        double radius = 3.0;

        double area = Math.PI * radius * radius;      // 넓이 = πr²
        double circumference = 2 * Math.PI * radius;  // 둘레 = 2πr

        System.out.printf("넓이: %.2f%n", area);          // 넓이: 28.27
        System.out.printf("둘레: %.2f%n", circumference); // 둘레: 18.85
    }
}
```

**핵심 포인트**: 원주율은 직접 3.14를 적지 말고 `Math.PI`를 쓴다. 실수 출력은 `printf`의 `%.2f`로 자릿수를 다듬으면 보기 좋다.

### B-3. `String[]` 5개 중 null 아닌 값만 출력

(ch4 학습 후 풀 수 있는 문제 — 반복문/조건문 사용)

```java
public class NullFilterDemo {
    public static void main(String[] args) {
        String[] words = {"apple", null, "banana", null, "cherry"};

        for (int i = 0; i < words.length; i++) {
            if (words[i] != null) {
                System.out.println(words[i]);
            }
        }
        // 출력:
        // apple
        // banana
        // cherry
    }
}
```

**핵심 포인트**: 참조형 배열의 요소는 초기값이 `null`일 수 있다. null인 요소에 `words[i].length()`처럼 메소드를 호출하면 NPE가 터지므로, 사용 전에 null 검사를 하는 습관이 중요하다.

### B-4. `int[]`의 합계/평균/최대/최소

(ch4 학습 후 풀 수 있는 문제 — 반복문 사용)

```java
public class ArrayStatsDemo {
    public static void main(String[] args) {
        int[] scores = {70, 85, 90, 60, 95};

        int sum = 0;
        int max = scores[0];
        int min = scores[0];

        for (int i = 0; i < scores.length; i++) {
            sum += scores[i];
            if (scores[i] > max) max = scores[i];
            if (scores[i] < min) min = scores[i];
        }

        double average = (double) sum / scores.length; // int/int가 되지 않도록 캐스팅!

        System.out.println("합계: " + sum);      // 합계: 400
        System.out.println("평균: " + average);  // 평균: 80.0
        System.out.println("최대: " + max);      // 최대: 95
        System.out.println("최소: " + min);      // 최소: 60
    }
}
```

**핵심 포인트**: 평균 계산 시 `sum / scores.length`는 정수 나눗셈이라 소수부가 버려진다. `(double) sum`으로 먼저 실수로 바꾼 뒤 나눠야 정확한 평균이 나온다. 최대/최소의 초기값은 0이 아니라 `scores[0]`으로 잡아야 안전하다(전부 음수인 배열에서도 동작).

### B-5. 문자열 내용 비교 (`==` 금지, `equals` 사용)

```java
public class StringCompareDemo {
    public static void main(String[] args) {
        String a = new String("java");
        String b = new String("java");

        System.out.println(a == b);        // false (서로 다른 객체 — 주소가 다름)
        System.out.println(a.equals(b));   // true  (내용은 같음)

        String c = "java"; // 리터럴은 String Pool에 저장됨
        String d = "java"; // 같은 리터럴은 같은 객체를 공유
        System.out.println(c == d);        // true  (풀 공유로 항상 true — 그래도 여기에 의존하면 안 됨!)
        System.out.println(c.equals(d));   // true

        // 대소문자 무시 비교
        System.out.println("Java".equalsIgnoreCase("JAVA")); // true
    }
}
```

**핵심 포인트**: `==`는 주소 비교다. 리터럴끼리는 String Pool을 공유해서 `==`도 항상 `true`가 나오기 때문에 오히려 "== 로 비교해도 되네?"라고 착각하기 쉽고, `new`로 만들면 `false`가 나온다. **문자열 내용 비교는 무조건 `equals`로 통일**한다.

---

## C. 형변환 집중

### C-1. 축소 형변환 손실 예제

```java
public class NarrowingDemo {
    public static void main(String[] args) {
        // 1) long -> int: 32bit를 넘는 비트가 잘려나감
        long big = 3_000_000_000L;
        int i = (int) big;
        System.out.println(i);      // -1294967296 (int 범위 초과로 값이 깨짐)

        // 2) double -> int: 소수부를 버림 (반올림 아님!)
        double pi = 3.99;
        int n = (int) pi;
        System.out.println(n);      // 3

        // 3) int -> byte: 하위 8bit만 남음
        int value = 130;
        byte b = (byte) value;
        System.out.println(b);      // -126 (130은 byte 최댓값 127을 넘어 래핑됨)
    }
}
```

**핵심 포인트**: `(타입)` 캐스팅은 "손실을 감수하겠다"는 개발자의 선언이다. 컴파일러는 더 이상 막아주지 않으므로, 값이 대상 타입의 범위 안에 있는지는 스스로 확인해야 한다.

### C-2. char 코드값/다음 문자/소문자 변환

```java
public class CharDemo {
    public static void main(String[] args) {
        char ch = 'A';

        System.out.println((int) ch);              // 65  ('A'의 유니코드 코드값)
        System.out.println((char) (ch + 1));       // B   (코드값 66 -> 문자로 변환)
        System.out.println((char) (ch + 32));      // a   (대문자 + 32 = 소문자)
        System.out.println(Character.toLowerCase(ch)); // a (표준 API 사용 — 실무 권장)
    }
}
```

**핵심 포인트**: `char`는 내부적으로 숫자(유니코드 번호)라서 산술 연산이 가능하다. 단, `ch + 1`의 결과는 int로 승격되므로 문자로 보려면 `(char)` 캐스팅이 필요하다.

### C-3. 정수 나눗셈 vs 실수 나눗셈

```java
public class DivisionDemo {
    public static void main(String[] args) {
        int a = 5;
        int b = 2;

        System.out.println(a / b);            // 2    (int / int -> int, 소수부 버림)
        System.out.println(a / (double) b);   // 2.5  (한쪽이 double이면 실수 나눗셈)
        System.out.println(5.0 / 2);          // 2.5  (double 리터럴 사용)

        double wrong = a / b;   // 나눗셈이 먼저 int로 계산된 뒤 대입됨
        System.out.println(wrong);            // 2.0  (2.5가 아님! 흔한 실수)
    }
}
```

**핵심 포인트**: 결과를 double 변수에 받는다고 실수 나눗셈이 되는 게 아니다. **나눗셈이 일어나는 시점**에 피연산자 중 하나가 실수여야 한다.

### C-4. 언박싱 NPE 재현 + 방어 코드

(ch4 학습 후 풀 수 있는 문제 — 방어 코드에 조건문 사용)

```java
public class UnboxingDemo {
    public static void main(String[] args) {
        Integer boxed = null;

        // int n = boxed; // 이 줄의 주석을 풀면 NullPointerException!
        //                // null인 Integer를 int로 자동 언박싱하는 순간 터진다

        // 방어 코드: 언박싱 전에 null 검사
        int safe;
        if (boxed != null) {
            safe = boxed;   // null이 아닐 때만 언박싱
        } else {
            safe = 0;       // 기본값 사용
        }
        System.out.println(safe); // 0
    }
}
```

**핵심 포인트**: `Integer`는 참조형이라 null이 될 수 있는데, `int`에 대입하는 순간 자바가 자동으로 `boxed.intValue()`를 호출한다(언박싱). null이면 그 호출에서 NPE가 발생한다. 외부에서 넘어온 래퍼 타입은 항상 null 가능성을 의심하자.

---

## D. String/배열 심화

### D-1. `String +` vs `StringBuilder` 성능 비교

(ch4 학습 후 풀 수 있는 문제 — 반복문 사용)

```java
public class ConcatPerformanceDemo {
    public static void main(String[] args) {
        int count = 50_000;

        // 1) String + : 매번 새 문자열 객체 생성
        long start1 = System.currentTimeMillis();
        String s = "";
        for (int i = 0; i < count; i++) {
            s += "a";
        }
        long time1 = System.currentTimeMillis() - start1;

        // 2) StringBuilder: 내부 버퍼에 이어붙임
        long start2 = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("a");
        }
        String result = sb.toString();
        long time2 = System.currentTimeMillis() - start2;

        System.out.println("String +      : " + time1 + "ms"); // 예: String +      : 1500ms (환경마다 다름)
        System.out.println("StringBuilder : " + time2 + "ms"); // 예: StringBuilder : 2ms    (환경마다 다름)
        System.out.println(s.length() == result.length());     // true (결과 내용은 동일)
    }
}
```

**핵심 포인트**: String은 불변이라 `+`를 할 때마다 기존 내용을 통째로 복사한 새 객체가 만들어진다(반복 횟수가 늘수록 급격히 느려짐). 반복문 안에서 문자열을 이어붙일 때는 `StringBuilder`가 정답이다.

### D-2. 가변 행 2차원 배열 합계

(ch4·ch5 학습 후 풀 수 있는 문제 — 반복문, 다차원 배열 사용)

```java
public class JaggedArrayDemo {
    public static void main(String[] args) {
        // 행마다 길이가 다른 "가변(jagged) 배열"
        int[][] scores = {
            {10, 20},
            {30, 40, 50},
            {60}
        };

        int total = 0;
        for (int i = 0; i < scores.length; i++) {
            for (int j = 0; j < scores[i].length; j++) { // 행마다 길이가 다르므로 scores[i].length 사용
                total += scores[i][j];
            }
        }
        System.out.println("합계: " + total); // 합계: 210
    }
}
```

**핵심 포인트**: 2차원 배열은 "배열을 담는 배열"이다. 각 행의 길이가 다를 수 있으므로 안쪽 반복은 반드시 `scores[i].length`를 기준으로 돌아야 `ArrayIndexOutOfBoundsException`을 피할 수 있다.

### D-3. 배열 복사 3방식 구현 및 비교

```java
import java.util.Arrays;

public class ArrayCopyDemo {
    public static void main(String[] args) {
        int[] origin = {1, 2, 3};

        // 방식 1: Arrays.copyOf — 길이 조절 가능, 가장 간편
        int[] copy1 = Arrays.copyOf(origin, origin.length);

        // 방식 2: clone — 자기 자신과 같은 길이의 복사본
        int[] copy2 = origin.clone();

        // 방식 3: System.arraycopy — 원본 일부를 대상 배열의 원하는 위치로 복사 (저수준, 세밀한 제어)
        int[] copy3 = new int[origin.length];
        System.arraycopy(origin, 0, copy3, 0, origin.length);

        // 복사본을 수정해도 원본은 영향 없음 (진짜 복사이므로)
        copy1[0] = 100;
        copy2[0] = 200;
        copy3[0] = 300;

        System.out.println(Arrays.toString(origin)); // [1, 2, 3]  <- 원본 그대로!
        System.out.println(Arrays.toString(copy1));  // [100, 2, 3]
        System.out.println(Arrays.toString(copy2));  // [200, 2, 3]
        System.out.println(Arrays.toString(copy3));  // [300, 2, 3]
    }
}
```

**핵심 포인트**: 일상적인 복사는 `Arrays.copyOf`(길이 확장/축소도 가능)가 가장 편하다. `clone`은 같은 길이 복사 전용, `System.arraycopy`는 "원본의 어디부터, 대상의 어디로, 몇 개"를 지정하는 저수준 도구다.

### D-4. 참조 복사 vs 값 복사

- **값 복사**: 기본형 변수를 대입하면 **값 자체가 복사**된다. 복사 후 두 변수는 완전히 독립적이다.

```java
int x = 10;
int y = x;   // 값 10이 복사됨
y = 99;
System.out.println(x); // 10 (x는 영향 없음)
```

- **참조 복사**: 참조형 변수를 대입하면 객체가 아니라 **주소만 복사**된다. 두 변수가 같은 객체를 가리키므로, 한쪽에서 객체 내용을 바꾸면 다른 쪽에서도 바뀐 것이 보인다.

```java
int[] a = {1, 2, 3};
int[] b = a;   // 주소만 복사 — 배열은 하나
b[0] = 99;
System.out.println(a[0]); // 99 (같은 배열이므로)
```

- 참조형에서 **내용까지 독립적인 복사본**을 원하면 `Arrays.copyOf` 등으로 새 객체를 만들어야 한다(D-3 참고).

**핵심 포인트**: "대입(`=`)은 변수 공간에 든 것을 복사한다"는 규칙은 하나다. 다만 그 공간에 든 것이 기본형은 값, 참조형은 주소라서 결과가 달라 보이는 것이다.

---

## E. 상수/리터럴/var

### E-1. `static final` 상수 5개 선언

```java
public class Constants {
    public static final int MAX_RETRY = 3;                    // 최대 재시도 횟수
    public static final int TIMEOUT_SECONDS = 30;             // 요청 제한 시간(초)
    public static final double TAX_RATE = 0.1;                // 부가세율 10%
    public static final long MAX_UPLOAD_SIZE = 10_485_760L;   // 업로드 제한 10MB (byte)
    public static final String APP_NAME = "MyShop";           // 애플리케이션 이름

    public static void main(String[] args) {
        System.out.println(APP_NAME + " 최대 재시도: " + MAX_RETRY);
        // 출력: MyShop 최대 재시도: 3

        // MAX_RETRY = 5; // 컴파일 오류: final 변수 재대입 불가
    }
}
```

**핵심 포인트**: 상수 이름은 `대문자 + 언더스코어(SCREAMING_SNAKE_CASE)` 관례를 따른다. 코드 곳곳에 3, 0.1 같은 "매직 넘버"를 흩뿌리는 대신 의미 있는 이름의 상수로 모으면 읽기 쉽고 고치기 쉬워진다.

### E-2. 같은 수를 2/8/10/16진으로 표현

```java
public class LiteralBaseDemo {
    public static void main(String[] args) {
        int binary  = 0b11010; // 2진수  (접두사 0b)
        int octal   = 032;     // 8진수  (접두사 0)
        int decimal = 26;      // 10진수
        int hex     = 0x1A;    // 16진수 (접두사 0x)

        System.out.println(binary);  // 26
        System.out.println(octal);   // 26
        System.out.println(decimal); // 26
        System.out.println(hex);     // 26
    }
}
```

**핵심 포인트**: 진법은 사람이 코드에 적는 표기법일 뿐, 메모리에는 모두 같은 2진수 값 26으로 저장되고 `println`은 10진수로 출력한다. `0`으로 시작하는 정수가 8진수로 해석된다는 점(예: `010`은 8)은 함정이니 주의.

### E-3. `var` 가독성 좋은/나쁜 사례

```java
public class VarDemo {
    public static void main(String[] args) {
        // [좋은 사례] 우변만 봐도 타입이 명확 — 중복 제거 효과
        var message = "hello java";           // 누가 봐도 String
        var builder = new StringBuilder();    // 우변에 타입 이름이 그대로 보임
        var maxRetry = 3;                     // 누가 봐도 int

        // [나쁜 사례] 우변만 봐서는 타입을 알 수 없음
        var result = calculate();     // 반환 타입이 뭐지? int? double? String?
        var data = getData();         // 메소드 이름만으로 타입을 알 수 없음

        System.out.println(message);          // hello java
        System.out.println(result);           // 42
    }

    static int calculate() { return 42; }
    static String getData() { return "data"; }
}
```

**핵심 포인트**: `var`는 타입이 없어지는 게 아니라 컴파일 시점에 확정되는 것이다. 판단 기준은 하나 — "우변만 보고 타입이 바로 보이는가?" 보이면 써도 좋고, 안 보이면 명시적 타입이 낫다. (지역 변수에서만 사용 가능, `var x = null;` 불가)

### E-4. `final` 참조 변수와 객체 불변성 차이

`final`은 **변수의 재대입**을 막을 뿐, 변수가 가리키는 **객체의 내용 변경**은 막지 않는다.

```java
final int[] arr = {1, 2, 3};

arr[0] = 99;            // OK! 객체(배열)의 내용 변경은 막지 않음
System.out.println(arr[0]); // 99

// arr = new int[]{4, 5, 6}; // 컴파일 오류: final 변수에 다른 주소를 재대입할 수 없음
```

- `final` 참조 변수 = "이 변수는 평생 **같은 객체만 가리킨다**"는 뜻 (주소 고정)
- 객체 불변성 = "객체 **내부의 값이 절대 바뀌지 않는다**"는 뜻 — 이건 객체를 만든 클래스가 결정한다 (예: `String`은 불변으로 설계된 클래스)

**핵심 포인트**: `final String[] names`라도 `names[0] = "x"`는 가능하다. "final = 불변 객체"라는 오해가 가장 흔한 함정이다.

---

## F. 챌린지

### F-1. 문자열 숫자 파싱 합계 + 예외 처리

(ch9 학습 후 풀 수 있는 문제 — 예외 처리 사용, 반복문은 ch4)

```java
public class ParseSumDemo {
    public static void main(String[] args) {
        String input = "10,20,abc,30";
        String[] tokens = input.split(",");

        int sum = 0;
        for (String token : tokens) {
            try {
                sum += Integer.parseInt(token);   // 문자열 -> int 변환
            } catch (NumberFormatException e) {
                System.out.println("숫자가 아닌 값 무시: " + token);
            }
        }
        System.out.println("합계: " + sum);
        // 출력:
        // 숫자가 아닌 값 무시: abc
        // 합계: 60
    }
}
```

**핵심 포인트**: `Integer.parseInt`는 숫자가 아닌 문자열을 만나면 `NumberFormatException`을 던진다. try-catch로 잘못된 값 하나 때문에 전체 계산이 중단되지 않도록 방어한다.

### F-2. 문자열 배열 중복 제거 + 길이 내림차순 정렬

(ch4·ch5 학습 후 풀 수 있는 문제 — 반복문, 배열 조작 사용)

```java
import java.util.Arrays;

public class DedupSortDemo {
    public static void main(String[] args) {
        String[] words = {"banana", "apple", "banana", "kiwi", "apple", "fig"};

        // 1) 중복 제거: 앞에서부터 훑으며 처음 보는 값만 임시 배열에 담기
        String[] temp = new String[words.length];
        int count = 0;
        for (String word : words) {
            boolean exists = false;
            for (int i = 0; i < count; i++) {
                if (temp[i].equals(word)) {   // 내용 비교는 반드시 equals!
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                temp[count] = word;
                count++;
            }
        }
        String[] unique = Arrays.copyOf(temp, count); // 실제 개수만큼 잘라내기

        // 2) 길이 내림차순 정렬 (선택 정렬)
        for (int i = 0; i < unique.length - 1; i++) {
            for (int j = i + 1; j < unique.length; j++) {
                if (unique[j].length() > unique[i].length()) {
                    String t = unique[i];
                    unique[i] = unique[j];
                    unique[j] = t;
                }
            }
        }

        System.out.println(Arrays.toString(unique));
        // 출력: [banana, apple, kiwi, fig]
    }
}
```

**핵심 포인트**: 중복 검사에서 `temp[i] == word`가 아니라 `equals`를 써야 한다(리터럴끼리는 풀 공유로 `==`도 동작하지만, 입력·연산으로 만든 문자열에서는 깨진다). 나중에 컬렉션(ch12)을 배우면 `Set`과 `Comparator`로 훨씬 짧게 풀 수 있다.

### F-3. 점수 배열 평균/표준편차 계산

(ch4 학습 후 풀 수 있는 문제 — 반복문 사용)

```java
public class StatisticsDemo {
    public static void main(String[] args) {
        int[] scores = {85, 90, 70, 60, 95};

        // 1) 평균: 합은 int로 모아도 되지만, 나눗셈 결과는 double로 받아야 함
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        double average = (double) sum / scores.length;

        // 2) 분산: (각 값 - 평균)² 의 평균
        double squaredDiffSum = 0.0;
        for (int score : scores) {
            double diff = score - average;
            squaredDiffSum += diff * diff;
        }
        double variance = squaredDiffSum / scores.length;

        // 3) 표준편차 = 분산의 제곱근
        double stdDev = Math.sqrt(variance);

        System.out.printf("평균: %.2f%n", average);       // 평균: 80.00
        System.out.printf("표준편차: %.2f%n", stdDev);    // 표준편차: 13.04
    }
}
```

**타입 선택 이유 — 왜 `double`인가?**

- 점수 자체는 정수라 `int[]`로 충분하지만, **평균은 400 / 5처럼 딱 떨어지지 않는 경우가 대부분**이다. `int`로 계산하면 소수부가 버려져 통계값이 왜곡된다.
- 표준편차 계산에는 편차의 제곱, 나눗셈, `Math.sqrt`(제곱근)가 들어가는데, 이 값들은 본질적으로 실수다. `Math.sqrt`의 반환 타입도 `double`이다.
- `float`가 아닌 `double`을 쓰는 이유: `double`은 유효 자릿수가 약 15~16자리로 `float`(6~7자리)보다 정밀하고, 자바 실수 연산의 **기본 타입**이라 캐스팅 없이 자연스럽게 계산된다. 통계처럼 곱셈/나눗셈이 반복되는 계산은 오차가 누적되므로 정밀도가 높은 쪽이 안전하다.
- 단, 돈 계산처럼 오차가 한 푼도 허용되지 않는 곳이라면 `double`도 부적합하며 `BigDecimal`(ch10)을 검토해야 한다. 통계값은 근사여도 충분하므로 `double`이 적절하다.

**핵심 포인트**: "저장은 int, 계산 결과는 double"이 이 문제의 핵심 타입 설계다. `(double) sum / scores.length`에서 캐스팅을 빼먹으면 평균이 80.0이 아니라 80(정수 나눗셈)으로 계산되는 함정에 주의.

---

## 제출 체크리스트 대조

1. 경계값 테스트 — C-1에서 `Integer.MAX_VALUE` 초과, byte 범위 초과(130) 확인
2. 문자열 비교 — B-5, F-2 모두 `equals` 사용
3. 축소 형변환 손실 — C-1에서 3가지 손실 패턴 직접 확인
4. `var` 가독성 — E-3에서 좋은/나쁜 사례 구분
