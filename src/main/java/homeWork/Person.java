package homeWork;

import javax.persistence.*;
import java.util.Random;
import java.util.Scanner;

@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private static Scanner scanner = new Scanner(System.in);

    public Person() {}

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Person(int id, String firstName, String lastName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public void updateFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void updateLastName(String lastName) {
        this.lastName = lastName;
    }

    public void updateAge() {
        this.age = age-1;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }

    public static Person create(){
       return new Person(
               promptString("Введите Имя:"),
               promptString("Введите Фамилию:"),
               promptInt("Введите возраст:")) ;
    }


    private static int promptInt(String string){
        System.out.println(string);
        return scanner.nextInt();
    }

    private static String promptString(String string){
        System.out.println(string);
        return scanner.nextLine();
    }

}
