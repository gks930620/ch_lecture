---
layout: default
title: ch9_예외처리
description: ch9_예외처리 통합 문서
---

# ch9_예외처리

통합 문서입니다.

---

## 1. 예외 처리

# 예외 처리 (Exception Handling)

## 학습 목표
- Java 예외 계층과 checked/unchecked 예외 차이를 정확히 이해할 수 있다.
- `try-catch-finally`, `throws`, `try-with-resources`를 상황에 맞게 선택할 수 있다.
- 사용자 정의 예외와 예외 변환(translate) 전략을 실무 코드에 적용할 수 있다.

---

## 1. 예외란 무엇인가

예외(Exception)는 프로그램 실행 중 발생하는 비정상 상황을 의미한다.  
예외 처리는 문제를 숨기는 기술이 아니라, **문제를 통제 가능한 방식으로 다루는 기술**이다.

예:
- 파일 없음
- 네트워크 끊김
- 잘못된 입력
- 도메인 규칙 위반

---

## 2. Throwable 계층 구조

큰 구조:
1. `Error`: JVM/시스템 수준 심각 문제(보통 애플리케이션 복구 대상 아님)
2. `Exception`: 애플리케이션에서 처리 가능한 문제

`Exception`은 다시:
- Checked Exception
- Unchecked Exception (`RuntimeException` 하위)

---

## 3. Checked vs Unchecked

### 3.1 Checked Exception

컴파일러가 처리(또는 위임)를 강제한다.

```java
public String read(Path p) throws IOException { ... }
```

특징:
- 복구 가능성이 있는 외부 요인(I/O, DB, 네트워크)에서 자주 사용

### 3.2 Unchecked Exception

`RuntimeException` 하위. 컴파일 강제가 없다.

예:
- `NullPointerException`
- `IllegalArgumentException`
- `IllegalStateException`

특징:
- 프로그래밍 오류/검증 실패/상태 위반 표현에 적합

---

## 4. try-catch-finally 기본

```java
try {
    risky();
} catch (SpecificException e) {
    recover();
} finally {
    cleanup();
}
```

규칙:
- 더 구체적인 예외를 먼저 catch
- finally는 예외 여부와 무관하게 실행
- finally에서도 예외가 발생하면 원래 예외가 가려질 수 있으므로 주의

---

## 5. 예외 전파와 처리 위치

예외는 호출 스택을 따라 위로 전파된다.

![예외 전파와 처리 흐름]({{ '/assets/images/java_basic/ch9/exception-handling-flow.svg' | relative_url }})

처리 위치 가이드:
1. 현재 레이어가 복구 가능하면 여기서 처리
2. 복구 불가하면 상위로 전달
3. 경계 레이어(Controller/Batch entry)에서 사용자 친화 메시지/로그 변환

---

## 6. `throws`와 예외 위임

```java
public void importFile(Path path) throws IOException {
    Files.readString(path);
}
```

`throws`는 "예외를 무시"가 아니라 "호출자에게 처리 책임 위임"이다.

좋은 위임:
- API 계약에서 어떤 실패가 가능한지 명확히 표현

나쁜 위임:
- 무분별한 `throws Exception`

---

## 7. try-with-resources (권장)

I/O 자원은 반드시 닫아야 한다.

```java
try (BufferedReader br = Files.newBufferedReader(path)) {
    return br.readLine();
}
```

장점:
- 정상/예외 여부와 무관하게 자동 close
- 코드 간결
- suppressed exception 정보도 유지

---

## 8. 사용자 정의 예외

도메인 규칙 위반을 명확히 표현하기 위해 커스텀 예외를 만든다.

```java
class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(long balance, long amount) {
        super("balance=" + balance + ", amount=" + amount);
    }
}
```

장점:
- 예외 이름만 봐도 의미 파악 가능
- 처리 분기/에러 코드 매핑이 명확

---

## 9. 예외 변환(Exception Translation)

하위 기술 예외를 상위 도메인 예외로 감싸 전파하는 패턴.

```java
try {
    repository.save(entity);
} catch (SQLException e) {
    throw new OrderSaveException("주문 저장 실패", e);
}
```

효과:
- 기술 세부사항 누수 방지
- 상위 레이어는 도메인 관점으로 처리 가능

---

## 10. 예외 처리 모범 사례

1. 예외를 삼키지 말 것 (`catch (Exception e) {}` 금지)
2. 로그는 원인/맥락/입력값을 포함할 것
3. 정상 흐름 제어를 예외로 대체하지 말 것
4. 가능한 한 구체 예외를 사용
5. 메시지는 사용자용/개발자용을 구분

---

## 11. 자주 하는 실수

1. `printStackTrace()`만 하고 끝냄
2. 무조건 최상위에서 catch해 원인 맥락 소실
3. checked 예외를 무의미하게 runtime으로만 래핑
4. finally에서 또 예외를 내서 원인 예외를 가림
5. 예외 클래스명이 도메인 의미를 담지 못함

---

## 12. 정리

- 예외 처리는 방어 코드가 아니라 신뢰성 설계다.
- 어디서 잡고 어디까지 전달할지 경계를 명확히 해야 한다.
- try-with-resources, 의미 있는 예외 타입, 예외 변환 전략이 실무 핵심이다.

---

## 2. 문제

# 문제

`ch9` 범위(예외 계층, try-catch, throws, 사용자 정의 예외, 자원 관리) 문제입니다.

---

## A. 기본 예외 처리

1. 0으로 나누기 예외를 `try-catch`로 처리하시오.
2. 배열 인덱스 범위 초과 예외를 재현하고 처리하시오.
3. `NumberFormatException`을 처리해 잘못된 입력을 안내하시오.
4. `finally` 블록이 항상 실행되는지 확인하시오.

---

## B. checked 예외

1. 파일 읽기 메소드를 작성하고 `IOException`을 `throws`로 위임하시오.
2. 호출부에서 `try-catch`로 처리하시오.
3. `throws Exception`과 구체 예외 선언의 차이를 설명하시오.

---

## C. try-with-resources

1. 파일을 읽는 코드를 `try-finally`로 작성하시오.
2. 같은 코드를 `try-with-resources`로 리팩터링하시오.
3. 두 방식의 차이와 장점을 비교하시오.

---

## D. 사용자 정의 예외

1. `InsufficientBalanceException`을 작성하시오.
2. 계좌 출금 로직에서 잔액 부족 시 예외를 발생시키시오.
3. 상위 레이어에서 예외를 잡아 사용자 메시지로 변환하시오.

---

## E. 예외 변환/전파

1. Repository에서 발생한 기술 예외를 Service에서 도메인 예외로 변환하시오.
2. 원인 예외(cause)를 유지한 채 재던지시오.
3. 최상위 레이어에서 로깅 + 응답 변환을 수행하시오.

---

## F. 챌린지

1. 회원 가입 유스케이스에서 입력 검증 실패/중복 이메일/DB 실패 예외를 분리 설계하시오.
2. 배치 처리 중 일부 레코드 실패 시 전체 중단 vs 계속 처리 전략을 구현하시오.
3. 예외별 에러 코드 체계를 설계하시오.

---

## 제출 체크리스트

1. 예외를 무의미하게 삼키지 않았는가?
2. 구체 예외를 사용했는가?
3. 자원 해제가 누락되지 않았는가?
4. 원인 예외(cause)를 보존했는가?


