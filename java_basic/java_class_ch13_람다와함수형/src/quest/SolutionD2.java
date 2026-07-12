package quest;

import java.util.List;
import java.util.function.Predicate;

// D-2: Predicate의 and, or, negate로 복합 조건 조립
public class SolutionD2 {
    public static void main(String[] args) {
        Predicate<String> notBlank = s -> s != null && !s.isBlank();
        Predicate<String> shortWord = s -> s.length() <= 4;
        Predicate<String> startsWithJ = s -> s.startsWith("j");

        // 비어있지 않고, (4자 이하이거나 j로 시작)
        Predicate<String> filter = notBlank.and(shortWord.or(startsWithJ));

        List<String> words = List.of("java", "  ", "kotlin", "go", "javascript");
        System.out.println(words.stream().filter(filter).toList());
        // [java, go, javascript]

        // negate: 위 조건의 반대
        System.out.println(words.stream().filter(filter.negate()).toList());
        // [  , kotlin]
    }
}
