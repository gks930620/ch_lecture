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
        //누적값에는 변환을 적용하면 안 되므로 map으로 분리 (reduce 안에서 절삭하면 누적값까지 절삭돼버림)
        Integer sum = list.stream().map(person -> person.age / 10 * 10)  //10의 자리만 남김
                .reduce(0, Integer::sum);
        System.out.println(sum);

    }
}
