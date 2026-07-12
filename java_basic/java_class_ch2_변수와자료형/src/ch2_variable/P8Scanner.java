package ch2_variable;

import java.util.Scanner;

public class P8Scanner {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("정수를 입력하세요");
        int i = scanner.nextInt();
        System.out.println("입력한 정수 : " + i);
    }
}
