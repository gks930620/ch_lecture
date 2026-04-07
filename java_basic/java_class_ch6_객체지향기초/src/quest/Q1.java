package quest;

public class Q1 {
    static class Person {
        private final String name;
        private final int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        void introduce() {
            System.out.println("안녕하세요. 저는 " + name + "이고, " + age + "살입니다.");
        }
    }

    public static void main(String[] args) {
        Person person = new Person("홍길동", 25);
        person.introduce();
    }
}
