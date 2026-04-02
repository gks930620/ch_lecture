package 셋;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class 셋해쉬코드main2 {
    public static void main(String[] args) {
        Set<Person> set=new HashSet<>();
        set.add(new Person(30,"민지"));
        set.add(new Person(30,"민지"));
        set.add(new Person(30,"민지"));
        set.add(new Person(30,"민지"));
        set.add(new Person(30,"민지"));

        System.out.println(set);
        System.out.println(set.size());
        //index가 없다는 것만 빼면 list랑 거의 비슷
    }
}
