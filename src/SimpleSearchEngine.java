import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleSearchEngine {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IO.println("Input sentence:");
        String sentence = sc.nextLine();
        IO.println("Search word:");
        String searchWord = sc.nextLine();

        List<String> words = new ArrayList<String>();
        char[] sourceSentence = sentence.toCharArray();
        int length = sentence.length();

        // iterate over sentence
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (sourceSentence[i] == ' ') {
                words.add(string.toString());
                string = new StringBuilder();
            } else {
                string.append(sourceSentence[i]);
            }
        }
        words.add(string.toString());

        // IO.println(words);

        // Compare words to search word
        for (int i = 0; i < words.size(); i++) {
            String value = words.get(i);
            if (value.compareToIgnoreCase(searchWord) == 0) {
                System.out.println(i + 1);
                return;
            }
        }
        IO.println("Not found");
    }
}
