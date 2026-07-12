package quest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

// G-2. 대용량 텍스트 파일 단어 빈도수 계산
public class SolutionG2 {
    public static void main(String[] args) {
        Path path = Path.of("big.txt");
        try {
            // 테스트용 파일 준비 (UTF-8 명시)
            Files.writeString(path, """
                    java is fun
                    Java is powerful
                    fun fun coding
                    """, StandardCharsets.UTF_8);

            Map<String, Integer> freq = new HashMap<>();
            // 대용량 대비: 전체를 메모리에 올리지 않고 라인 단위 스트리밍
            try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String line;
                while ((line = br.readLine()) != null) {
                    for (String word : line.toLowerCase().split("\\s+")) {
                        if (word.isBlank()) continue;
                        freq.merge(word, 1, Integer::sum);
                    }
                }
            }

            freq.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
        } catch (IOException e) {
            System.out.println("파일 처리 실패: " + e.getMessage());
        }
    }
}
// 출력:
// fun: 3
// java: 2
// is: 2
// powerful: 1
// coding: 1
