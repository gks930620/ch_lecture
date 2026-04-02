package P2스트림중간처리;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class 스트림5요소정렬메인 {
    public static void main(String[] args) {
        List<Person> list= new ArrayList<>();
        list.add(new Person("민지",21));
        list.add(new Person("하니",21));
        list.add(new Person("다니엘",20));
        list.add(new Person("해린",19));
        list.add(new Person("혜인",17));

        List<Person> sorted = list.stream().sorted((o1, o2) -> o1.name.compareTo(o2.name))
                .collect(Collectors.toList()); //이름순
        System.out.println(sorted);
    }
}
