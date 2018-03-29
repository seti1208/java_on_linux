public class Rectangle implements Figure {
    protected double sideA;
    protected double sideB;
    
    public Rectangle(double sideA, double sideB) {
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
      return sideA + sideB * 2.0;
    }
	    
    @Override
     public double getArea() {
       return sideA * sideB;
    }
	    
    @Override
     public String getName() {
       return "Rectangle";
     }   
}
