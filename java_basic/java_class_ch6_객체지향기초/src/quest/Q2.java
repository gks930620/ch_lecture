package quest;

public class Q2 {
    static class BankAccount {
        private int balance;

        void deposit(int amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("입금액은 0보다 커야 합니다.");
            }
            balance += amount;
        }

        void withdraw(int amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("출금액은 0보다 커야 합니다.");
            }
            if (amount > balance) {
                throw new IllegalStateException("잔액이 부족합니다.");
            }
            balance -= amount;
        }

        int getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        account.deposit(10000);
        account.withdraw(2500);
        System.out.println("현재 잔액: " + account.getBalance());
    }
}
