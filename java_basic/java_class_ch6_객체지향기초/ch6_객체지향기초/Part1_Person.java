package ch6_객체지향_기초;

public class Part1_Person {
    // 필드 (속성)
    private String name;
    private int age;
    private String email;

    // 기본 생성자
    public Part1_Person() {
        System.out.println("Person 객체가 생성되었습니다.");
    }

    // 매개변수가 있는 생성자
    public Part1_Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 모든 필드를 초기화하는 생성자
    public Part1_Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    // Getter
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        if (age > 0 && age < 150) {
            this.age = age;
        } else {
            System.out.println("올바른 나이를 입력하세요.");
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // 메소드
    public void introduce() {
        System.out.println("안녕하세요! 저는 " + name + "이고, " + age + "살입니다.");
        if (email != null) {
            System.out.println("이메일: " + email);
        }
    }

    public boolean isAdult() {
        return age >= 18;
    }
}

