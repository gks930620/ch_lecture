package quest;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

// D-3: 리스트 전처리 파이프라인을 함수 조합으로 구성 (UnaryOperator<List<String>> 조합)
public class SolutionD3 {
    public static void main(String[] args) {
        UnaryOperator<List<String>> removeBlank =
                list -> list.stream().filter(s -> !s.isBlank()).toList();
        UnaryOperator<List<String>> trimAll =
                list -> list.stream().map(String::trim).toList();
        UnaryOperator<List<String>> distinctSorted =
                list -> list.stream().distinct().sorted().toList();

        Function<List<String>, List<String>> preprocess =
                removeBlank.andThen(trimAll).andThen(distinctSorted);

        List<String> raw = List.of(" banana ", "", "apple", " apple", "  ", "cherry");
        System.out.println(preprocess.apply(raw));
        // [apple, banana, cherry]  (" apple"은 trim 후 "apple"과 중복 제거됨)
    }
}
