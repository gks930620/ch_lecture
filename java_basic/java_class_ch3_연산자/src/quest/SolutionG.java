package quest;

import java.util.Scanner;

/**
 * ch3 G. 챌린지 — 정답 예시 (G-1 ~ G-3)
 */
public class SolutionG {
    // G-3에서 사용하는 로그 레벨 비트 플래그
    static final int DEBUG = 1 << 0; // 0001
    static final int INFO  = 1 << 1; // 0010
    static final int WARN  = 1 << 2; // 0100
    static final int ERROR = 1 << 3; // 1000

    public static void main(String[] args) {
        g1();
        g2();
        g3();
    }

    // ── G-1. 계산기 프로그램 ──
    static void g1() {
        Scanner sc = new Scanner("10 3 /"); // 입력 예: 10 3 /

        int a = sc.nextInt();
        int b = sc.nextInt();
        String op = sc.next();

        if (op.equals("+")) {
            System.out.println(a + b);
        } else if (op.equals("-")) {
            System.out.println(a - b);
        } else if (op.equals("*")) {
            System.out.println(a * b);
        } else if (op.equals("/")) {
            if (b == 0) {
                System.out.println("오류: 0으로 나눌 수 없습니다");
            } else {
                System.out.println(a / b); // 3
            }
        } else if (op.equals("%")) {
            if (b == 0) {
                System.out.println("오류: 0으로 나눌 수 없습니다");
            } else {
                System.out.println(a % b);
            }
        } else {
            System.out.println("오류: 지원하지 않는 연산자입니다 (" + op + ")");
        }
    }

    // ── G-2. 비트 연산만 사용한 홀짝 판별, 절댓값 근사 ──
    // 최하위 비트가 1이면 홀수 (음수도 2의 보수 표현상 동일하게 동작)
    static boolean isOdd(int n) {
        return (n & 1) == 1;
    }

    // 절댓값: mask = n >> 31 (양수면 0, 음수면 -1)
    // 음수일 때 (n ^ mask) - mask = (~n) + 1 = -n
    static int abs(int n) {
        int mask = n >> 31;
        return (n ^ mask) - mask;
    }

    static void g2() {
        System.out.println(isOdd(7));   // true
        System.out.println(isOdd(-3));  // true
        System.out.println(isOdd(10));  // false

        System.out.println(abs(5));    // 5
        System.out.println(abs(-5));   // 5
        System.out.println(abs(0));    // 0
        // 주의: abs(Integer.MIN_VALUE)는 표현 불가로 그대로 MIN_VALUE가 나온다 (Math.abs도 동일)
    }

    // ── G-3. 로그 시스템 상태 코드 비트 플래그 설계 ──
    static int enable(int status, int flag) {   // 플래그 조합(켜기)
        return status | flag;
    }

    static int disable(int status, int flag) {  // 플래그 해제(끄기)
        return status & ~flag;
    }

    static boolean isEnabled(int status, int flag) { // 플래그 검사
        return (status & flag) != 0;
    }

    static void g3() {
        int status = 0;

        status = enable(status, INFO);
        status = enable(status, ERROR);
        System.out.println(Integer.toBinaryString(status)); // 1010

        System.out.println(isEnabled(status, ERROR)); // true
        System.out.println(isEnabled(status, DEBUG)); // false

        status = disable(status, ERROR);
        System.out.println(isEnabled(status, ERROR)); // false
        System.out.println(Integer.toBinaryString(status)); // 10
    }
}
