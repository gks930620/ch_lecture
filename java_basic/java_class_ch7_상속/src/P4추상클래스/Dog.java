package P4추상클래스;

public class Dog extends Animal {
    @Override
    public void sound() {
        System.out.println("강아지: 멍멍!");
    }

    @Override
    public void move() {
        System.out.println("강아지는 네 발로 달립니다.");
    }
}
