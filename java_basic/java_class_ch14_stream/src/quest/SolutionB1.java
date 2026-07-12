package quest;

import java.util.List;

// B-1. distinct 후 limit(5)
// distinct는 equals 기준 중복 제거, limit은 앞에서부터 N개만 통과시키는 단축(short-circuit) 중간 연산이다.
public class SolutionB1 {
    public static void main(String[] args) {
        List<Integer> nums = List.of(5, 3, 5, 1, 9, 3, 7, 1, 8, 2);

        List<Integer> top5 = nums.stream()
                .distinct()   // 5, 3, 1, 9, 7, 8, 2 (등장 순서 유지)
                .limit(5)     // 5, 3, 1, 9, 7
                .toList();

        System.out.println(top5); // [5, 3, 1, 9, 7]
    }
}
