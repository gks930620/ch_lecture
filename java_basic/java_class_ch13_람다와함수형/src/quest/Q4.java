package quest;

import java.util.List;

public class Q4 {
    public static void main(String[] args) {
        List<String> names = List.of("kim", "lee", "park");

        names.stream()
            .map(String::toUpperCase)
            .forEach(System.out::println);
    }
}
