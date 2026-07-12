package P4추상클래스;

// abstract : 추상 클래스.
// '동물 일반'은 실제로 존재하지 않으므로 직접 객체 생성을 막고 싶을 때 추상으로 선언한다.
// (P1에서는 Animal을 new로 직접 만들 수 있었지만, 여기서는 new Animal()을 금지한다.)
public abstract class Animal {

    // 추상 메소드 : 몸통(구현)이 없다. 하위 클래스에서 반드시 재정의해야 한다(안 하면 컴파일 에러).
    public abstract void sound();
    public abstract void move();

    // 일반 메소드 : 공통 구현을 제공한다. 재정의해도 되고, 안 해도 된다.
    public void sleep() {
        System.out.println("동물이 잠을 잡니다.");
    }
}
