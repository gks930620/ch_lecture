package P2타입변환;

public class 강제타입변환Main {
    public static void main(String[] args) {
        Animal animalButRealDog = new Dog();
        animalButRealDog.sound();  // 오버라이딩된 Dog.sound() 실행

        // Dog 고유 메소드 bark()를 쓰려면 하위타입으로 강제 타입변환(다운캐스팅) 해야 한다.
        Dog dog = (Dog) animalButRealDog;  // 실제 객체가 Dog이므로 성공
        dog.bark();

        // Java 16+ instanceof 패턴 매칭: 검사와 캐스팅을 한 번에 (Java 17 기준 권장 스타일)
        Animal something = new Dog();
        if (something instanceof Dog d) {
            d.bark();  // 검사에 통과했을 때만 안전하게 다운캐스팅되어 호출
        }

        // 잘못된 다운캐스팅 데모
        Animal animalButRealCat = new Cat();
        animalButRealCat.sound();
        // 주의: 아래 줄은 실제 객체가 Cat인데 Dog로 캐스팅하므로
        //       ClassCastException이 발생해 프로그램이 비정상 종료됩니다(의도된 데모).
        Dog wrong = (Dog) animalButRealCat;  // 실행 시 ClassCastException
        wrong.bark();
        // ClassCastException : 타입변환이 불가능한 경우 발생하는 예외
    }
}
