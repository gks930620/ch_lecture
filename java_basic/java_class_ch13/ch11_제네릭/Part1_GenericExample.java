package ch11_제네릭;

// 제네릭 클래스
class Box<T> {
    private T item;

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}

// 제네릭 클래스 (2개 타입 파라미터)
class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

public class Part1_GenericExample {
    public static void main(String[] args) {
        System.out.println("=== 제네릭 클래스 ===");

        // String 타입
        Box<String> stringBox = new Box<>();
        stringBox.setItem("Hello");
        String str = stringBox.getItem();
        System.out.println("String Box: " + str);

        // Integer 타입
        Box<Integer> intBox = new Box<>();
        intBox.setItem(100);
        int num = intBox.getItem();
        System.out.println("Integer Box: " + num);

        // Pair 사용
        System.out.println("\n=== Pair (2개 타입) ===");
        Pair<String, Integer> pair1 = new Pair<>("나이", 25);
        System.out.println(pair1.getKey() + ": " + pair1.getValue());

        Pair<String, String> pair2 = new Pair<>("이름", "홍길동");
        System.out.println(pair2.getKey() + ": " + pair2.getValue());

        // 제네릭 메소드 호출
        System.out.println("\n=== 제네릭 메소드 ===");
        String[] strArray = {"A", "B", "C"};
        printArray(strArray);

        Integer[] intArray = {1, 2, 3, 4, 5};
        printArray(intArray);

        System.out.println("\n첫 번째 요소:");
        System.out.println(getFirst(strArray));
        System.out.println(getFirst(intArray));
    }

    // 제네릭 메소드
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    public static <T> T getFirst(T[] array) {
        if (array.length > 0) {
            return array[0];
        }
        return null;
    }
}

