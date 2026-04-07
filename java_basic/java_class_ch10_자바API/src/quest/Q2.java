package quest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Q2 {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDate target = LocalDate.of(today.getYear(), 12, 25);
        if (target.isBefore(today)) {
            target = target.plusYears(1);
        }
        long days = ChronoUnit.DAYS.between(today, target);
        System.out.println("오늘: " + today);
        System.out.println("목표일: " + target);
        System.out.println("남은 일수: " + days);
    }
}
