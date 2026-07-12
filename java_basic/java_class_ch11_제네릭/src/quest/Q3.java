package quest;

import java.util.List;

public class Q3 {
    //C1: Number 상한 경계 타입 파라미터로 숫자 리스트의 평균 계산
    static <T extends Number> double average(List<T> list) {
        double sum = 0;
        for (T number : list) {
            sum += number.doubleValue();
        }
        return sum / list.size();
    }

    public static void main(String[] args) {
        List<Integer> scores = List.of(80, 90, 100);
        System.out.println("평균: " + average(scores));
    }
}
