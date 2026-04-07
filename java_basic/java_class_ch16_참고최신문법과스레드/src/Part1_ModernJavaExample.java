import java.util.*;

// Record 클래스 (Java 16+)
record Person(String name, int age, String email) {
    // 자동으로 생성자, getter, equals, hashCode, toString 생성
}

public class Part1_ModernJavaExample {
    public static void main(String[] args) {
        System.out.println("=== var (타입 추론) ===");
        var message = "Hello, Java!";
        var number = 100;
        var list = new ArrayList<String>();
        list.add("A");
        list.add("B");

        System.out.println(message);
        System.out.println("number: " + number);
        System.out.println("list: " + list);

        System.out.println("\n=== 텍스트 블록 ===");
        var json = """
            {
                "name": "홍길동",
                "age": 25,
                "email": "hong@example.com"
            }
            """;
        System.out.println(json);

        var sql = """
            SELECT *
            FROM users
            WHERE age > 20
            ORDER BY name
            """;
        System.out.println(sql);

        System.out.println("=== Record 클래스 ===");
        Person p1 = new Person("홍길동", 25, "hong@example.com");
        Person p2 = new Person("김철수", 30, "kim@example.com");

        System.out.println(p1);
        System.out.println("이름: " + p1.name());
        System.out.println("나이: " + p1.age());
        System.out.println("이메일: " + p1.email());

        System.out.println("\n같은 객체? " + p1.equals(p2));

        System.out.println("\n=== Switch 표현식 ===");
        int day = 3;
        var dayName = switch (day) {
            case 1 -> "월요일";
            case 2 -> "화요일";
            case 3 -> "수요일";
            case 4 -> "목요일";
            case 5 -> "금요일";
            case 6, 7 -> "주말";
            default -> "잘못된 요일";
        };
        System.out.println(day + "일: " + dayName);

        System.out.println("\n=== instanceof 패턴 매칭 ===");
        Object obj = "Hello";

        // 기존 방식
        if (obj instanceof String) {
            String str = (String) obj;
            System.out.println("길이 (기존): " + str.length());
        }

        // 패턴 매칭 (형변환 불필요)
        if (obj instanceof String str) {
            System.out.println("길이 (패턴): " + str.length());
            System.out.println("대문자: " + str.toUpperCase());
        }

        System.out.println("\n=== 실용 예제 ===");
        var people = List.of(
            new Person("홍길동", 25, "hong@example.com"),
            new Person("김철수", 30, "kim@example.com"),
            new Person("이영희", 28, "lee@example.com")
        );

        people.stream()
            .filter(p -> p.age() >= 25)
            .forEach(p -> System.out.println(p.name() + ": " + p.age() + "세"));
    }
}

