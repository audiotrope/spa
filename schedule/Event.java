package schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {

    LocalDateTime start;
    LocalDateTime slut;
    String eventName;
    String activity;
    public ArrayList<ArrayList> locations = new ArrayList<ArrayList>();
    public ArrayList<ArrayList> userData = new ArrayList<ArrayList>();

    public Event(LocalDateTime start, LocalDateTime slut, String eventName, String activity,
            ArrayList<ArrayList> locations, ArrayList<ArrayList> userData) {

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
