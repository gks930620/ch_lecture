package quest;

import java.util.Scanner;

/**
 * ch3 A. 기초 문제 — 정답 예시 (A-1 ~ A-4)
 *
 * 원래 답안은 System.in 에서 입력을 받지만, 실행해서 결과를 바로 확인할 수 있도록
 * 각 예제의 "입력 예" 값을 문자열 Scanner로 넣어 두었습니다.
 */
public class SolutionA {
    public static void main(String[] args) {
        a1();
        a2();
        a3();
        a4();
    }

    // ── A-1. 두 정수의 합, 차, 곱, 몫, 나머지 ──
    static void a1() {
        Scanner sc = new Scanner("7 3"); // 입력 예: 7 3
        int a = sc.nextInt();
        int b = sc.nextInt();

        System.out.println("합: " + (a + b));   // 합: 10
        System.out.println("차: " + (a - b));   // 차: 4
        System.out.println("곱: " + (a * b));   // 곱: 21
        System.out.println("몫: " + (a / b));   // 몫: 2
        System.out.println("나머지: " + (a % b)); // 나머지: 1
    }

    // ── A-2. 세 점수의 평균과 PASS/FAIL ──
    static void a2() {
        Scanner sc = new Scanner("70 55 65"); // 입력 예: 70 55 65
        int s1 = sc.nextInt();
        int s2 = sc.nextInt();
        int s3 = sc.nextInt();

        double avg = (s1 + s2 + s3) / 3.0; // 3이 아니라 3.0으로 나눠야 실수 평균
        String result = avg >= 60 ? "PASS" : "FAIL";

        System.out.println("평균: " + avg);   // 평균: 63.333333333333336
        System.out.println(result);           // PASS
    }

    // ── A-3. 짝수/홀수 판별 ──
    static void a3() {
        Scanner sc = new Scanner("13"); // 입력 예: 13
        int n = sc.nextInt();

        String result = n % 2 == 0 ? "짝수" : "홀수";
        System.out.println(result); // 홀수
    }

    // ── A-4. 일반 요금 / 우대 요금 ──
    static void a4() {
        Scanner sc = new Scanner("70"); // 입력 예: 70
        int age = sc.nextInt();

        String fare = (age >= 20 && age < 65) ? "일반 요금" : "우대 요금";
        System.out.println(fare); // 우대 요금
    }
}
