---
layout: default
title: ch4 문제 답안
description: ch4 제어문과 반복문 문제 예시 답안
---

# ch4 제어문과 반복문 — 문제 예시 답안

> 답은 한 가지가 아닙니다. 아래는 예시 답안이며, 스스로 푼 뒤에 비교해 보세요.

## A. 조건문 기초

### A-1. 양수/음수/0 판별

```java
import java.util.Scanner;

public class A1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // 입력 예: -7

        if (n > 0) {
            System.out.println("양수");
        } else if (n < 0) {
            System.out.println("음수"); // 출력 예: 음수
        } else {
            System.out.println("0");
        }
    }
}
```

**핵심 포인트**: 세 가지 경우는 `if / else if / else`로 빠짐없이, 겹침 없이 나눈다. `else`가 "나머지 전부"를 담당하므로 0을 따로 검사할 필요가 없다.

### A-2. 학점(A/B/C/D/F) 출력

```java
import java.util.Scanner;

public class A2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int score = sc.nextInt(); // 입력 예: 83

        String grade;
        if (score >= 90) {
            grade = "A";
        } else if (score >= 80) {
            grade = "B";
        } else if (score >= 70) {
            grade = "C";
        } else if (score >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }
        System.out.println(grade); // 출력 예: B
    }
}
```

**핵심 포인트**: 큰 값부터 내려가며 검사하면 각 조건에 상한을 쓸 필요가 없다(`score >= 80 && score < 90` 불필요). 순서를 바꾸면 로직이 깨진다.

### A-3. 세 수의 중간값

```java
import java.util.Scanner;

public class A3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt(); // 입력 예: 3
        int b = sc.nextInt(); // 입력 예: 9
        int c = sc.nextInt(); // 입력 예: 5

        int mid;
        if ((a >= b && a <= c) || (a <= b && a >= c)) {
            mid = a;
        } else if ((b >= a && b <= c) || (b <= a && b >= c)) {
            mid = b;
        } else {
            mid = c;
        }
        System.out.println("중간값: " + mid); // 출력 예: 중간값: 5
    }
}
```

**핵심 포인트**: "a가 중간"이란 "b와 c 사이에 있다"는 뜻이므로 두 방향(`b <= a <= c` 또는 `c <= a <= b`)을 `||`로 묶는다. 같은 값이 섞여도 동작한다.

### A-4. 성인/청소년/아동 분류

```java
import java.util.Scanner;

public class A4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int age = sc.nextInt(); // 입력 예: 15  (만 나이 기준)

        if (age >= 19) {
            System.out.println("성인");
        } else if (age >= 13) {
            System.out.println("청소년"); // 출력 예: 청소년
        } else {
            System.out.println("아동");
        }
    }
}
```

**핵심 포인트**: 분류 기준(19세 이상 성인, 13~18세 청소년, 12세 이하 아동)을 코드 앞 주석이나 상수로 명시해 두면 정책이 바뀔 때 수정 지점이 분명해진다.

---

## B. switch 문제

### B-1. 월 → 계절 (전통적 switch)

```java
import java.util.Scanner;

public class B1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int month = sc.nextInt(); // 입력 예: 4

        String season;
        switch (month) {
            case 12:
            case 1:
            case 2:
                season = "겨울";
                break;
            case 3:
            case 4:
            case 5:
                season = "봄";
                break;
            case 6:
            case 7:
            case 8:
                season = "여름";
                break;
            case 9:
            case 10:
            case 11:
                season = "가을";
                break;
            default:
                season = "잘못된 월";
        }
        System.out.println(season); // 출력 예: 봄
    }
}
```

**핵심 포인트**: `break`가 없는 case는 아래로 흘러내린다(fall-through). 여기서는 12/1/2를 하나로 묶는 데 의도적으로 활용했고, 그 외에는 반드시 `break`를 붙인다.

### B-2. 요일 번호 → 요일명

```java
import java.util.Scanner;

public class B2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int day = sc.nextInt(); // 입력 예: 3

        switch (day) {
            case 1: System.out.println("월요일"); break;
            case 2: System.out.println("화요일"); break;
            case 3: System.out.println("수요일"); break; // 출력 예: 수요일
            case 4: System.out.println("목요일"); break;
            case 5: System.out.println("금요일"); break;
            case 6: System.out.println("토요일"); break;
            case 7: System.out.println("일요일"); break;
            default: System.out.println("잘못된 번호");
        }
    }
}
```

**핵심 포인트**: 하나의 값에 대한 1:1 매칭은 switch가 if 사슬보다 읽기 쉽다. `default`로 범위 밖 입력을 반드시 처리한다.

### B-3. 메뉴 선택 프로그램

```java
import java.util.Scanner;

public class B3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1:조회, 2:등록, 3:삭제");
        int menu = sc.nextInt(); // 입력 예: 2

        switch (menu) {
            case 1:
                System.out.println("조회 화면입니다");
                break;
            case 2:
                System.out.println("등록 화면입니다"); // 출력 예: 등록 화면입니다
                break;
            case 3:
                System.out.println("삭제 화면입니다");
                break;
            default:
                System.out.println("없는 메뉴입니다");
        }
    }
}
```

**핵심 포인트**: 메뉴 분기는 switch의 대표 용례다. 실제 앱에서는 각 case 본문을 메소드 호출 한 줄로 유지해야 switch가 비대해지지 않는다(ch5의 메소드 분리).

### B-4. switch expression으로 리팩터링

```java
import java.util.Scanner;

public class B4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int month = sc.nextInt(); // 입력 예: 11

        String season = switch (month) {
            case 12, 1, 2 -> "겨울";
            case 3, 4, 5 -> "봄";
            case 6, 7, 8 -> "여름";
            case 9, 10, 11 -> "가을";
            default -> "잘못된 월";
        };
        System.out.println(season); // 출력 예: 가을
    }
}
```

**핵심 포인트**: switch expression(Java 14+)은 값을 반환하고, `case 12, 1, 2 ->`처럼 다중 라벨을 지원하며, `break` 누락 버그가 원천적으로 없다. B-1과 비교하면 코드가 절반 이하로 준다.

---

## C. 반복문 기초

### C-1. 1~n 합계 (for)

```java
import java.util.Scanner;

public class C1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // 입력 예: 10

        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        System.out.println("합계: " + sum); // 출력 예: 합계: 55
    }
}
```

**핵심 포인트**: 누적 패턴의 기본형 — 누적 변수를 0으로 초기화하고 루프에서 더한다. n이 매우 커질 수 있으면 `long sum`으로 선언한다(ch3 오버플로우).

### C-2. 구구단 n단

```java
import java.util.Scanner;

public class C2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // 입력 예: 3

        for (int i = 1; i <= 9; i++) {
            System.out.println(n + " x " + i + " = " + (n * i));
        }
        // 출력 예:
        // 3 x 1 = 3
        // 3 x 2 = 6
        // ...
        // 3 x 9 = 27
    }
}
```

**핵심 포인트**: 반복 횟수(9회)가 명확하므로 `for`가 적합하다. `n * i`를 괄호로 감싸지 않으면 문자열 연결로 `= 31`처럼 붙어버리는 것에 주의.

### C-3. 1~100 홀수 합 / 짝수 합

```java
public class C3 {
    public static void main(String[] args) {
        int oddSum = 0;
        int evenSum = 0;

        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                evenSum += i;
            } else {
                oddSum += i;
            }
        }
        System.out.println("홀수 합: " + oddSum);  // 홀수 합: 2500
        System.out.println("짝수 합: " + evenSum); // 짝수 합: 2550
    }
}
```

**핵심 포인트**: 한 번의 순회에서 조건 분기로 두 누적 변수를 나눠 담는다. 루프를 두 번 도는 것보다 효율적이고 의도도 명확하다.

### C-4. 문자열의 모음 개수 세기

```java
import java.util.Scanner;

public class C4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.next(); // 입력 예: Programming

        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = Character.toLowerCase(text.charAt(i));
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count++;
            }
        }
        System.out.println("모음 개수: " + count); // 출력 예: 모음 개수: 3  (o, a, i)
    }
}
```

**핵심 포인트**: 문자열은 `charAt(i)`로 문자 단위 순회한다. 대문자 입력을 고려해 `toLowerCase`로 정규화한 뒤 비교하면 조건이 절반으로 준다.

---

## D. while / do-while

### D-1. 0이 입력될 때까지 총합 (while, 센티넬 패턴)

```java
import java.util.Scanner;

public class D1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int sum = 0;

        int n = sc.nextInt();
        while (n != 0) {
            sum += n;
            n = sc.nextInt();
        }
        System.out.println("총합: " + sum);
        // 입력 예: 5 3 7 0
        // 출력 예: 총합: 15
    }
}
```

**핵심 포인트**: 종료값(0)을 센티넬로 쓰는 전형적인 패턴. "먼저 읽고 → 검사하고 → 처리 후 다시 읽기" 순서를 지키면 종료값이 합계에 섞이지 않는다.

### D-2. 비밀번호 맞출 때까지 입력 (do-while)

```java
import java.util.Scanner;

public class D2 {
    public static void main(String[] args) {
        final String PASSWORD = "java17";
        Scanner sc = new Scanner(System.in);
        String input;

        do {
            System.out.print("비밀번호 입력: ");
            input = sc.next();
        } while (!PASSWORD.equals(input));

        System.out.println("인증 성공");
        // 입력 예: hello -> java -> java17
        // 출력 예: (세 번째 입력 후) 인증 성공
    }
}
```

**핵심 포인트**: "최소 한 번은 입력을 받아야 한다"는 요구가 do-while의 존재 이유다. 문자열 비교는 `equals`, 상수를 앞에 둬 null 안전하게 만든다.

### D-3. 메뉴 반복 출력, 0 입력 시 종료

```java
import java.util.Scanner;

public class D3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("=== 메뉴 === 1:조회 2:등록 3:삭제 0:종료");
            int menu = sc.nextInt();

            if (menu == 0) {
                System.out.println("프로그램을 종료합니다");
                break;
            }
            switch (menu) {
                case 1 -> System.out.println("조회 실행");
                case 2 -> System.out.println("등록 실행");
                case 3 -> System.out.println("삭제 실행");
                default -> System.out.println("잘못된 메뉴입니다");
            }
        }
        // 입력 예: 1 -> 조회 실행 / 9 -> 잘못된 메뉴입니다 / 0 -> 프로그램을 종료합니다
    }
}
```

**핵심 포인트**: `while (true) + break`는 콘솔 앱 메인 루프의 표준형이다. 종료 조건(0)을 switch 앞에서 먼저 처리하면 흐름이 단순해진다.

### D-4. 입력 횟수 제한 로그인 시뮬레이터

```java
import java.util.Scanner;

public class D4 {
    public static void main(String[] args) {
        final String PASSWORD = "1234";
        final int MAX_TRY = 3;
        Scanner sc = new Scanner(System.in);

        boolean success = false;
        int attempt = 0;

        while (attempt < MAX_TRY) {
            attempt++;
            System.out.print("비밀번호 (" + attempt + "/" + MAX_TRY + "): ");
            String input = sc.next();

            if (PASSWORD.equals(input)) {
                success = true;
                break;
            }
            System.out.println("불일치");
        }

        System.out.println(success ? "로그인 성공" : "계정 잠금: 시도 횟수 초과");
        // 입력 예: 1111 -> 2222 -> 3333
        // 출력 예: 불일치 x3 후 "계정 잠금: 시도 횟수 초과"
    }
}
```

**핵심 포인트**: "성공 시 즉시 탈출(break) + 횟수 조건으로 자연 종료"의 이중 종료 구조. 루프가 끝난 뒤 성공 여부를 boolean 플래그로 판정하는 것이 탐색 패턴의 기본형이다.

---

## E. break / continue

### E-1. 처음 등장하는 음수의 인덱스 찾기 (break)

(ch5 학습 후 — 배열은 ch5에서 배웁니다)

```java
public class E1 {
    public static void main(String[] args) {
        int[] arr = {3, 8, 2, -5, 9, -1};

        int foundIndex = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                foundIndex = i;
                break; // 첫 음수를 찾았으므로 더 볼 필요 없음
            }
        }
        System.out.println("첫 음수 인덱스: " + foundIndex); // 첫 음수 인덱스: 3
    }
}
```

**핵심 포인트**: 탐색 패턴에서는 찾는 즉시 `break`로 탈출해 불필요한 반복을 없앤다. "없으면 -1" 규약을 위해 결과 변수를 -1로 초기화한다.

### E-2. 3의 배수만 건너뛰고 출력 (continue)

```java
public class E2 {
    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            if (i % 3 == 0) {
                continue; // 3의 배수는 이번 회차만 건너뜀
            }
            System.out.println(i);
        }
        // 출력 예: 1 2 4 5 7 8 10 11 ... 98 100 (3, 6, 9, ...는 출력 안 됨)
    }
}
```

**핵심 포인트**: `continue`는 "제외 조건을 앞에서 걸러내는" 필터 패턴에 적합하다. `break`(루프 전체 종료)와 달리 다음 회차로 계속 진행한다.

### E-3. 이중 반복문에서 바깥 루프까지 종료 (label break)

```java
public class E3 {
    public static void main(String[] args) {
        int target = 12;

        outer:
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (i * j == target) {
                    System.out.println("발견: " + i + " x " + j + " = " + target);
                    break outer; // 안쪽만이 아니라 바깥 루프까지 즉시 종료
                }
            }
        }
        // 출력: 발견: 2 x 6 = 12
        // (label 없이 break만 쓰면 i=3,4,6에서도 다시 발견해 여러 번 출력됨)
    }
}
```

**핵심 포인트**: 일반 `break`는 가장 안쪽 루프만 종료한다. 바깥까지 한 번에 탈출하려면 라벨을 쓰되, 남용하면 흐름 추적이 어려워지므로 메소드 분리 + `return`도 대안으로 고려한다.

### E-4. 소수 판별 루프에 break 적용

```java
import java.util.Scanner;

public class E4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // 입력 예: 97

        boolean isPrime = n >= 2;
        for (int i = 2; i <= n / i; i++) { // i * i <= n 과 같음 (오버플로우 안전형)
            if (n % i == 0) {
                isPrime = false;
                break; // 약수를 하나라도 찾으면 더 검사할 필요 없음
            }
        }
        System.out.println(n + (isPrime ? "은(는) 소수" : "은(는) 소수 아님"));
        // 출력 예: 97은(는) 소수
        // 입력 예: 91 -> 91은(는) 소수 아님 (7 x 13)
    }
}
```

**핵심 포인트**: 약수 발견 즉시 `break`하고, 검사 범위를 `i*i <= n`(제곱근)까지로 줄이는 두 가지 최적화로 반복 횟수가 크게 감소한다.

---

## F. 패턴/응용

### F-1. 별 피라미드(정삼각형) 출력

```java
import java.util.Scanner;

public class F1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int height = sc.nextInt(); // 입력 예: 5

        for (int i = 1; i <= height; i++) {
            for (int j = 0; j < height - i; j++) {
                System.out.print(" ");   // 공백: height - i 개
            }
            for (int j = 0; j < 2 * i - 1; j++) {
                System.out.print("*");   // 별: 2i - 1 개
            }
            System.out.println();
        }
        // 출력 예:
        //     *
        //    ***
        //   *****
        //  *******
        // *********
    }
}
```

**핵심 포인트**: 도형 출력은 "행별로 공백 개수/별 개수의 규칙"을 먼저 표로 정리하는 것이 요령이다. i행 = 공백 `height-i` + 별 `2i-1`.

### F-2. 2차원 배열의 대각선 합

(ch5 학습 후 — 2차원 배열은 ch5에서 배웁니다)

```java
public class F2 {
    public static void main(String[] args) {
        int[][] m = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        int n = m.length;

        int mainDiag = 0; // 주대각선 (왼쪽 위 -> 오른쪽 아래)
        int antiDiag = 0; // 반대각선 (오른쪽 위 -> 왼쪽 아래)
        for (int i = 0; i < n; i++) {
            mainDiag += m[i][i];
            antiDiag += m[i][n - 1 - i];
        }
        System.out.println("주대각선 합: " + mainDiag); // 주대각선 합: 15 (1+5+9)
        System.out.println("반대각선 합: " + antiDiag); // 반대각선 합: 15 (3+5+7)
    }
}
```

**핵심 포인트**: 대각선 요소의 인덱스 규칙은 주대각선 `[i][i]`, 반대각선 `[i][n-1-i]`. 이중 루프가 아니라 단일 루프로 충분하다.

### F-3. 로또 번호(중복 없는 6개) 생성

(ch5 학습 후 — 배열은 ch5에서 배웁니다)

```java
public class F3 {
    public static void main(String[] args) {
        int[] lotto = new int[6];
        int count = 0;

        while (count < 6) {
            int num = (int) (Math.random() * 45) + 1; // 1~45 난수

            boolean duplicated = false;
            for (int i = 0; i < count; i++) { // 이미 뽑은 번호인지 검사
                if (lotto[i] == num) {
                    duplicated = true;
                    break;
                }
            }
            if (!duplicated) {
                lotto[count] = num;
                count++;
            }
        }

        for (int n : lotto) {
            System.out.print(n + " ");
        }
        System.out.println();
        // 출력 예: 7 23 41 3 15 32  (실행마다 다름, 중복 없음)
    }
}
```

**핵심 포인트**: "뽑고 → 기존 것과 비교 → 중복이면 버리고 다시"의 재시도 루프. 채워진 개수(`count`)까지만 비교하는 것이 포인트다.

### F-4. 숫자 야구 입력 검증 루프

```java
import java.util.Scanner;

public class F4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int number;

        while (true) {
            System.out.print("서로 다른 1~9 숫자 3자리 입력: ");
            number = sc.nextInt();

            if (number < 111 || number > 999) {
                System.out.println("3자리 수가 아닙니다");
                continue;
            }
            int d1 = number / 100;        // 백의 자리
            int d2 = number / 10 % 10;    // 십의 자리
            int d3 = number % 10;         // 일의 자리

            if (d1 == 0 || d2 == 0 || d3 == 0) {
                System.out.println("0은 사용할 수 없습니다");
                continue;
            }
            if (d1 == d2 || d2 == d3 || d1 == d3) {
                System.out.println("중복된 숫자가 있습니다");
                continue;
            }
            break; // 모든 검증 통과
        }
        System.out.println("입력 확인: " + number);
        // 입력 예: 1234 -> "3자리 수가 아닙니다"
        //          505  -> "0은 사용할 수 없습니다"
        //          373  -> "중복된 숫자가 있습니다"
        //          123  -> "입력 확인: 123"
    }
}
```

**핵심 포인트**: 검증 실패마다 `continue`로 재입력을 유도하고, 모두 통과하면 `break`. "실패 조건을 하나씩 걸러내는 guard 스타일"이 중첩 if보다 읽기 쉽다.

---

## G. 실무 시나리오형

### G-1. 주문 목록의 총액, 할인, 무료 배송 여부

(ch5 학습 후 — 배열은 ch5에서 배웁니다)

```java
public class G1 {
    public static void main(String[] args) {
        int[] prices = {12000, 5500, 30000, 8000};
        final int DISCOUNT_THRESHOLD = 50000; // 5만원 이상 10% 할인
        final int FREE_SHIPPING = 30000;      // 3만원 이상 무료 배송

        int total = 0;
        for (int price : prices) {
            total += price;
        }

        int payAmount = total >= DISCOUNT_THRESHOLD
                ? (int) (total * 0.9)
                : total;
        boolean freeShipping = payAmount >= FREE_SHIPPING;

        System.out.println("총액: " + total);            // 총액: 55500
        System.out.println("결제 금액: " + payAmount);   // 결제 금액: 49950
        System.out.println("무료 배송: " + freeShipping); // 무료 배송: true
    }
}
```

**핵심 포인트**: 매직 넘버(50000, 30000)를 상수로 뽑으면 정책 변경 시 수정 지점이 한 곳이 된다. 할인 "후" 금액으로 배송 정책을 판단하는지 등 계산 순서를 요구사항으로 확정해야 한다.

### G-2. 비활성 계정만 필터링해 출력

(ch5 학습 후 — 배열은 ch5에서 배웁니다)

```java
public class G2 {
    public static void main(String[] args) {
        String[] names = {"kim", "lee", "park", "choi"};
        boolean[] active = {true, false, true, false};

        System.out.println("== 비활성 계정 ==");
        for (int i = 0; i < names.length; i++) {
            if (active[i]) {
                continue; // 활성 계정은 건너뜀 (필터 패턴)
            }
            System.out.println(names[i]);
        }
        // 출력:
        // == 비활성 계정 ==
        // lee
        // choi
    }
}
```

**핵심 포인트**: "관심 없는 데이터를 `continue`로 먼저 걸러내는" 필터 패턴. 조건을 뒤집어 `if (!active[i]) { ... }`로 써도 되지만, 제외 조건이 늘어날수록 continue 스타일이 중첩을 줄인다.

### G-3. 에러 로그 발견 시 알림 후 순회 중단

(ch5 학습 후 — 배열은 ch5에서 배웁니다)

```java
public class G3 {
    public static void main(String[] args) {
        String[] logs = {
            "INFO 서버 시작",
            "INFO 요청 처리",
            "ERROR DB 연결 실패",
            "INFO 요청 처리"
        };

        for (String log : logs) {
            if (log.startsWith("ERROR")) {
                System.out.println("[관리자 알림] " + log);
                break; // 첫 에러 발견 시 즉시 순회 중단
            }
        }
        // 출력: [관리자 알림] ERROR DB 연결 실패
    }
}
```

**핵심 포인트**: 탐색 패턴 + 조기 종료. 요구사항이 "모든 에러 알림"으로 바뀌면 `break`만 제거하면 된다 — 종료 조건이 코드에서 분명히 드러나는 것이 중요하다.

### G-4. 재고가 임계치보다 낮은 상품 추출

(ch5 학습 후 — 배열은 ch5에서 배웁니다)

```java
public class G4 {
    public static void main(String[] args) {
        String[] items = {"키보드", "마우스", "모니터", "케이블"};
        int[] stocks = {12, 3, 7, 1};
        final int THRESHOLD = 5;

        System.out.println("== 재고 부족(" + THRESHOLD + " 미만) ==");
        for (int i = 0; i < items.length; i++) {
            if (stocks[i] >= THRESHOLD) {
                continue;
            }
            System.out.println(items[i] + " (재고: " + stocks[i] + ")");
        }
        // 출력:
        // == 재고 부족(5 미만) ==
        // 마우스 (재고: 3)
        // 케이블 (재고: 1)
    }
}
```

**핵심 포인트**: 두 배열(상품명/재고)을 같은 인덱스로 짝지어 순회하므로 for-each가 아닌 인덱스 for가 필요하다. 임계치는 상수로 분리한다.

---

## H. 디버깅 문제

(ch5 학습 후 — 배열은 ch5에서 배웁니다)

**버그 원인**

1. **경계 조건(오프바이원)**: `i <= arr.length`는 마지막 회차에 `arr[arr.length]`를 읽어 `ArrayIndexOutOfBoundsException`이 발생한다. 유효 인덱스는 `0 ~ length-1`이므로 `i < arr.length`여야 한다.
2. **중괄호 생략 위험**: 지금은 `if` 아래 문장이 하나라 동작하지만, 나중에 한 줄을 추가하면 들여쓰기와 무관하게 `if` 밖 문장이 되어 의도와 다른 흐름이 된다.

**수정 코드**

```java
public class H1 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};

        for (int i = 0; i < arr.length; i++) { // 수정 1: <= -> <
            if (arr[i] % 2 == 0) {             // 수정 2: 중괄호 명시
                continue;
            }
            System.out.println(arr[i]);
        }
        // 출력:
        // 1
        // 3
        // 5
    }
}
```

**핵심 포인트**: 배열 순회의 기본형은 `for (int i = 0; i < arr.length; i++)`. `<=`는 예외로 즉시 드러나는 "운 좋은 버그"지만, 중괄호 생략은 나중에 터지는 "잠복 버그"라 더 위험하다.
