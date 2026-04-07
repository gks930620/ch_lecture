package quest;

import java.util.List;
import java.util.function.Function;

public class Q3 {
    public static void main(String[] args) {
        List<String> words = List.of("java", "lambda", "function");
        Function<String, Integer> lengthFn = String::length;

        words.stream()
            .map(lengthFn)
            .forEach(System.out::println);
    }
}
