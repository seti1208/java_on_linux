class OuterClass {

    private int privInt = 10;

    class InnerClass {
        public void accessOuter() {
            System.out.println("The outer class's privInt is " + privInt);
        }
    }
}

public class Zad3 {
    public static void main(String[] args) {
        new OuterClass().new InnerClass().accessOuter();
        
        // or we can do it like this:
        //
        //OuterClass outer = new OuterClass();
        //
        //OuterClass.InnerClass inner = outer.new InnerClass();
        //
        //inner.accessOuter();
    }
}
