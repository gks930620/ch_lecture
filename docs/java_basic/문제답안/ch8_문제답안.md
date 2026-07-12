---
layout: default
title: ch8 문제 답안
description: ch8 인터페이스 문제 예시 답안
---

# ch8 인터페이스 — 문제 예시 답안

> 답은 한 가지가 아닙니다. 아래는 예시 답안이며, 스스로 푼 뒤에 비교해 보세요.

## A. 인터페이스 기초

### A-1 ~ A-4. `Playable` 인터페이스 + `MusicPlayer`/`VideoPlayer` 교체

```java
public class PlayableDemo {

    // A-1. 계약 정의: "재생/정지를 제공해야 한다"
    interface Playable {
        void play();
        void stop();
    }

    // A-2. 구현 클래스 — 인터페이스 메소드는 반드시 public으로 구현
    static class MusicPlayer implements Playable {
        @Override
        public void play() {
            System.out.println("[음악] 재생 시작");
        }

        @Override
        public void stop() {
            System.out.println("[음악] 정지");
        }
    }

    // A-4. 같은 계약의 다른 구현
    static class VideoPlayer implements Playable {
        @Override
        public void play() {
            System.out.println("[영상] 재생 시작");
        }

        @Override
        public void stop() {
            System.out.println("[영상] 정지");
        }
    }

    // 호출 코드는 Playable 계약만 안다 — 구현체가 무엇인지 모른다
    static void use(Playable player) {
        player.play();
        player.stop();
    }

    public static void main(String[] args) {
        // A-3. 인터페이스 타입 참조로 구현체 호출
        Playable p = new MusicPlayer();
        use(p);
        // [음악] 재생 시작
        // [음악] 정지

        // A-4. 구현체 교체 — use()는 한 글자도 안 바뀐다
        p = new VideoPlayer();
        use(p);
        // [영상] 재생 시작
        // [영상] 정지
    }
}
```

**핵심 포인트**: `use(Playable player)`처럼 호출 코드가 인터페이스에만 의존하면, 구현체를 아무리 추가/교체해도 호출 코드는 수정이 없다. 이것이 "구현이 아니라 계약에 의존하라"의 실체다.

---

## B. 다중 인터페이스 구현

### B-1, B-2. `Flyable` + `Swimmable`을 동시에 구현하는 `Duck`

```java
public class DuckDemo {

    interface Flyable {
        void fly();
    }

    interface Swimmable {
        void swim();
    }

    // B-1. 클래스는 단일 상속만 되지만 인터페이스는 여러 개 구현 가능
    static class Duck implements Flyable, Swimmable {
        @Override
        public void fly() {
            System.out.println("오리가 난다");
        }

        @Override
        public void swim() {
            System.out.println("오리가 헤엄친다");
        }
    }

    public static void main(String[] args) {
        Duck duck = new Duck();
        duck.fly();  // 오리가 난다
        duck.swim(); // 오리가 헤엄친다

        // B-2. 업캐스팅하면 해당 인터페이스의 메소드만 보인다
        Flyable f = duck;
        f.fly();        // 오리가 난다
        // f.swim();    // 컴파일 오류! Flyable 타입에는 swim()이 없다

        Swimmable s = duck;
        s.swim();       // 오리가 헤엄친다
        // s.fly();     // 컴파일 오류! Swimmable 타입에는 fly()가 없다
    }
}
```

**핵심 포인트**: 인터페이스는 "능력(할 수 있는 일)" 단위의 타입이다. `Flyable`로 업캐스팅하면 "나는 능력"만 보이는 것처럼, 참조 타입이 곧 그 객체를 바라보는 관점이 된다.

### B-3. 구현 누락 시 컴파일 오류

```java
static class BrokenDuck implements Flyable, Swimmable {
    @Override
    public void fly() {
        System.out.println("오리가 난다");
    }
    // swim() 구현을 빼먹으면 컴파일 오류!
    // error: BrokenDuck is not abstract and does not override
    //        abstract method swim() in Swimmable
}
```

**핵심 포인트**: 인터페이스는 계약이므로, 구현 클래스는 추상 메소드를 전부 구현하거나 스스로 `abstract`가 되어야 한다. 계약 위반을 런타임이 아니라 **컴파일 타임**에 잡아주는 것이 인터페이스의 큰 가치다.

---

## C. default/static

### C-1 ~ C-3. `Loggable`의 default/static 메소드와 오버라이딩

```java
public class LoggableDemo {

    interface Loggable {
        // C-1. default 메소드: 구현 없이도 모든 구현체가 물려받는 기본 동작
        default void info(String msg) {
            System.out.println("[INFO] " + msg);
        }

        // C-2. static 메소드: 인터페이스 이름으로 직접 호출하는 유틸
        static String now() {
            return java.time.LocalDateTime.now().toString();
        }
    }

    // default를 그대로 쓰는 구현체 — info()를 구현하지 않아도 된다
    static class UserService implements Loggable {
    }

    // C-3. default 메소드를 오버라이딩한 구현체
    static class OrderService implements Loggable {
        @Override
        public void info(String msg) {
            System.out.println("[ORDER-INFO] " + msg);
        }
    }

    public static void main(String[] args) {
        new UserService().info("사용자 조회");  // [INFO] 사용자 조회
        new OrderService().info("주문 생성");   // [ORDER-INFO] 주문 생성

        // static 메소드는 인터페이스 이름으로 호출 (구현체/인스턴스로는 호출 불가)
        System.out.println(Loggable.now()); // 예: 2026-07-03T10:15:30.123456 (실행 시각에 따라 다름)
    }
}
```

**핵심 포인트**: default 메소드 덕분에 이미 구현체가 많은 인터페이스에도 기존 코드를 깨지 않고 메소드를 추가할 수 있다(Java 8에서 도입된 이유). 단, 비즈니스 로직을 default에 쌓기 시작하면 인터페이스가 "계약"에서 "구현 덩어리"로 변질되니 주의한다.

---

## D. 함수형 인터페이스

### D-1, D-2. `@FunctionalInterface` Calculator + 람다 덧셈/뺄셈

```java
public class CalculatorDemo {

    // D-1. 추상 메소드가 정확히 1개 — 컴파일러가 이를 검증해 준다
    @FunctionalInterface
    interface Calculator {
        int calc(int a, int b);
        // int another(int x); // 하나 더 추가하면 @FunctionalInterface가 컴파일 오류를 낸다
    }

    public static void main(String[] args) {
        // D-2. 람다식 = "그 하나뿐인 추상 메소드의 구현"을 식으로 표현
        Calculator add = (a, b) -> a + b;
        Calculator subtract = (a, b) -> a - b;

        System.out.println(add.calc(10, 3));      // 13
        System.out.println(subtract.calc(10, 3)); // 7
    }
}
```

**핵심 포인트**: 람다는 새 문법의 마법이 아니라 "추상 메소드가 하나뿐이라 어떤 메소드를 구현하는지 자명한" 인터페이스의 구현을 줄여 쓴 것이다. `@FunctionalInterface`는 나중에 누가 메소드를 추가해 람다가 깨지는 사고를 컴파일 타임에 막아준다.

### D-3. `List<String>` + `Predicate<String>` 필터링

```java
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PredicateDemo {

    // 조건(Predicate)을 매개변수로 받는 범용 필터 메소드
    static List<String> filter(List<String> list, Predicate<String> condition) {
        List<String> result = new ArrayList<>();
        for (String s : list) {
            if (condition.test(s)) {
                result.add(s);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> names = List.of("java", "kotlin", "go", "javascript", "rust");

        // 조건 1: "java"로 시작하는 것
        System.out.println(filter(names, s -> s.startsWith("java")));
        // [java, javascript]

        // 조건 2: 길이가 4 이하인 것 — 필터 메소드는 재사용, 조건만 갈아끼움
        System.out.println(filter(names, s -> s.length() <= 4));
        // [java, go, rust]
    }
}
```

**핵심 포인트**: `Predicate<String>`은 "String을 받아 boolean을 돌려주는 조건"이라는 자바 내장 함수형 인터페이스다. 조건을 값처럼 넘길 수 있으므로 필터 메소드 하나로 무한히 많은 조건을 처리할 수 있다. (`<>` 제네릭은 ch11, `List`는 ch12에서 자세히 다룬다.)

---

## E. 설계 문제

### E-1 ~ E-4. 결제 도메인 인터페이스 설계 + 구현체 3종 + DIP + Fake 테스트

```java
public class PaymentSystemDemo {

    // E-1. 결제 도메인 계약
    interface PaymentService {
        void pay(long amount);
    }

    // E-2. 구현체 3종
    static class CardPaymentService implements PaymentService {
        @Override
        public void pay(long amount) {
            System.out.println("[카드결제] " + amount + "원 승인");
        }
    }

    static class BankTransferPaymentService implements PaymentService {
        @Override
        public void pay(long amount) {
            System.out.println("[계좌이체] " + amount + "원 이체");
        }
    }

    static class EasyPaymentService implements PaymentService {
        @Override
        public void pay(long amount) {
            System.out.println("[간편결제] " + amount + "원 결제");
        }
    }

    // E-3. OrderService는 구현체가 아니라 인터페이스에 의존하고,
    //      구체 구현은 생성자로 주입받는다 (의존성 역전 + DI)
    static class OrderService {
        private final PaymentService paymentService;

        OrderService(PaymentService paymentService) {
            this.paymentService = paymentService;
        }

        void order(String product, long amount) {
            System.out.println("주문 접수: " + product);
            paymentService.pay(amount);
        }
    }

    // E-4. 테스트용 가짜 구현 — 실제 결제 없이 호출 여부/금액만 기록
    static class FakePaymentService implements PaymentService {
        long paidAmount = -1;

        @Override
        public void pay(long amount) {
            this.paidAmount = amount; // 실제 결제는 하지 않고 기록만
        }
    }

    public static void main(String[] args) {
        // 운영: 카드 결제로 조립
        OrderService cardOrder = new OrderService(new CardPaymentService());
        cardOrder.order("키보드", 30_000);
        // 주문 접수: 키보드
        // [카드결제] 30000원 승인

        // 구현체 교체 — OrderService 코드는 그대로
        OrderService easyOrder = new OrderService(new EasyPaymentService());
        easyOrder.order("마우스", 15_000);
        // 주문 접수: 마우스
        // [간편결제] 15000원 결제

        // 테스트: Fake를 주입해 실제 결제 없이 검증
        FakePaymentService fake = new FakePaymentService();
        OrderService testOrder = new OrderService(fake);
        testOrder.order("테스트상품", 9_900);
        // 주문 접수: 테스트상품

        System.out.println(fake.paidAmount == 9_900 ? "테스트 통과" : "테스트 실패");
        // 테스트 통과
    }
}
```

**핵심 포인트**: `OrderService` 안에서 `new CardPaymentService()`를 직접 하지 않는 것이 설계의 전부다. 인터페이스에 의존 + 외부 주입 구조 덕분에 (1) 결제 수단 추가 시 `OrderService` 무수정, (2) 테스트에서 Fake로 대체 가능이라는 두 가지 이득을 동시에 얻는다.

---

## F. 챌린지

### F-1. `Comparator`로 학생 목록을 이름/점수 기준 정렬

```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentSortDemo {

    static class Student {
        private final String name;
        private final int score;

        Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        String getName() { return name; }
        int getScore() { return score; }

        @Override
        public String toString() {
            return name + "(" + score + ")";
        }
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>(List.of(
                new Student("철수", 85),
                new Student("영희", 92),
                new Student("민수", 78)
        ));

        // Comparator = "두 객체의 순서를 정하는 계약"을 람다로 구현
        students.sort(Comparator.comparing(Student::getName)); // 이름 오름차순
        System.out.println(students); // [민수(78), 영희(92), 철수(85)]

        students.sort(Comparator.comparingInt(Student::getScore).reversed()); // 점수 내림차순
        System.out.println(students); // [영희(92), 철수(85), 민수(78)]
    }
}
```

**핵심 포인트**: `Comparator<T>`도 추상 메소드(`compare`)가 하나인 함수형 인터페이스라 람다/메소드 참조로 즉석 구현이 가능하다. "정렬 기준"을 값처럼 갈아끼우는 것 — 인터페이스 다형성의 대표 활용이다.

### F-2. 익명 클래스 vs 람다로 `Runnable` 구현 비교

```java
public class RunnableDemo {
    public static void main(String[] args) {
        // 방법 1: 익명 클래스 — 이름 없는 일회용 구현 클래스를 그 자리에서 정의
        Runnable anonymous = new Runnable() {
            @Override
            public void run() {
                System.out.println("익명 클래스로 실행");
            }
        };

        // 방법 2: 람다 — Runnable은 추상 메소드(run)가 하나뿐이므로 가능
        Runnable lambda = () -> System.out.println("람다로 실행");

        anonymous.run(); // 익명 클래스로 실행
        lambda.run();    // 람다로 실행
    }
}
```

비교:

| 구분 | 익명 클래스 | 람다 |
|---|---|---|
| 코드량 | 5줄 안팎의 보일러플레이트 | 한 줄 |
| 적용 범위 | 추상 메소드가 여러 개인 인터페이스/추상 클래스도 가능 | 함수형 인터페이스(추상 메소드 1개)만 가능 |
| 상태 | 자체 필드를 가질 수 있음 | 필드 불가, 순수한 동작 표현 |

**핵심 포인트**: 함수형 인터페이스 하나를 구현하는 상황이면 람다가 압도적으로 간결하다. 익명 클래스는 "메소드가 여러 개인 인터페이스의 일회성 구현"이나 "자체 상태가 필요한 경우"에만 남는다.

### F-3. 전략 패턴 + 런타임 교체

```java
public class StrategyDemo {

    // 전략 계약: 할인 방법
    interface DiscountStrategy {
        long discount(long price);
    }

    static class NoDiscount implements DiscountStrategy {
        @Override
        public long discount(long price) {
            return price;
        }
    }

    static class PercentDiscount implements DiscountStrategy {
        private final int percent;

        PercentDiscount(int percent) {
            this.percent = percent;
        }

        @Override
        public long discount(long price) {
            return price - price * percent / 100;
        }
    }

    static class FixedDiscount implements DiscountStrategy {
        private final long amount;

        FixedDiscount(long amount) {
            this.amount = amount;
        }

        @Override
        public long discount(long price) {
            return Math.max(0, price - amount);
        }
    }

    // 컨텍스트: 전략을 필드로 갖고, 런타임에 교체 가능
    static class PriceCalculator {
        private DiscountStrategy strategy;

        PriceCalculator(DiscountStrategy strategy) {
            this.strategy = strategy;
        }

        void changeStrategy(DiscountStrategy strategy) { // 런타임 교체 기능
            this.strategy = strategy;
        }

        long finalPrice(long price) {
            return strategy.discount(price);
        }
    }

    public static void main(String[] args) {
        PriceCalculator calculator = new PriceCalculator(new NoDiscount());
        System.out.println(calculator.finalPrice(10_000)); // 10000

        calculator.changeStrategy(new PercentDiscount(10)); // 10% 할인으로 교체
        System.out.println(calculator.finalPrice(10_000)); // 9000

        calculator.changeStrategy(new FixedDiscount(3_000)); // 3000원 정액 할인으로 교체
        System.out.println(calculator.finalPrice(10_000)); // 7000

        // 함수형 인터페이스이므로 람다로 즉석 전략도 가능
        calculator.changeStrategy(price -> price / 2); // 반값 이벤트
        System.out.println(calculator.finalPrice(10_000)); // 5000
    }
}
```

**핵심 포인트**: 전략 패턴 = "알고리즘을 인터페이스로 추상화하고, 구현을 갈아끼우는 것". 컨텍스트(`PriceCalculator`)는 할인 계산법을 전혀 모르며, 새 할인 정책이 생겨도 클래스(또는 람다) 하나만 추가하면 된다. ch7의 조합(Composition) 설계가 인터페이스와 만나 완성되는 지점이다.
