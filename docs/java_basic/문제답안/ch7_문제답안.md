---
layout: default
title: ch7 문제 답안
description: ch7 상속 문제 예시 답안
---

# ch7 상속 — 문제 예시 답안

> 답은 한 가지가 아닙니다. 아래는 예시 답안이며, 스스로 푼 뒤에 비교해 보세요.

## A. 상속 기초

### A-1 ~ A-4. `Animal` / `Dog` / `Cat` (공통 필드 + 고유 메소드 + super 생성자)

```java
public class InheritanceDemo {

    // A-1, A-2. 공통 필드(name, age)는 부모에 둔다
    static class Animal {
        protected String name;
        protected int age;

        public Animal(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void printInfo() {
            System.out.println(name + " (" + age + "살)");
        }
    }

    static class Dog extends Animal {
        // A-4. super(...)로 부모 생성자 호출 — 자식 생성자 첫 줄
        public Dog(String name, int age) {
            super(name, age);
        }

        // A-3. Dog 고유 메소드
        public void fetch() {
            System.out.println(name + ": 공을 물어온다!");
        }
    }

    static class Cat extends Animal {
        public Cat(String name, int age) {
            super(name, age);
        }

        // A-3. Cat 고유 메소드
        public void scratch() {
            System.out.println(name + ": 스크래처를 긁는다!");
        }
    }

    public static void main(String[] args) {
        Dog dog = new Dog("바둑이", 3);
        Cat cat = new Cat("나비", 2);

        dog.printInfo(); // 바둑이 (3살)   <- 부모에게 물려받은 메소드 재사용
        cat.printInfo(); // 나비 (2살)
        dog.fetch();     // 바둑이: 공을 물어온다!
        cat.scratch();   // 나비: 스크래처를 긁는다!
    }
}
```

**핵심 포인트**: `Dog is-a Animal`, `Cat is-a Animal`이 의미적으로 성립하므로 상속이 적절하다. 공통 상태/행위(name, age, printInfo)는 부모 한 곳에만 두어 중복을 없애고, 자식은 고유 행동만 추가한다.

---

## B. 오버라이딩

### B-1, B-4. `sound()` 오버라이딩 + `super.sound()`로 부모 메소드도 호출

```java
public class OverridingDemo {

    static class Animal {
        void sound() {
            System.out.println("(동물 울음소리)");
        }
    }

    static class Dog extends Animal {
        @Override
        void sound() {
            System.out.println("멍멍");
        }
    }

    static class Cat extends Animal {
        @Override
        void sound() {
            super.sound(); // B-4. 부모 구현을 먼저 실행하고
            System.out.println("야옹"); // 자식 동작을 덧붙인다
        }
    }

    public static void main(String[] args) {
        new Dog().sound(); // 멍멍
        new Cat().sound(); // (동물 울음소리)
                           // 야옹
    }
}
```

**핵심 포인트**: `super.sound()`는 "부모의 원래 구현"을 명시적으로 호출하는 유일한 방법이다. 부모 동작을 완전히 대체할지, 확장(부모 실행 + 추가)할지를 자식이 선택할 수 있다.

### B-2. `@Override`를 제거하면 생길 수 있는 문제 (설명)

`@Override`는 "이 메소드는 부모 메소드를 재정의한 것"이라고 컴파일러에게 선언하는 애노테이션이다. 이를 빼면:

- 메소드 이름 오타(`sound` → `soud`)나 매개변수 불일치가 있어도 **컴파일 오류가 나지 않고**, 그냥 "새로운 별개의 메소드"로 조용히 추가된다.
- 그 결과 `Animal a = new Dog(); a.sound();`가 자식 구현이 아닌 **부모 구현을 호출**하는, 찾기 어려운 런타임 버그가 된다.

```java
class Dog extends Animal {
    // @Override가 없으면 이 오타가 컴파일을 통과한다!
    void soud() { System.out.println("멍멍"); } // 오버라이딩이 아니라 새 메소드
}
```

**핵심 포인트**: `@Override`가 있으면 위 오타는 즉시 컴파일 오류("method does not override...")가 된다. 재정의 의도가 있는 메소드에는 반드시 붙인다.

### B-3. 접근 제어를 좁힐 때의 컴파일 오류 재현

```java
class Animal {
    public void sound() { System.out.println("..."); }
}

class Dog extends Animal {
    @Override
    void sound() { // 컴파일 오류!
        // error: sound() in Dog cannot override sound() in Animal
        //        attempting to assign weaker access privileges; was public
        System.out.println("멍멍");
    }
}
```

부모가 `public`인데 자식이 package-private(생략)으로 좁혔기 때문에 오류가 난다. `public`으로 맞추면 해결된다.

**핵심 포인트**: 다형성에서는 `Animal a = new Dog(); a.sound();`처럼 부모 타입으로 호출한다. 부모가 "public으로 호출 가능"이라고 약속했는데 자식이 접근을 좁히면 그 약속이 깨지므로, 오버라이딩 시 접근 범위는 같거나 넓힐 수만 있다.

---

## C. 다형성

### C-1, C-2. `Animal[]` 배열과 반복 호출 + 공통 메소드만 호출 가능한 이유

```java
public class PolymorphismDemo {

    static class Animal {
        void sound() { System.out.println("..."); }
    }

    static class Dog extends Animal {
        @Override
        void sound() { System.out.println("멍멍"); }

        void fetch() { System.out.println("공 물어오기"); }
    }

    static class Cat extends Animal {
        @Override
        void sound() { System.out.println("야옹"); }
    }

    static class Bird extends Animal {
        @Override
        void sound() { System.out.println("짹짹"); }
    }

    public static void main(String[] args) {
        // C-1. 서로 다른 자식들을 부모 타입 배열에 담는다 (업캐스팅)
        Animal[] animals = { new Dog(), new Cat(), new Bird() };

        for (Animal a : animals) {
            a.sound(); // 실제 객체 타입에 따라 다른 메소드가 실행됨 (동적 바인딩)
        }
        // 멍멍
        // 야옹
        // 짹짹

        // C-2. 업캐스팅된 참조로는 공통 메소드만 호출 가능
        Animal a = new Dog();
        a.sound();     // OK — Animal에 정의된 메소드
        // a.fetch();  // 컴파일 오류! Animal 타입에는 fetch()가 없다
    }
}
```

**C-2 설명**: 컴파일러는 **참조 변수의 타입(Animal)** 만 보고 호출 가능 여부를 판단한다. 실제 객체가 `Dog`라도 컴파일 시점에는 알 수 없으므로, `Animal`에 선언된 멤버만 호출을 허용한다. 반면 "어떤 구현이 실행될지"는 런타임의 실제 객체가 결정한다 — 컴파일 타임(무엇을 부를 수 있나)과 런타임(누가 실행하나)의 역할이 나뉘어 있는 것이다.

**핵심 포인트**: 같은 `a.sound()` 한 줄이 객체에 따라 다르게 동작하는 것이 다형성이다. 새 동물(`Fish`)을 추가해도 반복문 코드는 한 글자도 바꿀 필요가 없다.

### C-3, C-4. 안전한 다운캐스팅과 `ClassCastException` 재현/수정

```java
public class CastingDemo {

    static class Animal { void sound() { System.out.println("..."); } }

    static class Dog extends Animal {
        @Override void sound() { System.out.println("멍멍"); }
        void fetch() { System.out.println("공 물어오기"); }
    }

    static class Cat extends Animal {
        @Override void sound() { System.out.println("야옹"); }
    }

    public static void main(String[] args) {
        // C-4. 잘못된 다운캐스팅 — 컴파일은 되지만 실행 시 예외
        Animal a = new Cat();
        try {
            Dog d = (Dog) a; // 실제 객체는 Cat인데 Dog로 캐스팅
            d.fetch();
        } catch (ClassCastException e) {
            System.out.println("예외 발생: class Cat cannot be cast to class Dog");
        }

        // C-3. 수정: instanceof로 실제 타입을 확인한 뒤에만 캐스팅
        Animal[] animals = { new Dog(), new Cat() };
        for (Animal animal : animals) {
            // Java 17 권장 스타일: instanceof 패턴 매칭 (검사 + 캐스팅 한 번에)
            if (animal instanceof Dog d) {
                d.fetch(); // 공 물어오기
            } else {
                animal.sound(); // 야옹
            }
        }
    }
}
```

**핵심 포인트**: 다운캐스팅은 "이 참조가 사실은 자식 타입"이라고 개발자가 컴파일러에게 장담하는 것이라, 장담이 틀리면 런타임에 `ClassCastException`이 터진다. Java 16+의 `instanceof Dog d` 패턴 매칭을 쓰면 검사와 캐스팅이 한 번에 안전하게 처리된다. 다만 instanceof 분기가 자꾸 늘어난다면 오버라이딩으로 풀 수 없는지 먼저 의심하자.

---

## D. 추상 클래스

### D-1 ~ D-4. `Shape` 추상 클래스 + `Circle`/`Rectangle` + 넓이 합계 + 공통 메소드

```java
public class ShapeDemo {

    // D-1. 추상 클래스 — 직접 new 불가, area() 구현을 자식에게 강제
    static abstract class Shape {
        abstract double area();

        // D-4. 공통 구현 메소드 — 모든 자식이 그대로 물려받는다
        void printInfo() {
            System.out.println(getClass().getSimpleName() + "의 넓이: " + area());
        }
    }

    // D-2. 자식 구현
    static class Circle extends Shape {
        private final double radius;

        Circle(double radius) { this.radius = radius; }

        @Override
        double area() {
            return Math.PI * radius * radius;
        }
    }

    static class Rectangle extends Shape {
        private final double width;
        private final double height;

        Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        @Override
        double area() {
            return width * height;
        }
    }

    public static void main(String[] args) {
        // new Shape(); // 컴파일 오류! 추상 클래스는 인스턴스화 불가

        // D-3. 부모 타입 배열(Shape[])로 다형적 합계 계산
        Shape[] shapes = { new Circle(1), new Rectangle(3, 4), new Rectangle(2, 5) };

        double total = 0;
        for (Shape s : shapes) {
            s.printInfo();
            total += s.area(); // 각 도형이 자기 방식으로 넓이를 계산
        }
        // Circle의 넓이: 3.141592653589793
        // Rectangle의 넓이: 12.0
        // Rectangle의 넓이: 10.0

        System.out.println("전체 넓이 합계: " + total); // 전체 넓이 합계: 25.141592653589793
    }
}
```

**핵심 포인트**: 추상 클래스는 "규약 강제(area는 반드시 구현하라)"와 "공통 구현 제공(printInfo)"을 동시에 할 수 있다는 것이 인터페이스와의 실질적 차이다. 합계 로직은 `Shape`라는 추상 타입만 알면 되므로 새 도형이 늘어나도 수정이 없다.

---

## E. 설계 문제

### E-1. 결제 시스템 — 상속 구조

```java
public class PaymentInheritanceDemo {

    // 부모: 공통 흐름(검증 → 결제)을 갖고, 실제 결제 방식만 자식에 위임
    static abstract class Payment {
        void pay(long amount) {
            if (amount <= 0) throw new IllegalArgumentException("금액은 양수여야 합니다");
            doPay(amount); // 방식별 차이는 자식이 구현
        }

        protected abstract void doPay(long amount);
    }

    static class CardPayment extends Payment {
        @Override
        protected void doPay(long amount) {
            System.out.println("[카드] " + amount + "원 결제");
        }
    }

    static class BankTransferPayment extends Payment {
        @Override
        protected void doPay(long amount) {
            System.out.println("[계좌이체] " + amount + "원 결제");
        }
    }

    public static void main(String[] args) {
        Payment p1 = new CardPayment();
        Payment p2 = new BankTransferPayment();
        p1.pay(10_000); // [카드] 10000원 결제
        p2.pay(20_000); // [계좌이체] 20000원 결제
    }
}
```

### E-2. 같은 요구사항 — 조합(Composition) 구조

```java
public class PaymentCompositionDemo {

    // 결제 "방식"을 인터페이스(계약)로 분리
    interface PaymentMethod {
        void pay(long amount);
    }

    static class CardMethod implements PaymentMethod {
        @Override
        public void pay(long amount) {
            System.out.println("[카드] " + amount + "원 결제");
        }
    }

    static class BankTransferMethod implements PaymentMethod {
        @Override
        public void pay(long amount) {
            System.out.println("[계좌이체] " + amount + "원 결제");
        }
    }

    // 결제 처리기는 방식을 "필드로 포함"한다 (has-a)
    static class PaymentProcessor {
        private PaymentMethod method;

        PaymentProcessor(PaymentMethod method) {
            this.method = method;
        }

        // 조합의 강점: 런타임에 방식 교체 가능
        void changeMethod(PaymentMethod method) {
            this.method = method;
        }

        void pay(long amount) {
            if (amount <= 0) throw new IllegalArgumentException("금액은 양수여야 합니다");
            method.pay(amount);
        }
    }

    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor(new CardMethod());
        processor.pay(10_000); // [카드] 10000원 결제

        processor.changeMethod(new BankTransferMethod()); // 실행 중 교체!
        processor.pay(20_000); // [계좌이체] 20000원 결제
    }
}
```

### E-3. 두 설계의 장단점 비교

| 구분 | 상속 구조 | 조합 구조 |
|---|---|---|
| 공통 로직 재사용 | 부모 클래스에 두면 자동 상속되어 간편 | 처리기(`PaymentProcessor`)에 모아야 함 |
| 결합도 | 부모-자식 강결합. 부모 변경이 모든 자식에 파급 | 인터페이스 계약만 지키면 서로 독립 |
| 런타임 교체 | 불가 — 객체 생성 시 타입이 고정됨 | 가능 — `changeMethod`로 갈아끼움 |
| 확장 방향 | 단일 상속 제약. 계층이 깊어지기 쉬움 | 여러 부품을 자유롭게 조립 가능 |
| 적합한 경우 | is-a가 명확하고 공통 골격(템플릿)이 중요할 때 | 기능을 유연하게 조립/교체해야 할 때 |

**핵심 포인트**: 상속은 "카드결제 is-a 결제"가 자연스러워 보이지만, 실제 요구사항은 "결제 방식을 갈아끼우고 싶다"인 경우가 많다. 그럴 때는 조합 + 인터페이스가 더 유연하다 — "상속보다 조합을 우선 검토하라"는 격언이 여기서 나온다.

---

## F. 챌린지

### F-1. 게임 캐릭터 계층과 스킬 오버라이딩

부모 클래스 이름은 `java.lang.Character`(char의 래퍼 클래스)와 겹치지 않도록 `GameCharacter`로 짓는다.

```java
public class GameDemo {

    static abstract class GameCharacter {
        protected final String name;
        protected int hp;

        GameCharacter(String name, int hp) {
            this.name = name;
            this.hp = hp;
        }

        // 공통 기본 공격
        void attack() {
            System.out.println(name + ": 기본 공격!");
        }

        // 직업별로 반드시 다른 스킬
        abstract void useSkill();
    }

    static class Warrior extends GameCharacter {
        Warrior(String name) { super(name, 150); }

        @Override
        void useSkill() {
            System.out.println(name + ": [강타] 방패로 내려찍는다!");
        }
    }

    static class Mage extends GameCharacter {
        Mage(String name) { super(name, 80); }

        @Override
        void useSkill() {
            System.out.println(name + ": [파이어볼] 화염구를 날린다!");
        }

        @Override
        void attack() { // 기본 공격도 재정의 가능
            System.out.println(name + ": 지팡이 공격!");
        }
    }

    public static void main(String[] args) {
        GameCharacter[] party = { new Warrior("전사A"), new Mage("마법사B") };

        for (GameCharacter c : party) {
            c.attack();
            c.useSkill();
        }
        // 전사A: 기본 공격!
        // 전사A: [강타] 방패로 내려찍는다!
        // 마법사B: 지팡이 공격!
        // 마법사B: [파이어볼] 화염구를 날린다!
    }
}
```

**핵심 포인트**: 공통 골격(name/hp/attack)은 부모에, 직업별 차이(useSkill)는 추상 메소드로 강제한다. 파티 순회 코드는 `GameCharacter` 타입만 알기 때문에 새 직업 추가에 닫혀 있지 않다(OCP).

### F-2. `final`로 확장을 제한해야 하는 케이스

```java
public class FinalDemo {

    static class AuthService {
        // 보안 검증 순서는 자식이 바꾸면 안 된다 → final 메소드
        final boolean authenticate(String user, String password) {
            if (user == null || password == null) return false;
            return checkPassword(user, password);
        }

        protected boolean checkPassword(String user, String password) {
            return "1234".equals(password); // 예시용
        }
    }

    static class CustomAuthService extends AuthService {
        // @Override
        // boolean authenticate(String u, String p) { return true; } // 컴파일 오류!
        // error: authenticate(...) in CustomAuthService cannot override
        //        authenticate(...) in AuthService; overridden method is final
    }

    // 클래스 자체를 확장 금지: 불변 값 객체는 상속으로 불변성이 깨질 수 있다
    static final class ApiKey {
        private final String value;
        ApiKey(String value) { this.value = value; }
        String value() { return value; }
    }
    // class FakeApiKey extends ApiKey { ... } // 컴파일 오류! cannot inherit from final ApiKey

    public static void main(String[] args) {
        AuthService auth = new CustomAuthService();
        System.out.println(auth.authenticate("kim", "1234")); // true
        System.out.println(auth.authenticate("kim", "0000")); // false
    }
}
```

**핵심 포인트**: `final` 메소드는 "핵심 절차(보안 검증 흐름 등)는 고정하되 세부 단계만 열어주는" 용도, `final` 클래스는 "상속으로 불변성/보안이 깨지면 안 되는 값 객체" 용도다. 실제로 `String`이 final 클래스인 이유이기도 하다.

### F-3. 리스코프 치환 원칙(LSP)을 깨는 예시와 개선

LSP: "자식 객체는 부모 타입이 쓰이는 자리에 넣어도 프로그램이 올바르게 동작해야 한다."

**나쁜 예 — 유명한 정사각형/직사각형 문제:**

```java
class Rectangle {
    protected int width, height;
    void setWidth(int w) { this.width = w; }
    void setHeight(int h) { this.height = h; }
    int area() { return width * height; }
}

class Square extends Rectangle {
    // 정사각형은 가로세로가 항상 같아야 하므로 부모의 규칙을 몰래 바꾼다
    @Override void setWidth(int w) { this.width = w; this.height = w; }
    @Override void setHeight(int h) { this.width = h; this.height = h; }
}

// 부모 타입 기준으로 작성된 코드:
static void resize(Rectangle r) {
    r.setWidth(5);
    r.setHeight(4);
    System.out.println(r.area());
    // Rectangle이면 20 (기대대로)
    // Square이면  16 (setHeight(4)가 width까지 4로 바꿔버림) — 기대가 깨진다!
}
```

`Square`를 넣는 순간 "width를 5로 설정했으면 5여야 한다"는 부모의 암묵적 약속이 깨진다. `Square is-a Rectangle`은 수학적으로는 맞지만, **가로세로를 독립적으로 바꿀 수 있는** Rectangle의 행동 규약 관점에서는 성립하지 않는다.

**개선 — 상속 관계를 버리고 공통 추상만 공유:**

```java
interface Shape {
    int area();
}

// 불변 객체로 만들면 "setter의 약속" 문제 자체가 사라진다
record Rect(int width, int height) implements Shape {
    public int area() { return width * height; }
}

record Square(int side) implements Shape {
    public int area() { return side * side; }
}

public class LspDemo {
    public static void main(String[] args) {
        Shape[] shapes = { new Rect(5, 4), new Square(4) };
        for (Shape s : shapes) {
            System.out.println(s.area());
        }
        // 20
        // 16
    }
}
```

**핵심 포인트**: LSP 위반의 신호는 "자식이 부모 메소드를 오버라이딩하면서 부모가 암묵적으로 약속한 동작을 바꾸는 것"이다. 상속으로 억지 관계를 만들지 말고, 공통점은 인터페이스(area 계산 가능)로만 묶고 각자는 독립 타입으로 두는 것이 해법이다.
