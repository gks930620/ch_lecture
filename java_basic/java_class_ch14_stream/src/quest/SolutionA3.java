package quest;

import java.util.List;

// A-3. 길이 3 이상인 문자열 개수
// 조건 검사는 filter + 최종 연산 count의 전형적인 조합이다.
public class SolutionA3 {
    public static void main(String[] args) {
        List<String> words = List.of("go", "java", "c", "rust", "kotlin");

        long count = words.stream()
                .filter(w -> w.length() >= 3)  // java, rust, kotlin
                .count();

        System.out.println(count); // 3
    }
}
