package schedule.providers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import schedule.Event;
import schedule.providers.IcsFile;

import java.io.BufferedReader;
import java.util.ArrayList;

import icsFile;
public class icstest {
    public static void main(String[] args) {
        System.out.print(args);
        
        for (Event event : sch) {
            System.out.println(event);
        }
        // Creating BufferedReader for Input
        BufferedReader bfri = new BufferedReader(
            new InputStreamReader(System.in));
            
            System.out.print("Enter the Path : ");
            
            // Reading File name
            String path = bfri.readLine();
            
            BufferedReader bfro
            = new BufferedReader(new FileReader(path));
            String st;
            
            while ((st = bfro.readLine()) != null)
                System.out.println(st);
            
        }
        ArrayList<Event> sch = IcsFile.scheduleFromString();
}
