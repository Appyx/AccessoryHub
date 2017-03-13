import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Robert on 14/01/2017.
 */
public class DataHandler {

    ArrayList<String> list = new ArrayList<>();

    DataHandler() {
        getAvailableCommands();
    }


    public String[] getAvailableCommands() {
        list.clear();
        try {
            Scanner in = new Scanner(new FileReader("commands.txt"));
            while (in.hasNext()) {
                String line = in.nextLine();
                if (!line.startsWith("#") && !line.isEmpty()) {
                    list.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list.toArray(new String[0]);
    }

    public void executeCommand(String data) {
        if (list.contains(data)) {
            String[] args = data.split(" ");
            ProcessBuilder pb = new ProcessBuilder(args);
            try {
                pb.start();
                System.out.println("executed: " + data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
