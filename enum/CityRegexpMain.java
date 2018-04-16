import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CityRegexpMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
	System.out.print("Enter a City: ");
        
        String cityInput = sc.nextLine();
        
        Pattern cityPattern = Pattern.compile("([A-Z][A-Za-z\\-]*)");
        
        Matcher matcher = cityPattern.matcher(cityInput);
        
        if (matcher.matches()) {
            System.out.println(
                    "matches! "
                    + matcher.group(1)
            );
        } else {
            System.out.println("doesn't match!");
        }
    }
}
