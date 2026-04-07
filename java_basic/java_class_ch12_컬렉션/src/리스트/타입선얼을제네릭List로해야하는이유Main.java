package 리스트;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class 타입선얼을제네릭List로해야하는이유Main {
    public static void main(String[] args) {
            //ArrayList<String> list=new ArrayList<>();
//            LinkedList<String> list=new LinkedList<>(); //버니즈회원탈퇴및가입이 활발해서 LinkedList로 하기로함
//            list.add("한창희");
            //Bunny.makeBunnies(list); 기존에 쓰던 ArrayList가 아니기 때문에 사용할 수 없음.

        //List<String> list2=new ArrayList<>();
        List<String> list2=new LinkedList<>(); //똑같이 LinkedList로 바꿈
        list2.add("한창희");
        list2.add("김민수");
        Bunny.makeBunnies2(list2); // LinkedList로도 똑같이 잘 동작함.
    }
}
