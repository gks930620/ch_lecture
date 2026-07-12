package quest;

/**
 * ch3 C. 실수/정밀도 — 정답 예시 (C-1 ~ C-3)
 */
public class SolutionC {
    static final double EPSILON = 1e-9;

    public static void main(String[] args) {
        c1();
        c2();
        c3();
    }

    // ── C-1. 0.1 + 0.2 == 0.3 비교 결과 ──
    static void c1() {
        double a = 0.1 + 0.2;
        System.out.println(a);          // 0.30000000000000004
        System.out.println(a == 0.3);   // false
    }

    // ── C-2. 허용 오차(EPSILON) 기반 실수 비교 ──
    static boolean nearlyEquals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    static void c2() {
        double sum = 0.1 + 0.2;
        System.out.println(sum == 0.3);              // false
        System.out.println(nearlyEquals(sum, 0.3));  // true
        System.out.println(nearlyEquals(1.0, 1.5));  // false
    }

    // ── C-3. 정수 나눗셈 vs 실수 나눗셈 예제 3개 ──
    static void c3() {
        // 예제 1: 몫이 버려지는 경우
        System.out.println(7 / 2);     // 3
        System.out.println(7 / 2.0);   // 3.5

        // 예제 2: 결과가 0이 되어버리는 경우 (비율 계산 버그의 단골)
        System.out.println(1 / 3);         // 0
        System.out.println(1.0 / 3);       // 0.3333333333333333

        // 예제 3: 평균 계산
        int s1 = 90, s2 = 85;
        System.out.println((s1 + s2) / 2);     // 87  (87.5의 소수부 버려짐)
        System.out.println((s1 + s2) / 2.0);   // 87.5
    }
}
