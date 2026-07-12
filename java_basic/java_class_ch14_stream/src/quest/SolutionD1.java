package quest;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

// D-1. 단어 빈도수 맵 집계
// 같은 키로 묶는 groupingBy와 묶인 요소를 세는 다운스트림 counting()의 조합이 정석이다.
public class SolutionD1 {
    public static void main(String[] args) {
        List<String> words = List.of("java", "stream", "java", "lambda", "stream", "java");

        Map<String, Long> freq = words.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),   // 단어 자체가 키
                        TreeMap::new,          // 키 정렬(출력 고정용)
                        Collectors.counting()));

        System.out.println(freq); // {java=3, lambda=1, stream=2}
    }
}
