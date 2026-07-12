package quest;

import java.util.ArrayList;
import java.util.List;

// F-2: for-each 중 구조 변경 예외와 수정 코드
public class SolutionF2 {
    public static void main(String[] args) {
        // 1) 문제 코드: for-each 순회 중 리스트를 직접 수정
        List<Integer> bad = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        try {
            for (Integer n : bad) {
                if (n % 2 == 0) {
                    bad.remove(n); // 순회 중 구조 변경!
                }
            }
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getClass().getSimpleName());
            // 예외 발생: ConcurrentModificationException
        }

        // 2) 수정 코드: removeIf 사용 (내부적으로 안전하게 삭제)
        List<Integer> good = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        good.removeIf(n -> n % 2 == 0);
        System.out.println(good); // [1, 3, 5]
    }
}
