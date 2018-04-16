import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HouseRegexpMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
	System.out.print("Enter a House number: ");
        
        String addrInput = sc.nextLine();
        
        Pattern addrPattern = Pattern.compile("(\\d+)([A-Za-z]?)(\\\\(\\d+)([A-Za-z]?))?");
        //Pattern addrPattern = Pattern.compile("(\\d+)([A-Za-z]?)\\\\(\\d+)([A-Za-z]?)");
        
        Matcher matcher = addrPattern.matcher(addrInput);
        
        if (matcher.matches()) {
            System.out.println(
                    "matches! "
                    + matcher.group(1)
                    + " "
                    + matcher.group(2)
                    + " "
                    + matcher.group(4) //3 do 2 regexp pasuje
                    + " "
                    + matcher.group(5)
            );
        } else {
            System.out.println("doesn't match!");
        }
    }
}
