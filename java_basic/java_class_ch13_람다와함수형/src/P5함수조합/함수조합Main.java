package P5함수조합;

import java.util.function.Function;
import java.util.function.Predicate;

public class 함수조합Main {
    public static void main(String[] args) {
        //작은 함수들을 조합해서 새로운 함수를 만들 수 있음

        Function<String, String> trim = str -> str.trim();
        Function<String, String> 소문자 = str -> str.toLowerCase();

        //andThen : 앞의 함수를 먼저 실행하고 그 결과를 뒤의 함수에 전달 (trim -> toLowerCase)
        Function<String, String> trim후소문자 = trim.andThen(소문자);
        System.out.println("[" + trim후소문자.apply(" Hello ") + "]");

        //compose : 뒤(인자)의 함수를 먼저 실행하고 그 결과를 앞의 함수에 전달
        //소문자.compose(trim)은 trim.andThen(소문자)와 실행 순서가 같음 (trim -> toLowerCase)
        Function<String, String> trim후소문자2 = 소문자.compose(trim);
        System.out.println("[" + trim후소문자2.apply(" Hello ") + "]");

        //Predicate 조합 : and / or / negate
        Predicate<Integer> 성인인가 = age -> age >= 20;
        Predicate<Integer> 삼십미만인가 = age -> age < 30;

        System.out.println("21살은 20대? " + 성인인가.and(삼십미만인가).test(21));      //둘 다 만족해야 true
        System.out.println("17살은 성인이거나 30미만? " + 성인인가.or(삼십미만인가).test(17)); //하나만 만족해도 true
        System.out.println("35살은 30미만 아님? " + 삼십미만인가.negate().test(35));   //조건 반전

    }
}
