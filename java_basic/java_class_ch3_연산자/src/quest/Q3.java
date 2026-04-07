package quest;

import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("정수 입력: ");
        int n = scanner.nextInt();

        System.out.println("2진수: " + Integer.toBinaryString(n));
        System.out.println("n << 1: " + (n << 1));
        System.out.println("n >> 1: " + (n >> 1));
    }
}
