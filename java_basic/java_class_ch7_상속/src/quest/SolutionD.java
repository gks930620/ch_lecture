package quest;

// D. 추상 클래스 (D-1 ~ D-4) — Shape / Circle / Rectangle
public class SolutionD {

    // D-1. 추상 클래스 — 직접 new 불가, area() 구현을 자식에게 강제
    static abstract class Shape {
        abstract double area();

        // D-4. 공통 구현 메소드 — 모든 자식이 그대로 물려받는다
        void printInfo() {
            System.out.println(getClass().getSimpleName() + "의 넓이: " + area());
        }
    }

    // D-2. 자식 구현
    static class Circle extends Shape {
        private final double radius;

        Circle(double radius) { this.radius = radius; }

        @Override
        double area() {
            return Math.PI * radius * radius;
        }
    }

    static class Rectangle extends Shape {
        private final double width;
        private final double height;

        Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        @Override
        double area() {
            return width * height;
        }
    }

    public static void main(String[] args) {
        // new Shape(); // 컴파일 오류! 추상 클래스는 인스턴스화 불가

        // D-3. 부모 타입 배열(Shape[])로 다형적 합계 계산
        Shape[] shapes = { new Circle(1), new Rectangle(3, 4), new Rectangle(2, 5) };

        double total = 0;
        for (Shape s : shapes) {
            s.printInfo();
            total += s.area(); // 각 도형이 자기 방식으로 넓이를 계산
        }
        System.out.println("전체 넓이 합계: " + total); // 25.141592653589793
    }
}
