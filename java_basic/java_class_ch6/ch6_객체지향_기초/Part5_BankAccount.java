package ch6_객체지향_기초;

public class Part5_BankAccount {
    private String accountNumber;
    private String owner;
    private int balance;

    public Part5_BankAccount(String accountNumber, String owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = 0;
    }

    public Part5_BankAccount(String accountNumber, String owner, int initialBalance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = initialBalance;
    }

    // 입금
    public void deposit(int amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(amount + "원 입금 완료. 잔액: " + balance + "원");
        } else {
            System.out.println("입금액은 0보다 커야 합니다.");
        }
    }

    // 출금
    public boolean withdraw(int amount) {
        if (amount <= 0) {
            System.out.println("출금액은 0보다 커야 합니다.");
            return false;
        }
        if (amount > balance) {
            System.out.println("잔액이 부족합니다. 현재 잔액: " + balance + "원");
            return false;
        }
        balance -= amount;
        System.out.println(amount + "원 출금 완료. 잔액: " + balance + "원");
        return true;
    }

    // 잔액 조회
    public int getBalance() {
        return balance;
    }

    // 계좌 정보 출력
    public void printInfo() {
        System.out.println("=== 계좌 정보 ===");
        System.out.println("계좌번호: " + accountNumber);
        System.out.println("예금주: " + owner);
        System.out.println("잔액: " + balance + "원");
    }

    public static void main(String[] args) {
        System.out.println("=== 은행 계좌 관리 시스템 ===\n");

        Part5_BankAccount account = new Part5_BankAccount("123-456-789", "홍길동", 10000);
        account.printInfo();

        System.out.println();
        account.deposit(5000);
        account.deposit(3000);

        System.out.println();
        account.withdraw(7000);
        account.withdraw(20000);  // 잔액 부족

        System.out.println("\n최종 잔액: " + account.getBalance() + "원");
    }
}
