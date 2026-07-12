---
layout: default
title: ch5 문제 답안
description: ch5 메소드/스코프/배열 문제 예시 답안
---

# ch5 배열 — 문제 예시 답안

> 답은 한 가지가 아닙니다. 아래는 예시 답안이며, 스스로 푼 뒤에 비교해 보세요.

## A. 메소드 기초

### A-1. 두 정수의 합/차/곱/몫을 반환하는 메소드

```java
public class A1 {
    static int add(int a, int b) {
        return a + b;
    }

    static int subtract(int a, int b) {
        return a - b;
    }

    static int multiply(int a, int b) {
        return a * b;
    }

    static int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("0으로 나눌 수 없습니다");
        }
        return a / b;
    }

    public static void main(String[] args) {
        System.out.println(add(7, 3));      // 10
        System.out.println(subtract(7, 3)); // 4
        System.out.println(multiply(7, 3)); // 21
        System.out.println(divide(7, 3));   // 2
    }
}
```

**핵심 포인트**: 메소드는 "한 가지 책임 + 명확한 입력/출력". 나눗셈처럼 실패할 수 있는 연산은 guard clause로 예외 조건을 먼저 배제한다.

### A-2. 문자열 길이가 8 이상이면 true

```java
public class A2 {
    static boolean isLongEnough(String s) {
        return s != null && s.length() >= 8;
    }

    public static void main(String[] args) {
        System.out.println(isLongEnough("password123")); // true
        System.out.println(isLongEnough("short"));       // false
        System.out.println(isLongEnough(null));          // false (NPE 없이 안전)
    }
}
```

**핵심 포인트**: boolean 반환 메소드는 `if (cond) return true; else return false;` 대신 조건식 자체를 반환한다. `null` 검사를 `&&` 왼쪽에 두면 단락 평가로 NPE를 막는다(ch3).

### A-3. 배열 평균 계산 메소드 (double 반환)

```java
public class A3 {
    static double average(int[] arr) {
        int sum = 0;
        for (int v : arr) {
            sum += v;
        }
        return (double) sum / arr.length; // 정수 나눗셈 방지 캐스팅
    }

    public static void main(String[] args) {
        int[] scores = {90, 85, 77};
        System.out.println(average(scores)); // 84.0
    }
}
```

**핵심 포인트**: `sum / arr.length`는 정수 나눗셈이라 소수부가 버려진다. 분자를 `(double)`로 캐스팅해 실수 나눗셈을 강제한다.

### A-4. 매개변수 검증을 추가해 개선

```java
public class A4 {
    static double average(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("배열이 null입니다");
        }
        if (arr.length == 0) {
            throw new IllegalArgumentException("배열이 비어 있습니다");
        }
        int sum = 0;
        for (int v : arr) {
            sum += v;
        }
        return (double) sum / arr.length;
    }

    public static void main(String[] args) {
        System.out.println(average(new int[]{90, 85, 77})); // 84.0

        try {
            average(new int[]{});
        } catch (IllegalArgumentException e) {
            System.out.println("오류: " + e.getMessage()); // 오류: 배열이 비어 있습니다
        }
    }
}
```

**핵심 포인트**: 검증 없는 A-3은 null이면 NPE, 빈 배열이면 `0/0` NaN 대신 여기서는 0으로 나누기 전에 의미 있는 메시지로 실패한다. "일찍, 명확하게 실패"가 디버깅 비용을 줄인다.

---

## B. 스코프/호출 스택

### B-1. 지역 변수와 필드 이름이 같을 때 `this`로 구분

(ch6 학습 후 — 클래스/필드/this는 ch6에서 자세히 배웁니다)

```java
public class B1 {
    static class User {
        private String name; // 필드

        void setName(String name) { // 매개변수가 필드를 가림(shadowing)
            this.name = name;       // this.name = 필드, name = 매개변수
        }

        String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.setName("kim");
        System.out.println(user.getName()); // kim
    }
}
```

**핵심 포인트**: `setName` 안에서 그냥 `name = name;`이라고 쓰면 매개변수를 자기 자신에 대입할 뿐 필드는 그대로다(대표적 잠복 버그). `this.`로 필드임을 명시한다.

### B-2. 블록 밖에서 블록 내부 변수 사용 시 컴파일 오류

```java
public class B2 {
    public static void main(String[] args) {
        int outer = 1;

        if (outer > 0) {
            int inner = 10; // if 블록 스코프
            System.out.println(inner); // 10 (블록 안에서는 사용 가능)
        }

        // System.out.println(inner);
        // 컴파일 오류: cannot find symbol - variable inner
        // inner는 if 블록이 끝나는 순간 스코프에서 사라진다

        for (int i = 0; i < 3; i++) { }
        // System.out.println(i); // 마찬가지로 컴파일 오류
    }
}
```

**핵심 포인트**: 변수는 선언된 블록(`{ }`) 안에서만 보인다. 블록 밖에서 필요하다면 블록 밖에서 선언해야 하지만, 원칙적으로는 스코프를 최대한 좁게 잡는 것이 안전하다.

### B-3. 재귀 팩토리얼과 종료 조건의 중요성

```java
public class B3 {
    static long factorial(int n) {
        if (n <= 1) {          // 종료 조건 (base case)
            return 1;
        }
        return n * factorial(n - 1);
    }

    // 종료 조건이 없는 잘못된 버전
    // static long badFactorial(int n) {
    //     return n * badFactorial(n - 1); // 끝없이 자기 자신을 호출
    // }

    public static void main(String[] args) {
        System.out.println(factorial(5));  // 120
        System.out.println(factorial(10)); // 3628800
        // badFactorial(5) 를 호출하면 StackOverflowError 발생
    }
}
```

**설명**: 재귀 호출마다 스택 프레임이 새로 쌓인다. 종료 조건이 없으면(또는 도달할 수 없으면) 프레임이 무한히 쌓이다가 스택 한계를 넘어 `StackOverflowError`가 발생한다.

**핵심 포인트**: 재귀 작성 순서는 "종료 조건 먼저, 재귀 호출은 그다음". 재귀 호출의 인자가 종료 조건 방향으로 반드시 줄어드는지 확인한다.

### B-4. 호출 스택 흐름 확인 (main → A → B)

```java
public class B4 {
    static void methodA() {
        System.out.println("A 시작");
        methodB();
        System.out.println("A 끝");
    }

    static void methodB() {
        System.out.println("B 시작");
        System.out.println("B 끝");
    }

    public static void main(String[] args) {
        System.out.println("main 시작");
        methodA();
        System.out.println("main 끝");
    }
}
```

```text
출력:
main 시작
A 시작
B 시작
B 끝
A 끝
main 끝
```

**핵심 포인트**: 호출 스택은 후입선출(LIFO) — 가장 나중에 호출된 B가 먼저 끝나고, 프레임이 제거되며 호출 지점으로 복귀한다. "시작"과 "끝" 출력이 괄호처럼 대칭으로 감싸이는 것이 그 증거다.

---

## C. 배열 기초

### C-1. 최댓값, 최솟값, 합계, 평균

```java
public class C1 {
    public static void main(String[] args) {
        int[] arr = {5, 3, 9, 1, 7};

        int max = arr[0];
        int min = arr[0];
        int sum = 0;
        for (int v : arr) {
            if (v > max) max = v;
            if (v < min) min = v;
            sum += v;
        }
        double avg = (double) sum / arr.length;

        System.out.println("최댓값: " + max); // 최댓값: 9
        System.out.println("최솟값: " + min); // 최솟값: 1
        System.out.println("합계: " + sum);   // 합계: 25
        System.out.println("평균: " + avg);   // 평균: 5.0
    }
}
```

**핵심 포인트**: 최댓값/최솟값의 초기값은 0이 아니라 `arr[0]`이어야 한다(전부 음수인 배열에서 0으로 초기화하면 오답). 빈 배열이면 `arr[0]` 접근 전에 검증이 필요하다.

### C-2. 배열 역순 출력

```java
public class C2 {
    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 40, 50};

        for (int i = arr.length - 1; i >= 0; i--) {
            System.out.println(arr[i]);
        }
        // 출력: 50 40 30 20 10 (한 줄씩)
    }
}
```

**핵심 포인트**: 역순 순회는 `length - 1`에서 시작해 `i >= 0`까지. `length`에서 시작하거나 `i > 0`으로 끝내는 오프바이원 실수가 가장 흔하다.

### C-3. 짝수 개수와 홀수 개수

```java
public class C3 {
    public static void main(String[] args) {
        int[] arr = {3, 8, 2, 7, 5, 10, 4};

        int evenCount = 0;
        int oddCount = 0;
        for (int v : arr) {
            if (v % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }
        }
        System.out.println("짝수: " + evenCount + "개"); // 짝수: 4개
        System.out.println("홀수: " + oddCount + "개");  // 홀수: 3개
    }
}
```

**핵심 포인트**: 인덱스가 필요 없는 읽기 전용 순회이므로 for-each가 적합하다. 두 카운터의 합이 `arr.length`와 같은지로 결과를 자가 검증할 수 있다.

### C-4. 특정 값의 첫 인덱스 찾기 (없으면 -1)

```java
public class C4 {
    static int indexOf(int[] arr, int target) {
        if (arr == null) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // 찾는 즉시 반환 (조기 반환)
            }
        }
        return -1; // 끝까지 못 찾음
    }

    public static void main(String[] args) {
        int[] arr = {4, 7, 1, 7, 9};
        System.out.println(indexOf(arr, 7));  // 1  (첫 번째 7의 위치)
        System.out.println(indexOf(arr, 100)); // -1
    }
}
```

**핵심 포인트**: 찾는 즉시 `return`하면 `break` + 결과 변수가 필요 없다. "없으면 -1"은 `String.indexOf` 등 자바 표준 API와 같은 규약이다.

---

## D. 배열 응용

### D-1. 1~45 중복 없는 로또 번호 6개

```java
public class D1 {
    public static void main(String[] args) {
        int[] lotto = new int[6];
        int count = 0;

        while (count < 6) {
            int num = (int) (Math.random() * 45) + 1;

            boolean duplicated = false;
            for (int i = 0; i < count; i++) {
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

        System.out.println(java.util.Arrays.toString(lotto));
        // 출력 예: [7, 23, 41, 3, 15, 32]  (실행마다 다름, 중복 없음)
    }
}
```

**핵심 포인트**: 지금까지 채워진 `count`개와만 비교한다. `Arrays.toString`을 쓰면 배열 내용을 한 줄로 확인할 수 있다(배열을 그냥 println하면 주소 형태가 출력됨).

### D-2. 2차원 배열의 행별 합계와 전체 합계

```java
public class D2 {
    public static void main(String[] args) {
        int[][] table = {
            {1, 2, 3},
            {4, 5, 6}
        };

        int total = 0;
        for (int i = 0; i < table.length; i++) {
            int rowSum = 0;
            for (int j = 0; j < table[i].length; j++) {
                rowSum += table[i][j];
            }
            System.out.println(i + "행 합계: " + rowSum);
            total += rowSum;
        }
        System.out.println("전체 합계: " + total);
        // 출력:
        // 0행 합계: 6
        // 1행 합계: 15
        // 전체 합계: 21
    }
}
```

**핵심 포인트**: `rowSum`을 바깥 루프 안에서 선언해 행마다 0으로 리셋한다(바깥에 선언하면 누적이 섞이는 버그). 안쪽 루프 조건은 `table[i].length`.

### D-3. 가변 행(jagged) 2차원 배열 순회

```java
public class D3 {
    public static void main(String[] args) {
        int[][] jagged = {
            {1, 2},
            {3, 4, 5},
            {6}
        };

        for (int i = 0; i < jagged.length; i++) {
            for (int j = 0; j < jagged[i].length; j++) { // 행마다 길이가 다름
                System.out.print(jagged[i][j] + " ");
            }
            System.out.println();
        }
        // 출력:
        // 1 2
        // 3 4 5
        // 6
    }
}
```

**핵심 포인트**: 자바 2차원 배열은 "배열의 배열"이라 행마다 길이가 다를 수 있다. 안쪽 조건에 `jagged[0].length`처럼 고정 길이를 쓰면 `ArrayIndexOutOfBoundsException`이 난다.

### D-4. 정렬 후 이진 탐색

```java
import java.util.Arrays;

public class D4 {
    public static void main(String[] args) {
        int[] arr = {9, 3, 7, 1, 5};

        Arrays.sort(arr); // 이진 탐색의 전제: 정렬
        System.out.println(Arrays.toString(arr)); // [1, 3, 5, 7, 9]

        int idx = Arrays.binarySearch(arr, 7);
        System.out.println("7의 인덱스: " + idx);  // 7의 인덱스: 3

        int notFound = Arrays.binarySearch(arr, 4);
        System.out.println("4 검색 결과: " + notFound); // 음수 (없음을 의미)
    }
}
```

**핵심 포인트**: `binarySearch`는 반드시 정렬된 배열에서만 올바르게 동작한다(정렬 안 하면 결과가 무의미). 못 찾으면 "삽입 위치를 인코딩한 음수"가 반환되므로 `>= 0`으로 존재 여부를 판정한다.

---

## E. 복사/참조 문제

### E-1. `b = a` vs `a.clone()` 비교

```java
import java.util.Arrays;

public class E1 {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};

        int[] b = a;         // 참조 복사: 같은 배열을 가리킴
        int[] c = a.clone(); // 실제 복사: 새 배열 생성

        b[0] = 99;
        c[0] = -1;

        System.out.println(Arrays.toString(a)); // [99, 2, 3]  <- b의 변경이 a에 반영됨
        System.out.println(Arrays.toString(b)); // [99, 2, 3]
        System.out.println(Arrays.toString(c)); // [-1, 2, 3]  <- a에 영향 없음

        System.out.println(a == b); // true  (같은 객체)
        System.out.println(a == c); // false (다른 객체)
    }
}
```

**핵심 포인트**: `b = a`는 주소만 복사하므로 한쪽 수정이 양쪽에 보인다. 독립적인 사본이 필요하면 `clone` 등 실제 복사를 써야 한다.

### E-2. `Arrays.copyOf`와 `System.arraycopy`

```java
import java.util.Arrays;

public class E2 {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};

        // 방법 1: Arrays.copyOf (새 배열을 만들어 반환, 길이 확장/축소 가능)
        int[] c1 = Arrays.copyOf(a, a.length);
        int[] c2 = Arrays.copyOf(a, 7); // 늘어난 칸은 기본값 0

        // 방법 2: System.arraycopy (기존 배열에 구간 복사)
        int[] c3 = new int[a.length];
        System.arraycopy(a, 0, c3, 0, a.length);
        // (src, srcPos, dest, destPos, length)

        System.out.println(Arrays.toString(c1)); // [1, 2, 3, 4, 5]
        System.out.println(Arrays.toString(c2)); // [1, 2, 3, 4, 5, 0, 0]
        System.out.println(Arrays.toString(c3)); // [1, 2, 3, 4, 5]

        c1[0] = 99;
        System.out.println(a[0]); // 1  (원본 무영향)
    }
}
```

**핵심 포인트**: `copyOf`는 "새 배열이 필요할 때", `arraycopy`는 "이미 있는 배열의 특정 위치에 구간 복사할 때" 적합하다. 둘 다 요소 값을 복사하므로 원본과 독립적이다.

### E-3. 참조형 배열의 얕은 복사 문제

```java
import java.util.Arrays;

public class E3 {
    public static void main(String[] args) {
        // 2차원 배열 = "배열(참조)을 요소로 갖는 배열" -> 참조형 배열의 대표 사례
        int[][] original = {
            {1, 2},
            {3, 4}
        };

        int[][] shallow = original.clone(); // 얕은 복사: 바깥 배열만 새로 생성

        System.out.println(original == shallow);        // false (바깥 배열은 다름)
        System.out.println(original[0] == shallow[0]);  // true  (안쪽 배열은 공유!)

        shallow[0][0] = 99; // 사본을 고쳤는데...
        System.out.println(Arrays.deepToString(original));
        // [[99, 2], [3, 4]]  <- 원본까지 바뀜!
    }
}
```

**설명**: `clone`은 바깥 배열의 "요소 값"을 복사하는데, 참조형 배열의 요소 값은 객체의 주소다. 즉 안쪽 배열(객체)은 복사되지 않고 공유된다. 완전히 독립시키려면 각 행을 개별적으로 복사(깊은 복사)해야 한다.

```java
int[][] deep = new int[original.length][];
for (int i = 0; i < original.length; i++) {
    deep[i] = original[i].clone(); // 행마다 실제 복사
}
```

**핵심 포인트**: primitive 배열의 clone은 사실상 완전한 복사지만, 참조형 배열의 clone은 "겉만 복사"다. 객체 배열(String 제외 가변 객체)에서도 동일한 문제가 발생한다.

### E-4. 방어적 복사 (생성자/게터)

(ch6 학습 후 — 클래스/생성자는 ch6에서 자세히 배웁니다)

```java
import java.util.Arrays;

public class E4 {
    static class ScoreBoard {
        private final int[] scores;

        ScoreBoard(int[] scores) {
            // 방어적 복사 1: 외부 배열을 그대로 저장하지 않고 사본을 저장
            this.scores = scores.clone();
        }

        int[] getScores() {
            // 방어적 복사 2: 내부 배열을 그대로 노출하지 않고 사본을 반환
            return scores.clone();
        }
    }

    public static void main(String[] args) {
        int[] input = {90, 80, 70};
        ScoreBoard board = new ScoreBoard(input);

        input[0] = 0; // 외부에서 원본 배열을 조작해도
        System.out.println(Arrays.toString(board.getScores())); // [90, 80, 70] (내부 안전)

        int[] leaked = board.getScores();
        leaked[0] = -999; // 게터로 받은 배열을 조작해도
        System.out.println(Arrays.toString(board.getScores())); // [90, 80, 70] (내부 안전)
    }
}
```

**핵심 포인트**: 배열은 참조형이므로 그대로 저장/반환하면 외부에서 내부 상태를 마음대로 바꿀 수 있다. "들어올 때 복사, 나갈 때 복사"가 불변성을 지키는 방어적 복사의 원칙이다.

---

## F. 챌린지

### F-1. 성적 배열 → 등급 분포(A/B/C/D/F) 계산

```java
public class F1 {
    // 반환 배열: [A개수, B개수, C개수, D개수, F개수]
    static int[] gradeDistribution(int[] scores) {
        if (scores == null) {
            throw new IllegalArgumentException("scores must not be null");
        }
        int[] dist = new int[5];
        for (int score : scores) {
            if (score >= 90) {
                dist[0]++;
            } else if (score >= 80) {
                dist[1]++;
            } else if (score >= 70) {
                dist[2]++;
            } else if (score >= 60) {
                dist[3]++;
            } else {
                dist[4]++;
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        int[] scores = {95, 82, 77, 64, 58, 91, 45};
        int[] dist = gradeDistribution(scores);

        String[] labels = {"A", "B", "C", "D", "F"};
        for (int i = 0; i < dist.length; i++) {
            System.out.println(labels[i] + ": " + dist[i] + "명");
        }
        // 출력:
        // A: 2명
        // B: 1명
        // C: 1명
        // D: 1명
        // F: 2명
    }
}
```

**핵심 포인트**: 등급별 변수 5개 대신 카운트 배열 하나로 관리하면 등급이 늘어나도 구조가 유지된다. 분포 합계가 `scores.length`와 같은지로 검산할 수 있다.

### F-2. 회문(palindrome) 검사 (문자 배열 활용)

```java
public class F2 {
    static boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }
        char[] chars = s.toCharArray();
        int left = 0;
        int right = chars.length - 1;

        while (left < right) { // 양끝에서 가운데로 좁혀오며 비교
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("level"));  // true
        System.out.println(isPalindrome("noon"));   // true
        System.out.println(isPalindrome("java"));   // false
        System.out.println(isPalindrome("a"));      // true (한 글자는 회문)
    }
}
```

**핵심 포인트**: 투 포인터(양끝 인덱스)로 절반만 비교하면 O(n/2)에 끝난다. 다른 문자를 만나는 즉시 `return false`로 조기 반환하는 것이 탐색 패턴의 응용이다.

### F-3. 행렬 덧셈 (차원 검증 포함)

```java
import java.util.Arrays;

public class F3 {
    static int[][] addMatrix(int[][] a, int[][] b) {
        // 차원 검증
        if (a == null || b == null) {
            throw new IllegalArgumentException("행렬은 null일 수 없습니다");
        }
        if (a.length != b.length) {
            throw new IllegalArgumentException("행 수가 다릅니다");
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i].length != b[i].length) {
                throw new IllegalArgumentException(i + "행의 열 수가 다릅니다");
            }
        }

        int[][] result = new int[a.length][];
        for (int i = 0; i < a.length; i++) {
            result[i] = new int[a[i].length];
            for (int j = 0; j < a[i].length; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] a = {
            {1, 2},
            {3, 4}
        };
        int[][] b = {
            {5, 6},
            {7, 8}
        };

        int[][] sum = addMatrix(a, b);
        System.out.println(Arrays.deepToString(sum)); // [[6, 8], [10, 12]]

        try {
            addMatrix(a, new int[][]{{1, 2, 3}, {4, 5, 6}});
        } catch (IllegalArgumentException e) {
            System.out.println("오류: " + e.getMessage()); // 오류: 0행의 열 수가 다릅니다
        }
    }
}
```

**핵심 포인트**: 계산보다 검증 코드가 더 길어도 정상이다 — jagged 배열 가능성 때문에 행 수뿐 아니라 각 행의 열 수까지 확인해야 한다. 결과는 새 배열로 반환해 입력을 변경하지 않는다(부작용 없음).
