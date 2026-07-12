package quest;

// E. 예외 변환/전파 — 기술 예외 → 도메인 예외 변환, cause 유지, 최상위 로깅/응답 변환
public class SolutionE {

    // 하부 기술 예외(가정): 실제로는 SQLException 등이 이 자리에 온다
    static class DataAccessException extends RuntimeException {
        public DataAccessException(String message) { super(message); }
    }

    // 도메인 예외: unchecked + cause 보존용 생성자
    static class OrderSaveException extends RuntimeException {
        public OrderSaveException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static class OrderRepository {
        public void save(String order) {
            // DB 장애 상황 시뮬레이션
            throw new DataAccessException("connection refused: db01:5432");
        }
    }

    static class OrderService {
        private final OrderRepository repository = new OrderRepository();

        public void placeOrder(String order) {
            try {
                repository.save(order);
            } catch (DataAccessException e) {
                // E-1, E-2: 기술 예외를 도메인 예외로 변환하되 cause를 반드시 유지
                throw new OrderSaveException("주문 저장 실패: " + order, e);
            }
        }
    }

    public static void main(String[] args) {
        OrderService service = new OrderService();
        try {
            service.placeOrder("order-1001");
        } catch (OrderSaveException e) {
            // E-3: 최상위 레이어 — 개발자용 로그 + 사용자용 응답 변환
            System.out.println("[LOG] " + e.getMessage() + " / cause=" + e.getCause().getMessage());
            System.out.println("[응답] 주문 처리 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
        }
    }
}
// 출력:
// [LOG] 주문 저장 실패: order-1001 / cause=connection refused: db01:5432
// [응답] 주문 처리 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.
