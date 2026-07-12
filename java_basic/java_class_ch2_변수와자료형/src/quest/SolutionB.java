package quest;

/**
 * ch2 B. 코드 작성 — 정답 예시 (B-1 ~ B-5)
 */
public class SolutionB {
    public static void main(String[] args) {
        b1();
        b2();
        b3();
        b4();
        b5();
    }

    // ── B-1. 정수 2개 합/차/곱/몫/나머지 ──
    static void b1() {
        int a = 7;
        int b = 3;

        System.out.println("합: " + (a + b));      // 합: 10
        System.out.println("차: " + (a - b));      // 차: 4
        System.out.println("곱: " + (a * b));      // 곱: 21
        System.out.println("몫: " + (a / b));      // 몫: 2  (정수 나눗셈: 소수부 버림)
        System.out.println("나머지: " + (a % b));  // 나머지: 1
    }

    // ── B-2. 반지름으로 원의 넓이/둘레 출력 ──
    static void b2() {
        double radius = 3.0;

        double area = Math.PI * radius * radius;      // 넓이 = πr²
        double circumference = 2 * Math.PI * radius;  // 둘레 = 2πr

        System.out.printf("넓이: %.2f%n", area);          // 넓이: 28.27
        System.out.printf("둘레: %.2f%n", circumference); // 둘레: 18.85
    }

    // ── B-3. String[] 5개 중 null 아닌 값만 출력 ──
    static void b3() {
        String[] words = {"apple", null, "banana", null, "cherry"};

        for (int i = 0; i < words.length; i++) {
            if (words[i] != null) {
                System.out.println(words[i]);
            }
        }
    }

    // ── B-4. int[]의 합계/평균/최대/최소 ──
    static void b4() {
        int[] scores = {70, 85, 90, 60, 95};

        int sum = 0;
        int max = scores[0];
        int min = scores[0];

        for (int i = 0; i < scores.length; i++) {
            sum += scores[i];
            if (scores[i] > max) max = scores[i];
            if (scores[i] < min) min = scores[i];
        }

        double average = (double) sum / scores.length; // int/int가 되지 않도록 캐스팅!

        System.out.println("합계: " + sum);      // 합계: 400
        System.out.println("평균: " + average);  // 평균: 80.0
        System.out.println("최대: " + max);      // 최대: 95
        System.out.println("최소: " + min);      // 최소: 60
    }

    // ── B-5. 문자열 내용 비교 (== 금지, equals 사용) ──
    static void b5() {
        String a = new String("java");
        String b = new String("java");

        System.out.println(a == b);        // false (서로 다른 객체 — 주소가 다름)
        System.out.println(a.equals(b));   // true  (내용은 같음)

        String c = "java"; // 리터럴은 String Pool에 저장됨
        String d = "java"; // 같은 리터럴은 같은 객체를 공유
        System.out.println(c == d);        // true  (풀 공유로 항상 true — 그래도 여기에 의존하면 안 됨!)
        System.out.println(c.equals(d));   // true

        // 대소문자 무시 비교
        System.out.println("Java".equalsIgnoreCase("JAVA")); // true
    }
}
