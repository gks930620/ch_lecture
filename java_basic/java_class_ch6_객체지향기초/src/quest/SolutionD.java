package quest;

import java.util.ArrayList;
import java.util.List;

// D. 설계 문제 (D-1 ~ D-4) — Order / OrderItem / OrderPrinter
public class SolutionD {

    // D-1. 주문 항목: 상품명/단가/수량을 갖는 값 중심 클래스
    static class OrderItem {
        private final String productName;
        private final long price;
        private final int quantity;

        public OrderItem(String productName, long price, int quantity) {
            if (productName == null || productName.isBlank()) throw new IllegalArgumentException("productName");
            if (price < 0) throw new IllegalArgumentException("price");
            if (quantity <= 0) throw new IllegalArgumentException("quantity는 1 이상이어야 합니다");
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
        }

        public String getProductName() { return productName; }
        public long getPrice() { return price; }
        public int getQuantity() { return quantity; }

        public long subtotal() {
            return price * quantity;
        }
    }

    // D-1. 주문: 항목 목록을 관리하고 도메인 규칙을 책임진다
    static class Order {
        private final List<OrderItem> items = new ArrayList<>();

        // D-2. null, 수량 0 이하 차단 (이중 방어)
        public void addItem(OrderItem item) {
            if (item == null) throw new IllegalArgumentException("item은 null일 수 없습니다");
            if (item.getQuantity() <= 0) throw new IllegalArgumentException("수량은 1 이상이어야 합니다");
            items.add(item);
        }

        // D-3. 총액 계산을 Order 내부에 캡슐화
        public long totalAmount() {
            long total = 0;
            for (OrderItem item : items) {
                total += item.subtotal();
            }
            return total;
        }

        public List<OrderItem> getItems() {
            return List.copyOf(items); // 내부 리스트 원본은 노출하지 않는다
        }
    }

    // D-4. 출력 책임은 별도 클래스로 분리 (Order는 화면 출력 방식을 모른다)
    static class OrderPrinter {
        public void print(Order order) {
            for (OrderItem item : order.getItems()) {
                System.out.println(item.getProductName() + " x" + item.getQuantity()
                        + " = " + item.subtotal() + "원");
            }
            System.out.println("총액: " + order.totalAmount() + "원");
        }
    }

    public static void main(String[] args) {
        Order order = new Order();
        order.addItem(new OrderItem("키보드", 30_000, 1));
        order.addItem(new OrderItem("마우스", 15_000, 2));

        new OrderPrinter().print(order);
        // 키보드 x1 = 30000원
        // 마우스 x2 = 30000원
        // 총액: 60000원

        try {
            order.addItem(null);
        } catch (IllegalArgumentException e) {
            System.out.println("예외: " + e.getMessage()); // 예외: item은 null일 수 없습니다
        }
    }
}
