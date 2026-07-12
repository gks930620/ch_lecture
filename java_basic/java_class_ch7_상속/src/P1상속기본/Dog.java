package P1상속기본;

public class Dog extends Animal {

    // 부모(Animal)의 sound()를 자식이 다시 정의(오버라이딩)한다.
    // 재정의하면 Dog 객체에서 sound()를 호출할 때 부모가 아닌 여기서 만든 메소드가 실행된다.
    @Override
    public void sound() {
        System.out.println(name + ": 멍멍!");
    }

    // Dog에만 있는 고유 메소드 (부모 Animal에는 없다)
    public void fetch() {
        System.out.println(name + "가 공을 물어옵니다.");
    }
}
