package schedule.providers;

import java.util.ArrayList;
import schedule.providers.EventProvider;
import java.time.LocalDateTime;

public class icsFile {
    
    static final String SPLIT = "\r\n ";

    public static void fromString(String str) {
        String contentLine = str.replaceAll(SPLIT,"");
        //contentLine = contentLine.
        contentLine = contentLine.replaceAll("DTSTART",";DTSTART");
        String[] contentLines = contentLine.split(";");

        for (String cl : contentLines) {
            System.out.println(cl);
            System.out.println("---");
        }
        
        



    };
    public static void main(String[] args) {
        String ical = EventProvider.getIcalString(args[0]);
        fromString(ical);
    }
}
