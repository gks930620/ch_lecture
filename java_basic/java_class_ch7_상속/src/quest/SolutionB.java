package quest;

// B. 오버라이딩 (B-1, B-4) — sound() 재정의 + super.sound()
// B-2(@Override 누락), B-3(접근 제어 축소)은 "컴파일 오류를 유발하는 설명"이라 주석으로 남깁니다.
public class SolutionB {

    static class Animal {
        void sound() {
            System.out.println("(동물 울음소리)");
        }
    }

    static class Dog extends Animal {
        @Override
        void sound() {
            System.out.println("멍멍");
        }
        // B-2. @Override를 빼고 이름을 잘못 쓰면(soud) 조용히 새 메소드가 되어 버그가 된다.
        // void soud() { System.out.println("멍멍"); } // 오버라이딩 아님
    }

    static class Cat extends Animal {
        @Override
        void sound() {
            super.sound();              // B-4. 부모 구현을 먼저 실행하고
            System.out.println("야옹"); // 자식 동작을 덧붙인다
        }
    }

    public static void main(String[] args) {
        new Dog().sound(); // 멍멍
        new Cat().sound(); // (동물 울음소리) / 야옹
    }
}
