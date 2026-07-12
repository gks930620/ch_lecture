package 예외처리기본;

import java.util.Scanner;

public class 예외처리기본Main {
    public static void main(String[] args) {
        //키보드 입력처럼 개발자가 통제할 수 없는 상황에서 예외가 발생

        // 에러와 예외는 서로 다르지만 자바개발에서는
        // 일반적으로 느끼는 에러상황=예외,  그래서 그냥 에러처리한다고함
        // 이 예제는 예외 처리를 하지 않아서 숫자가 아닌 입력이 들어오면
        // NumberFormatException이 발생하며 프로그램이 종료된다.
        // try-catch 문으로 처리하는 방법은 Main2, Main3에서 다룬다.
        Scanner scanner=new Scanner(System.in);
        String str= scanner.nextLine();
        printEachNumberSum(str);
    }
    public static void printEachNumberSum(String str){
        int sum=0;
        for(int i=0 ; i<str.length() ; i++){
            String substring = str.substring(i, i + 1);
            sum+=Integer.parseInt(substring);
        }
        System.out.println(sum);
    }
}
