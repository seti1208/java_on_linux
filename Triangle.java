public class Triangle implements Figure {
    protected double sideA;
    protected double sideB;
    
    public Triangle(double sideA, double sideB) {
        this.sideA = sideA;
        this.sideB = sideB;
    }
    
    public double getSideA() {
        return sideA;
    }
    
    public double getSideB() {
        return sideB;
    }
    
    @Override
    public double getPerimeter() {
        double sideC = Math.sqrt(sideA * sideA + sideB * sideB);
        
        return sideA + sideB + sideC;
    }
    
    @Override
    public double getArea() {
        return sideA * sideB / 2.0;
    }
    
    @Override
    public String getName() {
        return "Triangle";
    }
}
