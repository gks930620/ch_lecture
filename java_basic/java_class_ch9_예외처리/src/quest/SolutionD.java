package quest;

// D. 사용자 정의 예외 — InsufficientBalanceException + 출금 로직 + 상위 레이어 변환
public class SolutionD {

    // 사용자 정의 예외: RuntimeException 상속(unchecked)
    // 잔액 부족은 "도메인 규칙 위반"이므로 호출부에 catch를 강제하지 않는 unchecked로 설계한다.
    static class InsufficientBalanceException extends RuntimeException {
        private final long balance;
        private final long amount;

        public InsufficientBalanceException(long balance, long amount) {
            super("잔액 부족: balance=" + balance + ", amount=" + amount);
            this.balance = balance;
            this.amount = amount;
        }

        public long getShortfall() { return amount - balance; }
    }

    static class Account {
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
