package ch12_람다와함수형;

import java.util.function.*;

public class Part1_LambdaExample {
    public static void main(String[] args) {
        System.out.println("=== 람다 표현식 기본 ===");

        // 기존 방식 (익명 클래스)
        Calculator add = new Calculator() {
            @Override
            public int calculate(int a, int b) {
                return a + b;
            }
        };

        // 람다 표현식
        Calculator subtract = (a, b) -> a - b;
        Calculator multiply = (a, b) -> a * b;
        Calculator divide = (a, b) -> {
            if (b == 0) return 0;
            return a / b;
        };

        System.out.println("10 + 5 = " + add.calculate(10, 5));
        System.out.println("10 - 5 = " + subtract.calculate(10, 5));
        System.out.println("10 * 5 = " + multiply.calculate(10, 5));
        System.out.println("10 / 5 = " + divide.calculate(10, 5));

        // Consumer (입력 O, 출력 X)
        System.out.println("\n=== Consumer ===");
        Consumer<String> print = str -> System.out.println(str);
        print.accept("Hello Lambda!");

        Consumer<Integer> printSquare = num -> System.out.println(num + "의 제곱: " + (num * num));
        printSquare.accept(5);

        // Supplier (입력 X, 출력 O)
        System.out.println("\n=== Supplier ===");
        Supplier<Integer> randomNum = () -> (int)(Math.random() * 100);
        System.out.println("랜덤 숫자: " + randomNum.get());

        Supplier<String> greeting = () -> "안녕하세요!";
        System.out.println(greeting.get());

        // Function (입력 O, 출력 O)
        System.out.println("\n=== Function ===");
        Function<String, Integer> strLength = str -> str.length();
        System.out.println("Hello의 길이: " + strLength.apply("Hello"));

        Function<Integer, String> intToStr = num -> "숫자: " + num;
        System.out.println(intToStr.apply(100));

        // Predicate (입력 O, boolean 출력)
        System.out.println("\n=== Predicate ===");
        Predicate<Integer> isPositive = num -> num > 0;
        System.out.println("10은 양수? " + isPositive.test(10));
        System.out.println("-5는 양수? " + isPositive.test(-5));

        Predicate<String> isEmpty = str -> str.isEmpty();
        System.out.println("빈 문자열? " + isEmpty.test(""));
        System.out.println("빈 문자열? " + isEmpty.test("Hello"));
    }
}

@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

