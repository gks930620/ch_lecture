package P1상속기본;

public class 상속기본Main {
    public static void main(String[] args) {
        // ch7 전체 흐름:  기본 상속, 재정의(오버라이딩), 타입변환, 다형성, 추상클래스
        // 여기(P1)에서는 기본 상속과 오버라이딩을 본다.
        // Animal(부모)  <-  Dog, Cat (자식)

        Dog dog = new Dog();
        dog.name = "바둑이";   // 부모 Animal에서 물려받은 필드
        dog.age = 3;
        dog.info();    // 부모 Animal의 메소드를 그대로 재사용
        dog.sound();   // 오버라이딩된 Dog.sound() 가 실행됨 -> 멍멍!
        dog.fetch();   // Dog 고유 메소드

        Cat cat = new Cat();
        cat.name = "나비";
        cat.age = 2;
        cat.info();
        cat.sound();   // 오버라이딩된 Cat.sound() -> 야옹~
        cat.scratch();

        Animal animal = new Animal();
        animal.name = "이름없는동물";
        animal.age = 1;
        animal.sound();  // 재정의하지 않았으므로 부모의 기본 sound()
    }
}
