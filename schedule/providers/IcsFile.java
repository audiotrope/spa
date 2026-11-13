package schedule.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import schedule.Event;
import schedule.providers.EventProvider;
import schedule.providers.IcsReader;
import java.time.LocalDateTime;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;

// Parser for ics files
public class IcsFile {

    static final String SPLIT = "\r\n ";

    private static void print (String s) {
        System.out.println(s);
    }
    
    public static ArrayList<Event> scheduleFromString(String str) {
        ArrayList<Event> schedule = new ArrayList<Event>();
        
        String contentLine = str.replaceAll(SPLIT,"");
        //contentLine = contentLine.
        contentLine = contentLine.replaceAll("DTSTART",";DTSTART");

        

        

        // This regex unfolds ics per specification: CRLF + whitespace or tab
        String[] contentLines = contentLine.replace("\r\n( |\t)","").split(";");
        
        
        for (String cl : contentLines) {
            System.out.println();
            LocalDateTime start;
            LocalDateTime slut;
            String eventName;
            String activity;
            ArrayList<String> locations = new ArrayList<String>();
            
            
            //System.out.println(cl);
            //System.out.println();
            /*
            if (cl.contains("DTSTART")){
                start = LocalDateTime.parse(cl.replace("DTSTART:", ""));
                }
            if (cl.contains("DTEND")){
                start = LocalDateTime.parse());
            }

            */


        }
        return schedule; 



    };
    private ArrayList<Event> parseStream(Reader reader){
        IcsReader icsReader = new IcsReader(reader);
        ArrayList<Event> schedule = new ArrayList<Event>();
        
    }

    public static void main(String[] args) {
        File f = new File("timeedit.ics");
        ArrayList<String> sl = new ArrayList<String>();
        try (Scanner myReader = new Scanner(f)) {
            
            while(myReader.hasNextLine()){
                sl.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        fromString();
    }
}