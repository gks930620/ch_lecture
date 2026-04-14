---
layout: default
title: ch6_객체지향기초
description: ch6_객체지향기초 통합 문서
---

# ch6_객체지향기초

통합 문서입니다.

---

## 1. 클래스와 객체

# 객체지향 프로그래밍 기초

## 학습 목표
- 클래스/객체/인스턴스의 차이를 정확히 설명할 수 있다.
- 필드/생성자/메소드/접근제어의 역할을 설계 관점으로 이해할 수 있다.
- 캡슐화, static 멤버, 객체 책임 분리를 코드에 적용할 수 있다.

---

## 1. 객체지향의 핵심 사고

객체지향은 "코드를 파일 단위로 나누는 방식"이 아니라  
"데이터와 행위를 함께 관리하는 방식"이다.

핵심 질문:
1. 이 객체는 어떤 상태를 가져야 하는가?
2. 어떤 행위를 스스로 책임져야 하는가?
3. 외부에 무엇을 공개하고 무엇을 숨겨야 하는가?

---

## 2. 클래스와 객체

- 클래스: 설계도(타입 정의)
- 객체(인스턴스): 클래스로 생성된 실제 데이터 + 동작 주체

```java
class User {
    String name;
}

User u1 = new User();
User u2 = new User();
```

`u1`, `u2`는 같은 클래스 타입이지만 서로 다른 상태를 갖는다.

![클래스 인스턴스 static 관계]({{ '/assets/images/java_basic/ch6/class-instance-static.svg' | relative_url }})

---

## 3. 클래스 구성 요소

## 3.1 필드(field)

객체의 상태를 저장한다.

```java
class Account {
    private long balance;
}
```

### 3.2 생성자(constructor)

객체 생성 시점 초기화를 담당한다.

```java
public Account(long initialBalance) {
    this.balance = initialBalance;
}
```

### 3.3 메소드(method)

객체가 수행할 동작을 정의한다.

```java
public void deposit(long amount) {
    this.balance += amount;
}
```

---

## 4. 캡슐화와 접근 제어

캡슐화는 "필드를 private으로 막는 것"이 끝이 아니다.  
객체 무결성을 유지하도록 접근 경로를 설계하는 것이다.

접근제어자:
- `private`: 클래스 내부만
- `default`: 같은 패키지
- `protected`: 같은 패키지 + 상속 관계
- `public`: 어디서나

예:

```java
public void withdraw(long amount) {
    if (amount <= 0) throw new IllegalArgumentException();
    if (amount > balance) throw new IllegalStateException();
    balance -= amount;
}
```

필드 직접 노출 대신, 검증 로직이 포함된 메소드로 상태를 변경해야 한다.

---

## 5. 생성자 설계 포인트

1. 객체가 유효한 상태로만 생성되게 만들 것
2. 필수값 누락을 방지할 것
3. 생성자 오버로딩이 많아지면 빌더 패턴 검토

예:

```java
public User(String name, int age) {
    if (name == null || name.isBlank()) throw new IllegalArgumentException("name");
    if (age < 0) throw new IllegalArgumentException("age");
    this.name = name;
    this.age = age;
}
```

---

## 6. `this`와 객체 자기 참조

`this`는 현재 인스턴스를 가리킨다.

용도:
1. 필드와 매개변수 이름 충돌 해소
2. 생성자 체이닝(`this(...)`)
3. 메소드 체이닝에서 자기 자신 반환

```java
public User rename(String name) {
    this.name = name;
    return this;
}
```

---

## 7. static vs 인스턴스 멤버

### 7.1 인스턴스 멤버
- 객체마다 별도로 존재
- 객체 상태를 다루는 동작에 적합

### 7.2 static 멤버
- 클래스 단위 공유
- 공통 상수/유틸 함수/팩토리 메소드에 적합

주의:
- static 메소드에서 인스턴스 필드를 직접 사용할 수 없다.
- 상태 공유(static 가변 필드)는 동시성/테스트 문제를 유발할 수 있다.

---

## 8. 객체 동등성: `==` vs `equals`

기본 `==`는 참조 동일성 비교다.  
도메인에서 "같은 사용자" 같은 의미 비교가 필요하면 `equals/hashCode` 재정의가 필요하다.

```java
@Override
public boolean equals(Object o) { ... }

@Override
public int hashCode() { ... }
```

컬렉션(`HashSet`, `HashMap`) 사용 시 특히 중요하다.

---

## 9. 불변 객체(Immutable Object) 기초

불변 객체는 생성 후 상태가 바뀌지 않는다.

장점:
- 스레드 안전
- 예측 가능성 향상
- 사이드이펙트 감소

패턴:
1. 필드 `private final`
2. setter 제거
3. 생성자에서 완전 초기화

---

## 10. 객체 책임 분리

나쁜 예:
- `User` 객체가 DB 조회, 파일 저장, UI 출력까지 다 담당

좋은 예:
- `User`: 상태/도메인 규칙
- `UserRepository`: 저장소 접근
- `UserService`: 유스케이스 조합

한 클래스에 너무 많은 책임이 모이면 유지보수가 급격히 어려워진다.

---

## 11. 실무에서 자주 하는 실수

1. 모든 필드를 `public`으로 공개
2. 생성자 검증 누락으로 불완전 객체 생성
3. static 가변 상태 남용
4. getter/setter만 있는 빈약한 모델(Anemic Domain Model)
5. 클래스가 비대해져 단일 책임 원칙 위반

---

## 12. 정리

- 객체지향 기초는 문법보다 "책임 있는 모델링"이 핵심이다.
- 필드 은닉, 생성자 검증, 명확한 메소드 책임이 좋은 객체를 만든다.
- 이 기반이 있어야 상속, 인터페이스, 예외 설계를 제대로 이해할 수 있다.

---

## 2. 문제

# 문제

`ch6` 범위(클래스/객체/생성자/캡슐화/static) 문제입니다.

---

## A. 클래스 기초

1. `Person` 클래스를 만들고 이름/나이 필드를 선언하시오.
2. 생성자를 통해 필수값을 받도록 수정하시오.
3. `introduce()` 메소드로 자기소개 문자열을 반환하시오.
4. 인스턴스를 3개 생성해 서로 다른 상태를 출력하시오.

---

## B. 캡슐화

1. `BankAccount`를 만들고 `balance`를 `private`으로 선언하시오.
2. `deposit`, `withdraw` 메소드에 유효성 검증을 추가하시오.
3. 잔액 부족, 음수 금액 입력 시 예외를 던지도록 구현하시오.
4. 외부에서 필드 직접 수정이 불가능함을 코드로 확인하시오.

---

## C. 생성자/this/static

1. 생성자 오버로딩으로 기본 계좌/초기금액 계좌를 모두 지원하시오.
2. `this(...)`를 사용해 생성자 중복 로직을 제거하시오.
3. 생성된 객체 개수를 static 필드로 집계하시오.
4. static 메소드에서 인스턴스 필드에 직접 접근하면 왜 안 되는지 코드로 확인하시오.

---

## D. 설계 문제

1. `Order`와 `OrderItem` 클래스를 설계하시오.
2. `Order.addItem()`에서 null, 수량 0 이하를 막으시오.
3. 총액 계산 로직을 `Order` 안에 캡슐화하시오.
4. 출력/저장 책임은 별도 클래스로 분리하시오.

---

## E. 챌린지

1. 불변 객체 `Money` 클래스를 구현하시오(`amount`, `currency`).
2. `equals/hashCode`를 재정의해 값 객체로 동작시키시오.
3. `User` 생성 시 이메일 형식 검증을 수행하고 실패 시 예외를 던지시오.

---

## 제출 체크리스트

1. 필드가 필요한 범위 이상으로 공개되지 않았는가?
2. 생성 시점에 객체 유효성 검증을 했는가?
3. static/인스턴스 역할을 구분했는가?
4. 클래스 하나에 너무 많은 책임이 몰리지 않았는가?


