package schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
public class Event {

    LocalDateTime start;
    LocalDateTime slut;
    String eventName;
    String activity;
    public ArrayList<String> locations = new ArrayList<String>();
    public ArrayList<String> userData = new ArrayList<String>();

    public Event(LocalDateTime start, LocalDateTime slut, String eventName, String activity,
            ArrayList<String> locations, ArrayList<String> userData) {

        this.start = start;
        this.slut = slut;
        this.eventName = eventName;
        this.activity = activity;
        this.locations = locations;
        this.userData = userData;

    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getSlut() {
        return this.slut;
    }

}
