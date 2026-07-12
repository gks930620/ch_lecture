package quest;

// A. 메소드 기초 (A-1 ~ A-4)
// 답안 문서의 A1~A4 클래스를 하나의 실행 파일로 묶었습니다.
public class SolutionA {

    // A-1. 두 정수의 합/차/곱/몫
    static int add(int a, int b) {
        return a + b;
    }

    static int subtract(int a, int b) {
        return a - b;
    }

    static int multiply(int a, int b) {
        return a * b;
    }

    static int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("0으로 나눌 수 없습니다");
        }
        return a / b;
    }

    // A-2. 문자열 길이가 8 이상이면 true (null도 안전하게 처리)
    static boolean isLongEnough(String s) {
        return s != null && s.length() >= 8;
    }

    // A-3 + A-4. 배열 평균 (매개변수 검증 추가한 개선판)
    static double average(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("배열이 null입니다");
        }
        if (arr.length == 0) {
            throw new IllegalArgumentException("배열이 비어 있습니다");
        }
        int sum = 0;
        for (int v : arr) {
            sum += v;
        }
        return (double) sum / arr.length; // 정수 나눗셈 방지 캐스팅
    }

    public static void main(String[] args) {
        // A-1
        System.out.println(add(7, 3));      // 10
        System.out.println(subtract(7, 3)); // 4
        System.out.println(multiply(7, 3)); // 21
        System.out.println(divide(7, 3));   // 2

        // A-2
        System.out.println(isLongEnough("password123")); // true
        System.out.println(isLongEnough("short"));       // false
        System.out.println(isLongEnough(null));          // false (NPE 없이 안전)

        // A-3
        int[] scores = {90, 85, 77};
        System.out.println(average(scores)); // 84.0

        // A-4. 빈 배열이면 의미 있는 메시지로 실패
        try {
            average(new int[]{});
        } catch (IllegalArgumentException e) {
            System.out.println("오류: " + e.getMessage()); // 오류: 배열이 비어 있습니다
        }
    }
}
