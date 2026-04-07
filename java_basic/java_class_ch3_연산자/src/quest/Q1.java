package quest;

import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("첫 번째 정수: ");
        int a = scanner.nextInt();
        System.out.print("두 번째 정수: ");
        int b = scanner.nextInt();

        System.out.println("합: " + (a + b));
        System.out.println("차: " + (a - b));
        System.out.println("곱: " + (a * b));
        System.out.println("몫: " + (a / b));
        System.out.println("나머지: " + (a % b));
    }
}
