---
layout: default
title: ch9 문제 답안
description: ch9 예외처리 문제 예시 답안
---

# ch9 예외처리 — 문제 예시 답안

> 답은 한 가지가 아닙니다. 아래는 예시 답안이며, 스스로 푼 뒤에 비교해 보세요.

## A. 기본 예외 처리

### A-1. 0으로 나누기 예외를 try-catch로 처리

```java
public class DivideByZeroDemo {
    public static void main(String[] args) {
        int a = 10;
        int b = 0;
        try {
            int result = a / b; // ArithmeticException 발생
            System.out.println("결과: " + result);
        } catch (ArithmeticException e) {
            System.out.println("0으로 나눌 수 없습니다: " + e.getMessage());
        }
        System.out.println("프로그램 정상 종료");
    }
}
// 출력:
// 0으로 나눌 수 없습니다: / by zero
// 프로그램 정상 종료
```

**핵심 포인트**: 정수 나눗셈의 0 나누기는 `ArithmeticException`(unchecked)이다. 예외를 잡으면 프로그램이 죽지 않고 try-catch 이후 코드가 계속 실행된다.

### A-2. 배열 인덱스 범위 초과 예외 재현/처리

```java
public class ArrayIndexDemo {
    public static void main(String[] args) {
        int[] arr = {10, 20, 30};
        try {
            System.out.println(arr[3]); // 유효 인덱스는 0~2
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("잘못된 인덱스 접근: " + e.getMessage());
        }
        System.out.println("배열 길이: " + arr.length);
    }
}
// 출력:
// 잘못된 인덱스 접근: Index 3 out of bounds for length 3
// 배열 길이: 3
```

**핵심 포인트**: `ArrayIndexOutOfBoundsException`은 전형적인 프로그래밍 오류(unchecked)다. 실무에서는 catch보다 인덱스 검증으로 애초에 예방하는 것이 우선이다.

### A-3. NumberFormatException 처리

```java
public class NumberParseDemo {
    public static void main(String[] args) {
        String input = "12a"; // 잘못된 입력 가정
        try {
            int number = Integer.parseInt(input);
            System.out.println("입력한 숫자: " + number);
        } catch (NumberFormatException e) {
            System.out.println("숫자 형식이 아닙니다. 다시 입력해 주세요: " + input);
        }
    }
}
// 출력:
// 숫자 형식이 아닙니다. 다시 입력해 주세요: 12a
```

**핵심 포인트**: 외부 입력을 숫자로 변환할 때는 `NumberFormatException` 처리가 필수다. 사용자에게는 원인(입력값)과 함께 안내 메시지를 보여준다.

### A-4. finally 블록이 항상 실행되는지 확인

```java
public class FinallyDemo {
    public static void main(String[] args) {
        System.out.println("--- 예외가 없는 경우 ---");
        run("100");
        System.out.println("--- 예외가 있는 경우 ---");
        run("abc");
    }

    static void run(String input) {
        try {
            int n = Integer.parseInt(input);
            System.out.println("변환 성공: " + n);
        } catch (NumberFormatException e) {
            System.out.println("변환 실패");
        } finally {
            System.out.println("finally 실행");
        }
    }
}
// 출력:
// --- 예외가 없는 경우 ---
// 변환 성공: 100
// finally 실행
// --- 예외가 있는 경우 ---
// 변환 실패
// finally 실행
```

**핵심 포인트**: finally는 예외 발생 여부와 무관하게 항상 실행된다. 그래서 자원 정리 코드를 두는 자리였고, 지금은 try-with-resources가 그 역할을 대신한다.

---

## B. checked 예외

### B-1. 파일 읽기 메소드에서 IOException을 throws로 위임

```java
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReadService {
    // 이 메소드는 예외를 처리하지 않고 호출자에게 위임한다
    public static String readFirstLine(Path path) throws IOException {
        return Files.readAllLines(path, StandardCharsets.UTF_8).get(0);
    }
}
```

**핵심 포인트**: `IOException`은 checked 예외라 처리(catch)하거나 선언(throws)해야 컴파일된다. `throws`는 "무시"가 아니라 "호출자에게 처리 책임을 넘긴다"는 API 계약이다.

### B-2. 호출부에서 try-catch로 처리

```java
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReadMain {
    public static void main(String[] args) {
        Path path = Path.of("data.txt");
        try {
            Files.writeString(path, "첫 번째 줄\n두 번째 줄", StandardCharsets.UTF_8); // 테스트용 파일 준비
            String firstLine = readFirstLine(path);
            System.out.println("첫 줄: " + firstLine);
        } catch (IOException e) {
            System.out.println("파일을 읽을 수 없습니다: " + e.getMessage());
        }
    }

    static String readFirstLine(Path path) throws IOException {
        return Files.readAllLines(path, StandardCharsets.UTF_8).get(0);
    }
}
// 출력:
// 첫 줄: 첫 번째 줄
```

**핵심 포인트**: 예외 처리 위치는 "복구/안내가 가능한 곳"이다. 여기서는 프로그램 진입점(main)이 경계 레이어 역할을 하며 사용자 메시지로 변환한다.

### B-3. `throws Exception` vs 구체 예외 선언의 차이

- **`throws IOException`(구체 예외)**: 호출자가 "어떤 실패가 가능한지" 정확히 알 수 있고, 그 예외에 맞는 복구/안내 코드를 작성할 수 있다. API 문서 역할도 한다.
- **`throws Exception`(포괄 선언)**: 모든 예외를 뭉뚱그려서 호출자는 뭘 대비해야 할지 알 수 없다. 호출자도 어쩔 수 없이 `catch (Exception e)`로 잡게 되어, 의도치 않은 예외(버그성 RuntimeException 포함)까지 함께 삼켜질 위험이 있다.

**핵심 포인트**: throws 선언은 좁고 구체적일수록 좋다. `throws Exception`은 예외 처리 강제(checked)의 장점을 스스로 무력화하는 안티패턴이다.

---

## C. try-with-resources

### C-1. try-finally로 파일 읽기

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TryFinallyRead {
    public static void main(String[] args) {
        Path path = Path.of("data.txt");
        BufferedReader br = null;
        try {
            Files.writeString(path, "hello\nworld", StandardCharsets.UTF_8); // 테스트용 파일 준비
            br = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("읽기 실패: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close(); // close 자체도 IOException을 던질 수 있다
                } catch (IOException e) {
                    System.out.println("close 실패: " + e.getMessage());
                }
            }
        }
    }
}
// 출력:
// hello
// world
```

**핵심 포인트**: 수동 close는 null 체크 + close 예외 처리까지 필요해서 코드가 장황하고 실수하기 쉽다. 이것이 try-with-resources가 도입된 이유다.

### C-2. try-with-resources로 리팩터링

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TryWithResourcesRead {
    public static void main(String[] args) {
        Path path = Path.of("data.txt");
        try {
            Files.writeString(path, "hello\nworld", StandardCharsets.UTF_8); // 테스트용 파일 준비
        } catch (IOException e) {
            System.out.println("준비 실패: " + e.getMessage());
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("읽기 실패: " + e.getMessage());
        }
    }
}
// 출력:
// hello
// world
```

**핵심 포인트**: `try ( ... )` 괄호 안에 선언한 자원은 블록을 벗어날 때(정상/예외 모두) 자동으로 close된다. finally에서 하던 자원 정리 코드가 통째로 사라진다.

### C-3. 두 방식의 차이와 장점 비교

| 항목 | try-finally | try-with-resources |
|---|---|---|
| close 호출 | 개발자가 직접(누락 위험) | 컴파일러가 자동 삽입 |
| 코드량 | null 체크 + 중첩 try로 장황 | 간결 |
| close 중 예외 | 본 예외를 **덮어써서** 원인이 사라질 수 있음 | 본 예외 유지 + close 예외는 suppressed로 함께 보존 |
| 여러 자원 | 중첩이 깊어짐 | `;`로 나열, 선언 역순으로 자동 close |

**핵심 포인트**: 기능이 같아 보여도 "예외 정보 보존(suppressed exception)"이 결정적 차이다. `AutoCloseable` 자원은 항상 try-with-resources가 정답이다.

---

## D. 사용자 정의 예외

### D-1 ~ D-3. InsufficientBalanceException + 출금 로직 + 상위 레이어 변환

```java
// 사용자 정의 예외: RuntimeException 상속(unchecked)
// 잔액 부족은 "도메인 규칙 위반"이므로 호출부에 catch를 강제하지 않는 unchecked로 설계한다.
class InsufficientBalanceException extends RuntimeException {
    private final long balance;
    private final long amount;

    public InsufficientBalanceException(long balance, long amount) {
        super("잔액 부족: balance=" + balance + ", amount=" + amount);
        this.balance = balance;
        this.amount = amount;
    }

    public long getShortfall() { return amount - balance; }
}

class Account {
    private long balance;

    public Account(long balance) { this.balance = balance; }

    public void withdraw(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("출금액은 양수여야 합니다: " + amount);
        }
        if (amount > balance) {
            throw new InsufficientBalanceException(balance, amount); // D-2: 잔액 부족 시 예외 발생
        }
        balance -= amount;
    }

    public long getBalance() { return balance; }
}

public class AccountMain {
    public static void main(String[] args) {
        Account account = new Account(10_000);

        account.withdraw(3_000);
        System.out.println("출금 후 잔액: " + account.getBalance());

        // D-3: 상위 레이어(진입점)에서 잡아 사용자 메시지로 변환
        try {
            account.withdraw(50_000);
        } catch (InsufficientBalanceException e) {
            System.out.println("[안내] 잔액이 부족합니다. " + e.getShortfall() + "원이 더 필요합니다.");
            System.out.println("(개발자 로그) " + e.getMessage());
        }
    }
}
// 출력:
// 출금 후 잔액: 7000
// [안내] 잔액이 부족합니다. 43000원이 더 필요합니다.
// (개발자 로그) 잔액 부족: balance=7000, amount=50000
```

**핵심 포인트**: 예외 이름 자체가 문서다(`InsufficientBalanceException`만 봐도 의미 파악). 예외 객체에 도메인 데이터(잔액/요청액)를 담아 두면 상위 레이어에서 사용자용/개발자용 메시지를 구분해 만들 수 있다.

---

## E. 예외 변환/전파

### E-1 ~ E-3. 기술 예외 → 도메인 예외 변환, cause 유지, 최상위 로깅/응답 변환

```java
// 하부 기술 예외(가정): 실제로는 SQLException 등이 이 자리에 온다
class DataAccessException extends RuntimeException {
    public DataAccessException(String message) { super(message); }
}

// 도메인 예외: unchecked + cause 보존용 생성자
class OrderSaveException extends RuntimeException {
    public OrderSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}

class OrderRepository {
    public void save(String order) {
        // DB 장애 상황 시뮬레이션
        throw new DataAccessException("connection refused: db01:5432");
    }
}

class OrderService {
    private final OrderRepository repository = new OrderRepository();

    public void placeOrder(String order) {
        try {
            repository.save(order);
        } catch (DataAccessException e) {
            // E-1, E-2: 기술 예외를 도메인 예외로 변환하되 cause를 반드시 유지
            throw new OrderSaveException("주문 저장 실패: " + order, e);
        }
    }
}

public class OrderMain {
    public static void main(String[] args) {
        OrderService service = new OrderService();
        try {
            service.placeOrder("order-1001");
        } catch (OrderSaveException e) {
            // E-3: 최상위 레이어 — 개발자용 로그 + 사용자용 응답 변환
            System.out.println("[LOG] " + e.getMessage() + " / cause=" + e.getCause().getMessage());
            System.out.println("[응답] 주문 처리 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
        }
    }
}
// 출력:
// [LOG] 주문 저장 실패: order-1001 / cause=connection refused: db01:5432
// [응답] 주문 처리 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.
```

**핵심 포인트**: 변환 시 `new 도메인예외(message, e)`처럼 원인 예외를 cause로 넘겨야 스택트레이스에 "Caused by:"로 근본 원인이 남는다. cause 없이 새 예외만 던지면 장애 분석이 불가능해진다.

---

## F. 챌린지

### F-1. 회원 가입 유스케이스 — 예외 분리 설계

```java
// 입력 검증 실패 / 중복 이메일 / DB 실패를 서로 다른 예외 타입으로 분리
class InvalidSignUpInputException extends RuntimeException {
    public InvalidSignUpInputException(String message) { super(message); }
}

class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) { super("이미 가입된 이메일: " + email); }
}

class MemberSaveException extends RuntimeException {
    public MemberSaveException(String message, Throwable cause) { super(message, cause); }
}

class MemberService {
    public void signUp(String email, String password) {
        // 1) 입력 검증 실패
        if (email == null || !email.contains("@")) {
            throw new InvalidSignUpInputException("이메일 형식이 올바르지 않습니다: " + email);
        }
        if (password == null || password.length() < 8) {
            throw new InvalidSignUpInputException("비밀번호는 8자 이상이어야 합니다");
        }
        // 2) 중복 이메일 (조회 시뮬레이션)
        if ("dup@test.com".equals(email)) {
            throw new DuplicateEmailException(email);
        }
        // 3) DB 실패 (저장 시뮬레이션)
        if ("dbfail@test.com".equals(email)) {
            throw new MemberSaveException("회원 저장 실패", new RuntimeException("DB timeout"));
        }
        System.out.println("가입 성공: " + email);
    }
}

public class SignUpMain {
    public static void main(String[] args) {
        MemberService service = new MemberService();
        String[][] inputs = {
                {"kim@test.com", "password123"},
                {"bad-email", "password123"},
                {"dup@test.com", "password123"},
                {"dbfail@test.com", "password123"},
        };
        for (String[] in : inputs) {
            try {
                service.signUp(in[0], in[1]);
            } catch (InvalidSignUpInputException e) {
                System.out.println("[400] 입력 오류: " + e.getMessage());
            } catch (DuplicateEmailException e) {
                System.out.println("[409] " + e.getMessage());
            } catch (MemberSaveException e) {
                System.out.println("[500] 일시적인 오류입니다. (원인: " + e.getCause().getMessage() + ")");
            }
        }
    }
}
// 출력:
// 가입 성공: kim@test.com
// [400] 입력 오류: 이메일 형식이 올바르지 않습니다: bad-email
// [409] 이미 가입된 이메일: dup@test.com
// [500] 일시적인 오류입니다. (원인: DB timeout)
```

**핵심 포인트**: 실패 원인별로 예외 타입을 나누면 최상위에서 catch 분기만으로 상태 코드/메시지 매핑이 끝난다. 하나의 예외에 문자열로 원인을 구분해 넣는 것보다 훨씬 안전하다.

### F-2. 배치 처리 — 전체 중단 vs 계속 처리 전략

```java
import java.util.ArrayList;
import java.util.List;

public class BatchDemo {
    public static void main(String[] args) {
        List<String> records = List.of("100", "200", "oops", "300", "bad", "400");

        System.out.println("--- 전략 1: 실패 시 전체 중단(fail-fast) ---");
        try {
            int total = 0;
            for (String r : records) {
                total += Integer.parseInt(r); // 실패하면 즉시 전파 -> 전체 중단
            }
            System.out.println("합계: " + total);
        } catch (NumberFormatException e) {
            System.out.println("배치 중단: 잘못된 레코드 발견 -> " + e.getMessage());
        }

        System.out.println("--- 전략 2: 실패 레코드 건너뛰고 계속 처리(skip-and-log) ---");
        int total = 0;
        List<String> failed = new ArrayList<>();
        for (String r : records) {
            try {
                total += Integer.parseInt(r);
            } catch (NumberFormatException e) {
                failed.add(r); // 실패 레코드는 기록만 하고 계속
            }
        }
        System.out.println("합계: " + total);
        System.out.println("실패 레코드: " + failed);
    }
}
// 출력:
// --- 전략 1: 실패 시 전체 중단(fail-fast) ---
// 배치 중단: 잘못된 레코드 발견 -> For input string: "oops"
// --- 전략 2: 실패 레코드 건너뛰고 계속 처리(skip-and-log) ---
// 합계: 1000
// 실패 레코드: [oops, bad]
```

**핵심 포인트**: 정합성이 최우선(정산 등)이면 fail-fast, 처리량이 우선(로그 수집 등)이면 skip-and-log가 적합하다. 어느 쪽이든 실패 레코드를 반드시 기록해 재처리가 가능해야 한다.

### F-3. 예외별 에러 코드 체계 설계

```java
enum ErrorCode {
    INVALID_INPUT("E1001", "입력값이 올바르지 않습니다"),
    DUPLICATE_EMAIL("E1002", "이미 가입된 이메일입니다"),
    DATA_ACCESS("E5001", "일시적인 시스템 오류입니다");

    private final String code;
    private final String userMessage;

    ErrorCode(String code, String userMessage) {
        this.code = code;
        this.userMessage = userMessage;
    }

    public String getCode() { return code; }
    public String getUserMessage() { return userMessage; }
}

// 모든 도메인 예외의 공통 부모: 에러 코드를 강제한다
class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode, String detail) {
        super(detail);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() { return errorCode; }
}

class DuplicateEmailException2 extends BusinessException {
    public DuplicateEmailException2(String email) {
        super(ErrorCode.DUPLICATE_EMAIL, "email=" + email);
    }
}

public class ErrorCodeMain {
    public static void main(String[] args) {
        try {
            throw new DuplicateEmailException2("dup@test.com");
        } catch (BusinessException e) {
            // 공통 처리: 코드 + 사용자 메시지 + 개발자 상세를 한 곳에서 매핑
            ErrorCode ec = e.getErrorCode();
            System.out.println("code=" + ec.getCode());
            System.out.println("사용자 메시지: " + ec.getUserMessage());
            System.out.println("개발자 상세: " + e.getMessage());
        }
    }
}
// 출력:
// code=E1002
// 사용자 메시지: 이미 가입된 이메일입니다
// 개발자 상세: email=dup@test.com
```

**핵심 포인트**: `ErrorCode` enum + 공통 부모 예외(`BusinessException`) 구조를 쓰면 새 예외를 추가해도 최상위 처리 코드는 바뀌지 않는다. 코드/사용자 메시지/개발자 상세를 처음부터 분리하는 것이 핵심이다.
