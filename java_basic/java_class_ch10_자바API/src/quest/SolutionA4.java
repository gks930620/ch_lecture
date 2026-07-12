package quest;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

// A-4. UTC Instant를 Asia/Seoul 시간으로 변환
public class SolutionA4 {
    public static void main(String[] args) {
        Instant nowUtc = Instant.parse("2026-04-09T00:00:00Z"); // 절대 시점(UTC)
        ZonedDateTime seoul = nowUtc.atZone(ZoneId.of("Asia/Seoul"));

        System.out.println("UTC 시점 : " + nowUtc);  // UTC 시점 : 2026-04-09T00:00:00Z
        System.out.println("서울 표시: " + seoul);   // 서울 표시: 2026-04-09T09:00+09:00[Asia/Seoul]
    }
}
