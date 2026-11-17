package se.chalmers.group21.spa.model.schedule.providers;

import java.io.Reader;
import java.util.ArrayList;

import se.chalmers.group21.spa.model.schedule.Event;
import se.chalmers.group21.spa.model.schedule.ScheduleImport;

public class IcsImporter implements ScheduleImport {

    @Override
    public ArrayList<Event> fromUrl(String url) {
        IcsParser p = new IcsParser();
        return p.parseUrl(url);
    }

    @Override
    public ArrayList<Event> fromReader(Reader reader) {
        IcsParser p = new IcsParser();
        ArrayList<Event> schedule = null ;
        try {
            schedule = p.parseStream(reader);
        } catch (Exception e) {
            System.err.print("Error parsing Ics file: {e}");
        }
        return schedule;
    }
    
}
