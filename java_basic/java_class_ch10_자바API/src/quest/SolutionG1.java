package quest;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

// G-1. 서울과 뉴욕의 현재 시각 동시 출력
public class SolutionG1 {
    public static void main(String[] args) {
        Instant now = Instant.now(); // 같은 절대 시점을 기준으로
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ZonedDateTime seoul = now.atZone(ZoneId.of("Asia/Seoul"));
        ZonedDateTime newYork = now.atZone(ZoneId.of("America/New_York"));

        System.out.println("서울: " + seoul.format(f));  // 서울: 2026-07-03 21:00:00 (실행 시점에 따라 다름)
        System.out.println("뉴욕: " + newYork.format(f)); // 뉴욕: 2026-07-03 08:00:00 (실행 시점에 따라 다름)
        // 같은 순간이지만 서울(UTC+9)과 뉴욕(UTC-4, 서머타임 적용 시)의 표시가 13시간 차이난다
    }
}
