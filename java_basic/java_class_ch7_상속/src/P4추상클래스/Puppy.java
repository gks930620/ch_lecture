package P4추상클래스;

public class Puppy  extends  Pet{

    @Override
    public void walk() {
        System.out.println("강아지는 부끄처럼 걷습니다.");
    }

    @Override
    public void eat() {
        System.out.println("강아지는 강아지 사료를 먹습니다.");
    }
}
