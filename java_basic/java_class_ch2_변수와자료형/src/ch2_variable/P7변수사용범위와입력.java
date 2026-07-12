package ch2_variable;

import java.util.Scanner;

public class P7변수사용범위와입력 {
    public static void main(String[] args) {
        if(true){
            int a=3;
        }
        //System.out.println(a);  //변수는 중괄호안에서만 사용가능합니다.
        Scanner scanner=new Scanner(System.in);
        String next = scanner.next();
        int num= scanner.nextInt();
        System.out.println("입력한 문자열 : " + next);
        System.out.println("입력한 숫자 : " + num);
    }
}
