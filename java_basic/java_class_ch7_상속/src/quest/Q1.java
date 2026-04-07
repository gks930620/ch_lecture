package quest;

public class Q1 {
    static class Animal {
        void sound() {
            System.out.println("동물이 소리를 냅니다.");
        }
    }

    static class Dog extends Animal {
        @Override
        void sound() {
            System.out.println("멍멍!");
        }
    }

    public static void main(String[] args) {
        Animal animal = new Dog();
        animal.sound();
    }
}
