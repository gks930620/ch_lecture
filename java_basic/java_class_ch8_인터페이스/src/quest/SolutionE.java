package quest;

// E. 설계 문제 — 결제 도메인 인터페이스 설계 + 구현체 3종 + DIP + Fake 테스트
public class SolutionE {

    // E-1. 결제 도메인 계약
    interface PaymentService {
        void pay(long amount);
    }

    // E-2. 구현체 3종
    static class CardPaymentService implements PaymentService {
        @Override
        public void pay(long amount) {
            System.out.println("[카드결제] " + amount + "원 승인");
        }
    }

    static class BankTransferPaymentService implements PaymentService {
        @Override
        public void pay(long amount) {
            System.out.println("[계좌이체] " + amount + "원 이체");
        }
    }

    static class EasyPaymentService implements PaymentService {
        @Override
        public void pay(long amount) {
            System.out.println("[간편결제] " + amount + "원 결제");
        }
    }

    // E-3. OrderService는 구현체가 아니라 인터페이스에 의존하고,
    //      구체 구현은 생성자로 주입받는다 (의존성 역전 + DI)
    static class OrderService {
        private final PaymentService paymentService;

        OrderService(PaymentService paymentService) {
            this.paymentService = paymentService;
        }

        void order(String product, long amount) {
            System.out.println("주문 접수: " + product);
            paymentService.pay(amount);
        }
    }

    // E-4. 테스트용 가짜 구현 — 실제 결제 없이 호출 여부/금액만 기록
    static class FakePaymentService implements PaymentService {
        long paidAmount = -1;

        @Override
        public void pay(long amount) {
            this.paidAmount = amount; // 실제 결제는 하지 않고 기록만
        }
    }

    public static void main(String[] args) {
        // 운영: 카드 결제로 조립
        OrderService cardOrder = new OrderService(new CardPaymentService());
        cardOrder.order("키보드", 30_000);
        // 주문 접수: 키보드
        // [카드결제] 30000원 승인

        // 구현체 교체 — OrderService 코드는 그대로
        OrderService easyOrder = new OrderService(new EasyPaymentService());
        easyOrder.order("마우스", 15_000);
        // 주문 접수: 마우스
        // [간편결제] 15000원 결제

        // 테스트: Fake를 주입해 실제 결제 없이 검증
        FakePaymentService fake = new FakePaymentService();
        OrderService testOrder = new OrderService(fake);
        testOrder.order("테스트상품", 9_900);
        // 주문 접수: 테스트상품

        System.out.println(fake.paidAmount == 9_900 ? "테스트 통과" : "테스트 실패");
        // 테스트 통과
    }
}
