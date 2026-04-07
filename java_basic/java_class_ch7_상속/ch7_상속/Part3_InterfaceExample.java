package ch7_객체지향_심화;

// 인터페이스들
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

interface Walkable {
    void walk();
}

// 다중 인터페이스 구현
class Duck implements Flyable, Swimmable, Walkable {
    private String name;

    public Duck(String name) {
        this.name = name;
    }

    @Override
    public void fly() {
        System.out.println(name + "이(가) 날아갑니다.");
    }

    @Override
    public void swim() {
        System.out.println(name + "이(가) 수영합니다.");
    }

    @Override
    public void walk() {
        System.out.println(name + "이(가) 걷습니다.");
    }
}

class Fish implements Swimmable {
    private String name;

    public Fish(String name) {
        this.name = name;
    }

    @Override
    public void swim() {
        System.out.println(name + "이(가) 수영합니다.");
    }
}

class Airplane implements Flyable {
    private String model;

    public Airplane(String model) {
        this.model = model;
    }

    @Override
    public void fly() {
        System.out.println(model + " 비행기가 날아갑니다.");
    }
}

public class Part3_InterfaceExample {
    public static void main(String[] args) {
        System.out.println("=== 인터페이스 예제 ===");

        Duck duck = new Duck("오리");
        duck.fly();
        duck.swim();
        duck.walk();

        System.out.println();

        // 다형성 - 같은 인터페이스 타입으로 다양한 객체 다루기
        Flyable[] flyables = {
            new Duck("청둥오리"),
            new Airplane("보잉747")
        };

        System.out.println("날 수 있는 것들:");
        for (Flyable f : flyables) {
            f.fly();
        }

        System.out.println("\n수영할 수 있는 것들:");
        Swimmable[] swimmables = {
            new Duck("집오리"),
            new Fish("금붕어")
        };

        for (Swimmable s : swimmables) {
            s.swim();
        }
    }
}

