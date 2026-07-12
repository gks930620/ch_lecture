package quest;

// A-2: 같은 인터페이스로 뺄셈/곱셈/나눗셈 람다 (동작 교체 = 전략 패턴)
public class SolutionA2 {
    @FunctionalInterface
    interface Calculator {
        int calc(int a, int b);
    }

    public static void main(String[] args) {
        Calculator sub = (a, b) -> a - b;
        Calculator mul = (a, b) -> a * b;
        Calculator div = (a, b) -> {
            if (b == 0) throw new ArithmeticException("0으로 나눌 수 없습니다");
            return a / b;
        };

        System.out.println(sub.calc(10, 3)); // 7
        System.out.println(mul.calc(10, 3)); // 30
        System.out.println(div.calc(10, 3)); // 3 (정수 나눗셈)
    }
}
