package P1인터페이스;

public class Idol   implements  Singable, Danceable {

    @Override
    public void dance() {
        System.out.println("아이돌은 춤도 출 줄 알아야함");
        System.out.println("아이돌이 춤을 춥니다.");
    }

    @Override
    public void sing() {
        System.out.println("아이돌은 노래도 할 줄 알아야함");
        System.out.println("아이돌이 노래합니다");
    }
}
