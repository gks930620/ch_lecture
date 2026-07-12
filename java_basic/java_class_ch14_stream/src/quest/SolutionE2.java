package quest;

import java.util.ArrayList;
import java.util.List;

// E-2. 같은 로직: 루프 버전 vs 스트림 버전
// 스트림은 "무엇을(필터->변환->수집)"이 한눈에 드러나고, 루프는 인덱스/임시 변수 등 "어떻게"가 섞인다.
public class SolutionE2 {
    public static void main(String[] args) {
        List<String> words = List.of("  java ", "", "stream", "  ", "lambda ");

        // 1) 루프 버전: 절차가 그대로 노출
        List<String> byLoop = new ArrayList<>();
        for (String w : words) {
            String t = w.trim();
            if (!t.isEmpty()) {
                byLoop.add(t.toUpperCase());
            }
        }
        System.out.println(byLoop); // [JAVA, STREAM, LAMBDA]

        // 2) 스트림 버전: 단계 선언만 남음
        List<String> byStream = words.stream()
                .map(String::trim)
                .filter(t -> !t.isEmpty())
                .map(String::toUpperCase)
                .toList();
        System.out.println(byStream); // [JAVA, STREAM, LAMBDA]
    }
}
