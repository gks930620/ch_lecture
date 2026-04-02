package 이전.p3_9클래스와객체필드생성자메소드기본;

public class Person {
    public String name;
    public int age;
    public int money;

    public Person() {
    }

    public Person( String name,int age, int money) {
        this.name = name;
        this.age = age;
        this.money = money;
    }

    public void earn(int amount){
        money+=amount;
        //this.money+=amount;
    }

    public void giveMoney(Person person, int amount){
        person.money+=amount;
        this.money-=amount;
    }



}
