package P2스트림중간처리;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class 스트림4요소flat메인 {
    public static void main(String[] args) {
        List<Person> list= new ArrayList<>();
        list.add(new Person("김 민지",21));
        list.add(new Person("팜 하니",21));
        list.add(new Person("다니엘 마쉬",20));
        list.add(new Person("강 해린",19));
        list.add(new Person("이 혜인",17));
        
        //flatMap의 Function.apply의 return값으로 여러개(list,배열)의 stream이 와야됨
        List<String> collect = list.stream().flatMap(person -> {
            return Arrays.stream(person.name.split(" "));
        }).collect(Collectors.toList());
        System.out.println(collect);
    }
}
