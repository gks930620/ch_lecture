package quest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

// D-3. List<String> + Predicate<String> 필터링
public class SolutionDFilter {

    // 조건(Predicate)을 매개변수로 받는 범용 필터 메소드
    static List<String> filter(List<String> list, Predicate<String> condition) {
        List<String> result = new ArrayList<>();
        for (String s : list) {
            if (condition.test(s)) {
                result.add(s);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> names = List.of("java", "kotlin", "go", "javascript", "rust");

        // 조건 1: "java"로 시작하는 것
        System.out.println(filter(names, s -> s.startsWith("java")));
        // [java, javascript]

        // 조건 2: 길이가 4 이하인 것 — 필터 메소드는 재사용, 조건만 갈아끼움
        System.out.println(filter(names, s -> s.length() <= 4));
        // [java, go, rust]
    }
}
