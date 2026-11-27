package se.chalmers.group21.spa.model.schedule;

import java.io.Reader;
import java.util.ArrayList;

public interface ScheduleImport {
    public ArrayList<Event> fromUrl(String url);
    public ArrayList<Event> fromReader(Reader reader);
}
