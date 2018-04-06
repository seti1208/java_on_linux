abstract class Zwierz {
   protected String name = "bez imienia";
//   private Zwierz() {}
   protected Zwierz(String s) { name = s; }
   abstract String getTyp();
   abstract String getVoice();
   String getName() { return  name; }
   void speak() {
    System.out.println(getTyp()+" "+getName()+" mówi "+getVoice());
   }
}

class Pies extends Zwierz {
//   Pies() {}
   Pies(String s)  { super(s); }
   String getTyp() { return "Pies";   }
   String getVoice() { return "HAU, HAU!"; }
}

class Kot extends Zwierz {
//   Kot() {}
   Kot(String s)  { super(s); }
   String getTyp() { return "Kot";        }
   String getVoice() { return "Miauuuu..."; }
}

class Zad5 {
    public static void main(String[] args) {
        Pies p = new Pies("Azor");
        Kot k = new Kot("Tom");
        
        p.speak();
        //System.out.println(p.getName());
        //System.out.println(p.getTyp());
        //System.out.println(p.getVoice());
        
        k.speak();
    }
}
