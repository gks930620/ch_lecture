package P3스트림최종처리;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class 스트림1요소처리메인 {
    public static void main(String[] args) {
        // 요소처리는 peek, forEach가 있다. peek은 중간처리이지만 여기서 소개
        List<Person> list= new ArrayList<>();
        list.add(new Person("민지",21));
        list.add(new Person("하니",21));
        list.add(new Person("다니엘",20));
        list.add(new Person("해린",19));
        list.add(new Person("혜인",17));

        list.stream().peek(person -> System.out.println(person))
                .mapToInt(person->person.age).sum();
        //반복처리하고 새로 stream 받음

        list.stream().forEach(person -> System.out.println(person));
        //반복처리하고 끝.

    }
}
