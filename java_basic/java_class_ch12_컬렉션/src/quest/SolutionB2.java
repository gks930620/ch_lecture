package quest;

import java.util.Set;
import java.util.TreeSet;

// B-2: 두 Set의 합집합/교집합/차집합 (Q2는 합집합/교집합만 다루므로 차집합 포함 전체 버전)
public class SolutionB2 {
    public static void main(String[] args) {
        Set<Integer> a = Set.of(1, 2, 3, 4);
        Set<Integer> b = Set.of(3, 4, 5, 6);

        // TreeSet으로 받아 출력 순서를 오름차순으로 고정
        Set<Integer> union = new TreeSet<>(a);
        union.addAll(b);

        Set<Integer> intersection = new TreeSet<>(a);
        intersection.retainAll(b);

        Set<Integer> difference = new TreeSet<>(a);
        difference.removeAll(b);

        System.out.println("합집합: " + union);          // 합집합: [1, 2, 3, 4, 5, 6]
        System.out.println("교집합: " + intersection);    // 교집합: [3, 4]
        System.out.println("차집합(a-b): " + difference); // 차집합(a-b): [1, 2]

        // 핵심: addAll/retainAll/removeAll은 원본을 파괴적으로 수정하므로 복사본을 만들어 연산한다.
    }
}
