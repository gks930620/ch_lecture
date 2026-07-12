package quest;

// B. 스코프/호출 스택 (B-1 ~ B-4)
public class SolutionB {

    // B-1. 지역 변수와 필드 이름이 같을 때 this로 구분
    static class User {
        private String name; // 필드

        void setName(String name) { // 매개변수가 필드를 가림(shadowing)
            this.name = name;       // this.name = 필드, name = 매개변수
        }

        String getName() {
            return name;
        }
    }

    // B-3. 재귀 팩토리얼 — 종료 조건(base case)이 핵심
    static long factorial(int n) {
        if (n <= 1) {          // 종료 조건
            return 1;
        }
        return n * factorial(n - 1);
    }

    // B-4. 호출 스택 흐름 확인 (main -> A -> B)
    static void methodA() {
        System.out.println("A 시작");
        methodB();
        System.out.println("A 끝");
    }

    static void methodB() {
        System.out.println("B 시작");
        System.out.println("B 끝");
    }

    public static void main(String[] args) {
        // B-1
        User user = new User();
        user.setName("kim");
        System.out.println(user.getName()); // kim

        // B-2. 블록 스코프: inner는 if 블록 안에서만 보인다
        int outer = 1;
        if (outer > 0) {
            int inner = 10; // if 블록 스코프
            System.out.println(inner); // 10 (블록 안에서는 사용 가능)
        }
        // System.out.println(inner); // 컴파일 오류: cannot find symbol - variable inner
        for (int i = 0; i < 3; i++) { }
        // System.out.println(i); // 마찬가지로 컴파일 오류

        // B-3
        System.out.println(factorial(5));  // 120
        System.out.println(factorial(10)); // 3628800

        // B-4. 후입선출(LIFO) — 나중에 호출된 B가 먼저 끝난다
        System.out.println("main 시작");
        methodA();
        System.out.println("main 끝");
    }
}
