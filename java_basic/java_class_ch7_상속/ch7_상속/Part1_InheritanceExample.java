package ch7_객체지향_심화;

// 부모 클래스
class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println(name + "이(가) 먹습니다.");
    }

    public void sound() {
        System.out.println("동물 소리");
    }
}

// 자식 클래스들
class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void sound() {
        System.out.println("멍멍!");
    }

    public void bark() {
        System.out.println(name + "이(가) 짖습니다.");
    }
}

class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void sound() {
        System.out.println("야옹~");
    }
}

class Bird extends Animal {
    public Bird(String name) {
        super(name);
    }

    @Override
    public void sound() {
        System.out.println("짹짹!");
    }
}

public class Part1_InheritanceExample {
    public static void main(String[] args) {
        System.out.println("=== 상속 예제 ===");

        Dog dog = new Dog("멍멍이");
        dog.eat();    // 부모 메소드
        dog.sound();  // 오버라이딩된 메소드
        dog.bark();   // 자식 메소드

        System.out.println("\n=== 다형성 예제 ===");
        Animal[] animals = {
            new Dog("강아지"),
            new Cat("고양이"),
            new Bird("새")
        };

        for (Animal animal : animals) {
            animal.sound();  // 각 객체의 오버라이딩된 메소드 호출
        }

        System.out.println("\n=== 업캐스팅/다운캐스팅 ===");
        Animal animal = new Dog("바둑이");  // 업캐스팅 (자동)
        animal.sound();
        // animal.bark();  // 에러! Animal 타입에는 bark() 없음

        // 다운캐스팅 (명시적)
        if (animal instanceof Dog) {
            Dog d = (Dog) animal;
            d.bark();
        }
    }
}

