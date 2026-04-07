package p6;

public class Person {
    private String name;
    private int age;
    private int money;
    public Person() {
    }

    public Person(String name, int age, int money) {
        this.name = name;
        this.age = age;
        this.money = money;
    }

    private  void earn(int amount){
        money+=amount;
        //this.money+=amount;
    }

    public void giveMoney(Person person, int amount){
        person.money+=amount;
        this.money-=amount;
    }



}
