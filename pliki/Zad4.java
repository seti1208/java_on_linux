import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Zad4 {
    public static void main(String[] args) throws IOException {
        String filePath = "/etc/fstab";
        
        int lineCount = 0;
        int nospaceCount = 0;
        int spaceCount = 0;
        int wordCount = 0;
        
        boolean wasLineSep = true;
        boolean wasWordChar = false;
        
        boolean isLineSep = true;
        boolean isWordChar = false;
        
        try (
                FileInputStream fis = new FileInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(fis);
                InputStreamReader isr = new InputStreamReader(bis, "utf-8");
                ) {
            for (;;) {
                // remembering a previous state
                wasLineSep = isLineSep;
                wasWordChar = isWordChar;
                
                // getting new a new char
                int charInt = isr.read();
                
                if (charInt == -1) {
                    break;
                }
                
                char c = (char) charInt;
                
                // getting new a new state
                isLineSep = false;
                isWordChar = false;
                switch (c) {
                    case '\n':
                        isLineSep = true;
                    case ' ':
                    case '\t':
                    case '\r':
                        break;
                    default:
                        isWordChar = true;
                }
                
                if (wasLineSep) {
                    ++lineCount;
                }
                
                if (isWordChar) {
                    ++nospaceCount;
                } else {
                    ++spaceCount;
                }
                
                if (!wasWordChar && isWordChar) {
                    ++wordCount;
                }
            }
        }
        
        System.out.println(
                "line count is " + lineCount + ";\n"
                + "nospace char count is " + nospaceCount + ";\n"
                + "space char count is " + spaceCount + ";\n"
                + "word count is " + wordCount
        );
    }
}
