package P1제네릭기본;

public class Box<T> {
    //T는 타입파라미터의 약자
    private T content;
    //Box객체는 필드로 모든타입의 객체를 담고 싶음.. Object 선언 할 수밖에 없겠지?

    public Box(T content) {
        this.content = content;
    }

    public T getContent(){
        return content;
    }

}
