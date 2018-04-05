import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Zad1 {
    public static void main(String[] args) throws IOException {

	 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	 System.out.print("Podaj coś: ");
	 String str = in.readLine();
     //   String str = "Ala ma kota.";

        try {
            PrintWriter fos = new PrintWriter("plik.txt"); //Otwieranie pliku
	    fos.print(str);
	    fos.close();

        } catch (IOException ex) {
            System.out.println("Błąd operacji na pliku: " + ex);
        } finally {
            try {
	   Scanner output = new Scanner(new File("plik.txt"));
	   System.out.println(output.nextLine());
	//    BufferedReader output = new BufferedReader(new File("plik.txt"));
	 //   System.out.println(output.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
}
