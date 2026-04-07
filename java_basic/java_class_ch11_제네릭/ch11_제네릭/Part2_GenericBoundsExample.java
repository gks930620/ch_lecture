package ch11_제네릭;

import java.util.*;

// 타입 제한 (extends Number)
class NumberBox<T extends Number> {
    private T number;

    public NumberBox(T number) {
        this.number = number;
    }

    public double getDoubleValue() {
        return number.doubleValue();
    }

    public T getNumber() {
        return number;
    }
}

public class Part2_GenericBoundsExample {
    public static void main(String[] args) {
        System.out.println("=== 타입 제한 (extends) ===");

        NumberBox<Integer> intBox = new NumberBox<>(100);
        System.out.println("Integer: " + intBox.getDoubleValue());

        NumberBox<Double> doubleBox = new NumberBox<>(3.14);
        System.out.println("Double: " + doubleBox.getDoubleValue());

        // NumberBox<String> stringBox = new NumberBox<>("Hello");  // 에러!

        System.out.println("\n=== 와일드카드 <?> ===");
        List<Integer> intList = Arrays.asList(1, 2, 3);
        List<String> strList = Arrays.asList("A", "B", "C");

        printList(intList);
        printList(strList);

        System.out.println("\n=== 와일드카드 <? extends Number> ===");
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> doubles = Arrays.asList(1.1, 2.2, 3.3);

        System.out.println("Integer 합계: " + sumOfList(integers));
        System.out.println("Double 합계: " + sumOfList(doubles));

        System.out.println("\n=== 와일드카드 <? super Integer> ===");
        List<Number> numberList = new ArrayList<>();
        addIntegers(numberList);
        System.out.println("추가된 숫자들: " + numberList);
    }

    // 와일드카드 <?> - 모든 타입 허용
    public static void printList(List<?> list) {
        for (Object obj : list) {
            System.out.print(obj + " ");
        }
        System.out.println();
    }

    // <? extends T> - 상한 제한 (읽기 전용)
    public static double sumOfList(List<? extends Number> list) {
        double sum = 0;
        for (Number num : list) {
            sum += num.doubleValue();
        }
        return sum;
    }

    // <? super T> - 하한 제한 (쓰기 가능)
    public static void addIntegers(List<? super Integer> list) {
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
    }
}

