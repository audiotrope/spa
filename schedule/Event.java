package schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {

    public LocalDateTime start;
    public LocalDateTime slut;
    public String eventName;
    public String activity;
    public ArrayList<String> locations = new ArrayList<String>();

    public Event(LocalDateTime start, LocalDateTime slut, String eventName, String activity,
            ArrayList<String> locations) {

        this.start = start;
        this.slut = slut;
        this.eventName = eventName;
        this.activity = activity;
        this.locations = locations;

    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getSlut() {
        return this.slut;
    }

}
