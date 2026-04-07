package quest;

import java.util.List;

public class Q1 {
    public static void main(String[] args) {
        int sum = List.of(1, 2, 3, 4, 5, 6).stream()
            .filter(n -> n % 2 == 0)
            .mapToInt(Integer::intValue)
            .sum();
        System.out.println("짝수 합계: " + sum);
    }
}
