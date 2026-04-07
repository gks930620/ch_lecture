package P3스트림최종처리;


import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class 스트림3요소기본집계 {
    public static void main(String[] args) {
        List<Person> list= new ArrayList<>();
        list.add(new Person("민지",21));
        list.add(new Person("하니",21));
        list.add(new Person("다니엘",20));
        list.add(new Person("해린",19));
        list.add(new Person("혜인",17));

       //요소 집계 : count, findFirst, max, min
       //           avreage , sum  (애네는 Stream<T>에 없음.  mapToInt->IntStream으로 변경해야됨

        long count = list.stream().count();
        System.out.println("전체 개수 : " + count);
        Person maxPerson = list.stream().max((o1, o2) -> o1.age - o2.age).get();
        System.out.println("나이 가장 많은 사람 : " + maxPerson);
        OptionalDouble average = list.stream().mapToInt(person -> person.age).average();
        System.out.println(average.getAsDouble()); //optional은 단수히 값을 제공하는게 아니라 값 없는 상황에서 처리도 도우ㅏ줌
        //average.orElse(0);
        //average.orElseThrow(() -> new NoSuchElementException());

        int sum = list.stream().mapToInt(person -> person.age).sum();
        System.out.println("합 : " +sum);


    }
}
