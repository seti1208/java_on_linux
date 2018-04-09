public class EnumTest {
    public enum Kolor {
 
        CZERWONY(false), 
        ZIELONY(true), 
        NIEBIESKI(true);
 
        boolean ladny;
 
        private Kolor(boolean czyLadny) {
            ladny = czyLadny;
        }
        @Override
        public String toString() {
	     return super.toString().toLowerCase();
        } 
    }
 
    public static void main(String[] args) {
 
    //bez użycia pętli:
    //    System.out.println(czyLadny(Kolor.CZERWONY));
    //    System.out.println(czyLadny(Kolor.ZIELONY));        
    //    System.out.println(czyLadny(Kolor.NIEBIESKI));        
    //z pętlą for
    //    Kolor[] kolory = Kolor.values();
    //    for(int i=0; i<kolory.length; i++) {
    //        System.out.println(czyLadny(kolory[i]));
    //        }
    //Za pomocą foreach
        for(Kolor kolor: Kolor.values()) {
            System.out.println(czyLadny(kolor));
            }
    }
 
    public static String czyLadny(Kolor kolor) {
        String czyLadny = (kolor.ladny) ? "ladny" : "brzydki";
 
        return "Kolor "+kolor.toString()+" jest "+czyLadny;
    }
 
}
