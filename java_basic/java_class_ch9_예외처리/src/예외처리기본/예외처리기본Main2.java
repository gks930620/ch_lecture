package 예외처리기본;

import java.util.Scanner;

public class 예외처리기본Main2 {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String str= scanner.nextLine();
        findClassAndPrinInfo(str);
    }

    //일반 예외가 발생하는 코드는 try-catch가 없으면 컴파일 에러.
    public static void findClassAndPrinInfo(String str){
        try{
            Class<?> clazz = Class.forName(str);
            System.out.println(clazz.getSimpleName());
        }catch (ClassNotFoundException e){
            System.out.println("없는 클래스 이름입니다.");
            e.printStackTrace();
        }finally {
            System.out.println("수고했습니다.");
        }
    }

}
