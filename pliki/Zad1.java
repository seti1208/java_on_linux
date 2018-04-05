import java.io.*;

public class Zad1 {
    public static void main(String[] args) throws IOException {

	 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	 System.out.print("Podaj coś: ");
	 String str = in.readLine();
     //   String str = "Ala ma kota.";

        try {
            BufferedWriter fos = new BufferedWriter(new FileWriter("plik.txt"));
	    fos.write(str);
	    fos.close();

        } catch (IOException ex) {
            System.out.println("Błąd operacji na pliku: " + ex);
        } finally {
            try {
	   //Scanner output = new Scanner(new File("plik.txt"));
	   //System.out.println(output.nextLine());

	   BufferedReader output = new BufferedReader(new FileReader("plik.txt"));
	   System.out.println(output.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
}
