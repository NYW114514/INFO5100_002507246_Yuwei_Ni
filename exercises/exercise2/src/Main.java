// base class Shape
abstract class Shape {
    public static String DEFAULT_COLOR = "Blue";

    public abstract double calculateArea();
    public abstract double calculatePerimeter();

    public String type() {
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return String.format("%s -> Area: %.2f, Perimeter: %.2f, Color: %s",
                type(), calculateArea(), calculatePerimeter(), DEFAULT_COLOR);
    }
}
// Rectangle
class Rectangle extends Shape {
    protected double length;
    protected double width;

    public static final String CLASS_COLOR = "Green";

    public Rectangle(double length, double width) {
        if (length <= 0 || width <= 0)
            throw new IllegalArgumentException("Length and width must be positive.");
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }
}
// Square
class Square extends Rectangle {
    public Square(double side) {
        super(side, side);
    }
}
// Circle
class Circle extends Shape {
    private double radius;
    public static final String CLASS_COLOR = "Red";

    public Circle(double radius) {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius must be positive.");
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}
// Triangle
class Triangle extends Shape {
    private double a, b, c;
    public static final String CLASS_COLOR = "Orange";

    public Triangle(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0)
            throw new IllegalArgumentException("Sides must be positive.");
        if (a + b <= c || a + c <= b || b + c <= a)
            throw new IllegalArgumentException("Invalid triangle sides.");
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calculateArea() {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double calculatePerimeter() {
        return a + b + c;
    }
}


public class Main {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Rectangle(5, 6),
                new Square(4),
                new Circle(2.5),
                new Triangle(3, 4, 5)

        };

        System.out.println("Default Color = " + Shape.DEFAULT_COLOR + "\n");
        for (Shape s : shapes) {
            System.out.println(s.toString());
        }

        Shape.DEFAULT_COLOR = "Black";
        System.out.println("\nAfter changing global color:");
        for (Shape s : shapes) {
            System.out.println(s.toString());
        }

        System.out.println("\nClass-specific static colors:");
        System.out.println("Rectangle color: " + Rectangle.CLASS_COLOR);
        System.out.println("Square color: " + Square.CLASS_COLOR);
        System.out.println("Circle color: " + Circle.CLASS_COLOR);
        System.out.println("Triangle color: " + Triangle.CLASS_COLOR);
    }
}
