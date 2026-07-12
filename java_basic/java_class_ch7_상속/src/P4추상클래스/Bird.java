package P4추상클래스;

public class Bird extends Animal {
    @Override
    public void sound() {
        System.out.println("새: 짹짹!");
    }

    @Override
    public void move() {
        System.out.println("새는 하늘을 납니다.");
    }

    // 공통 메소드 sleep()도 필요하면 재정의할 수 있다.
    @Override
    public void sleep() {
        System.out.println("새는 나뭇가지에 서서 잡니다.");
    }
}
