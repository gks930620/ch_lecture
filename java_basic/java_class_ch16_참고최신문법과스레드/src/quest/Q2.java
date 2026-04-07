package quest;

public class Q2 {
    static void printType(Object obj) {
        if (obj instanceof String s) {
            System.out.println("문자열 길이: " + s.length());
        } else if (obj instanceof Integer n) {
            System.out.println("정수 제곱: " + (n * n));
        } else {
            System.out.println("기타 타입");
        }
    }

    public static void main(String[] args) {
        printType("java");
        printType(12);
        printType(3.14);
    }
}
