import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Odczyt {

    public static void main(String[] args) {

        FileInputStream fis = null;
        int bajt = 0;

        try {
            fis = new FileInputStream("plik.txt");
            bajt = fis.read();
            while (bajt != -1) { // -1 - określa koniec pliku
                System.out.print((char) bajt);
                bajt = fis.read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
