package 리스트;

import java.util.LinkedList;
import java.util.List;

public class 타입선얼을제네릭List로해야하는이유Main2 {
    public static void main(String[] args) {

        //List<String> list2=new ArrayList<>();
        List<String> list2=new LinkedList<>(); //똑같이 LinkedList로 바꿈
        list2.add("한창희");
        list2.add("김민수");
        Bunny.makeBunnies2(list2); // LinkedList로도 똑같이 잘 동작함.
    }
}
