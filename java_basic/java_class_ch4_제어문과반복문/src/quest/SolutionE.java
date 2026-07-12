package quest;

import java.util.Scanner;

/**
 * ch4 E. break / continue — 정답 예시 (E-1 ~ E-4)
 */
public class SolutionE {
    public static void main(String[] args) {
        e1();
        e2();
        e3();
        e4();
    }

    // ── E-1. 처음 등장하는 음수의 인덱스 찾기 (break) ──
    static void e1() {
        int[] arr = {3, 8, 2, -5, 9, -1};

        int foundIndex = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                foundIndex = i;
                break; // 첫 음수를 찾았으므로 더 볼 필요 없음
            }
        }
        System.out.println("첫 음수 인덱스: " + foundIndex); // 첫 음수 인덱스: 3
    }

    // ── E-2. 3의 배수만 건너뛰고 출력 (continue) ──
    static void e2() {
        for (int i = 1; i <= 100; i++) {
            if (i % 3 == 0) {
                continue; // 3의 배수는 이번 회차만 건너뜀
            }
            System.out.println(i);
        }
    }

    // ── E-3. 이중 반복문에서 바깥 루프까지 종료 (label break) ──
    static void e3() {
        int target = 12;

        outer:
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (i * j == target) {
                    System.out.println("발견: " + i + " x " + j + " = " + target);
                    break outer; // 안쪽만이 아니라 바깥 루프까지 즉시 종료
                }
            }
        }
        // 발견: 2 x 6 = 12
    }

    // ── E-4. 소수 판별 루프에 break 적용 ──
    static void e4() {
        Scanner sc = new Scanner("97"); // 입력 예: 97
        int n = sc.nextInt();

        boolean isPrime = n >= 2;
        for (int i = 2; i <= n / i; i++) { // i * i <= n 과 같음 (오버플로우 안전형)
            if (n % i == 0) {
                isPrime = false;
                break; // 약수를 하나라도 찾으면 더 검사할 필요 없음
            }
        }
        System.out.println(n + (isPrime ? "은(는) 소수" : "은(는) 소수 아님")); // 97은(는) 소수
    }
}
