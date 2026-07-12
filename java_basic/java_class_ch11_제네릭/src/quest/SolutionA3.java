package quest;

// A-3: raw type(Box box) 사용 시 경고가 발생하는 이유 데모
public class SolutionA3 {
    // 제네릭 클래스지만, 타입 인자 없이 쓰면(raw type) 컴파일러가 타입 검사를 포기한다.
    static class Box<T> {
        private T value;

        void set(T value) { this.value = value; }
        T get() { return value; }
    }

    public static void main(String[] args) {
        Box box = new Box();          // raw type - "unchecked" 경고 발생
        box.set("hello");             // 무엇이든 들어감 (경고)
        box.set(123);                 // Integer도 들어감 - 컴파일러가 막지 못함

        // String s = (String) box.get(); // 실행하면 ClassCastException! (마지막에 123을 넣었으므로)
        Object o = box.get();
        System.out.println(o);        // 123

        // 핵심: raw type을 쓰면 제네릭의 존재 이유(컴파일 시점 오류 차단)가 사라진다.
        // 잘못 넣은 타입이 런타임 ClassCastException으로 미뤄지므로 "unchecked" 경고가 발생한다.
    }
}
