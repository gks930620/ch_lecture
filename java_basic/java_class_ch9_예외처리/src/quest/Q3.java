package quest;

public class Q3 {
    //비즈니스 규칙 위반은 RuntimeException(실행예외)을 상속하는 방침 (ch9 문서 8절과 동일)
    static class InsufficientBalanceException extends RuntimeException {
        InsufficientBalanceException(String message) {
            super(message);
        }
    }

    static class Account {
        private int balance;

        Account(int balance) {
            this.balance = balance;
        }

        void withdraw(int amount) {
            if (amount > balance) {
                throw new InsufficientBalanceException("잔액 부족: 현재 잔액 " + balance);
            }
            balance -= amount;
        }
    }

    public static void main(String[] args) {
        Account account = new Account(5000);
        try {
            account.withdraw(7000);
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        }
    }
}
