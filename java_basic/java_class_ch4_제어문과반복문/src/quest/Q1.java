package quest;

import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("정수 입력: ");
        int n = scanner.nextInt();
        if (n > 0) {
            System.out.println("양수");
        } else if (n < 0) {
            System.out.println("음수");
        } else {
            System.out.println("0");
        }
    }
}
