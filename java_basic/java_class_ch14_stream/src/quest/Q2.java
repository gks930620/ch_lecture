package quest;

import java.util.List;

public class Q2 {
    public static void main(String[] args) {
        List<String> result = List.of("java", "stream", "api").stream()
            .map(String::toUpperCase)
            .sorted()
            .toList();
        System.out.println(result);
    }
}
