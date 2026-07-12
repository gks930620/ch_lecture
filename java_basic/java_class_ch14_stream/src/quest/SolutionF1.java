package quest;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

// F-1. 주문 리스트로 카테고리별 총매출 계산
// "카테고리로 묶고, 묶인 주문들의 금액을 합산"이므로 groupingBy + 다운스트림 summingInt가 정확히 대응된다.
public class SolutionF1 {
    record Order(String product, String category, int price, int quantity) {
        int amount() { return price * quantity; }
    }

    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("키보드", "전자", 30000, 2),   // 60000
                new Order("모니터", "전자", 250000, 1),  // 250000
                new Order("노트", "문구", 2000, 10),     // 20000
                new Order("펜", "문구", 1000, 5),        // 5000
                new Order("의자", "가구", 90000, 1)      // 90000
        );

        Map<String, Integer> revenueByCategory = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::category,
                        TreeMap::new, // 출력 고정용
                        Collectors.summingInt(Order::amount)));

        revenueByCategory.forEach((cat, total) ->
                System.out.println(cat + ": " + total + "원"));
        // 가구: 90000원
        // 문구: 25000원
        // 전자: 310000원
    }
}
