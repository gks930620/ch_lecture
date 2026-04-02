package ch6_객체지향_기초;

public class Part2_PersonTest {

    public static void main(String[] args) {
        System.out.println("=== 객체 생성과 사용 ===");

        // 기본 생성자
        Part1_Person p1 = new Part1_Person();
        p1.setName("홍길동");
        p1.setAge(25);
        p1.setEmail("hong@example.com");
        p1.introduce();

        System.out.println();

        // 매개변수 생성자
        Part1_Person p2 = new Part1_Person("김철수", 30);
        p2.introduce();

        System.out.println();

        // 모든 필드 초기화 생성자
        Part1_Person p3 = new Part1_Person("이영희", 22, "lee@example.com");
        p3.introduce();

        System.out.println("\n=== 성인 여부 확인 ===");
        System.out.println(p1.getName() + ": " + (p1.isAdult() ? "성인" : "미성년자"));
        System.out.println(p2.getName() + ": " + (p2.isAdult() ? "성인" : "미성년자"));

        System.out.println("\n=== Getter 사용 ===");
        System.out.println("이름: " + p3.getName());
        System.out.println("나이: " + p3.getAge());
        System.out.println("이메일: " + p3.getEmail());
    }
}

