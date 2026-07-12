package quest;

// C. 생성자/this/static (C-1 ~ C-3)
// C-4는 "static 메소드에서 인스턴스 필드 직접 접근 불가"를 설명하는 부분이라 주석으로 남깁니다.
public class SolutionC {

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

        // C-4. static 메소드에는 this가 없어 인스턴스 필드에 접근할 수 없다
        public static void printBalance() {
            // System.out.println(balance); // 컴파일 오류!
            // error: non-static variable balance cannot be referenced from a static context
        }
    }

    public static void main(String[] args) {
        Account a1 = new Account("김자바");          // 기본 계좌
        Account a2 = new Account("이코딩", 50_000);  // 초기 금액 계좌
        Account a3 = new Account("박개발", 10_000);

        System.out.println(a1.getBalance());           // 0
        System.out.println(a2.getBalance());           // 50000
        System.out.println(Account.getAccountCount()); // 3
    }
}
