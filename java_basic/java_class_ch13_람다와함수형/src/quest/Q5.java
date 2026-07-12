package quest;

import java.util.function.Function;

public class Q5 {
    public static void main(String[] args) {
        //문자열 전처리 파이프라인을 함수 조합으로 구성해보세요
        //조건 : trim -> 소문자 변환 -> 공백을 '-'로 치환, andThen 사용

        Function<String, String> trim = str -> str.trim();
        Function<String, String> 소문자 = str -> str.toLowerCase();
        Function<String, String> 공백치환 = str -> str.replace(' ', '-');

        Function<String, String> 전처리 = trim.andThen(소문자).andThen(공백치환);

        System.out.println(전처리.apply("  Hello Java World  ")); //hello-java-world
    }
}
