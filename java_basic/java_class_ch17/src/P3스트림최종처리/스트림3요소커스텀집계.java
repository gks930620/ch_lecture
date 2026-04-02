package P3스트림최종처리;


import java.util.ArrayList;
import java.util.List;

public class 스트림3요소커스텀집계 {
    public static void main(String[] args) {
        List<Person> list= new ArrayList<>();
        list.add(new Person("민지",21));
        list.add(new Person("하니",21));
        list.add(new Person("다니엘",20));
        list.add(new Person("해린",19));
        list.add(new Person("혜인",17));

       //기본 요소 집계(count, findFirst, max, min) 에서 할 수 없던 복잡한 집계상황에서 사용
        Integer sum = list.stream().map(person -> person.age)
                .reduce(0, (a, b) -> a/10*10 + b/10*10);  //10의자리만 남겨야징
        System.out.println(sum);

    }
}
