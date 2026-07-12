package quest;

import java.time.Duration;
import java.time.LocalDateTime;

// A-3. 두 LocalDateTime 차이를 Duration으로 계산
public class SolutionA3 {
    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.of(2026, 4, 20, 9, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 21, 14, 30, 0);

        Duration d = Duration.between(start, end);
        System.out.println("총 시간: " + d.toHours() + "시간");          // 총 시간: 29시간
        System.out.println(d.toDays() + "일 " + d.toHoursPart() + "시간 "
                + d.toMinutesPart() + "분");                             // 1일 5시간 30분
    }
}
