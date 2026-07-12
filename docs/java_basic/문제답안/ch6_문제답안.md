---
layout: default
title: ch6 문제 답안
description: ch6 객체지향기초 문제 예시 답안
---

# ch6 객체지향기초 — 문제 예시 답안

> 답은 한 가지가 아닙니다. 아래는 예시 답안이며, 스스로 푼 뒤에 비교해 보세요.

## A. 클래스 기초

### A-1 ~ A-4. `Person` 클래스 (필드 → 생성자 → introduce → 인스턴스 3개)

1~4번을 하나의 완성 코드로 묶었다. 각 단계가 코드의 어느 부분인지 주석으로 표시했다.

```java
public class PersonDemo {

    static class Person {
        // A-1. 이름/나이 필드 선언
        private String name;
        private int age;

        // A-2. 생성자로 필수값을 받는다 (검증 포함)
        public Person(String name, int age) {
            if (name == null || name.isBlank()) throw new IllegalArgumentException("name은 필수입니다");
            if (age < 0) throw new IllegalArgumentException("age는 0 이상이어야 합니다");
            this.name = name;
            this.age = age;
        }

        // A-3. 자기소개 문자열 반환
        public String introduce() {
            return "안녕하세요, 저는 " + name + "이고 " + age + "살입니다.";
        }
    }

    public static void main(String[] args) {
        // A-4. 인스턴스 3개 생성 — 같은 클래스, 서로 다른 상태
        Person p1 = new Person("김자바", 25);
        Person p2 = new Person("이코딩", 30);
        Person p3 = new Person("박개발", 28);

        System.out.println(p1.introduce()); // 안녕하세요, 저는 김자바이고 25살입니다.
        System.out.println(p2.introduce()); // 안녕하세요, 저는 이코딩이고 30살입니다.
        System.out.println(p3.introduce()); // 안녕하세요, 저는 박개발이고 28살입니다.
    }
}
```

**핵심 포인트**: 클래스는 설계도 하나지만 `new`로 만든 인스턴스는 각자 자기 상태(name, age)를 따로 가진다. 생성자에서 검증하면 "불완전한 객체"가 아예 만들어지지 않는다.

---

## B. 캡슐화

### B-1 ~ B-4. `BankAccount` (private 필드 + 검증 + 예외 + 직접 접근 차단 확인)

```java
public class BankAccountDemo {

    static class BankAccount {
        // B-1. balance를 private으로 선언 — 외부 직접 접근 차단
        private long balance;

        public BankAccount(long initialBalance) {
            if (initialBalance < 0) throw new IllegalArgumentException("초기 잔액은 0 이상이어야 합니다");
            this.balance = initialBalance;
        }

        // B-2, B-3. 유효성 검증 + 예외
        public void deposit(long amount) {
            if (amount <= 0) throw new IllegalArgumentException("입금액은 양수여야 합니다: " + amount);
            balance += amount;
        }

        public void withdraw(long amount) {
            if (amount <= 0) throw new IllegalArgumentException("출금액은 양수여야 합니다: " + amount);
            if (amount > balance) throw new IllegalStateException("잔액 부족: 잔액 " + balance + ", 요청 " + amount);
            balance -= amount;
        }

        public long getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(10_000);

        account.deposit(5_000);
        System.out.println(account.getBalance()); // 15000

        account.withdraw(3_000);
        System.out.println(account.getBalance()); // 12000

        // B-3. 예외 발생 확인
        try {
            account.withdraw(100_000);
        } catch (IllegalStateException e) {
            System.out.println("예외: " + e.getMessage()); // 예외: 잔액 부족: 잔액 12000, 요청 100000
        }

        try {
            account.deposit(-500);
        } catch (IllegalArgumentException e) {
            System.out.println("예외: " + e.getMessage()); // 예외: 입금액은 양수여야 합니다: -500
        }

        // B-4. 필드 직접 수정은 컴파일 자체가 안 된다 (주석 해제 시 컴파일 오류)
        // account.balance = -99999; // 컴파일 오류: balance has private access in BankAccount
    }
}
```

**핵심 포인트**: 캡슐화의 목적은 "숨기기" 자체가 아니라, 잔액이 음수가 되는 것 같은 **잘못된 상태로 갈 수 있는 경로를 원천 차단**하는 것이다. 상태 변경은 반드시 검증이 있는 메소드를 거치게 한다.

---

## C. 생성자/this/static

### C-1 ~ C-3. 생성자 오버로딩 + `this(...)` 체이닝 + static 카운트

```java
public class AccountDemo {

    static class Account {
        // C-3. 생성된 객체 개수를 클래스 단위(static)로 집계
        private static int accountCount = 0;

        private final String owner;
        private long balance;

        // C-1. 초기 금액 계좌 — 검증/초기화 로직은 여기에만 존재
        public Account(String owner, long initialBalance) {
            if (owner == null || owner.isBlank()) throw new IllegalArgumentException("owner");
            if (initialBalance < 0) throw new IllegalArgumentException("initialBalance");
            this.owner = owner;
            this.balance = initialBalance;
            accountCount++;
        }

        // C-1, C-2. 기본 계좌(잔액 0) — this(...)로 위 생성자에 위임해 중복 제거
        public Account(String owner) {
            this(owner, 0);
        }

        public static int getAccountCount() {
            return accountCount;
        }

        public long getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) {
        Account a1 = new Account("김자바");          // 기본 계좌
        Account a2 = new Account("이코딩", 50_000);  // 초기 금액 계좌
        Account a3 = new Account("박개발", 10_000);

        System.out.println(a1.getBalance());            // 0
        System.out.println(a2.getBalance());            // 50000
        System.out.println(Account.getAccountCount());  // 3
    }
}
```

**핵심 포인트**: `this(owner, 0)`으로 체이닝하면 검증 로직이 한 곳에만 존재해서, 규칙이 바뀌어도 생성자 하나만 고치면 된다. 객체 개수처럼 "특정 인스턴스가 아니라 클래스 전체"에 속하는 값은 static이 맞다.

### C-4. static 메소드에서 인스턴스 필드 직접 접근이 안 되는 이유

```java
static class Account {
    private long balance;

    public static void printBalance() {
        // System.out.println(balance); // 컴파일 오류!
        // error: non-static variable balance cannot be referenced from a static context
    }
}
```

`balance`는 **인스턴스마다 하나씩** 존재하는 값이다. 반면 static 메소드는 특정 인스턴스 없이 `Account.printBalance()`처럼 클래스 이름만으로 호출된다. 그 시점에는 "어느 계좌의 balance인지" 지목할 대상이 없으므로 컴파일러가 접근을 막는다.

**핵심 포인트**: static 세계에는 `this`가 없다. 인스턴스 상태를 다루려면 인스턴스 메소드를 쓰거나, 매개변수로 객체를 받아야 한다(`printBalance(Account acc)`).

---

## D. 설계 문제

### D-1 ~ D-4. `Order`/`OrderItem` 설계 + 검증 + 총액 캡슐화 + 책임 분리

```java
import java.util.ArrayList;
import java.util.List;

public class OrderDemo {

    // D-1. 주문 항목: 상품명/단가/수량을 갖는 값 중심 클래스
    static class OrderItem {
        private final String productName;
        private final long price;
        private final int quantity;

        public OrderItem(String productName, long price, int quantity) {
            if (productName == null || productName.isBlank()) throw new IllegalArgumentException("productName");
            if (price < 0) throw new IllegalArgumentException("price");
            if (quantity <= 0) throw new IllegalArgumentException("quantity는 1 이상이어야 합니다");
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
        }

        public String getProductName() { return productName; }
        public long getPrice() { return price; }
        public int getQuantity() { return quantity; }

        public long subtotal() {
            return price * quantity;
        }
    }

    // D-1. 주문: 항목 목록을 관리하고 도메인 규칙을 책임진다
    static class Order {
        private final List<OrderItem> items = new ArrayList<>();

        // D-2. null, 수량 0 이하 차단 (수량은 OrderItem 생성자에서 이미 검증되지만 이중 방어)
        public void addItem(OrderItem item) {
            if (item == null) throw new IllegalArgumentException("item은 null일 수 없습니다");
            if (item.getQuantity() <= 0) throw new IllegalArgumentException("수량은 1 이상이어야 합니다");
            items.add(item);
        }

        // D-3. 총액 계산을 Order 내부에 캡슐화 — 외부는 계산 방법을 몰라도 된다
        public long totalAmount() {
            long total = 0;
            for (OrderItem item : items) {
                total += item.subtotal();
            }
            return total;
        }

        public List<OrderItem> getItems() {
            return List.copyOf(items); // 내부 리스트 원본은 노출하지 않는다
        }
    }

    // D-4. 출력 책임은 별도 클래스로 분리 (Order는 화면 출력 방식을 모른다)
    static class OrderPrinter {
        public void print(Order order) {
            for (OrderItem item : order.getItems()) {
                System.out.println(item.getProductName() + " x" + item.getQuantity()
                        + " = " + item.subtotal() + "원");
            }
            System.out.println("총액: " + order.totalAmount() + "원");
        }
    }

    public static void main(String[] args) {
        Order order = new Order();
        order.addItem(new OrderItem("키보드", 30_000, 1));
        order.addItem(new OrderItem("마우스", 15_000, 2));

        new OrderPrinter().print(order);
        // 키보드 x1 = 30000원
        // 마우스 x2 = 30000원
        // 총액: 60000원

        try {
            order.addItem(null);
        } catch (IllegalArgumentException e) {
            System.out.println("예외: " + e.getMessage()); // 예외: item은 null일 수 없습니다
        }
    }
}
```

**핵심 포인트**: 총액 계산 규칙(할인, 세금 등)이 바뀌면 `Order.totalAmount()`만 고치면 된다 — 이것이 캡슐화의 실익이다. 출력을 `OrderPrinter`로 분리했으므로 콘솔 대신 파일/웹으로 바꿔도 `Order`는 수정할 필요가 없다.

---

## E. 챌린지

### E-1, E-2. 불변 객체 `Money` + `equals/hashCode` 재정의

```java
import java.util.Objects;

public class MoneyDemo {

    static final class Money {
        // 불변 패턴: private final 필드 + setter 없음 + 생성자 완전 초기화
        private final long amount;
        private final String currency;

        public Money(long amount, String currency) {
            if (amount < 0) throw new IllegalArgumentException("amount");
            if (currency == null || currency.isBlank()) throw new IllegalArgumentException("currency");
            this.amount = amount;
            this.currency = currency;
        }

        // 상태를 바꾸는 대신 "새 객체"를 반환한다
        public Money plus(Money other) {
            if (!this.currency.equals(other.currency)) {
                throw new IllegalArgumentException("통화가 다릅니다: " + this.currency + " vs " + other.currency);
            }
            return new Money(this.amount + other.amount, this.currency);
        }

        public long getAmount() { return amount; }
        public String getCurrency() { return currency; }

        // E-2. 값 객체: 필드 값이 같으면 같은 돈으로 취급
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Money)) return false;
            Money money = (Money) o;
            return amount == money.amount && Objects.equals(currency, money.currency);
        }

        @Override
        public int hashCode() {
            return Objects.hash(amount, currency);
        }

        @Override
        public String toString() {
            return amount + " " + currency;
        }
    }

    public static void main(String[] args) {
        Money m1 = new Money(1_000, "KRW");
        Money m2 = new Money(1_000, "KRW");

        System.out.println(m1 == m2);      // false (서로 다른 객체 = 참조가 다름)
        System.out.println(m1.equals(m2)); // true  (값이 같음)

        Money m3 = m1.plus(new Money(500, "KRW"));
        System.out.println(m3);              // 1500 KRW
        System.out.println(m1);              // 1000 KRW (원본은 그대로 — 불변)

        // HashSet에서도 같은 값으로 인식되는지 확인
        java.util.Set<Money> set = new java.util.HashSet<>();
        set.add(m1);
        set.add(m2);
        System.out.println(set.size());      // 1 (equals/hashCode 덕분에 중복 제거)
    }
}
```

**핵심 포인트**: `plus`가 자기 상태를 바꾸지 않고 새 `Money`를 반환하는 것이 불변 설계의 핵심이다. `equals`만 재정의하고 `hashCode`를 빼먹으면 `HashSet`/`HashMap`에서 같은 값이 중복 저장되는 버그가 생긴다 — 둘은 반드시 함께 재정의한다. 참고로 Java 17에서는 `record Money(long amount, String currency) {}` 한 줄로 같은 효과를 얻을 수 있다.

### E-3. `User` 생성 시 이메일 형식 검증

```java
public class UserDemo {

    static class User {
        private final String name;
        private final String email;

        public User(String name, String email) {
            if (name == null || name.isBlank()) throw new IllegalArgumentException("name은 필수입니다");
            if (email == null || !email.matches("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$")) {
                throw new IllegalArgumentException("잘못된 이메일 형식: " + email);
            }
            this.name = name;
            this.email = email;
        }

        public String getEmail() { return email; }
    }

    public static void main(String[] args) {
        User ok = new User("김자바", "java@example.com");
        System.out.println(ok.getEmail()); // java@example.com

        try {
            new User("이코딩", "not-an-email");
        } catch (IllegalArgumentException e) {
            System.out.println("예외: " + e.getMessage()); // 예외: 잘못된 이메일 형식: not-an-email
        }
    }
}
```

**핵심 포인트**: 검증을 생성자에 두면 "이메일이 이상한 User"는 프로그램 어디에도 존재할 수 없게 된다. 객체를 쓰는 모든 곳에서 매번 검사할 필요가 없어진다.
