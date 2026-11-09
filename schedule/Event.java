package schedule;

import java.time.LocalTime;
import java.util.ArrayList;

public class Event {

    LocalTime start;
    LocalTime slut;
    String eventName;
    String activity;
    public ArrayList<ArrayList> locations = new ArrayList<ArrayList>();
    public ArrayList<ArrayList> userData = new ArrayList<ArrayList>();

    public Event(LocalTime start, LocalTime slut, String eventName, String activity,
            ArrayList<ArrayList> locations, ArrayList<ArrayList> userData) {

        this.start = start;
        this.slut = slut;
        this.eventName = eventName;
        this.activity = activity;
        this.locations = locations;
        this.userData = userData;

    }

    public Event(LocalTime start, LocalTime slut) {
        this(start, slut, null, null, null, null);
    }

    public LocalTime getStart() {
        return this.start;
    }

    public LocalTime getSlut() {
        return this.slut;
    }

}
