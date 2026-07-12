package P3메소드참조;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class 메소드참조main {
    public static void main(String[] args) {
        // 람다식이 많이 익숙해지면 그때 사용하면 됩니다.  생성자참조는 메소드참조랑 비슷합니다.
        //매개변수가 많을 때 참 유용하다!

        List<String> list= new ArrayList<>();
        list.add("c");list.add("a");list.add("b");
        Collections.sort(list, (o1, o2) -> { return o1.compareTo(o2) ;});
        Collections.sort(list, String :: compareTo);
        System.out.println(list); //[a, b, c]
    }
}
