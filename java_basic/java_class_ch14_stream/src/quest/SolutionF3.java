package quest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// F-3. Optional + Stream 조합의 null-safe 파이프라인
// null이 섞일 수 있는 입력을 Optional.ofNullable + Optional::stream(요소 0개 또는 1개 스트림)으로
// 흘려보내면, null 검사 if 없이 자연스럽게 걸러진다.
public class SolutionF3 {
    record Customer(String name, String email) {} // email이 null일 수 있다고 가정

    public static void main(String[] args) {
        List<Customer> customers = Arrays.asList(
                new Customer("kim", "kim@test.com"),
                new Customer("lee", null),
                null,                                  // 고객 자체가 null일 수도
                new Customer("park", "park@test.com")
        );

        List<String> emailDomains = customers.stream()
                .flatMap(c -> Optional.ofNullable(c).stream())        // null 고객 제거
                .flatMap(c -> Optional.ofNullable(c.email()).stream())// null 이메일 제거
                .map(email -> email.substring(email.indexOf('@') + 1))
                .distinct()
                .toList();

        System.out.println(emailDomains); // [test.com]

        // 단일 값 조회도 Optional 체인으로 null-safe하게
        String firstEmail = customers.stream()
                .flatMap(c -> Optional.ofNullable(c).stream())
                .map(Customer::email)
                .filter(e -> e != null)
                .findFirst()
                .orElse("이메일 없음");
        System.out.println(firstEmail); // kim@test.com
    }
}
