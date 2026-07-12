package 해쉬예제;

import java.util.HashSet;
import java.util.Set;

// 프로그래머스 "폰켓몬" 유형 — HashSet으로 "중복 제거(종류의 수)"를 구하는 대표 예제.
// N마리 중 N/2마리만 고를 수 있을 때, 고를 수 있는 종류의 최대 개수를 구한다.
public class 폰켓몬 {
    public static void main(String[] args) {
        int[] nums = {3, 1, 2, 3};   // 폰켓몬 종류 번호 (총 4마리 -> 2마리 선택 가능)

        // 1) 서로 다른 종류의 수 = Set의 크기
        Set<Integer> kinds = new HashSet<>();
        for (int n : nums) {
            kinds.add(n);   // 이미 있으면 무시됨(중복 제거)
        }

        int canPick = nums.length / 2;      // 뽑을 수 있는 마리 수
        int kindCount = kinds.size();       // 존재하는 종류 수

        // 2) 뽑을 수 있는 수와 종류 수 중 더 작은 값이 정답
        //    (종류가 아무리 많아도 N/2마리까지만 뽑을 수 있으므로)
        int answer = Math.min(canPick, kindCount);

        System.out.println("고를 수 있는 종류의 최대 개수: " + answer);  // 2
    }
}
