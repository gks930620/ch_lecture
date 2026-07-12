package quest;

import java.util.Arrays;

// E. 복사/참조 문제 (E-1 ~ E-4)
public class SolutionE {

    // E-4. 방어적 복사 (생성자/게터)
    static class ScoreBoard {
        private final int[] scores;

        ScoreBoard(int[] scores) {
            // 방어적 복사 1: 외부 배열을 그대로 저장하지 않고 사본을 저장
            this.scores = scores.clone();
        }

        int[] getScores() {
            // 방어적 복사 2: 내부 배열을 그대로 노출하지 않고 사본을 반환
            return scores.clone();
        }
    }

    public static void main(String[] args) {
        // E-1. b = a (참조 복사) vs a.clone() (실제 복사)
        int[] a = {1, 2, 3};
        int[] b = a;         // 참조 복사: 같은 배열을 가리킴
        int[] c = a.clone(); // 실제 복사: 새 배열 생성
        b[0] = 99;
        c[0] = -1;
        System.out.println(Arrays.toString(a)); // [99, 2, 3] <- b 변경이 a에 반영
        System.out.println(Arrays.toString(b)); // [99, 2, 3]
        System.out.println(Arrays.toString(c)); // [-1, 2, 3] <- a에 영향 없음
        System.out.println(a == b); // true  (같은 객체)
        System.out.println(a == c); // false (다른 객체)

        // E-2. Arrays.copyOf와 System.arraycopy
        int[] src = {1, 2, 3, 4, 5};
        int[] c1 = Arrays.copyOf(src, src.length);
        int[] c2 = Arrays.copyOf(src, 7); // 늘어난 칸은 기본값 0
        int[] c3 = new int[src.length];
        System.arraycopy(src, 0, c3, 0, src.length); // (src, srcPos, dest, destPos, length)
        System.out.println(Arrays.toString(c1)); // [1, 2, 3, 4, 5]
        System.out.println(Arrays.toString(c2)); // [1, 2, 3, 4, 5, 0, 0]
        System.out.println(Arrays.toString(c3)); // [1, 2, 3, 4, 5]
        c1[0] = 99;
        System.out.println(src[0]); // 1 (원본 무영향)

        // E-3. 참조형 배열의 얕은 복사 문제
        int[][] original = {
            {1, 2},
            {3, 4}
        };
        int[][] shallow = original.clone(); // 얕은 복사: 바깥 배열만 새로 생성
        System.out.println(original == shallow);       // false (바깥 배열은 다름)
        System.out.println(original[0] == shallow[0]); // true  (안쪽 배열은 공유!)
        shallow[0][0] = 99; // 사본을 고쳤는데...
        System.out.println(Arrays.deepToString(original)); // [[99, 2], [3, 4]] <- 원본까지 바뀜

        // 깊은 복사: 각 행을 개별적으로 복사해야 독립
        int[][] deep = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            deep[i] = original[i].clone(); // 행마다 실제 복사
        }
        deep[0][0] = -1;
        System.out.println(Arrays.deepToString(original)); // 원본 무영향

        // E-4. 방어적 복사 확인
        int[] input = {90, 80, 70};
        ScoreBoard board = new ScoreBoard(input);
        input[0] = 0; // 외부에서 원본 배열을 조작해도
        System.out.println(Arrays.toString(board.getScores())); // [90, 80, 70]
        int[] leaked = board.getScores();
        leaked[0] = -999; // 게터로 받은 배열을 조작해도
        System.out.println(Arrays.toString(board.getScores())); // [90, 80, 70]
    }
}
