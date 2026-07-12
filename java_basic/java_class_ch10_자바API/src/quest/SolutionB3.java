package quest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// B-3. 파싱 실패 시 예외 처리 + 사용자 안내
public class SolutionB3 {
    public static void main(String[] args) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String input = "2026/04/09 15:30:00"; // 잘못된 구분자

        try {
            LocalDateTime parsed = LocalDateTime.parse(input, f);
            System.out.println("파싱 결과: " + parsed);
        } catch (DateTimeParseException e) {
            System.out.println("날짜 형식이 올바르지 않습니다: " + input);
            System.out.println("yyyy-MM-dd HH:mm:ss 형식으로 입력해 주세요. (예: 2026-04-09 15:30:00)");
        }
    }
}
// 출력:
// 날짜 형식이 올바르지 않습니다: 2026/04/09 15:30:00
// yyyy-MM-dd HH:mm:ss 형식으로 입력해 주세요. (예: 2026-04-09 15:30:00)
