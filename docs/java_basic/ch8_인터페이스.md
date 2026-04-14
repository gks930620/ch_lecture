---
layout: default
title: ch8_인터페이스
description: ch8_인터페이스 통합 문서
---

# ch8_인터페이스

통합 문서입니다.

---

## 1. 인터페이스

# 인터페이스

## 학습 목표
- 인터페이스를 "규약(contract)" 관점으로 이해하고 설계에 적용할 수 있다.
- 다형성, 의존성 역전, 구현체 교체 가능한 구조를 설명할 수 있다.
- 추상 클래스/함수형 인터페이스와의 차이를 실무 기준으로 선택할 수 있다.

---

## 1. 인터페이스의 본질

인터페이스는 "어떻게 구현할지"가 아니라  
"무엇을 제공해야 하는지"를 정의한다.

```java
public interface PaymentService {
    void pay(long amount);
}
```

인터페이스를 구현하는 클래스는 해당 계약을 만족해야 한다.

---

## 2. 왜 인터페이스를 쓰는가

1. 구현 교체 용이성
2. 테스트 용이성(Mock/Fake 대체)
3. 모듈 간 결합도 감소
4. 팀 협업 시 역할 경계 명확화

![인터페이스 계약과 구현체 교체]({{ '/assets/images/java_basic/ch8/interface-contract-strategy.svg' | relative_url }})

---

## 3. 기본 문법과 특징

### 3.1 선언/구현

```java
interface Notifier {
    void send(String message);
}

class EmailNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("[EMAIL] " + message);
    }
}
```

### 3.2 다중 구현 가능

Java 클래스는 단일 상속만 가능하지만 인터페이스는 여러 개 구현 가능하다.

```java
class SmartPhone implements Camera, MusicPlayer, GPS { ... }
```

---

## 4. default/static 메소드

Java 8+에서 인터페이스는 `default`, `static` 메소드를 가질 수 있다.

```java
interface Loggable {
    default void info(String msg) {
        System.out.println("[INFO] " + msg);
    }

    static String now() {
        return java.time.LocalDateTime.now().toString();
    }
}
```

의의:
- 기존 구현체를 깨지 않고 기능 확장 가능
- 인터페이스 관련 유틸리티 제공 가능

주의:
- default 메소드 과다 사용은 인터페이스 책임을 흐릴 수 있다.

---

## 5. 인터페이스 기반 다형성

```java
PaymentService service = new CardPaymentService();
service.pay(10000);
```

호출자는 `CardPaymentService` 구체 구현을 몰라도 된다.  
구현체를 `KakaoPayService`로 교체해도 호출 코드는 동일.

---

## 6. 의존성 역전(DIP) 기초

좋지 않은 구조:
- 상위 모듈이 하위 구현 클래스에 직접 의존

좋은 구조:
- 상위 모듈이 인터페이스에 의존
- 구체 구현은 외부에서 주입(DI)

```java
class OrderService {
    private final PaymentService paymentService;
    OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

---

## 7. 추상 클래스와 비교

인터페이스:
- 기능 계약 중심
- 다중 구현 가능
- 상태 보유 제한적

추상 클래스:
- 공통 상태/구현 공유
- 단일 상속 제약
- 부분 구현 강제에 유리

선택 가이드:
- "능력/역할" 표현: 인터페이스
- "공통 베이스 구현" 공유: 추상 클래스

---

## 8. 함수형 인터페이스와 람다

추상 메소드가 하나인 인터페이스는 함수형 인터페이스다.

```java
@FunctionalInterface
interface Calculator {
    int calc(int a, int b);
}

Calculator add = (a, b) -> a + b;
```

람다/스트림 API의 기반이 된다.

---

## 9. 익명 클래스와 활용

일회성 구현은 익명 클래스로 작성 가능:

```java
Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("run");
    }
};
```

코드가 길어지면 별도 클래스로 분리하는 편이 읽기 쉽다.

---

## 10. 실무에서 자주 하는 실수

1. 의미 없는 인터페이스 남발
2. 인터페이스/구현 책임 경계 불명확
3. 구현 클래스에 직접 new를 박아 교체성 상실
4. default 메소드에 과도한 비즈니스 로직 누적
5. 인터페이스 이름이 역할을 설명하지 못함

---

## 11. 정리

- 인터페이스는 확장성과 테스트 용이성을 높이는 핵심 추상화 도구다.
- "구현이 아니라 계약에 의존"하는 설계 습관이 유지보수성을 크게 높인다.
- 추상 클래스와 경쟁 관계가 아니라, 목적에 따라 함께 사용하는 도구다.

---

## 2. 문제

# 문제

`ch8` 범위(인터페이스/다형성/default/static/함수형 인터페이스) 문제입니다.

---

## A. 인터페이스 기초

1. `Playable` 인터페이스를 만들고 `play()`, `stop()` 메소드를 선언하시오.
2. `MusicPlayer` 구현 클래스를 작성하시오.
3. 인터페이스 타입 참조로 구현체를 호출하시오.
4. 같은 인터페이스를 구현한 `VideoPlayer`를 추가해 교체 가능함을 확인하시오.

---

## B. 다중 인터페이스 구현

1. `Flyable`, `Swimmable` 인터페이스를 만들고 `Duck` 클래스에서 동시에 구현하시오.
2. 각 인터페이스 타입으로 업캐스팅 후 호출 가능 메소드를 확인하시오.
3. 클래스에 구현이 누락되었을 때 컴파일 오류를 확인하시오.

---

## C. default/static

1. `Loggable` 인터페이스에 `default info()` 메소드를 작성하시오.
2. `static now()` 메소드를 추가해 현재 시각 문자열을 반환하시오.
3. 구현 클래스에서 default 메소드를 오버라이딩하시오.

---

## D. 함수형 인터페이스

1. `@FunctionalInterface`로 `Calculator`를 작성하시오.
2. 람다식으로 덧셈/뺄셈 동작을 구현하시오.
3. `List<String>`에 `Predicate<String>`를 적용해 필터링하시오.

---

## E. 설계 문제

1. 결제 도메인(`PaymentService`)을 인터페이스 기반으로 설계하시오.
2. 카드/계좌/간편결제 구현체를 작성하시오.
3. `OrderService`가 구현체가 아니라 인터페이스에 의존하도록 작성하시오.
4. 테스트용 `FakePaymentService`를 넣어 테스트하시오.

---

## F. 챌린지

1. `Comparator`를 사용해 학생 목록을 이름/점수 기준으로 정렬하시오.
2. 익명 클래스와 람다를 각각 써서 `Runnable` 구현을 비교하시오.
3. 전략 패턴을 인터페이스로 구현하고 런타임 교체 기능을 추가하시오.

---

## 제출 체크리스트

1. 호출 코드가 구현체에 직접 의존하지 않는가?
2. 인터페이스 이름이 역할을 명확히 설명하는가?
3. default 메소드에 과도한 로직을 넣지 않았는가?
4. 함수형 인터페이스를 람다로 간결하게 표현했는가?


