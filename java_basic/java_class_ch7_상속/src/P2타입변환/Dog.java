package P2타입변환;

public class Dog extends Animal {
    @Override
    public void sound() {
        System.out.println("멍멍!");
    }

    // Dog 고유 메소드 — Animal 타입 참조로는 못 부르고, 다운캐스팅해야 호출 가능
    public void bark() {
        System.out.println("왈왈! (Dog 고유 메소드)");
    }
}
