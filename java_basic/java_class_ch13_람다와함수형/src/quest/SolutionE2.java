package quest;

import java.util.List;
import java.util.function.Predicate;

// E-2: 입력 검증 정책을 람다 리스트로 순차 적용
public class SolutionE2 {
    record Rule(String message, Predicate<String> check) {}

    static List<String> validate(String input, List<Rule> rules) {
        return rules.stream()
                .filter(rule -> !rule.check().test(input)) // 통과 못한 규칙만 남김
                .map(Rule::message)
                .toList();
    }

    public static void main(String[] args) {
        List<Rule> rules = List.of(
                new Rule("비어 있으면 안 됩니다", s -> s != null && !s.isBlank()),
                new Rule("8자 이상이어야 합니다", s -> s.length() >= 8),
                new Rule("숫자를 포함해야 합니다", s -> s.chars().anyMatch(Character::isDigit))
        );

        System.out.println(validate("abc", rules));
        // [8자 이상이어야 합니다, 숫자를 포함해야 합니다]
        System.out.println(validate("password1", rules));
        // []  (모든 검증 통과)
    }
}
