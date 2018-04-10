import java.util.Optional;
public class Human {
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

	    @Override
        public String toString() {
            return "Person{" +
                   "name='" + Optional.ofNullable(name).orElse("") + '\'' +
                   ", age=" + Optional.ofNullable(age).orElse(0) +
                   '}';
	}

    public static void main(String[] args) {
        // 1
        Human person = new Human();
        person.setName("John");

        Optional.ofNullable(person.getName()).ifPresent(System.out::println);
        System.out.println(Optional.ofNullable(person.getName()).map(value -> "imie: "+value).orElse("brak Imienia!"));

        person.getName().ifPresent(System.out::println);
        person.setAge(22);
        System.out.println(person.getAge()
                .filter(age -> age >= 18)
                .map(age -> "wiek: " + age + ". Osoba pełnoletnia")
                .orElse("Osoba niepełnoletnia"));

        for(ColorHair kolor: ColorHair.values()) {
            System.out.println(isFine(kolor));
	}
    }
        public static String isFine(ColorHair kolor) {
		        String isFine = (kolor.ladny) ? "ladny" : "brzydki";
			return "Kolor "+kolor.toString()+" jest "+isFine;
	}
}

  
