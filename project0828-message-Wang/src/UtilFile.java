
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.Test;

public class UtilFile {
   @Test
   public void testReadFile() {
      // A format of file "config.txt" consists of a key-value pair.
      List<String> contents = UtilFile.readFile("config.txt");
      for (int i = 0; i < contents.size(); i++) {
         String line = contents.get(i);
         System.out.println("[DBG] Line " + i + " - " + line);

         String name = line.split(":")[1].trim();
         System.out.println("[DBG] \tName: " + name);
      }
   }

   public static List<String> readFile(String filePath) {
      List<String> contents = new ArrayList<String>();
      File file = new File(filePath);
      Scanner scanner = null;
      try {
         scanner = new Scanner(file);
         while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            contents.add(line);
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } finally {
         if (scanner != null)
            scanner.close();
      }
      return contents;
   }
}
