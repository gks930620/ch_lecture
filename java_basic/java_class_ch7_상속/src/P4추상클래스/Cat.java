package P4추상클래스;

public class Cat extends Pet{
    @Override
    public void walk() {
        System.out.println("고양이는 얌전히 걷습니다");
    }
    @Override
    public void eat() {
        System.out.println("참치캔!!");
    }
}
