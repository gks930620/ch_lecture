package P4추상클래스;

public class Turtle extends  Pet{
    @Override
    public void walk() {
        System.out.println("거북이는 거북거북 걷습니다");
    }
    @Override
    public void eat() {
        System.out.println("거북이는  물고기 밥 먹음");
    }

    @Override
    public void call() {
        System.out.println("거북이는 부를 때 주인을 바라봅니다.");
    }
}
