class Telefon {
     // deklaracja pol
     private String numerTelefonu;
     private int lacznyCzasRozmow;
     private static double cenaRozmowy = 0.48;
 
     // konstruktor
     public Telefon (String numer) {
         numerTelefonu = numer;
     }
 
     // deklaracja metod
     public double obliczKwoteDoZaplaty() {
         return cenaRozmowy * (lacznyCzasRozmow / 60);
     }

     public static void ustawCeneRozmowy(double nowaCena){
         cenaRozmowy = nowaCena;
     }

     public void zadzwon(String nrTelefonu) {
         System.out.println ("Dzwoni4 do: " + nrTelefonu);
         System.out.println ("Dry5, dry5...");
         System.out.println ("Rozmowa w toku...");
         int czasRozmowy = (int) (Math.random()*3600);
         lacznyCzasRozmow += czasRozmowy;
         System.out.println ("Rozmowa zakonczona. ");
         System.out.printf ("Czas rozmowy: %d min. %d sek.\n\n", czasRozmowy/60, czasRozmowy%60 );
     }
}

public class Telefony {
    public static void main(String[] args){
            int n = 10;
            Telefon[] telefony = new Telefon[n];
            for (int i = 0; i < n; i++) {
		telefony[i] = new Telefon("string");
                telefony[i].zadzwon("112");
	        } 
        }
}


