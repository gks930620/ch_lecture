package quest;

// B. 다중 인터페이스 구현 — Flyable + Swimmable을 동시에 구현하는 Duck
public class SolutionB {

    interface Flyable {
        void fly();
    }

    interface Swimmable {
        void swim();
    }

    // B-1. 클래스는 단일 상속만 되지만 인터페이스는 여러 개 구현 가능
    static class Duck implements Flyable, Swimmable {
        @Override
        public void fly() {
            System.out.println("오리가 난다");
        }

        @Override
        public void swim() {
            System.out.println("오리가 헤엄친다");
        }
    }

    public static void main(String[] args) {
        Duck duck = new Duck();
        duck.fly();  // 오리가 난다
        duck.swim(); // 오리가 헤엄친다

        // B-2. 업캐스팅하면 해당 인터페이스의 메소드만 보인다
        Flyable f = duck;
        f.fly();        // 오리가 난다
        // f.swim();    // 컴파일 오류! Flyable 타입에는 swim()이 없다

        Swimmable s = duck;
        s.swim();       // 오리가 헤엄친다
        // s.fly();     // 컴파일 오류! Swimmable 타입에는 fly()가 없다
    }
}
