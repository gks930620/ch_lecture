package quest;

/**
 * ch4 G. 실무 시나리오형 — 정답 예시 (G-1 ~ G-4)
 */
public class SolutionG {
    public static void main(String[] args) {
        g1();
        g2();
        g3();
        g4();
    }

    // ── G-1. 주문 목록의 총액, 할인, 무료 배송 여부 ──
    static void g1() {
        int[] prices = {12000, 5500, 30000, 8000};
        final int DISCOUNT_THRESHOLD = 50000; // 5만원 이상 10% 할인
        final int FREE_SHIPPING = 30000;      // 3만원 이상 무료 배송

        int total = 0;
        for (int price : prices) {
            total += price;
        }

        int payAmount = total >= DISCOUNT_THRESHOLD
                ? (int) (total * 0.9)
                : total;
        boolean freeShipping = payAmount >= FREE_SHIPPING;

        System.out.println("총액: " + total);            // 총액: 55500
        System.out.println("결제 금액: " + payAmount);   // 결제 금액: 49950
        System.out.println("무료 배송: " + freeShipping); // 무료 배송: true
    }

    // ── G-2. 비활성 계정만 필터링해 출력 ──
    static void g2() {
        String[] names = {"kim", "lee", "park", "choi"};
        boolean[] active = {true, false, true, false};

        System.out.println("== 비활성 계정 ==");
        for (int i = 0; i < names.length; i++) {
            if (active[i]) {
                continue; // 활성 계정은 건너뜀 (필터 패턴)
            }
            System.out.println(names[i]);
        }
        // lee
        // choi
    }

    // ── G-3. 에러 로그 발견 시 알림 후 순회 중단 ──
    static void g3() {
        String[] logs = {
            "INFO 서버 시작",
            "INFO 요청 처리",
            "ERROR DB 연결 실패",
            "INFO 요청 처리"
        };

        for (String log : logs) {
            if (log.startsWith("ERROR")) {
                System.out.println("[관리자 알림] " + log);
                break; // 첫 에러 발견 시 즉시 순회 중단
            }
        }
        // [관리자 알림] ERROR DB 연결 실패
    }

    // ── G-4. 재고가 임계치보다 낮은 상품 추출 ──
    static void g4() {
        String[] items = {"키보드", "마우스", "모니터", "케이블"};
        int[] stocks = {12, 3, 7, 1};
        final int THRESHOLD = 5;

        System.out.println("== 재고 부족(" + THRESHOLD + " 미만) ==");
        for (int i = 0; i < items.length; i++) {
            if (stocks[i] >= THRESHOLD) {
                continue;
            }
            System.out.println(items[i] + " (재고: " + stocks[i] + ")");
        }
        // 마우스 (재고: 3)
        // 케이블 (재고: 1)
    }
}
