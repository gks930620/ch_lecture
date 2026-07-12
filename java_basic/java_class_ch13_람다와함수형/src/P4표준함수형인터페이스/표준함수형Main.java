package P4표준함수형인터페이스;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class 표준함수형Main {
    public static void main(String[] args) {
        //직접 함수형 인터페이스를 만들지 않아도 java.util.function 패키지에 표준으로 준비되어 있음

        //Predicate<T> : T를 받아 boolean 반환 (test)
        Predicate<Integer> 성인인가 = age -> age >= 20;
        System.out.println("21살은 성인? " + 성인인가.test(21));
        System.out.println("17살은 성인 아님? " + 성인인가.negate().test(17)); //negate()는 조건 반전

        //Function<T,R> : T를 받아 R 반환 (apply)
        Function<String, Integer> 글자수 = str -> str.length();
        System.out.println("newjeans 글자수 : " + 글자수.apply("newjeans"));

        //Consumer<T> : T를 받아 소비만 하고 반환 없음 (accept)
        Consumer<String> 출력기 = str -> System.out.println(str + "를 출력합니다.");
        출력기.accept("민지");

        //Supplier<T> : 받는 것 없이 T를 공급 (get)
        Supplier<Double> 랜덤값 = () -> Math.random();
        System.out.println("랜덤값 : " + 랜덤값.get());

        //BiFunction<T,U,R> : T,U 두 개를 받아 R 반환
        BiFunction<Integer, Integer, String> 합문자열 = (a, b) -> "합은 " + (a + b);
        System.out.println(합문자열.apply(3, 4));

        //UnaryOperator<T> : 입력과 출력 타입이 같은 Function
        UnaryOperator<String> 인사붙이기 = name -> name + "님 안녕하세요";
        System.out.println(인사붙이기.apply("하니"));

        //BinaryOperator<T> : 입력 두 개와 출력 타입이 모두 같은 BiFunction
        BinaryOperator<Integer> 곱 = (a, b) -> a * b;
        System.out.println("3*4 = " + 곱.apply(3, 4));

    }
}
