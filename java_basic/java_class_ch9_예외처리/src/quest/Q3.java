package quest;

public class Q3 {
    static class InsufficientBalanceException extends Exception {
        InsufficientBalanceException(String message) {
            super(message);
        }
    }

    static class Account {
        private int balance;

        Account(int balance) {
            this.balance = balance;
        }

        void withdraw(int amount) throws InsufficientBalanceException {
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
