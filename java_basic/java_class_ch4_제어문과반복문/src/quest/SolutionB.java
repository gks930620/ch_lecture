package quest;

import java.util.Scanner;

/**
 * ch4 B. switch 문제 — 정답 예시 (B-1 ~ B-4)
 */
public class SolutionB {
    public static void main(String[] args) {
        b1();
        b2();
        b3();
        b4();
    }

    // ── B-1. 월 → 계절 (전통적 switch) ──
    static void b1() {
        Scanner sc = new Scanner("4"); // 입력 예: 4
        int month = sc.nextInt();

        String season;
        switch (month) {
            case 12:
            case 1:
            case 2:
                season = "겨울";
                break;
            case 3:
            case 4:
            case 5:
                season = "봄";
                break;
            case 6:
            case 7:
            case 8:
                season = "여름";
                break;
            case 9:
            case 10:
            case 11:
                season = "가을";
                break;
            default:
                season = "잘못된 월";
        }
        System.out.println(season); // 봄
    }

    // ── B-2. 요일 번호 → 요일명 ──
    static void b2() {
        Scanner sc = new Scanner("3"); // 입력 예: 3
        int day = sc.nextInt();

        switch (day) {
            case 1: System.out.println("월요일"); break;
            case 2: System.out.println("화요일"); break;
            case 3: System.out.println("수요일"); break; // 수요일
            case 4: System.out.println("목요일"); break;
            case 5: System.out.println("금요일"); break;
            case 6: System.out.println("토요일"); break;
            case 7: System.out.println("일요일"); break;
            default: System.out.println("잘못된 번호");
        }
    }

    // ── B-3. 메뉴 선택 프로그램 ──
    static void b3() {
        Scanner sc = new Scanner("2"); // 입력 예: 2
        System.out.println("1:조회, 2:등록, 3:삭제");
        int menu = sc.nextInt();

        switch (menu) {
            case 1:
                System.out.println("조회 화면입니다");
                break;
            case 2:
                System.out.println("등록 화면입니다"); // 등록 화면입니다
                break;
            case 3:
                System.out.println("삭제 화면입니다");
                break;
            default:
                System.out.println("없는 메뉴입니다");
        }
    }

    // ── B-4. switch expression으로 리팩터링 ──
    static void b4() {
        Scanner sc = new Scanner("11"); // 입력 예: 11
        int month = sc.nextInt();

        String season = switch (month) {
            case 12, 1, 2 -> "겨울";
            case 3, 4, 5 -> "봄";
            case 6, 7, 8 -> "여름";
            case 9, 10, 11 -> "가을";
            default -> "잘못된 월";
        };
        System.out.println(season); // 가을
    }
}
