package P1상속기본;

public class Cat extends Animal {

    @Override
    public void sound() {
        System.out.println(name + ": 야옹~");
    }

    // Cat 고유 메소드
    public void scratch() {
        System.out.println(name + "가 스크래처를 긁습니다.");
    }
}
