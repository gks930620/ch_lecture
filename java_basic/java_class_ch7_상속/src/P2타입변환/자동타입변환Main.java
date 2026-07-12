package P2타입변환;

public class 자동타입변환Main {
    public static void main(String[] args) {
        // 타입변환 + 다형성  매우 중요
        // 상위타입 변수 = 하위타입 객체  ==  자동 타입 변환(업캐스팅)
        Animal dog = new Dog();      // 타입은 Animal, 실제 객체는 Dog
        Animal cat = new Cat();
        Animal animal = new Animal();

        // 업캐스팅된 참조로는 '부모가 약속한' 메소드만 부를 수 있다.
        // 단, 오버라이딩된 메소드라면 실제 객체의 것이 실행된다(동적 바인딩).
        dog.sound();     // 멍멍!
        cat.sound();     // 야옹~
        animal.sound();  // 동물이 소리를 냅니다.

        // dog.bark();   // 컴파일 에러 — Animal 타입에는 bark()가 없다.
        //               // bark()를 부르려면 아래(강제타입변환Main)처럼 다운캐스팅이 필요하다.
    }
}
