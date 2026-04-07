package p3클래스와객체필드생성자메소드기본;

public class 사람사는Main {
    public static void main(String[] args) {
        Person person1=new Person();
        person1.money=10000;
        person1.name="한창희";
        person1.age=30;

        Person person2=new Person("박지성",42,50000);

        person1.earn(10000);
        person2.giveMoney(person1, 30000);
    }
}
