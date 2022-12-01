import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AOC {


    public static List<String> input(int day) {

        File file = new File( "inputs/Day" + day + ".txt");
        if(!file.exists())
        {
            System.out.println("File doesn't exist");
            System.out.println(file.getPath());
            System.exit(0);
        }
        Scanner sc = null;

        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> input = new ArrayList<>();

        while(sc.hasNextLine()) {
            input.add(sc.nextLine());
        }

        return input;
    }

}
