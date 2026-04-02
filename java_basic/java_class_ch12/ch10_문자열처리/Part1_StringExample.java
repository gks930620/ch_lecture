package ch10_문자열처리;

public class Part1_StringExample {
    public static void main(String[] args) {
        System.out.println("=== String 메소드 ===");
        String str = "Hello World";

        System.out.println("원본: " + str);
        System.out.println("길이: " + str.length());
        System.out.println("첫 문자: " + str.charAt(0));
        System.out.println("대문자: " + str.toUpperCase());
        System.out.println("소문자: " + str.toLowerCase());
        System.out.println("부분문자열: " + str.substring(0, 5));
        System.out.println("포함여부: " + str.contains("World"));
        System.out.println("시작여부: " + str.startsWith("Hello"));
        System.out.println("교체: " + str.replace("World", "Java"));

        System.out.println("\n=== 문자열 분리 ===");
        String csv = "사과,바나나,체리,딸기";
        String[] fruits = csv.split(",");
        for (String fruit : fruits) {
            System.out.println(fruit);
        }

        System.out.println("\n=== 공백 제거 ===");
        String spaced = "  Hello World  ";
        System.out.println("원본: '" + spaced + "'");
        System.out.println("trim: '" + spaced.trim() + "'");

        System.out.println("\n=== 문자열 비교 ===");
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = new String("Hello");

        System.out.println("s1 == s2: " + (s1 == s2));        // true (같은 객체)
        System.out.println("s1 == s3: " + (s1 == s3));        // false (다른 객체)
        System.out.println("s1.equals(s3): " + s1.equals(s3));  // true (내용 같음)

        System.out.println("\n=== 문자열 포맷팅 ===");
        String name = "홍길동";
        int age = 25;
        double score = 95.5;

        String formatted = String.format("이름: %s, 나이: %d세, 점수: %.1f점", name, age, score);
        System.out.println(formatted);

        System.out.printf("이름: %s\n", name);
        System.out.printf("점수: %.2f%%\n", score);
    }
}

