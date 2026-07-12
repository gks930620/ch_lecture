package quest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

// C-1. try-finally로 파일 읽기 (자원을 직접 close)
public class SolutionC1 {
    public static void main(String[] args) {
        Path path = Path.of("data.txt");
        BufferedReader br = null;
        try {
            Files.writeString(path, "hello\nworld", StandardCharsets.UTF_8); // 테스트용 파일 준비
            br = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("읽기 실패: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close(); // close 자체도 IOException을 던질 수 있다
                } catch (IOException e) {
                    System.out.println("close 실패: " + e.getMessage());
                }
            }
        }
    }
}
// 출력:
// hello
// world
