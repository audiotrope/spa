package schedule.providers;

import java.util.ArrayList;

import schedule.Event;
import schedule.providers.EventProvider;
import java.time.LocalDateTime;

public class icsFile {

    static final String SPLIT = "\r\n ";

    public static void fromString(String str) {
        
        ArrayList<Event> schedule = new ArrayList<Event>();

        String contentLine = str.replaceAll(SPLIT,"");
        //contentLine = contentLine.
        contentLine = contentLine.replaceAll("DTSTART",";DTSTART");
        String[] contentLines = contentLine.split(";");

        for (String cl : contentLines) {
            System.out.println(cl);

            LocalDateTime start;
            LocalDateTime slut;
            String eventName;
            String activity;
            ArrayList<String> locations = new ArrayList<String>();

            if (cl.contains("DTSTART")){
                start = LocalDateTime.parse(cl.replace("DTSTART:", ""));
            }
            if (cl.contains("DTEND")){
                start = LocalDateTime.parse());
            }

            if (cl.contains("BEGIN:VEVENT")){

                schedule.add(event);


            }


        }



    
        
        



    };

    private String get_data(String line) {

    }

    public static void main(String[] args) {
        String ical = EventProvider.getIcalString(args[0]);
        fromString(ical);
    }
}
