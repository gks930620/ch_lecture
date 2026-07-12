package quest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

// B. checked 예외 — 파일 읽기 메소드는 throws로 위임하고(B-1), 호출부(main)에서 try-catch로 처리(B-2)
public class SolutionB {
    public static void main(String[] args) {
        Path path = Path.of("data.txt");
        try {
            Files.writeString(path, "첫 번째 줄\n두 번째 줄", StandardCharsets.UTF_8); // 테스트용 파일 준비
            String firstLine = readFirstLine(path);
            System.out.println("첫 줄: " + firstLine);
        } catch (IOException e) {
            System.out.println("파일을 읽을 수 없습니다: " + e.getMessage());
        }
    }

    // B-1. 이 메소드는 예외를 처리하지 않고 호출자에게 위임한다
    static String readFirstLine(Path path) throws IOException {
        return Files.readAllLines(path, StandardCharsets.UTF_8).get(0);
    }
}
// 출력:
// 첫 줄: 첫 번째 줄
