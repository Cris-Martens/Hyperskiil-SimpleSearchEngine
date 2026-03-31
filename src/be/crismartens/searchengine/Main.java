package be.crismartens.searchengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Search {
    private SearchStrategy strategy;

    public Search(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<String> lookUp (String query, Map<String, List<Integer>> map, List<String> people) {
        return strategy.lookUp(query, map, people);
    }
}

interface SearchStrategy {
    List<String> lookUp(String query, Map<String, List<Integer>> map, List<String> people);
}

class SearchAll implements SearchStrategy {
    @Override
    public List<String> lookUp(String query, Map<String, List<Integer>> map, List<String> people) {

        String[] words = query.split(" ");

        List<String> result = new ArrayList<>();
        Set<Integer> set = new HashSet<>();

        for (String word : words) {
            if (map.containsKey(word)) {
                if (!set.isEmpty()) {
                    set.retainAll(map.get(word));
                } else {
                    set.addAll(map.get(word));
                }
            }
        }

        for (Integer i : set) {
            result.add(people.get(i));
        }
        return result;
    }
}

class SearchAny implements SearchStrategy {
    @Override
    public List<String> lookUp(String query, Map<String, List<Integer>> map, List<String> people) {

        String[] words = query.split(" ");

        List<String> result = new ArrayList<>();
        Set<Integer> set = new HashSet<>();

        for (String word : words) {
            if (map.containsKey(word)) {
                set.addAll(map.get(word));
            }
        }

        for (Integer i : set) {
            result.add(people.get(i));
        }
        return result;
    }
}

class SearchNone implements SearchStrategy {
    @Override
    public List<String> lookUp(String query, Map<String, List<Integer>> map, List<String> people) {
        String[] words = query.split(" ");

        List<String> result = new ArrayList<>();
        Set<Integer> set = new HashSet<>();

        // add all indexes to set
        for (int i = 0; i < people.size(); i++) {
            set.add(i);
        }

        for (String word : words) {
            if (map.containsKey(word)) {
                set.removeAll(map.get(word));
            }
        }

        for (Integer i : set) {
            result.add(people.get(i));
        }
        return result;
    }
}


public class Main {
    public static void main(String[] args) {

        // read file into String array
        if (args.length != 2) {
            System.out.println("Usage: java Main --data <input_file>");
        }
        List<String> people = new ArrayList<>();
        File file = new File(args[1]);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                people.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }

        /*
        Scanner sc = new Scanner(System.in);
        System.out.print("How many people: ");
        int numberPeople = sc.nextInt();
        sc.nextLine();
        String[] people = new String[numberPeople];
        for (int i = 0; i < numberPeople; i++) {
            people[i] = sc.nextLine();
        }    */

        // create inverted index for people
        Map<String, List<Integer>> map = new LinkedHashMap<>();
        for (int i = 0; i < people.size(); i++) {
            String person = people.get(i);
            String[] split = person.split(" ");
            for (String raw : split) {
                String key = raw.toLowerCase();
                List<Integer> indexes = new ArrayList<>();
                if (!map.containsKey(key)) {
                    map.put(key, indexes);
                }
                map.get(key).add(i);
            }
        }

        // query menu
        int option;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("=== Menu ===");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("3. Exit");
            option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                System.out.println("Select a matching Strategy: ALL, ANY, NONE");
                String strategy = sc.nextLine();
                Search search = null;
                switch (strategy) {
                    case "ALL":
                        search = new Search(new SearchAll());
                        break;
                    case "ANY":
                        search = new Search(new SearchAny());
                        break;
                    case "NONE":
                        search = new Search(new SearchNone());
                        break;
                    default:
                        System.out.println("Invalid Strategy.");
                }


                // prompt query
                System.out.println("Enter a nome or email to search all suitable people.");
                String input = sc.nextLine();
                String query = input.toLowerCase();

                try {
                    List<String> searchResults = search.lookUp(query, map, people);
                    if (searchResults.isEmpty()) {
                        System.out.println("No matching people found.");
                    } else {
                        for (String searchResult : searchResults) {
                            System.out.println(searchResult);
                        }
                    }
                } catch (NullPointerException e) {
                    System.out.println("Internal error. No database.");
                }
            } else if (option == 2) {
                // print all people
                for (String person : people) {
                    System.out.println(person);
                }
            } else if (option > 2 || option < 0) {
                System.out.println("Invalid option.");
            }
        } while (option != 0);

        // Exit
        System.out.println();
        System.out.println("Bye!");
    }
}