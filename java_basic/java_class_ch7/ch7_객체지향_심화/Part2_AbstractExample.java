package ch7_객체지향_심화;

// 추상 클래스
abstract class Shape {
    protected String name;

    public Shape(String name) {
        this.name = name;
    }

    // 일반 메소드
    public void printName() {
        System.out.println("도형: " + name);
    }

    // 추상 메소드 (구현 없음)
    public abstract double getArea();
    public abstract double getPerimeter();
}

// 구체 클래스
class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        super("원");
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        super("사각형");
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }
}

public class Part2_AbstractExample {
    public static void main(String[] args) {
        System.out.println("=== 추상 클래스 예제 ===");

        // Shape shape = new Shape("도형");  // 에러! 추상 클래스는 객체 생성 불가

        Shape[] shapes = {
            new Circle(5),
            new Rectangle(4, 6)
        };

        for (Shape shape : shapes) {
            shape.printName();
            System.out.println("넓이: " + shape.getArea());
            System.out.println("둘레: " + shape.getPerimeter());
            System.out.println();
        }
    }
}

