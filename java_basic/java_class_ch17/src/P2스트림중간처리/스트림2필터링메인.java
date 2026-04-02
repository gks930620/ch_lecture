package P2스트림중간처리;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class 스트림2필터링메인 {
    public static void main(String[] args) {
        List<Person> list= new ArrayList<>();
        list.add(new Person("민지",21));
        list.add(new Person("하니",21));
        list.add(new Person("다니엘",20));
        list.add(new Person("해린",19));
        list.add(new Person("혜인",17));
        list.add(new Person("해린",19));
        list.add(new Person("해린",19));
        //해린이지만 equals정의안하면 다른객체로 distcinct안됨

        List<Person> distinctedList = list.stream().distinct().collect(Collectors.toList());
        //equals를 정의해야 중복제거됨
        System.out.println(distinctedList);

        List<Person> filteredList1 = list.stream().filter(person -> { //원소인 person을 이용해 어떻게 거르고 싶은지.
            if (person.name.length() > 2) {
                return false;
            }
            if (person.age > 18) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());
        System.out.println(filteredList1);
        List<Person> filteredList2 = list.stream().filter(p -> p.name.length() <=2).filter(p -> p.age <= 18).collect(Collectors.toList());
        //성능면에서 큰 차이없음. stream답게 여러번 처리해주는게 편함
        System.out.println(filteredList2);


    }
}
