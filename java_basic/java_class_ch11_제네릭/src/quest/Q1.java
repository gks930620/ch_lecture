package quest;

public class Q1 {
    static class Box<T> {
        private T value;

        void set(T value) {
            this.value = value;
        }

        T get() {
            return value;
        }
    }

    public static void main(String[] args) {
        Box<String> stringBox = new Box<>();
        stringBox.set("hello");
        System.out.println(stringBox.get());

        Box<Integer> intBox = new Box<>();
        intBox.set(100);
        System.out.println(intBox.get());
    }
}
