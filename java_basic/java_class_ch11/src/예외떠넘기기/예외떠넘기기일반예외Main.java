package 예외떠넘기기;

import java.util.Scanner;

public class 예외떠넘기기일반예외Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String str= scanner.nextLine();
        
        //main메소드에서도 던지면???? jvm이 처리. 이건 그냥 에러출력하고 끝
        try{
            findClassAndPrinInfo(str);
        } catch (ClassNotFoundException e){
            System.out.println("없는 클래스 이름입니다.");
            e.printStackTrace();
        }finally {
            System.out.println("수고했습니다.");
        }
    }

    //일반 예외가 발생하는 코드는 try-catch가 없으면 컴파일 에러.
    //일반 예외는 떠넘기면  호출하는 쪽에서 try-catch없으면 컴파일에러
    public static void findClassAndPrinInfo(String str)  throws ClassNotFoundException{
            Class<?> clazz = Class.forName(str);
            System.out.println(clazz.getSimpleName());
    }
}
