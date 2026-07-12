package quest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// F-2. 로그에서 ERROR 코드 추출 후 빈도수 순 정렬
// 1차 스트림으로 빈도 맵을 만들고(groupingBy+counting), 2차 스트림에서 Map.Entry 값 기준 내림차순 정렬한다.
public class SolutionF2 {
    public static void main(String[] args) {
        List<String> logs = List.of(
                "2026-07-01 INFO  boot ok",
                "2026-07-01 ERROR E42 db timeout",
                "2026-07-01 ERROR E17 auth fail",
                "2026-07-02 WARN  slow query",
                "2026-07-02 ERROR E42 db timeout",
                "2026-07-02 ERROR E42 db timeout",
                "2026-07-02 ERROR E17 auth fail"
        );

        // 1단계: ERROR 라인에서 코드(3번째 토큰)만 뽑아 빈도 집계
        Map<String, Long> freq = logs.stream()
                .filter(line -> line.contains("ERROR"))
                .map(line -> line.split("\\s+")[2]) // E42, E17, ...
                .collect(Collectors.groupingBy(code -> code, Collectors.counting()));

        // 2단계: 빈도 내림차순 정렬 (동률이면 코드 오름차순)
        List<String> ranked = freq.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry::getKey))
                .map(e -> e.getKey() + " x" + e.getValue())
                .toList();

        System.out.println(ranked); // [E42 x3, E17 x2]
    }
}
