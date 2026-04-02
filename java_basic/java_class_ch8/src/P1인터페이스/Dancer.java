package P1인터페이스;

public class Dancer implements Danceable {
    @Override
    public void dance() {
        System.out.println("댄서는 춤만 출 줄 알면 됨.");
        System.out.println("댄서가 춤을 춤");
    }
}
