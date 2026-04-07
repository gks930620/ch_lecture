package P2스트림중간처리;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class 스트림3요소변환메인 {
    public static void main(String[] args) {
        List<Person> list= new ArrayList<>();
        list.add(new Person("민지",21));
        list.add(new Person("하니",21));
        list.add(new Person("다니엘",20));
        list.add(new Person("해린",19));
        list.add(new Person("혜인",17));

        List<String> mappedList = list.stream().map(person -> person.name). //바꾸기전의 원소는 Person
         filter(str-> !str.equals("해린")) //바뀐후의 스트림원소는 String
        .collect(Collectors.toList());  //중간처리는 몇번이고 할 수 있음
        System.out.println(mappedList);
    }
}
