package P4추상클래스;

public class 추상클래스Main {
    public static void main(String[] args) {
        // Animal은 추상 클래스라 직접 생성할 수 없다:
        //     Animal a = new Animal();  // 컴파일 에러!
        // 하지만 하위 클래스의 객체는 만들 수 있고, Animal 타입으로 묶어서 다룰 수 있다(다형성).
        Animal[] animals = { new Dog(), new Cat(), new Bird() };

        for (Animal animal : animals) {
            animal.sound();  // 각자 재정의한 소리 (추상 메소드 구현)
            animal.move();   // 각자 재정의한 이동   (추상 메소드 구현)
            animal.sleep();  // 공통 구현 (Bird만 재정의)
            System.out.println();
        }
    }
}
