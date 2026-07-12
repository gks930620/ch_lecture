package quest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// B-2. 문자열을 LocalDateTime으로 파싱
public class SolutionB2 {
    public static void main(String[] args) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsed = LocalDateTime.parse("2026-04-09 15:30:00", f);

        System.out.println(parsed);              // 2026-04-09T15:30
        System.out.println(parsed.getHour());    // 15
    }
}
