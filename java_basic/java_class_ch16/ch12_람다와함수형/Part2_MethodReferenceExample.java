package ch12_람다와함수형;

import java.util.*;
import java.util.function.*;

public class Part2_MethodReferenceExample {
    public static void main(String[] args) {
        System.out.println("=== 메소드 참조 ===");

        // 1. 정적 메소드 참조
        System.out.println("\n1. 정적 메소드 참조");
        Function<String, Integer> func1 = str -> Integer.parseInt(str);
        Function<String, Integer> func2 = Integer::parseInt;

        System.out.println(func1.apply("100"));
        System.out.println(func2.apply("200"));

        // 2. 인스턴스 메소드 참조
        System.out.println("\n2. 인스턴스 메소드 참조");
        String str = "hello";
        Supplier<String> sup1 = () -> str.toUpperCase();
        Supplier<String> sup2 = str::toUpperCase;

        System.out.println(sup1.get());
        System.out.println(sup2.get());

        // 3. 특정 타입의 임의 객체 인스턴스 메소드 참조
        System.out.println("\n3. 임의 객체의 인스턴스 메소드 참조");
        Function<String, Integer> func3 = String::length;
        System.out.println("Hello의 길이: " + func3.apply("Hello"));

        // 4. 생성자 참조
        System.out.println("\n4. 생성자 참조");
        Supplier<ArrayList<String>> listSup = ArrayList::new;
        ArrayList<String> list = listSup.get();
        list.add("A");
        list.add("B");
        System.out.println(list);

        Function<Integer, int[]> arrayCreator = int[]::new;
        int[] arr = arrayCreator.apply(5);
        System.out.println("배열 크기: " + arr.length);

        // 실용 예제
        System.out.println("\n=== 실용 예제 ===");
        List<String> names = Arrays.asList("홍길동", "김철수", "이영희", "박민수");

        // 람다식
        System.out.println("람다식:");
        names.forEach(name -> System.out.println(name));

        // 메소드 참조
        System.out.println("\n메소드 참조:");
        names.forEach(System.out::println);

        // 정렬
        List<String> fruits = new ArrayList<>(Arrays.asList("Banana", "Apple", "Cherry"));
        System.out.println("\n정렬 전: " + fruits);

        // 람다식
        fruits.sort((s1, s2) -> s1.compareTo(s2));
        System.out.println("람다식 정렬: " + fruits);

        // 메소드 참조
        fruits = new ArrayList<>(Arrays.asList("Banana", "Apple", "Cherry"));
        fruits.sort(String::compareTo);
        System.out.println("메소드 참조 정렬: " + fruits);
    }
}

