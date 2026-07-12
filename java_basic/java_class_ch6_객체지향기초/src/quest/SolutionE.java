package quest;

import java.util.Objects;

// E. 챌린지 (E-1 ~ E-3)
// E-1, E-2: 불변 객체 Money + equals/hashCode 재정의
// E-3:      User 생성 시 이메일 형식 검증
public class SolutionE {

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

    // E-3. 생성 시 이메일 형식 검증
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
        // E-1, E-2
        Money m1 = new Money(1_000, "KRW");
        Money m2 = new Money(1_000, "KRW");

        System.out.println(m1 == m2);      // false (참조가 다름)
        System.out.println(m1.equals(m2)); // true  (값이 같음)

        Money m3 = m1.plus(new Money(500, "KRW"));
        System.out.println(m3); // 1500 KRW
        System.out.println(m1); // 1000 KRW (원본은 그대로 — 불변)

        // equals/hashCode 덕분에 HashSet에서 같은 값으로 인식되어 중복 제거
        java.util.Set<Money> set = new java.util.HashSet<>();
        set.add(m1);
        set.add(m2);
        System.out.println(set.size()); // 1

        // E-3
        User ok = new User("김자바", "java@example.com");
        System.out.println(ok.getEmail()); // java@example.com
        try {
            new User("이코딩", "not-an-email");
        } catch (IllegalArgumentException e) {
            System.out.println("예외: " + e.getMessage()); // 예외: 잘못된 이메일 형식: not-an-email
        }
    }
}
