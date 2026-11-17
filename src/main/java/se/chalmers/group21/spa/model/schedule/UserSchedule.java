package se.chalmers.group21.spa.model.schedule;

import java.util.ArrayList;
import java.util.UUID;

public class UserSchedule implements Schedule {

    String url;
    public ArrayList<Event> schedule;
    private final UUID id;

    public UserSchedule(UUID id, String url) { // enskild person Schedule

        // ends with ics

        this.id = id;
        this.url = url;

        // this.schedule = icsFile.fromString(url.toString());

        this.schedule = new ArrayList<Event>();

    }

    public UUID getId() {
        return id;
    }

    public ArrayList<Event> getSchedule() {
        return schedule;
    }

    public void addEvent(Event event) {

        this.schedule.add(event);

        short_Schedule();

    }

    public void short_Schedule() {

        this.schedule.sort((a, b) -> a.getStart().compareTo(b.getStart())); // Ai generated line

    }

    public void show_Schedule() {

        for (Event e : this.schedule) {

            e.show_event();

        }

    }

    // public static void main(String[] args) {

    // UserSchedule s = new UserSchedule(
    // "https://cloud.timeedit.net/chalmers/web/public/ri6y7YQQu1QZnZQ98Z45beZ35685Q.ics");

    // s.show_Schedule();

    // }

}