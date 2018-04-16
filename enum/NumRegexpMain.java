import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumRegexpMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
	System.out.print("Enter a Number: ");
        
        String numberInput = sc.nextLine();
        
        Pattern numberPattern = Pattern.compile("(-?\\d+)(\\.(\\d+))?");
        
        Matcher matcher = numberPattern.matcher(numberInput);
        
        if (matcher.matches()) {
            System.out.println(
                    "matches! "
                    + matcher.group(1)
                    + ","
                    + matcher.group(3)
            );
        } else {
            System.out.println("doesn't match!");
        }
    }
}
