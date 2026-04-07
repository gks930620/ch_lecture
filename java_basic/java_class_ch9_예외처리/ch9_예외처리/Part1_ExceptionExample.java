package ch9_예외처리;

public class Part1_ExceptionExample {
    public static void main(String[] args) {
        System.out.println("=== try-catch 기본 ===");

        try {
            int result = 10 / 0;  // ArithmeticException 발생
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("예외 발생: 0으로 나눌 수 없습니다.");
            System.out.println("메시지: " + e.getMessage());
        }

        System.out.println("\n=== 다중 catch ===");
        try {
            String str = null;
            System.out.println(str.length());  // NullPointerException
        } catch (NullPointerException e) {
            System.out.println("Null 예외 발생");
        } catch (Exception e) {
            System.out.println("기타 예외 발생");
        }

        System.out.println("\n=== finally ===");
        try {
            System.out.println("try 블록");
            int result = 10 / 2;
        } catch (Exception e) {
            System.out.println("catch 블록");
        } finally {
            System.out.println("finally 블록 (항상 실행)");
        }

        System.out.println("\n=== 배열 인덱스 예외 ===");
        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[5]);  // ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("배열 인덱스 오류: " + e.getMessage());
        }

        System.out.println("\n프로그램 정상 종료");
    }
}

