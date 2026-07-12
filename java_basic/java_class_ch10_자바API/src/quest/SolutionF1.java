package quest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

// F-1. 로그 파일에서 ERROR 라인만 추출해 별도 파일로 저장
public class SolutionF1 {
    public static void main(String[] args) {
        Path source = Path.of("app.log");
        Path target = Path.of("errors.log");

        try {
            // 테스트용 로그 파일 준비
            Files.writeString(source, """
                    2026-07-03 10:00:01 INFO 서버 시작
                    2026-07-03 10:00:05 ERROR DB 연결 실패
                    2026-07-03 10:00:06 INFO 재시도
                    2026-07-03 10:00:10 ERROR 타임아웃
                    """, StandardCharsets.UTF_8);

            int count = 0;
            try (BufferedReader br = Files.newBufferedReader(source, StandardCharsets.UTF_8);
                 BufferedWriter bw = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("ERROR")) {
                        bw.write(line);
                        bw.newLine();
                        count++;
                    }
                }
            }
            System.out.println("추출된 ERROR 라인 수: " + count);
            Files.readAllLines(target, StandardCharsets.UTF_8).forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("로그 처리 실패: " + e.getMessage());
        }
    }
}
// 출력:
// 추출된 ERROR 라인 수: 2
// 2026-07-03 10:00:05 ERROR DB 연결 실패
// 2026-07-03 10:00:10 ERROR 타임아웃
