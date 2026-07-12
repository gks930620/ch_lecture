package quest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Q3 {
    public static void main(String[] args) {
        Path path = Path.of("sample_io.txt");
        try {
            //인코딩(UTF-8)을 명시해서 저장
            Files.write(path, List.of("Java I/O", "file write/read test"), StandardCharsets.UTF_8);

            //try-with-resources로 자원 해제를 보장하며 라인 단위로 읽기
            try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("파일 처리에 실패했습니다: " + e.getMessage());
        }
    }
}
