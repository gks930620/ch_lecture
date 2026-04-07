package quest;

import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("나이 입력: ");
        int age = scanner.nextInt();

        if (age >= 20 && age < 65) {
            System.out.println("일반 요금");
        } else {
            System.out.println("우대 요금");
        }
    }
}
