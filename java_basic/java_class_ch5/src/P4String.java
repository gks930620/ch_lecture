import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class P4String {
    public static void main(String[] args) {
        // 메소드의 return type , 파라미터 등을 몰라도 사용가능.

        //클래스 중 조금 특이한 클래스
        // 문자열은 String 객체로 생성
        // 자바에서 문자열 "문자"로 String 타입의 객체를 특별하게 제공하는 것. String자체는 그냥 하나의 class일 뿐

        String member1="민지";     //"민지" String 객체가  heap영역에 생성되고 , member1이 거기를 가리킴
        String member1Same="민지";
        System.out.println("member1==member1Same : " + (member1==member1Same) );   // 괄호 꼭 해줘야한다.

        Scanner sc=new Scanner(System.in);
        String member2=sc.next();
        String member2Same=sc.next();
        System.out.println("member2==member2Same : " +  (member2==member2Same));

        String member1New= new String("민지");
        System.out.println("member1==member1New: " + (member1==member1New) );
        System.out.println("member1.eqauls(member1New) : " + member1.equals(member1New)); //참조값 비교가 아닌 내부의 문자열 값 비교

        //대부분 주소값 비교보다는 문자열 값 비교를 하는 경우가 많음.   ex: 사용자가 입력한 값이랑 DB에 저장된 값이 같은지.... 같다는게 주소가 같은지를 묻는건 아니겠죠?
        //==이 true일 때도 물론 있지만, 복잡한 프로그램에서 ==이 같다는 보장이 없음.
        // 그래서 문자열 값 비교를 할 때는 equals를 사용함   (자바에서... 이건 외워두세요)


    }
}
