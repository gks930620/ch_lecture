package quest;

public class Q2 {
    abstract static class Shape {
        abstract double area();
    }

    static class Circle extends Shape {
        private final double r;

        Circle(double r) {
            this.r = r;
        }

        @Override
        double area() {
            return Math.PI * r * r;
        }
    }

    static class Rectangle extends Shape {
        private final double w;
        private final double h;

        Rectangle(double w, double h) {
            this.w = w;
            this.h = h;
        }

        @Override
        double area() {
            return w * h;
        }
    }

    public static void main(String[] args) {
        Shape c = new Circle(3);
        Shape r = new Rectangle(4, 5);
        System.out.println("원 넓이: " + c.area());
        System.out.println("직사각형 넓이: " + r.area());
    }
}
