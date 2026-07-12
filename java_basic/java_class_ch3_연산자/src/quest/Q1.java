package quest;

import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("첫 번째 정수: ");
        int a = scanner.nextInt();
        System.out.print("두 번째 정수: ");
        int b = scanner.nextInt();
        // 주의: 두 번째 정수로 0을 입력하면 몫/나머지 계산에서 ArithmeticException 발생
        // (0 입력 방지는 ch4 조건문 학습 후 추가할 수 있다)

        System.out.println("합: " + (a + b));
        System.out.println("차: " + (a - b));
        System.out.println("곱: " + (a * b));
        System.out.println("몫: " + (a / b));
        System.out.println("나머지: " + (a % b));
    }
}
