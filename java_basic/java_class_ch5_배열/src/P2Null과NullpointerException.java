import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class P2Null과NullpointerException {
    public static void main(String[] args) {
        //아직 번지(주소값)를 저장하고 있지 않다는 뜻으로 null'값'을 가질수 있다.
        //참조타입만.
        //객체의 데이터나 메소드를 사용하려하면 예외가 발생
        String str="뉴진스";
        String str2= null;
        System.out.println(str.length());  // heap영역에가서 메소드 실행하면 됨
//        System.out.println(str2.length()); // heap영역 어디로 감?


        //null vs only 선언 차이
        String a;
        String b=null;  //b는 "값이 없습니다"는 값을 할당 null이라는 값으로 초기화 된거 .
        // a는 그냥 값이 없음.

        // a.length();   컴파일러단계에서 걸림
        b.length();

    }
}
