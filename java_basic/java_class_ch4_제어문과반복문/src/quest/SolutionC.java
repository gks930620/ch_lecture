package quest;

import java.util.Scanner;

/**
 * ch4 C. 반복문 기초 — 정답 예시 (C-1 ~ C-4)
 */
public class SolutionC {
    public static void main(String[] args) {
        c1();
        c2();
        c3();
        c4();
    }

    // ── C-1. 1~n 합계 (for) ──
    static void c1() {
        Scanner sc = new Scanner("10"); // 입력 예: 10
        int n = sc.nextInt();

        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        System.out.println("합계: " + sum); // 합계: 55
    }

    // ── C-2. 구구단 n단 ──
    static void c2() {
        Scanner sc = new Scanner("3"); // 입력 예: 3
        int n = sc.nextInt();

        for (int i = 1; i <= 9; i++) {
            System.out.println(n + " x " + i + " = " + (n * i));
        }
    }

    // ── C-3. 1~100 홀수 합 / 짝수 합 ──
    static void c3() {
        int oddSum = 0;
        int evenSum = 0;

        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                evenSum += i;
            } else {
                oddSum += i;
            }
        }
        System.out.println("홀수 합: " + oddSum);  // 홀수 합: 2500
        System.out.println("짝수 합: " + evenSum); // 짝수 합: 2550
    }

    // ── C-4. 문자열의 모음 개수 세기 ──
    static void c4() {
        Scanner sc = new Scanner("Programming"); // 입력 예: Programming
        String text = sc.next();

        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = Character.toLowerCase(text.charAt(i));
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count++;
            }
        }
        System.out.println("모음 개수: " + count); // 모음 개수: 3  (o, a, i)
    }
}
