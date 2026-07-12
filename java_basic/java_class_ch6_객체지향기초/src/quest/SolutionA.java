package quest;

// A. 클래스 기초 (A-1 ~ A-4) — Person 클래스
public class SolutionA {

    static class Person {
        // A-1. 이름/나이 필드 선언
        private String name;
        private int age;

        // A-2. 생성자로 필수값을 받는다 (검증 포함)
        public Person(String name, int age) {
            if (name == null || name.isBlank()) throw new IllegalArgumentException("name은 필수입니다");
            if (age < 0) throw new IllegalArgumentException("age는 0 이상이어야 합니다");
            this.name = name;
            this.age = age;
        }

        // A-3. 자기소개 문자열 반환
        public String introduce() {
            return "안녕하세요, 저는 " + name + "이고 " + age + "살입니다.";
        }
    }

    public static void main(String[] args) {
        // A-4. 인스턴스 3개 생성 — 같은 클래스, 서로 다른 상태
        Person p1 = new Person("김자바", 25);
        Person p2 = new Person("이코딩", 30);
        Person p3 = new Person("박개발", 28);

        System.out.println(p1.introduce()); // 안녕하세요, 저는 김자바이고 25살입니다.
        System.out.println(p2.introduce()); // 안녕하세요, 저는 이코딩이고 30살입니다.
        System.out.println(p3.introduce()); // 안녕하세요, 저는 박개발이고 28살입니다.
    }
}
