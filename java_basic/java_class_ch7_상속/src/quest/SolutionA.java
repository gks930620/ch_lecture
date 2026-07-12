package quest;

// A. 상속 기초 (A-1 ~ A-4) — Animal / Dog / Cat
public class SolutionA {

    // A-1, A-2. 공통 필드(name, age)는 부모에 둔다
    static class Animal {
        protected String name;
        protected int age;

        public Animal(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void printInfo() {
            System.out.println(name + " (" + age + "살)");
        }
    }

    static class Dog extends Animal {
        // A-4. super(...)로 부모 생성자 호출 — 자식 생성자 첫 줄
        public Dog(String name, int age) {
            super(name, age);
        }

        // A-3. Dog 고유 메소드
        public void fetch() {
            System.out.println(name + ": 공을 물어온다!");
        }
    }

    static class Cat extends Animal {
        public Cat(String name, int age) {
            super(name, age);
        }

        // A-3. Cat 고유 메소드
        public void scratch() {
            System.out.println(name + ": 스크래처를 긁는다!");
        }
    }

    public static void main(String[] args) {
        Dog dog = new Dog("바둑이", 3);
        Cat cat = new Cat("나비", 2);

        dog.printInfo(); // 바둑이 (3살) <- 부모에게 물려받은 메소드 재사용
        cat.printInfo(); // 나비 (2살)
        dog.fetch();     // 바둑이: 공을 물어온다!
        cat.scratch();   // 나비: 스크래처를 긁는다!
    }
}
