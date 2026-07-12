package quest;

// C. default/static — Loggable의 default/static 메소드와 오버라이딩
public class SolutionC {

    interface Loggable {
        // C-1. default 메소드: 구현 없이도 모든 구현체가 물려받는 기본 동작
        default void info(String msg) {
            System.out.println("[INFO] " + msg);
        }

        // C-2. static 메소드: 인터페이스 이름으로 직접 호출하는 유틸
        static String now() {
            return java.time.LocalDateTime.now().toString();
        }
    }

    // default를 그대로 쓰는 구현체 — info()를 구현하지 않아도 된다
    static class UserService implements Loggable {
    }

    // C-3. default 메소드를 오버라이딩한 구현체
    static class OrderService implements Loggable {
        @Override
        public void info(String msg) {
            System.out.println("[ORDER-INFO] " + msg);
        }
    }

    public static void main(String[] args) {
        new UserService().info("사용자 조회");  // [INFO] 사용자 조회
        new OrderService().info("주문 생성");   // [ORDER-INFO] 주문 생성

        // static 메소드는 인터페이스 이름으로 호출 (구현체/인스턴스로는 호출 불가)
        System.out.println(Loggable.now()); // 예: 2026-07-03T10:15:30.123456 (실행 시각에 따라 다름)
    }
}
