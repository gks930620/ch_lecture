package quest;

import java.util.Scanner;

/**
 * ch3 D. 비트/시프트 연산 — 정답 예시 (D-1 ~ D-4)
 */
public class SolutionD {
    // D-2에서 사용하는 권한 플래그
    static final int READ    = 0b001; // 1
    static final int WRITE   = 0b010; // 2
    static final int EXECUTE = 0b100; // 4

    public static void main(String[] args) {
        d1();
        d2();
        d3();
        d4();
    }

    // ── D-1. 2진수 문자열과 시프트 결과 ──
    static void d1() {
        Scanner sc = new Scanner("10"); // 입력 예: 10
        int n = sc.nextInt();

        System.out.println("2진수: " + Integer.toBinaryString(n)); // 2진수: 1010
        System.out.println("<< 1 : " + (n << 1));   // << 1 : 20
        System.out.println(">> 1 : " + (n >> 1));   // >> 1 : 5
        System.out.println(">>> 1: " + (n >>> 1));  // >>> 1: 5
    }

    // ── D-2. 비트 마스크 권한 플래그 ──
    static void d2() {
        int perm = 0;

        perm |= READ;           // 권한 부여(조합)
        perm |= WRITE;
        System.out.println("현재 권한: " + Integer.toBinaryString(perm)); // 현재 권한: 11

        System.out.println("READ 있음? " + ((perm & READ) != 0));       // true
        System.out.println("EXECUTE 있음? " + ((perm & EXECUTE) != 0)); // false

        perm &= ~WRITE;         // 권한 해제
        System.out.println("WRITE 해제 후: " + Integer.toBinaryString(perm)); // 1
        System.out.println("WRITE 있음? " + ((perm & WRITE) != 0));    // false
    }

    // ── D-3. RGB 정수값에서 R, G, B 분리 ──
    static void d3() {
        int color = 0xFF8040; // R=FF, G=80, B=40

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        System.out.println("R: " + r); // R: 255
        System.out.println("G: " + g); // G: 128
        System.out.println("B: " + b); // B: 64
    }

    // ── D-4. 음수 시프트에서 >> vs >>> ──
    static void d4() {
        int x = -8;
        // -8의 32비트 표현: 11111111 11111111 11111111 11111000

        System.out.println(x >> 1);   // -4  (부호 비트 1로 채움 -> 음수 유지)
        System.out.println(x >>> 1);  // 2147483644  (0으로 채움 -> 큰 양수)

        System.out.println(Integer.toBinaryString(x >> 1));
        // 11111111111111111111111111111100
        System.out.println(Integer.toBinaryString(x >>> 1));
        // 1111111111111111111111111111100 (31비트, 맨 앞이 0)
    }
}
