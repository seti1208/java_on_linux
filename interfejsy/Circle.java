public class Circle implements Figure {
    protected double radius;
	        
    public Circle(double radius) {
        this.radius = radius;
    }
	    
    public double getRadius() {
        return radius;
    }
		        
    @Override
    public double getPerimeter() {
        return radius * 2.0 * Math.PI;
    }

    @Override
    public double getArea() {
        return radius * radius * Math.PI;
    }

    @Override
    public String getName() {
        return "Circle";
    }
}
