package quest;

import java.util.ArrayList;
import java.util.List;

// F-2. 배치 처리 — 전체 중단 vs 계속 처리 전략
public class SolutionF2 {
    public static void main(String[] args) {
        List<String> records = List.of("100", "200", "oops", "300", "bad", "400");

        System.out.println("--- 전략 1: 실패 시 전체 중단(fail-fast) ---");
        try {
            int total = 0;
            for (String r : records) {
                total += Integer.parseInt(r); // 실패하면 즉시 전파 -> 전체 중단
            }
            System.out.println("합계: " + total);
        } catch (NumberFormatException e) {
            System.out.println("배치 중단: 잘못된 레코드 발견 -> " + e.getMessage());
        }

        System.out.println("--- 전략 2: 실패 레코드 건너뛰고 계속 처리(skip-and-log) ---");
        int total = 0;
        List<String> failed = new ArrayList<>();
        for (String r : records) {
            try {
                total += Integer.parseInt(r);
            } catch (NumberFormatException e) {
                failed.add(r); // 실패 레코드는 기록만 하고 계속
            }
        }
        System.out.println("합계: " + total);
        System.out.println("실패 레코드: " + failed);
    }
}
// 출력:
// --- 전략 1: 실패 시 전체 중단(fail-fast) ---
// 배치 중단: 잘못된 레코드 발견 -> For input string: "oops"
// --- 전략 2: 실패 레코드 건너뛰고 계속 처리(skip-and-log) ---
// 합계: 1000
// 실패 레코드: [oops, bad]
