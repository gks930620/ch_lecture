package P4추상클래스;

public class Cat extends Animal {
    @Override
    public void sound() {
        System.out.println("고양이: 야옹~");
    }

    @Override
    public void move() {
        System.out.println("고양이는 살금살금 걷습니다.");
    }
}
