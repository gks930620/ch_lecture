package P4제네릭메소드;

public class Box<T> {
    private T content;

    public Box(T t) {
        this.content=t;
    }

    public T getContent() {
        return content;
    }
}
