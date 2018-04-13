import java.util.Optional;
import java.util.stream.Stream;

public class Human {
    public enum ColorEye {
        GREEN,
        BLUE,
	BROWN,
        GRAY;
        
        @Override
        public String toString() {
	     return super.toString().toLowerCase();
        }
    }
    
    public enum ColorHair {
        BROWN(true), 
        CHOCOLATE(true),
        GRAY(false), 
        ORANGE(false),
        BLACK(true);

	     
        boolean ladny;
		     
        private ColorHair(boolean isFine) {
               ladny = isFine;
        }
    
        @Override
        public String toString() {
	     return super.toString().toLowerCase();
        }
    }
    private String name;
    private Integer age;
    private ColorEye colorEye;
    private ColorHair colorHair;

    public Optional<String> getName() {
          return Optional.ofNullable(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<Integer> getAge() {
        return Optional.ofNullable(age);
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    
    public Optional<ColorEye> getColorEye() {
          return Optional.ofNullable(colorEye);
    }

    public void setColorEye(ColorEye colorEye) {
        this.colorEye = colorEye;
    }
    
    public Optional<ColorHair> getColorHair() {
          return Optional.ofNullable(colorHair);
    }

    public void setColorHair(ColorHair colorHair) {
        this.colorHair = colorHair;
    }
    
    @Override
    public String toString() {
        return "Person{" +
               "name='" + Optional.ofNullable(name).orElse("") + '\'' +
               ", age=" + Optional.ofNullable(age).orElse(0) +
               '}';
    }

    public static void main(String[] args) {
        Human person = new Human();
	//ustawiamy vartośći
       // person.setName("Grzegorz");
        person.setAge(22);
        
        //person.setColorHair(ColorHair.BROWN);
        person.setColorEye(ColorEye.GREEN);

        //Optional.ofNullable(person.getName()).ifPresent(System.out::println);
        //person.getName().ifPresent(System.out::println);
        System.out.println(person.getName().map(value -> "imie: "+value).orElse("brak Imienia!"));

        System.out.println(person.getAge()
                .filter(age -> age >= 18)
                .map(age -> "wiek: " + age + ". Osoba pełnoletnia")
                .orElse("Osoba niepełnoletnia"));

	System.out.println(person.getColorEye().map(value -> "Oczy:" +value).orElse("brak Oczy!"));
	System.out.println(person.getColorHair().map(value -> "Włosy:" +value).orElse("brak Włosów!"));
        //Stream.of(ColorHair.values()).forEach(System.out::println);
    }
    
    public static String isFine(ColorHair kolor) {
                    String isFine = (kolor.ladny) ? "ladny" : "brzydki";
                    return "Kolor "+kolor.toString()+" jest "+isFine;
    }
}
