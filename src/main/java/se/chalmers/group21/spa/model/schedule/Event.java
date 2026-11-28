package se.chalmers.group21.spa.model.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Event {
    private final UUID id;
    public LocalDateTime start;
    public LocalDateTime end;
    public String eventName;
    public String activity;
    public ArrayList<String> locations = new ArrayList<String>();

    public Event(LocalDateTime start, LocalDateTime end, String eventName, String activity,
            ArrayList<String> locations) {

        this(UUID.randomUUID(), start, end, eventName, activity, locations);

    }

    public Event(UUID id, LocalDateTime start, LocalDateTime end, String eventName, String activity,
            List<String> locations) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.eventName = eventName;
        this.activity = activity;
        this.locations = (locations != null) ? new ArrayList<>(locations) : new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getEventName() {
        return eventName;
    }

    public String getActivity() {
        return activity;
    }

    public List<LocalTime> getEventTimespan() {
        return List.of(start.toLocalTime(), end.toLocalTime());
    }

    public List<String> getLocations() {
        return List.copyOf(locations);
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
