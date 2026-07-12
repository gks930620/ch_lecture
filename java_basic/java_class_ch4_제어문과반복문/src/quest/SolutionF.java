package quest;

import java.util.Scanner;

/**
 * ch4 F. 패턴/응용 — 정답 예시 (F-1 ~ F-4)
 */
public class SolutionF {
    public static void main(String[] args) {
        f1();
        f2();
        f3();
        f4();
    }

    // ── F-1. 별 피라미드(정삼각형) 출력 ──
    static void f1() {
        Scanner sc = new Scanner("5"); // 입력 예: 5
        int height = sc.nextInt();

        for (int i = 1; i <= height; i++) {
            for (int j = 0; j < height - i; j++) {
                System.out.print(" ");   // 공백: height - i 개
            }
            for (int j = 0; j < 2 * i - 1; j++) {
                System.out.print("*");   // 별: 2i - 1 개
            }
            System.out.println();
        }
    }

    // ── F-2. 2차원 배열의 대각선 합 ──
    static void f2() {
        int[][] m = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        int n = m.length;

        int mainDiag = 0; // 주대각선 (왼쪽 위 -> 오른쪽 아래)
        int antiDiag = 0; // 반대각선 (오른쪽 위 -> 왼쪽 아래)
        for (int i = 0; i < n; i++) {
            mainDiag += m[i][i];
            antiDiag += m[i][n - 1 - i];
        }
        System.out.println("주대각선 합: " + mainDiag); // 15 (1+5+9)
        System.out.println("반대각선 합: " + antiDiag); // 15 (3+5+7)
    }

    // ── F-3. 로또 번호(중복 없는 6개) 생성 ──
    static void f3() {
        int[] lotto = new int[6];
        int count = 0;

        while (count < 6) {
            int num = (int) (Math.random() * 45) + 1; // 1~45 난수

            boolean duplicated = false;
            for (int i = 0; i < count; i++) { // 이미 뽑은 번호인지 검사
                if (lotto[i] == num) {
                    duplicated = true;
                    break;
                }
            }
            if (!duplicated) {
                lotto[count] = num;
                count++;
            }
        }

        for (int n : lotto) {
            System.out.print(n + " ");
        }
        System.out.println();
        // 출력 예: 7 23 41 3 15 32  (실행마다 다름, 중복 없음)
    }

    // ── F-4. 숫자 야구 입력 검증 루프 ──
    static void f4() {
        Scanner sc = new Scanner("1234 505 373 123"); // 입력 예: 1234 -> 505 -> 373 -> 123
        int number;

        while (true) {
            System.out.print("서로 다른 1~9 숫자 3자리 입력: ");
            number = sc.nextInt();

            if (number < 111 || number > 999) {
                System.out.println("3자리 수가 아닙니다");
                continue;
            }
            int d1 = number / 100;        // 백의 자리
            int d2 = number / 10 % 10;    // 십의 자리
            int d3 = number % 10;         // 일의 자리

            if (d1 == 0 || d2 == 0 || d3 == 0) {
                System.out.println("0은 사용할 수 없습니다");
                continue;
            }
            if (d1 == d2 || d2 == d3 || d1 == d3) {
                System.out.println("중복된 숫자가 있습니다");
                continue;
            }
            break; // 모든 검증 통과
        }
        System.out.println("입력 확인: " + number); // 입력 확인: 123
    }
}
