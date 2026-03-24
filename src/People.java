import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class People {
    public String firstName;
    public String lastName;
    public String mail;

    public People(String valueFirstName, String valueLastName, String valueMail) {
        firstName = valueFirstName;
        lastName = valueLastName;
        mail = valueMail;
    }
    public static List<People> people = new ArrayList<>();

    public static void lookUpSearch(String query) {
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).firstName.toLowerCase().contains(query.toLowerCase()) || people.get(i).lastName.toLowerCase().contains(query.toLowerCase()) || people.get(i).mail.toLowerCase().contains(query.toLowerCase())) {
                indexes.add(i);
            }
        }
        if (indexes.isEmpty()) {
            System.out.println("No matching people found");
        } else {
            IO.println("Found people:");
            for (Integer index : indexes) {
                System.out.println(people.get(index).firstName + " " + people.get(index).lastName + " " + people.get(index).mail);
            }
        }
    }

    static void main() {
        Scanner sc = new Scanner(System.in);

        // Prompt for number of people
        IO.println("Enter the number of people:");
        int numberOfPeople = sc.nextInt();

        IO.println("Enter all people:");

        // Add each person
        for (int i = 0; i < numberOfPeople; i++) {
            String firstName = sc.next();
            String lastName = sc.next();
            String mail = sc.next();
            People person = new People(firstName, lastName, mail);
            people.add(person);
        }

        // Prompt for number of queries
        IO.println("Enter the number of search queries:");
        int numberOfQueries = sc.nextInt();

        // search each query
        for (int i = 0; i < numberOfQueries; i++) {
            IO.println("Enter data to search people:");
            String query = sc.next();
            lookUpSearch(query);
        }
    }
}

