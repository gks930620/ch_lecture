package quest;

import java.util.List;

public class Q3 {
    static double average(List<? extends Number> list) {
        double sum = 0;
        for (Number number : list) {
            sum += number.doubleValue();
        }
        return sum / list.size();
    }

    public static void main(String[] args) {
        List<Integer> scores = List.of(80, 90, 100);
        System.out.println("평균: " + average(scores));
    }
}
