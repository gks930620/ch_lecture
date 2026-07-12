package quest;

import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("월 입력(1~12): ");
        int month = scanner.nextInt();

        // switch expression: switch 자체가 값을 반환한다
        String season = switch (month) {
            case 12, 1, 2 -> "겨울";
            case 3, 4, 5 -> "봄";
            case 6, 7, 8 -> "여름";
            case 9, 10, 11 -> "가을";
            default -> "잘못된 월";
        };
        System.out.println("계절: " + season);
    }
}
