package quest;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

// D-3. 점수 요약 통계(IntSummaryStatistics)
// 개수/합/최소/최대/평균이 모두 필요하면 스트림을 5번 돌리는 대신 summarizingInt 한 번으로 끝낸다.
public class SolutionD3 {
    public static void main(String[] args) {
        List<Integer> scores = List.of(72, 85, 90, 61, 88);

        IntSummaryStatistics stats = scores.stream()
                .collect(Collectors.summarizingInt(Integer::intValue));

        System.out.println("개수: " + stats.getCount());   // 개수: 5
        System.out.println("합계: " + stats.getSum());     // 합계: 396
        System.out.println("최소: " + stats.getMin());     // 최소: 61
        System.out.println("최대: " + stats.getMax());     // 최대: 90
        System.out.println("평균: " + stats.getAverage()); // 평균: 79.2
    }
}
