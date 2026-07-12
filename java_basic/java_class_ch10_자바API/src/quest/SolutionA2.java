package quest;

import java.time.LocalDate;
import java.time.Period;

// A-2. 생년월일로 현재 나이 계산
public class SolutionA2 {
    public static void main(String[] args) {
        LocalDate birth = LocalDate.of(2000, 5, 10);
        LocalDate today = LocalDate.now();

        Period period = Period.between(birth, today);
        System.out.println("만 나이: " + period.getYears() + "세"); // 만 나이: 26세 (실행 시점에 따라 다름)
        System.out.println("정확히: " + period.getYears() + "년 "
                + period.getMonths() + "개월 " + period.getDays() + "일");
        // 정확히: 26년 1개월 23일 (실행 시점에 따라 다름)
    }
}
