package se.chalmers.group21.spa.model.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Event {

    public LocalDateTime start;
    public LocalDateTime end;
    public String eventName;
    public String activity;
    public ArrayList<String> locations = new ArrayList<String>();

    public Event(LocalDateTime start, LocalDateTime end, String eventName, String activity,
            ArrayList<String> locations) {

        this.start = start;
        this.end = end;
        this.eventName = eventName;
        this.activity = activity;
        this.locations = locations;
        

    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public String getEventName() {
        return eventName;
    }

    public ArrayList<LocalTime> get_Event_Timespan() {

        return new ArrayList<>(List.of(this.start.toLocalTime(), this.end.toLocalTime()));
    }

    public void show_event() {

        System.err.println(this.start.toLocalTime() + " - " + this.end.toLocalTime());
        System.err.println(eventName);
        System.err.println(activity);
        System.err.println(locations);

    }

}
