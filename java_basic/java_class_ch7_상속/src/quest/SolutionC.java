package quest;

// C. 다형성 (C-1 ~ C-4) — Animal[] 반복 호출 + 안전한 다운캐스팅
public class SolutionC {

    static class Animal {
        void sound() { System.out.println("..."); }
    }

    static class Dog extends Animal {
        @Override
        void sound() { System.out.println("멍멍"); }

        void fetch() { System.out.println("공 물어오기"); }
    }

    static class Cat extends Animal {
        @Override
        void sound() { System.out.println("야옹"); }
    }

    static class Bird extends Animal {
        @Override
        void sound() { System.out.println("짹짹"); }
    }

    public static void main(String[] args) {
        // C-1. 서로 다른 자식들을 부모 타입 배열에 담는다 (업캐스팅)
        Animal[] animals = { new Dog(), new Cat(), new Bird() };
        for (Animal a : animals) {
            a.sound(); // 실제 객체 타입에 따라 다른 메소드 실행 (동적 바인딩)
        }
        // 멍멍 / 야옹 / 짹짹

        // C-2. 업캐스팅된 참조로는 공통 메소드만 호출 가능
        Animal a = new Dog();
        a.sound();    // OK — Animal에 정의된 메소드
        // a.fetch(); // 컴파일 오류! Animal 타입에는 fetch()가 없다

        // C-4. 잘못된 다운캐스팅 — 컴파일은 되지만 실행 시 예외
        Animal x = new Cat();
        try {
            Dog d = (Dog) x; // 실제 객체는 Cat인데 Dog로 캐스팅
            d.fetch();
        } catch (ClassCastException e) {
            System.out.println("예외 발생: class Cat cannot be cast to class Dog");
        }

        // C-3. 수정: instanceof 패턴 매칭으로 검사 + 캐스팅을 한 번에
        Animal[] more = { new Dog(), new Cat() };
        for (Animal animal : more) {
            if (animal instanceof Dog d) {
                d.fetch();      // 공 물어오기
            } else {
                animal.sound(); // 야옹
            }
        }
    }
}
