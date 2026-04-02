package P4추상클래스;

public class 추상클래스Main {
    public static void main(String[] args) {
        // 분명히 상위 클래스이고, 이거를 상속받는 하위클래스도 있지만
        // 직접 객체를 만들지는 못하게 해야하는 경우 추상클래스로 선언합니다.
        // Pet은 객체가 없다.  강아지 객체, 고양이 객체는 있다.
        Pet puppy=new Puppy();
        Pet turtle=new Turtle();
        Cat cat= new Cat();

        puppy.walk();
        puppy.eat();
        puppy.call();

        turtle.walk();
        turtle.eat();
        turtle.call();

        cat.walk();
        cat.eat();
        cat.call();



    }
}
