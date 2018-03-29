import java.util.Scanner;

public class Z1Main {
    public static void main(String[] args) {
        int figuresCount = 3;
        Figure[] figures = new Figure[figuresCount];
        
        Scanner sc = new Scanner(System.in);
        
        {
            System.out.print("Enter Circle's Radius: ");
            double r = sc.nextDouble();
            
            figures[0] = new Circle(r);
        }
        
        {
            System.out.print("Enter Triangle's Side A: ");
            double a = sc.nextDouble();
            System.out.print("Enter Triangle's Side B: ");
            double b = sc.nextDouble();
            
            figures[1] = new Triangle(a, b);
        }
        
        {
            System.out.print("Enter Rectangle's Side A: ");
            double a = sc.nextDouble();
            System.out.print("Enter Rectangle's Side B: ");
            double b = sc.nextDouble();
            
            figures[2] = new Rectangle(a, b);
        }
        
        for (int i = 0; i < figuresCount; ++i) {
            System.out.println("Figure " + figures[i].getName());
            System.out.println("Perimeter: " + figures[i].getPerimeter());
            System.out.println("Area: " + figures[i].getArea());
            System.out.println();
        }
    }
}
