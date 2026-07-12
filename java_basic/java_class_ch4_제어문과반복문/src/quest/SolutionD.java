package quest;

import java.util.Scanner;

/**
 * ch4 D. while / do-while — 정답 예시 (D-1 ~ D-4)
 *
 * 입력 루프가 있는 예제도 실행되도록, 각 예제의 "입력 예"를 문자열 Scanner로 넣었습니다.
 */
public class SolutionD {
    public static void main(String[] args) {
        d1();
        d2();
        d3();
        d4();
    }

    // ── D-1. 0이 입력될 때까지 총합 (while, 센티넬 패턴) ──
    static void d1() {
        Scanner sc = new Scanner("5 3 7 0"); // 입력 예: 5 3 7 0
        int sum = 0;

        int n = sc.nextInt();
        while (n != 0) {
            sum += n;
            n = sc.nextInt();
        }
        System.out.println("총합: " + sum); // 총합: 15
    }

    // ── D-2. 비밀번호 맞출 때까지 입력 (do-while) ──
    static void d2() {
        final String PASSWORD = "java17";
        Scanner sc = new Scanner("hello java java17"); // 입력 예: hello -> java -> java17
        String input;

        do {
            System.out.print("비밀번호 입력: ");
            input = sc.next();
        } while (!PASSWORD.equals(input));

        System.out.println("인증 성공"); // (세 번째 입력 후) 인증 성공
    }

    // ── D-3. 메뉴 반복 출력, 0 입력 시 종료 ──
    static void d3() {
        Scanner sc = new Scanner("1 9 0"); // 입력 예: 1 -> 조회, 9 -> 잘못된 메뉴, 0 -> 종료

        while (true) {
            System.out.println("=== 메뉴 === 1:조회 2:등록 3:삭제 0:종료");
            int menu = sc.nextInt();

            if (menu == 0) {
                System.out.println("프로그램을 종료합니다");
                break;
            }
            switch (menu) {
                case 1 -> System.out.println("조회 실행");
                case 2 -> System.out.println("등록 실행");
                case 3 -> System.out.println("삭제 실행");
                default -> System.out.println("잘못된 메뉴입니다");
            }
        }
    }

    // ── D-4. 입력 횟수 제한 로그인 시뮬레이터 ──
    static void d4() {
        final String PASSWORD = "1234";
        final int MAX_TRY = 3;
        Scanner sc = new Scanner("1111 2222 3333"); // 입력 예: 1111 -> 2222 -> 3333

        boolean success = false;
        int attempt = 0;

        while (attempt < MAX_TRY) {
            attempt++;
            System.out.print("비밀번호 (" + attempt + "/" + MAX_TRY + "): ");
            String input = sc.next();

            if (PASSWORD.equals(input)) {
                success = true;
                break;
            }
            System.out.println("불일치");
        }

        System.out.println(success ? "로그인 성공" : "계정 잠금: 시도 횟수 초과");
        // 불일치 x3 후 "계정 잠금: 시도 횟수 초과"
    }
}
