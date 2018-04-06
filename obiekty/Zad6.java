class Employee { //lepiej osobne pliki public class
	//deklaracja zmięnnych
    private String firstname;
    private String surname;
    private int age;
    
    //tworzymy konstruktory imie, nazwisko, wiek
    public Employee(String firstname, String surname, int age) {
        this.firstname = firstname;
        this.surname = surname;
        this.age = age;
    }
    
    //w przypadku gdy przykładowo konstruktor przyjmuje tylko 1 parametr, zainicjuj pozostałe pola jakimiś domyślnymi wartościami
    public Employee(String firstname, String surname) {
        this.firstname = firstname;
        this.surname = surname;
        this.age = -1;
    }
    
    public Employee(String firstname, int age) {
        this.firstname = firstname;
        this.surname = "Snow";
        this.age = age;
    }
    
    //tylko imię
    public Employee(String firstname) {
        this.firstname = firstname;
        this.surname = "Snow";
        this.age = -1;
    }

   //metody 
    public String getFirstname() {
        return firstname;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public int getAge() {
	return age;
    }
}
class Company { //lepiej osobne pliki public class
	//nazwa firmy oraz pracowniki w tabelce
    private String title;
    private Employee[] employees;
    
    public Company(String title) {
        this.title = title;
        
        Employee dae = new Employee("Daenerys", "Targaryen", 22);
        Employee jon = new Employee("Jon");
        Employee rob = new Employee("Robb", "Stark");
        Employee ram = new Employee("Ramsay", 25);
        
        employees = new Employee[] {dae, jon, rob, ram};
    }
    
    public String getEmployeeDetails(Employee emp) {
        int age = emp.getAge();
        
        String details = emp.getFirstname() + " " + emp.getSurname();
        
        if (age != -1) {
            details += " (Age: " + age + ")";
        }
        
        return details;
    }
    
    public void printDetails() {
        System.out.println("*** " + title + " ***");
        
	//Mile widziane wykorzystanie tablic oraz pętli
        for (int i = 0; i < employees.length; ++i) {
            Employee employee = employees[i];
            
            System.out.println("Employee " + (i + 1) + ": " + getEmployeeDetails(employee));
        }
    }
}
public class Zad6 {
    public static void main(String[] args) {
        Company someCompany = new Company(
                "Game of Thrones Candy Factory"
        );
        
        someCompany.printDetails();
    }
}
