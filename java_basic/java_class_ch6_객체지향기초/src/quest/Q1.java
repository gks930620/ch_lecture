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
        Person person1 = new Person("홍길동", 25);
        Person person2 = new Person("김영희", 31);
        Person person3 = new Person("이철수", 28);

        person1.introduce();
        person2.introduce();
        person3.introduce();
    }
}
