package P2스트림중간처리;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 스트림1스트림얻기메인 {
    public static void main(String[] args) {
        List<Person> list= new ArrayList<>();
        list.add(new Person("민지",21));
        list.add(new Person("하니",21));
        list.add(new Person("다니엘",20));
        list.add(new Person("해린",19));
        list.add(new Person("혜인",17));
        Person[] array = list.toArray(new Person[list.size()]);
        //List<Person> list2= new ArrayList<>(Arrays.asList());

        list.stream().forEach(person ->  {
            System.out.println(person);
        });
        Arrays.stream(array).forEach(person -> {
            System.out.println(person);
        });


    }
}
