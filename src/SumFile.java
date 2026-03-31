import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class SumFile {
    public static void  main(String args[]) {
        File file = new File("dataset_91065.txt");
        try (Scanner sc = new Scanner(file)) {
            int count = 0;

            while(sc.hasNextInt()) {
                int next =  sc.nextInt();
                if (next == 0) {
                    break;
                }
                if (next % 2 == 0) {
                    count++;
                }
            }
            System.out.println(count);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.getAbsolutePath());
        }
    }
}
