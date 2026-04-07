package ch2_variable;

import java.io.IOException;
import java.util.Scanner;

public class P7변수사용범위와입력 {
    public static void main(String[] args) throws IOException {
        if(true){
            int a=3;
        }
        //System.out.println(a);  //변수는 중괄호안에서만 사용가능합니다.
        Scanner scanner=new Scanner(System.in);
        String next = scanner.next();
        int num= scanner.nextInt();
    }
}
