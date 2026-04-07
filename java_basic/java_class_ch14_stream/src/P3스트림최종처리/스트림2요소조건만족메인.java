package P3스트림최종처리;


import java.util.ArrayList;
import java.util.List;

public class 스트림2요소조건만족메인 {
    public static void main(String[] args) {
        List<Person> list= new ArrayList<>();
        list.add(new Person("민지",21));
        list.add(new Person("하니",21));
        list.add(new Person("다니엘",20));
        list.add(new Person("해린",19));
        list.add(new Person("혜인",17));

        boolean all10이상 = list.stream().allMatch(person -> person.age > 10);
        System.out.println("모두 10살 이상 : " +all10이상);
        boolean 한명은20이상=list.stream().anyMatch(person -> person.age > 20);  //최소한 하나라도 만족하나?
        System.out.println("한명은 20 살 이상 : " +한명은20이상);
        boolean 한명도30넘은사람없음= list.stream().noneMatch(person -> person.age > 30);
        System.out.println("한명도 30 넘은 사람 없음 :"  + 한명도30넘은사람없음);

    }
}
