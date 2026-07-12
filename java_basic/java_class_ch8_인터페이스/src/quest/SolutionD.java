package quest;

// D-1, D-2. @FunctionalInterface Calculator + 람다 덧셈/뺄셈
public class SolutionD {

    // D-1. 추상 메소드가 정확히 1개 — 컴파일러가 이를 검증해 준다
    @FunctionalInterface
    interface Calculator {
        int calc(int a, int b);
        // int another(int x); // 하나 더 추가하면 @FunctionalInterface가 컴파일 오류를 낸다
    }

    public static void main(String[] args) {
        // D-2. 람다식 = "그 하나뿐인 추상 메소드의 구현"을 식으로 표현
        Calculator add = (a, b) -> a + b;
        Calculator subtract = (a, b) -> a - b;

        System.out.println(add.calc(10, 3));      // 13
        System.out.println(subtract.calc(10, 3)); // 7
    }
}
