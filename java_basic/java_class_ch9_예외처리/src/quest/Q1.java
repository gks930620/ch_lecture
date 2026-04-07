package quest;

public class Q1 {
    public static void main(String[] args) {
        int a = 10;
        int b = 0;
        try {
            System.out.println(a / b);
        } catch (ArithmeticException e) {
            System.out.println("예외 발생: 0으로 나눌 수 없습니다.");
        }
    }
}
