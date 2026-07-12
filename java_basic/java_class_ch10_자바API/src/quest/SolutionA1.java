package quest;

import java.time.LocalDate;

// A-1. 오늘 기준 100일 뒤 날짜
public class SolutionA1 {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDate after100 = today.plusDays(100);

        System.out.println("오늘: " + today);        // 오늘: 2026-07-03 (실행 시점에 따라 다름)
        System.out.println("100일 뒤: " + after100); // 100일 뒤: 2026-10-11 (실행 시점에 따라 다름)
    }
}
