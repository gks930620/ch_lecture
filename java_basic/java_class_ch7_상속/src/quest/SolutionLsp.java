package quest;

// F-3. 챌린지 — 리스코프 치환 원칙(LSP)을 깨는 예시와 개선
// LSP: 자식 객체는 부모 타입이 쓰이는 자리에 넣어도 프로그램이 올바르게 동작해야 한다.
public class SolutionLsp {

    // ===== 나쁜 예: 정사각형/직사각형 문제 =====
    static class Rectangle {
        protected int width, height;
        void setWidth(int w) { this.width = w; }
        void setHeight(int h) { this.height = h; }
        int area() { return width * height; }
    }

    static class Square extends Rectangle {
        // 정사각형은 가로세로가 항상 같아야 하므로 부모의 규칙을 몰래 바꾼다
        @Override void setWidth(int w) { this.width = w; this.height = w; }
        @Override void setHeight(int h) { this.width = h; this.height = h; }
    }

    // 부모 타입 기준으로 작성된 코드
    static void resize(Rectangle r) {
        r.setWidth(5);
        r.setHeight(4);
        System.out.println(r.area());
        // Rectangle이면 20 (기대대로)
        // Square이면  16 (setHeight(4)가 width까지 4로 바꿔버림) — 기대가 깨진다!
    }

    // ===== 개선: 상속을 버리고 공통 추상(인터페이스)만 공유 =====
    interface Shape {
        int area();
    }

    // 불변 record로 만들면 "setter의 약속" 문제 자체가 사라진다
    record Rect(int width, int height) implements Shape {
        public int area() { return width * height; }
    }

    record SquareShape(int side) implements Shape {
        public int area() { return side * side; }
    }

    public static void main(String[] args) {
        // 나쁜 예: 같은 코드에 Square를 넣으면 기대가 깨진다
        resize(new Rectangle()); // 20
        resize(new Square());    // 16 (LSP 위반)

        // 개선: 각자 독립 타입, 공통점은 area()로만 묶는다
        Shape[] shapes = { new Rect(5, 4), new SquareShape(4) };
        for (Shape s : shapes) {
            System.out.println(s.area()); // 20 / 16
        }
    }
}
