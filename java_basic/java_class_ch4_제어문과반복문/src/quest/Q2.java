package quest;

import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("월 입력(1~12): ");
        int month = scanner.nextInt();

        String season;
        switch (month) {
            case 12, 1, 2 -> season = "겨울";
            case 3, 4, 5 -> season = "봄";
            case 6, 7, 8 -> season = "여름";
            case 9, 10, 11 -> season = "가을";
            default -> season = "잘못된 월";
        }
        System.out.println("계절: " + season);
    }
}
