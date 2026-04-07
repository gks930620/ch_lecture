package quest;

import java.util.List;

public class Q4 {
    public static void main(String[] args) {
        double avgTop3 = List.of(70, 95, 88, 100, 91, 84).stream()
            .sorted((a, b) -> b - a)
            .limit(3)
            .mapToInt(Integer::intValue)
            .average()
            .orElse(0);

        System.out.println("상위 3개 평균: " + avgTop3);
    }
}
