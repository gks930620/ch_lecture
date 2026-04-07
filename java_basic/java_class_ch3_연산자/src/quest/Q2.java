package quest;

import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("국어 점수: ");
        int kor = scanner.nextInt();
        System.out.print("영어 점수: ");
        int eng = scanner.nextInt();
        System.out.print("수학 점수: ");
        int math = scanner.nextInt();

        double avg = (kor + eng + math) / 3.0;
        String result = avg >= 60 ? "PASS" : "FAIL";

        System.out.println("평균: " + avg);
        System.out.println("결과: " + result);
    }
}
