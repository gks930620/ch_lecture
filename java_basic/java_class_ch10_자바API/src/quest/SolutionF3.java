package quest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// F-3. 회원 CSV를 객체 리스트로 변환
public class SolutionF3 {

    record Member(long id, String name, String email, int age) {
        static Member fromCsv(String line) {
            String[] f = line.split(",");
            return new Member(Long.parseLong(f[0].strip()), f[1].strip(),
                    f[2].strip(), Integer.parseInt(f[3].strip()));
        }
    }

    public static void main(String[] args) {
        Path path = Path.of("members.csv");
        try {
            // 테스트용 CSV 준비 (UTF-8 명시)
            Files.writeString(path, """
                    1001,김자바,kim@test.com,28
                    1002,이코딩,lee@test.com,31
                    1003,박개발,park@test.com,25
                    """, StandardCharsets.UTF_8);

            List<Member> members = new ArrayList<>();
            for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                if (line.isBlank()) continue; // 빈 줄 방어
                members.add(Member.fromCsv(line));
            }

            for (Member m : members) {
                System.out.println(m.name() + " (" + m.age() + "세) - " + m.email());
            }
        } catch (IOException e) {
            System.out.println("CSV 처리 실패: " + e.getMessage());
        }
    }
}
// 출력:
// 김자바 (28세) - kim@test.com
// 이코딩 (31세) - lee@test.com
// 박개발 (25세) - park@test.com
