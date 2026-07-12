package quest;

import java.util.Scanner;

/**
 * ch4 A. 조건문 기초 — 정답 예시 (A-1 ~ A-4)
 *
 * 원래 답안은 System.in 입력을 받지만, 실행 결과를 바로 볼 수 있도록
 * 각 예제의 "입력 예" 값을 문자열 Scanner로 넣어 두었습니다.
 */
public class SolutionA {
    public static void main(String[] args) {
        a1();
        a2();
        a3();
        a4();
    }

    // ── A-1. 양수/음수/0 판별 ──
    static void a1() {
        Scanner sc = new Scanner("-7"); // 입력 예: -7
        int n = sc.nextInt();

        if (n > 0) {
            System.out.println("양수");
        } else if (n < 0) {
            System.out.println("음수"); // 음수
        } else {
            System.out.println("0");
        }
    }

    // ── A-2. 학점(A/B/C/D/F) 출력 ──
    static void a2() {
        Scanner sc = new Scanner("83"); // 입력 예: 83
        int score = sc.nextInt();

        String grade;
        if (score >= 90) {
            grade = "A";
        } else if (score >= 80) {
            grade = "B";
        } else if (score >= 70) {
            grade = "C";
        } else if (score >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }
        System.out.println(grade); // B
    }

    // ── A-3. 세 수의 중간값 ──
    static void a3() {
        Scanner sc = new Scanner("3 9 5"); // 입력 예: 3 9 5
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();

        int mid;
        if ((a >= b && a <= c) || (a <= b && a >= c)) {
            mid = a;
        } else if ((b >= a && b <= c) || (b <= a && b >= c)) {
            mid = b;
        } else {
            mid = c;
        }
        System.out.println("중간값: " + mid); // 중간값: 5
    }

    // ── A-4. 성인/청소년/아동 분류 ──
    static void a4() {
        Scanner sc = new Scanner("15"); // 입력 예: 15 (만 나이 기준)
        int age = sc.nextInt();

        if (age >= 19) {
            System.out.println("성인");
        } else if (age >= 13) {
            System.out.println("청소년"); // 청소년
        } else {
            System.out.println("아동");
        }
    }
}
