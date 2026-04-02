package P1함수형프로그래밍과람다;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class 함수형람다Main {
    public static void main(String[] args) {
        //데이터처리부(sort메소드)에 매개변수 값으로 함수(Comaprator)를 넣고 함수에 정의된 내용을 실행
        //함수에 정의된 내용에 따라 다르게 실행됨.

        List<String> list= new ArrayList<>();
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });




    }
}
