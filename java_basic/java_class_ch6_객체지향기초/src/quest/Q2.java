package quest;

public class Q2 {
    static class BankAccount {
        private int balance;

        void deposit(int amount) {
            balance += amount;
        }

        boolean withdraw(int amount) {
            if (amount > balance) {
                return false;
            }
            balance -= amount;
            return true;
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
