package quest;

// E. 설계 문제 (E-1, E-2) — 결제 시스템: 상속 구조 vs 조합 구조
// E-3은 두 설계의 장단점 비교 표(설명)라서 코드로 옮기지 않습니다.
public class SolutionE {

    // ===== E-1. 상속 구조 =====
    // 부모: 공통 흐름(검증 -> 결제)을 갖고, 실제 결제 방식만 자식에 위임
    static abstract class Payment {
        void pay(long amount) {
            if (amount <= 0) throw new IllegalArgumentException("금액은 양수여야 합니다");
            doPay(amount); // 방식별 차이는 자식이 구현
        }

        protected abstract void doPay(long amount);
    }

    static class CardPayment extends Payment {
        @Override
        protected void doPay(long amount) {
            System.out.println("[카드] " + amount + "원 결제");
        }
    }

    static class BankTransferPayment extends Payment {
        @Override
        protected void doPay(long amount) {
            System.out.println("[계좌이체] " + amount + "원 결제");
        }
    }

    // ===== E-2. 조합(Composition) 구조 =====
    // 결제 "방식"을 인터페이스(계약)로 분리
    interface PaymentMethod {
        void pay(long amount);
    }

    static class CardMethod implements PaymentMethod {
        @Override
        public void pay(long amount) {
            System.out.println("[카드] " + amount + "원 결제");
        }
    }

    static class BankTransferMethod implements PaymentMethod {
        @Override
        public void pay(long amount) {
            System.out.println("[계좌이체] " + amount + "원 결제");
        }
    }

    // 결제 처리기는 방식을 "필드로 포함"한다 (has-a)
    static class PaymentProcessor {
        private PaymentMethod method;

        PaymentProcessor(PaymentMethod method) {
            this.method = method;
        }

        // 조합의 강점: 런타임에 방식 교체 가능
        void changeMethod(PaymentMethod method) {
            this.method = method;
        }

        void pay(long amount) {
            if (amount <= 0) throw new IllegalArgumentException("금액은 양수여야 합니다");
            method.pay(amount);
        }
    }

    public static void main(String[] args) {
        // E-1. 상속 구조
        Payment p1 = new CardPayment();
        Payment p2 = new BankTransferPayment();
        p1.pay(10_000); // [카드] 10000원 결제
        p2.pay(20_000); // [계좌이체] 20000원 결제

        // E-2. 조합 구조 — 실행 중 방식 교체
        PaymentProcessor processor = new PaymentProcessor(new CardMethod());
        processor.pay(10_000); // [카드] 10000원 결제
        processor.changeMethod(new BankTransferMethod()); // 실행 중 교체!
        processor.pay(20_000); // [계좌이체] 20000원 결제
    }
}
