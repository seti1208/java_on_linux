import java.io.FileOutputStream;
import java.io.IOException;

public class Zapis {
    public static void main(String[] args) {

        FileOutputStream fos = null;
        String str = "Ala ma kota.";

        try {
            fos = new FileOutputStream("plik.txt"); //Otwieranie pliku
            for (int i = 0; i < str.length(); ++i) {
                fos.write((int) str.charAt(i)); //Zapis bajt po bajcie każdego znaku
            }
        } catch (IOException ex) {
            System.out.println("Błąd operacji na pliku: " + ex);
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
