package quest;

import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        while (true) {
            System.out.print("정수 입력(종료: 0): ");
            int n = scanner.nextInt();
            if (n == 0) {
                break;
            }
            sum += n;
        }
        System.out.println("최종 합계: " + sum);
    }
}
