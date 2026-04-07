package quest;

import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("정수 입력: ");
        int n = scanner.nextInt();
        if (n % 2 == 0) {
            System.out.println("짝수");
        } else {
            System.out.println("홀수");
        }
    }
}
