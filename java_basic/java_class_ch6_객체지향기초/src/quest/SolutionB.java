package quest;

// B. 캡슐화 (B-1 ~ B-4) — BankAccount
public class SolutionB {

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
