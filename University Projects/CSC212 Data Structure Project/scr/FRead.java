import java.io.File;
import java.util.Scanner;

public class FRead {
  
    public static void read(String name) {
        String line = null;
        
        try {
            File file = new File(name);
            
            if (!file.exists()) {
                System.out.println("File not found: " + name);
                return;
            }
            
            Scanner in = new Scanner(file);
            
            // Optionally skip the first line if it's a header
            if (in.hasNextLine()) {
                in.nextLine();  // skip header line if needed
            }
            
            while (in.hasNextLine()) {
                line = in.nextLine();
                
                if (line.trim().length() < 3) {
                    System.out.println("Empty line found, skipping: " + line);
                    continue;  // skip empty line, do not break
                }
                
                System.out.println(line);
                String[] words = line.split(",");
                
                if (words.length > 1) {
                    int id = Integer.parseInt(words[0].trim());
                    String content = words[1].trim();
                    // Process the id and content as needed
                }
            }
            
            in.close();
        } catch (Exception e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        read("dataset.csv");
        read("stop.txt");
    }
}
