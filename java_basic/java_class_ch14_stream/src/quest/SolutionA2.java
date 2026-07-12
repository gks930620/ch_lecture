package quest;

import java.util.List;

// A-2. trim + 대문자 변환 후 정렬
// 요소 하나하나를 변환하므로 map을 단계별로 연결하고, sorted()로 자연 순서 정렬 후 toList()로 수집한다.
public class SolutionA2 {
    public static void main(String[] args) {
        List<String> names = List.of("  banana", "Apple  ", " cherry ");

        List<String> result = names.stream()
                .map(String::trim)
                .map(String::toUpperCase)
                .sorted()
                .toList();

        System.out.println(result); // [APPLE, BANANA, CHERRY]
    }
}
