package quest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// B-1. yyyy-MM-dd HH:mm:ss 패턴으로 현재 시각 출력
public class SolutionB1 {
    public static void main(String[] args) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String text = LocalDateTime.now().format(f);
        System.out.println(text); // 2026-07-03 10:15:30 (실행 시점에 따라 다름)
    }
}
