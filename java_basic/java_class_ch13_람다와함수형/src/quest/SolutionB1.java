package quest;

import java.util.List;
import java.util.function.Predicate;

// B-1: Predicate<String>로 빈 문자열 필터
public class SolutionB1 {
    public static void main(String[] args) {
        Predicate<String> notBlank = s -> s != null && !s.isBlank();

        List<String> input = List.of("java", "", "  ", "stream");
        List<String> result = input.stream().filter(notBlank).toList();
        System.out.println(result); // [java, stream]
    }
}
