package P3스트림최종처리;

import P2스트림중간처리.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class 스트림4요소수집 {
    public static void main(String[] args) {
        List<Person> list= new ArrayList<>();
        list.add(new Person("민지",21));
        list.add(new Person("민지",25));
        list.add(new Person("민지",30));
        list.add(new Person("하니",21));
        list.add(new Person("하니",38));
        list.add(new Person("다니엘",20));
        list.add(new Person("해린",19));
        list.add(new Person("혜인",39));
        list.add(new Person("혜인",16));
        list.add(new Person("혜인",14));
        list.add(new Person("혜인",17));


        //stream처리한 결과를 이제 stream이 아니라 컬렉션으로 새로 받아야지
        List<Human> humanList = list.stream().map(person -> new Human(person.name, person.age)) //Stream<Person>->Stream<Human>
                .collect(Collectors.toList());
        System.out.println(humanList);


        //그룹핑
        Map<String, List<Person>> collect = list.stream().collect(Collectors.groupingBy(person -> person.name));
        //이름별로 그룹핑



    }
}
