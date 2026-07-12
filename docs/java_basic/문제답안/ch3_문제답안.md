---
layout: default
title: ch3 문제 답안
description: ch3 연산자 문제 예시 답안
---

# ch3 연산자 — 문제 예시 답안

> 답은 한 가지가 아닙니다. 아래는 예시 답안이며, 스스로 푼 뒤에 비교해 보세요.

## A. 기초 문제

### A-1. 두 정수의 합, 차, 곱, 몫, 나머지

```java
import java.util.Scanner;

public class A1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt(); // 입력 예: 7
        int b = sc.nextInt(); // 입력 예: 3

        System.out.println("합: " + (a + b));   // 출력 예: 합: 10
        System.out.println("차: " + (a - b));   // 출력 예: 차: 4
        System.out.println("곱: " + (a * b));   // 출력 예: 곱: 21
        System.out.println("몫: " + (a / b));   // 출력 예: 몫: 2
        System.out.println("나머지: " + (a % b)); // 출력 예: 나머지: 1
    }
}
```

**핵심 포인트**: `"합: " + a + b`처럼 쓰면 문자열 연결이 먼저 일어나 `합: 73`이 된다. 산술 결과를 붙일 때는 반드시 괄호로 감싼다.

### A-2. 세 점수의 평균과 PASS/FAIL

```java
import java.util.Scanner;

public class A2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int s1 = sc.nextInt(); // 입력 예: 70
        int s2 = sc.nextInt(); // 입력 예: 55
        int s3 = sc.nextInt(); // 입력 예: 65

        double avg = (s1 + s2 + s3) / 3.0; // 3이 아니라 3.0으로 나눠야 실수 평균
        String result = avg >= 60 ? "PASS" : "FAIL";

        System.out.println("평균: " + avg);   // 출력 예: 평균: 63.333333333333336
        System.out.println(result);           // 출력 예: PASS
    }
}
```

**핵심 포인트**: `(s1+s2+s3) / 3`은 정수 나눗셈이라 소수부가 버려진다. `3.0`으로 나눠 실수 나눗셈을 강제한다.

### A-3. 짝수/홀수 판별

```java
import java.util.Scanner;

public class A3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // 입력 예: 13

        String result = n % 2 == 0 ? "짝수" : "홀수";
        System.out.println(result); // 출력 예: 홀수
    }
}
```

**핵심 포인트**: 음수 홀수는 `n % 2`가 `-1`이므로 `== 1` 대신 `== 0`(짝수 기준) 또는 `!= 0`으로 판별하는 것이 안전하다.

### A-4. 일반 요금 / 우대 요금

```java
import java.util.Scanner;

public class A4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int age = sc.nextInt(); // 입력 예: 70

        String fare = (age >= 20 && age < 65) ? "일반 요금" : "우대 요금";
        System.out.println(fare); // 출력 예: 우대 요금
    }
}
```

**핵심 포인트**: "20 이상 65 미만"은 `>= 20 && < 65`. 경계값(20, 64, 65)을 넣어 조건을 검증하는 습관이 중요하다.

---

## B. 비교/논리 연산

### B-1. 아이디/비밀번호 로그인

(ch4 학습 후 — `if` 조건문을 사용합니다. 삼항 연산자만으로도 풀 수 있습니다)

```java
import java.util.Scanner;

public class B1 {
    public static void main(String[] args) {
        final String ID = "admin";
        final String PW = "1234";

        Scanner sc = new Scanner(System.in);
        String id = sc.next(); // 입력 예: admin
        String pw = sc.next(); // 입력 예: 1234

        if (ID.equals(id) && PW.equals(pw)) {
            System.out.println("로그인 성공"); // 출력 예: 로그인 성공
        } else {
            System.out.println("로그인 실패");
        }
    }
}
```

**핵심 포인트**: 문자열 비교는 `==`가 아닌 `equals`. 상수를 앞에 두면(`ID.equals(id)`) 입력이 `null`이어도 NPE가 나지 않는다.

### B-2. 세 정수의 최댓값/최솟값 (Math.max/min 금지)

(ch4 학습 후 — `if` 조건문을 사용합니다. 삼항 연산자 중첩으로도 풀 수 있습니다)

```java
import java.util.Scanner;

public class B2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt(); // 입력 예: 3
        int b = sc.nextInt(); // 입력 예: 9
        int c = sc.nextInt(); // 입력 예: 5

        int max = a;
        if (b > max) max = b;
        if (c > max) max = c;

        int min = a;
        if (b < min) min = b;
        if (c < min) min = c;

        System.out.println("최댓값: " + max); // 출력 예: 최댓값: 9
        System.out.println("최솟값: " + min); // 출력 예: 최솟값: 3
    }
}
```

**핵심 포인트**: "첫 값을 후보로 두고 갱신"하는 패턴은 값이 몇 개로 늘어나도 그대로 확장된다. 삼항 연산자 `(a > b ? a : b)`를 중첩하는 방법도 있지만 가독성이 떨어진다.

### B-3. 삼각형 성립 여부

```java
import java.util.Scanner;

public class B3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt(); // 입력 예: 3
        int b = sc.nextInt(); // 입력 예: 4
        int c = sc.nextInt(); // 입력 예: 5

        boolean positive = a > 0 && b > 0 && c > 0;
        boolean triangle = positive
                && a + b > c
                && b + c > a
                && a + c > b;

        System.out.println(triangle ? "삼각형 가능" : "삼각형 불가"); // 출력 예: 삼각형 가능
    }
}
```

**핵심 포인트**: 삼각형 성립 조건은 "가장 긴 변 < 나머지 두 변의 합". 어떤 변이 가장 긴지 모르면 세 부등식을 모두 `&&`로 묶으면 된다.

### B-4. 윤년 판별

```java
import java.util.Scanner;

public class B4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int year = sc.nextInt(); // 입력 예: 2024

        boolean leap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

        System.out.println(year + "년: " + (leap ? "윤년" : "평년")); // 출력 예: 2024년: 윤년
        // 2100 -> 평년, 2000 -> 윤년
    }
}
```

**핵심 포인트**: "4의 배수이면서 100의 배수가 아니거나, 400의 배수". `&&`가 `||`보다 우선순위가 높지만, 괄호로 의도를 명시하는 편이 좋다.

---

## C. 실수/정밀도

### C-1. `0.1 + 0.2 == 0.3` 비교 결과

```java
public class C1 {
    public static void main(String[] args) {
        double a = 0.1 + 0.2;
        System.out.println(a);          // 0.30000000000000004
        System.out.println(a == 0.3);   // false
    }
}
```

**설명**: `double`은 IEEE 754 2진 부동소수점이다. 0.1, 0.2, 0.3은 2진수로 정확히 표현할 수 없는 무한소수라서 각각 가장 가까운 근사값으로 저장된다. 근사값끼리의 덧셈 결과(0.30000000000000004)는 0.3의 근사값과 비트가 다르므로 `==` 비교가 `false`가 된다.

**핵심 포인트**: 실수 `==` 비교는 원칙적으로 금지. 허용 오차 비교(C-2)나 `BigDecimal`을 사용한다.

### C-2. 허용 오차(EPSILON) 기반 실수 비교 함수

(ch5 학습 후 — 메소드 작성은 ch5에서 배웁니다)

```java
public class C2 {
    static final double EPSILON = 1e-9;

    static boolean nearlyEquals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    public static void main(String[] args) {
        double sum = 0.1 + 0.2;
        System.out.println(sum == 0.3);              // false
        System.out.println(nearlyEquals(sum, 0.3));  // true
        System.out.println(nearlyEquals(1.0, 1.5));  // false
    }
}
```

**핵심 포인트**: "두 값의 차이가 아주 작으면 같다고 본다"는 발상이다. EPSILON은 도메인에 맞게 정한다(금액이면 `BigDecimal`이 정답).

### C-3. 정수 나눗셈 vs 실수 나눗셈 예제 3개

```java
public class C3 {
    public static void main(String[] args) {
        // 예제 1: 몫이 버려지는 경우
        System.out.println(7 / 2);     // 3
        System.out.println(7 / 2.0);   // 3.5

        // 예제 2: 결과가 0이 되어버리는 경우 (비율 계산 버그의 단골)
        System.out.println(1 / 3);         // 0
        System.out.println(1.0 / 3);       // 0.3333333333333333

        // 예제 3: 평균 계산
        int s1 = 90, s2 = 85;
        System.out.println((s1 + s2) / 2);     // 87  (87.5의 소수부 버려짐)
        System.out.println((s1 + s2) / 2.0);   // 87.5
    }
}
```

**핵심 포인트**: 피연산자 중 하나라도 실수면 실수 나눗셈이 된다. "정수 / 정수 = 정수"라는 규칙을 잊으면 비율/평균 계산에서 조용한 버그가 생긴다.

---

## D. 비트/시프트 연산

### D-1. 2진수 문자열과 시프트 결과

```java
import java.util.Scanner;

public class D1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // 입력 예: 10

        System.out.println("2진수: " + Integer.toBinaryString(n)); // 출력 예: 2진수: 1010
        System.out.println("<< 1 : " + (n << 1));   // 출력 예: << 1 : 20
        System.out.println(">> 1 : " + (n >> 1));   // 출력 예: >> 1 : 5
        System.out.println(">>> 1: " + (n >>> 1));  // 출력 예: >>> 1: 5

        // 입력 예: -10 이면
        // 2진수: 11111111111111111111111111110110
        // << 1 : -20, >> 1 : -5, >>> 1: 2147483643
    }
}
```

**핵심 포인트**: `<< 1`은 ×2, `>> 1`은 ÷2(부호 유지)와 같다. 양수에서는 `>>`와 `>>>`가 같지만, 음수에서는 `>>>`가 부호 비트 자리를 0으로 채워 큰 양수가 된다.

### D-2. 비트 마스크 권한 플래그

```java
public class D2 {
    static final int READ    = 0b001; // 1
    static final int WRITE   = 0b010; // 2
    static final int EXECUTE = 0b100; // 4

    public static void main(String[] args) {
        int perm = 0;

        perm |= READ;           // 권한 부여(조합)
        perm |= WRITE;
        System.out.println("현재 권한: " + Integer.toBinaryString(perm)); // 현재 권한: 11

        System.out.println("READ 있음? " + ((perm & READ) != 0));       // READ 있음? true
        System.out.println("EXECUTE 있음? " + ((perm & EXECUTE) != 0)); // EXECUTE 있음? false

        perm &= ~WRITE;         // 권한 해제
        System.out.println("WRITE 해제 후: " + Integer.toBinaryString(perm)); // WRITE 해제 후: 1
        System.out.println("WRITE 있음? " + ((perm & WRITE) != 0));    // WRITE 있음? false
    }
}
```

**핵심 포인트**: 부여는 `|=`, 해제는 `&= ~FLAG`, 검사는 `(perm & FLAG) != 0`. 플래그 하나가 비트 하나를 차지하도록 1, 2, 4, 8... 값을 쓴다.

### D-3. RGB 정수값에서 R, G, B 분리

```java
public class D3 {
    public static void main(String[] args) {
        int color = 0xFF8040; // R=FF, G=80, B=40

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        System.out.println("R: " + r); // R: 255
        System.out.println("G: " + g); // G: 128
        System.out.println("B: " + b); // B: 64
    }
}
```

**핵심 포인트**: 원하는 바이트가 최하위로 오도록 시프트한 뒤 `& 0xFF`로 하위 8비트만 남긴다. "시프트로 이동 + 마스크로 절단"이 비트 파싱의 기본 공식이다.

### D-4. 음수 시프트에서 `>>` vs `>>>`

```java
public class D4 {
    public static void main(String[] args) {
        int x = -8;
        // -8의 32비트 표현: 11111111 11111111 11111111 11111000

        System.out.println(x >> 1);   // -4  (부호 비트 1로 채움 -> 음수 유지)
        System.out.println(x >>> 1);  // 2147483644  (0으로 채움 -> 큰 양수)

        System.out.println(Integer.toBinaryString(x >> 1));
        // 11111111111111111111111111111100
        System.out.println(Integer.toBinaryString(x >>> 1));
        // 1111111111111111111111111111100 (31비트, 맨 앞이 0)
    }
}
```

**핵심 포인트**: `>>`는 산술 시프트(부호 유지, ÷2 효과), `>>>`는 논리 시프트(무조건 0 채움). 해시/비트 포맷 처리처럼 "부호 없는 비트열"로 다뤄야 할 때 `>>>`를 쓴다.

---

## E. 대입/증감/삼항

### E-1. 전위/후위 증감 예제 5개 (예측 후 검증)

```java
public class E1 {
    public static void main(String[] args) {
        // 1) 전위: 먼저 증가시키고 그 값을 사용
        int i1 = 5;
        int a = ++i1;
        System.out.println(a + ", " + i1); // 6, 6

        // 2) 후위: 현재 값을 먼저 사용하고 나서 증가
        int i2 = 5;
        int b = i2++;
        System.out.println(b + ", " + i2); // 5, 6

        // 3) 출력식 안의 후위
        int i3 = 5;
        System.out.println(i3++); // 5
        System.out.println(i3);   // 6

        // 4) 출력식 안의 전위
        int i4 = 5;
        System.out.println(++i4); // 6

        // 5) 한 식에 섞인 경우 (왼쪽에서 오른쪽으로 평가)
        int i5 = 5;
        int c = i5++ + ++i5; // 5 + 7
        System.out.println(c + ", " + i5); // 12, 7
    }
}
```

**핵심 포인트**: 전위는 "증가 후 사용", 후위는 "사용 후 증가". 5번처럼 한 식에 섞으면 맞힐 수는 있어도 읽는 사람이 고통받으므로 실무에서는 증감을 별도 문장으로 분리한다.

### E-2. `s += 1;`은 되는데 `s = s + 1;`이 안 되는 이유

```java
public class E2 {
    public static void main(String[] args) {
        short s = 1;
        s += 1;              // OK: s = (short)(s + 1) 로 컴파일됨
        // s = s + 1;        // 컴파일 오류: s + 1의 결과 타입은 int
        s = (short) (s + 1); // 명시적 캐스팅을 하면 통과
        System.out.println(s); // 3
    }
}
```

**설명**: `short + int` 산술 연산의 결과는 `int`로 승격되므로 `s = s + 1;`은 "int를 short에 대입"이 되어 컴파일 오류다. 반면 복합 대입 `s += 1;`은 언어 명세상 `s = (short)(s + 1);`처럼 **결과를 왼쪽 타입으로 자동 캐스팅**하는 코드로 정의되어 있어 통과한다.

**핵심 포인트**: 복합 대입에는 숨은 캐스팅이 있다. 편리하지만 오버플로우/소수부 절단을 조용히 삼킬 수 있으니 타입 변화를 항상 의식해야 한다(E-4 참고).

### E-3. 중첩 삼항 연산자 → if-else 리팩터링

```java
public class E3 {
    public static void main(String[] args) {
        int score = 85;

        // 리팩터링 전: 읽기 어려운 중첩 삼항
        String grade1 = score >= 90 ? "A" : score >= 80 ? "B" : score >= 70 ? "C" : "F";

        // 리팩터링 후: if-else if
        String grade2;
        if (score >= 90) {
            grade2 = "A";
        } else if (score >= 80) {
            grade2 = "B";
        } else if (score >= 70) {
            grade2 = "C";
        } else {
            grade2 = "F";
        }

        System.out.println(grade1); // B
        System.out.println(grade2); // B
    }
}
```

**핵심 포인트**: 삼항 연산자는 "단일 분기값 선택" 한 단계까지만. 두 번 이상 중첩되면 `if-else`(또는 ch4의 switch)로 바꾸는 것이 가독성 면에서 낫다.

### E-4. 복합 대입 연산의 타입 변화 추적

```java
public class E4 {
    public static void main(String[] args) {
        // 1) byte += int : 내부에서 (byte) 캐스팅
        byte b = 10;
        b += 5; // b = (byte)(b + 5)
        System.out.println(b); // 15

        // 2) int *= double : 실수 곱셈 후 (int)로 절단!
        int n = 7;
        n *= 1.5; // n = (int)(7 * 1.5) = (int)10.5
        System.out.println(n); // 10

        // 3) char += int : 문자 코드 이동
        char c = 'A';
        c += 1; // c = (char)('A' + 1)
        System.out.println(c); // B

        // 4) byte 오버플로우가 조용히 발생
        byte big = 120;
        big += 10; // (byte)130 -> 래핑
        System.out.println(big); // -126
    }
}
```

**핵심 포인트**: 복합 대입은 항상 "연산 → 왼쪽 타입으로 캐스팅" 순서다. 2번처럼 소수부가 잘리거나 4번처럼 오버플로우가 나도 컴파일러가 경고하지 않으므로 주의한다.

---

## F. 디버깅 문제

(ch4 학습 후 — 반복문 `for`가 포함된 코드입니다)

**버그 원인**

1. **문자열 비교를 `==`로 수행**: `new String("ADMIN")`은 새 객체를 만들므로 리터럴 `"ADMIN"`과 참조가 다르다. `role == "ADMIN"`은 `false`가 되어 "관리자"가 출력되지 않는다. 내용 비교는 `equals`를 써야 한다.
2. **정수 오버플로우**: 1부터 1,000,000까지의 합은 500,000,500,000으로 `int` 최대값(약 21억)을 훨씬 넘는다. `int total`은 wrap-around 되어 `1784293664` 같은 엉뚱한 값이 출력된다.

**수정 코드**

```java
public class F1 {
    public static void main(String[] args) {
        String role = new String("ADMIN");
        if ("ADMIN".equals(role)) {   // 수정 1: equals로 내용 비교
            System.out.println("관리자"); // 출력: 관리자
        }

        long total = 0L;              // 수정 2: long으로 누적
        for (int i = 1; i <= 1000000; i++) {
            total += i;
        }
        System.out.println(total);    // 출력: 500000500000
    }
}
```

**핵심 포인트**: 문자열은 `equals`(상수를 앞에 두면 null 안전), 큰 누적 합계는 처음부터 `long`. 두 가지 모두 "컴파일은 되지만 결과가 틀리는" 대표 버그다.

---

## G. 챌린지

### G-1. 계산기 프로그램

(ch4 학습 후 — `if` 조건 분기를 사용합니다. ch4의 switch로 바꾸면 더 깔끔해집니다)

```java
import java.util.Scanner;

public class G1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int a = sc.nextInt();       // 입력 예: 10
        int b = sc.nextInt();       // 입력 예: 3
        String op = sc.next();      // 입력 예: /

        if (op.equals("+")) {
            System.out.println(a + b);
        } else if (op.equals("-")) {
            System.out.println(a - b);
        } else if (op.equals("*")) {
            System.out.println(a * b);
        } else if (op.equals("/")) {
            if (b == 0) {
                System.out.println("오류: 0으로 나눌 수 없습니다");
            } else {
                System.out.println(a / b); // 출력 예: 3
            }
        } else if (op.equals("%")) {
            if (b == 0) {
                System.out.println("오류: 0으로 나눌 수 없습니다");
            } else {
                System.out.println(a % b);
            }
        } else {
            System.out.println("오류: 지원하지 않는 연산자입니다 (" + op + ")");
        }
        // 입력 예: 10 0 /  -> 출력: 오류: 0으로 나눌 수 없습니다
        // 입력 예: 10 3 ^  -> 출력: 오류: 지원하지 않는 연산자입니다 (^)
    }
}
```

**핵심 포인트**: 정상 흐름보다 예외 입력(0 나눗셈, 잘못된 연산자) 처리가 먼저 설계되어야 한다. ch4의 switch를 배우면 이 분기를 더 선언적으로 바꿀 수 있다.

### G-2. 비트 연산만 사용한 홀짝 판별, 절댓값 근사

(ch5 학습 후 — 메소드 작성은 ch5에서 배웁니다)

```java
public class G2 {
    // 최하위 비트가 1이면 홀수 (음수도 2의 보수 표현상 동일하게 동작)
    static boolean isOdd(int n) {
        return (n & 1) == 1;
    }

    // 절댓값: mask = n >> 31 (양수면 0, 음수면 -1)
    // 음수일 때 (n ^ mask) - mask = (~n) + 1 = -n
    static int abs(int n) {
        int mask = n >> 31;
        return (n ^ mask) - mask;
    }

    public static void main(String[] args) {
        System.out.println(isOdd(7));   // true
        System.out.println(isOdd(-3));  // true
        System.out.println(isOdd(10));  // false

        System.out.println(abs(5));    // 5
        System.out.println(abs(-5));   // 5
        System.out.println(abs(0));    // 0
        // 주의: abs(Integer.MIN_VALUE)는 표현 불가로 그대로 MIN_VALUE가 나온다 (Math.abs도 동일)
    }
}
```

**핵심 포인트**: `n % 2` 대신 `n & 1`은 최하위 비트만 본다. 절댓값 트릭은 "음수의 2의 보수 = 비트 반전 + 1"을 이용한 것이며, `Integer.MIN_VALUE`는 절댓값을 int로 표현할 수 없다는 한계까지 알아두면 좋다.

### G-3. 로그 시스템 상태 코드 비트 플래그 설계

(ch5 학습 후 — 메소드 작성은 ch5에서 배웁니다)

```java
public class G3 {
    static final int DEBUG = 1 << 0; // 0001
    static final int INFO  = 1 << 1; // 0010
    static final int WARN  = 1 << 2; // 0100
    static final int ERROR = 1 << 3; // 1000

    // 플래그 조합(켜기)
    static int enable(int status, int flag) {
        return status | flag;
    }

    // 플래그 해제(끄기)
    static int disable(int status, int flag) {
        return status & ~flag;
    }

    // 플래그 검사
    static boolean isEnabled(int status, int flag) {
        return (status & flag) != 0;
    }

    public static void main(String[] args) {
        int status = 0;

        status = enable(status, INFO);
        status = enable(status, ERROR);
        System.out.println(Integer.toBinaryString(status)); // 1010

        System.out.println(isEnabled(status, ERROR)); // true
        System.out.println(isEnabled(status, DEBUG)); // false

        status = disable(status, ERROR);
        System.out.println(isEnabled(status, ERROR)); // false
        System.out.println(Integer.toBinaryString(status)); // 10
    }
}
```

**핵심 포인트**: 플래그 값을 `1 << n`으로 정의하면 비트 위치가 한눈에 보인다. int 하나로 32개 상태를 관리할 수 있어 로그 레벨/권한/옵션 설계에 널리 쓰이는 패턴이다.
