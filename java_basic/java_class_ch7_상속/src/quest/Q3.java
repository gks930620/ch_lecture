package quest;

public class Q3 {
    static class Animal {
        void speak() {
            System.out.println("...");
        }
    }

    static class Cat extends Animal {
        @Override
        void speak() {
            System.out.println("야옹");
        }
    }

    static class Dog extends Animal {
        @Override
        void speak() {
            System.out.println("멍멍");
        }
    }

    public static void main(String[] args) {
        Animal[] animals = {new Cat(), new Dog(), new Cat()};
        for (Animal animal : animals) {
            animal.speak();
        }
    }
}
