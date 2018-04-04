import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Z5Main {
    public static double valueFromString(String inputStr) {
        if (inputStr == null || inputStr.isEmpty()) {
            throw new IllegalArgumentException("input string is empty");
        }
        
        double input;
        
        try {
            input = Double.valueOf(inputStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("input string is not a number", e);
        }
        
        if (input < 0) {
            throw new IllegalArgumentException("input value is below zero");
        }
        
        return input;
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("Enter a Number: ");
        String inputStr = in.readLine();
        
        double input = valueFromString(inputStr);
        
        double result = Math.sqrt(input);
        
        System.out.println("The Square Root is " + result);
    }
}
