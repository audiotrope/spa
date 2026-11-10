package schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import schedule.providers.EventProvider;

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

    public String getEventName() {
        return eventName;
    }

    public ArrayList<LocalTime> get_Event_Timespan() {

        return new ArrayList<>(List.of(this.start.toLocalTime(), this.slut.toLocalTime()));
    }

    public void show_event() {

        System.err.println(this.start.toLocalTime() + " - " + this.slut.toLocalTime());
        System.err.println(eventName);
        System.err.println(activity);
        System.err.println(locations);

    }

}
