package 셋;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class 셋main {
    public static void main(String[] args) {
        Set<String> set=new HashSet<>();
        set.add("JAVA");
        set.add("JAVA");
        set.add("JSP");
        set.add("Spring");
        set.add("Javascript");

        System.out.println(set);
        //index가 없다는 것만 빼면 list랑 거의 비슷

        //set반복  향상된for문 or iterator
        for(String str : set){
            System.out.println(str);
        }
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            //System.out.println(iterator.next()); next()를 두번쓰면 안됨. 반드시 변수로 받기
        }
    }
}
