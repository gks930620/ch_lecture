package quest;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

// F-3: Stream 파이프라인과 결합한 람다 중심 데이터 처리
public class SolutionF3 {
    record Product(String name, String category, int price) {}

    public static void main(String[] args) {
        List<Product> products = List.of(
                new Product("키보드", "전자", 30000),
                new Product("모니터", "전자", 250000),
                new Product("노트", "문구", 2000),
                new Product("마우스", "전자", 15000)
        );

        Predicate<Product> electronics = p -> p.category().equals("전자");
        Predicate<Product> affordable = p -> p.price() <= 100000;
        Function<Product, String> display = p -> p.name() + "(" + p.price() + "원)";

        List<String> result = products.stream()
                .filter(electronics.and(affordable)) // 조건을 값으로 조합
                .sorted((a, b) -> a.price() - b.price())
                .map(display)
                .toList();
        System.out.println(result); // [마우스(15000원), 키보드(30000원)]

        int total = products.stream()
                .filter(electronics)
                .mapToInt(Product::price)
                .sum();
        System.out.println("전자 총액: " + total); // 전자 총액: 295000
    }
}
