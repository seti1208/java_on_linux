public class Calculator {
   public enum Computation {
       MULTIPY, DIVIDE, ADD, SUBTRACT;
       
       public double perform (double x, double y) {
           switch (this) {
               case MULTIPY: return x * y;
               case DIVIDE: return x / y;
               case ADD: return x + y;
               case SUBTRACT: return x - y;
               default: throw new IllegalStateException();
           }
       }
   }
   public static void main(String[] args) {
	   System.out.println(Computation.ADD.perform(1, -7));
   }
}
