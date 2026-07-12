package quest;

import java.util.List;

// C-2. anyMatch/allMatch/noneMatch 조건 검사
// 세 연산 모두 Predicate를 받아 boolean을 반환하며, 결과가 확정되는 즉시 순회를 멈추는 단축 평가 연산이다.
public class SolutionC2 {
    public static void main(String[] args) {
        List<Integer> scores = List.of(72, 85, 90, 61, 88);

        boolean anyPerfectish = scores.stream().anyMatch(s -> s >= 90);
        boolean allPassed = scores.stream().allMatch(s -> s >= 60);
        boolean noneFailed = scores.stream().noneMatch(s -> s < 60);

        System.out.println("90점 이상 존재? " + anyPerfectish); // true
        System.out.println("전원 60점 이상? " + allPassed);     // true
        System.out.println("낙제자 없음? " + noneFailed);       // true
    }
}
