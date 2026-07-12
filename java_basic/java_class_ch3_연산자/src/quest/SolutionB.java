package quest;

import java.util.Scanner;

/**
 * ch3 B. 비교/논리 연산 — 정답 예시 (B-1 ~ B-4)
 * Scanner에는 각 예제의 "입력 예" 값을 넣어 바로 실행되도록 했습니다.
 */
public class SolutionB {
    public static void main(String[] args) {
        b1();
        b2();
        b3();
        b4();
    }

    // ── B-1. 아이디/비밀번호 로그인 ──
    static void b1() {
        final String ID = "admin";
        final String PW = "1234";

        Scanner sc = new Scanner("admin 1234"); // 입력 예: admin 1234
        String id = sc.next();
        String pw = sc.next();

        if (ID.equals(id) && PW.equals(pw)) {
            System.out.println("로그인 성공"); // 로그인 성공
        } else {
            System.out.println("로그인 실패");
        }
    }

    // ── B-2. 세 정수의 최댓값/최솟값 (Math.max/min 금지) ──
    static void b2() {
        Scanner sc = new Scanner("3 9 5"); // 입력 예: 3 9 5
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();

        int max = a;
        if (b > max) max = b;
        if (c > max) max = c;

        int min = a;
        if (b < min) min = b;
        if (c < min) min = c;

        System.out.println("최댓값: " + max); // 최댓값: 9
        System.out.println("최솟값: " + min); // 최솟값: 3
    }

    // ── B-3. 삼각형 성립 여부 ──
    static void b3() {
        Scanner sc = new Scanner("3 4 5"); // 입력 예: 3 4 5
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();

        boolean positive = a > 0 && b > 0 && c > 0;
        boolean triangle = positive
                && a + b > c
                && b + c > a
                && a + c > b;

        System.out.println(triangle ? "삼각형 가능" : "삼각형 불가"); // 삼각형 가능
    }

    // ── B-4. 윤년 판별 ──
    static void b4() {
        Scanner sc = new Scanner("2024"); // 입력 예: 2024
        int year = sc.nextInt();

        boolean leap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

        System.out.println(year + "년: " + (leap ? "윤년" : "평년")); // 2024년: 윤년
        // 2100 -> 평년, 2000 -> 윤년
    }
}
