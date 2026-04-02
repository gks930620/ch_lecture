package ch9_예외처리;

// 사용자 정의 예외
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

class BankAccount {
    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public void withdraw(int amount) throws InsufficientBalanceException {
        if (amount > balance) {
            throw new InsufficientBalanceException("잔액이 부족합니다. 현재 잔액: " + balance);
        }
        balance -= amount;
        System.out.println(amount + "원 출금 완료. 잔액: " + balance);
    }

    public int getBalance() {
        return balance;
    }
}

public class Part2_CustomExceptionExample {
    public static void main(String[] args) {
        System.out.println("=== 사용자 정의 예외 ===");

        // 나이 검증
        try {
            checkAge(15);
        } catch (InvalidAgeException e) {
            System.out.println("예외 발생: " + e.getMessage());
        }

        try {
            checkAge(20);
            System.out.println("나이 검증 통과");
        } catch (InvalidAgeException e) {
            System.out.println("예외 발생: " + e.getMessage());
        }

        // 은행 계좌
        System.out.println("\n=== 은행 계좌 예외 ===");
        BankAccount account = new BankAccount(10000);

        try {
            account.withdraw(5000);
            account.withdraw(7000);  // 잔액 부족
        } catch (InsufficientBalanceException e) {
            System.out.println("출금 실패: " + e.getMessage());
        }

        System.out.println("최종 잔액: " + account.getBalance());
    }

    public static void checkAge(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("나이가 18세 미만입니다: " + age + "세");
        }
    }
}

