package se.chalmers.group21.spa.model.schedule;

import java.util.ArrayList;
import java.util.UUID;

public interface Schedule {

    UUID getId();

    ArrayList<Event> getSchedule();

    void addEvent(Event event);

}