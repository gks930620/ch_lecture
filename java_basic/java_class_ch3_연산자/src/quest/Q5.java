package quest;

import java.util.Scanner;

public class Q5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("정수 입력: ");
        int n = scanner.nextInt();

        // 나머지 연산과 삼항 연산자만으로 짝수/홀수 판별
        String result = n % 2 == 0 ? "짝수" : "홀수";
        System.out.println(result);
    }
}
