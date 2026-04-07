package quest;

public class Q1 {
    @FunctionalInterface
    interface Calc {
        int apply(int a, int b);
    }

    public static void main(String[] args) {
        Calc add = (a, b) -> a + b;
        System.out.println("결과: " + add.apply(10, 20));
    }
}
