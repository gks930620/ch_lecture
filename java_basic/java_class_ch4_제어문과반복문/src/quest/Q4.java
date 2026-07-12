package quest;

import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        System.out.print("정수 입력(종료: 0): ");
        int n = scanner.nextInt();
        while (n != 0) {   // 조건식으로 종료를 표현
            sum += n;
            System.out.print("정수 입력(종료: 0): ");
            n = scanner.nextInt();
        }
        System.out.println("최종 합계: " + sum);
    }
}
