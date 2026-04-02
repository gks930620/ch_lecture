package 이전.p13;

import java.util.Arrays;
import java.util.List;

public class P6접근제한자 {
    public static void main(String[] args) {
        //public과 private만 알면 됩니다.
        Person person1=new Person();
        //private가 된 필드와 메소드는 외부에서 사용 불가능.   내부에서는 사용가능
//        person1.money=10000;
//        person1.name="한창희";
//        person1.age=30;
        Person person2=new Person("박지성",42,50000);
//        person1.earn(10000);
        person2.giveMoney(person1, 30000);
    }
}
