package se.chalmers.group21.spa.model.schedule;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import se.chalmers.group21.spa.model.schedule.providers.IcsImporter;

public class UserSchedule implements Schedule {

    // skapa en egen tebel där user har en huser tebel.
    // events ska ha it egen tabel i data basen
    // event ska koplas till scheduel som har sina eget id
    // sceduel id ska kolas till ett user/grupp id.
    // bord, tasklist och task.

    // får upp till databasen
    // userReposetory find user

    private final UUID id;
    private final UUID userId;
    private final ArrayList<Event> schedule;
    private final IcsImporter icsImporter;

    public UserSchedule(UUID userId) {
        this(UUID.randomUUID(), userId, new ArrayList<>());
    }

    public UserSchedule(UUID id, UUID userId, List<Event> events) {
        this.id = id;
        this.userId = userId;
        this.icsImporter = new IcsImporter();
        this.schedule = (events != null) ? new ArrayList<>(events) : new ArrayList<>(); // Ai generated line
        sortSchedule();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public ArrayList<Event> getSchedule() {
        return schedule;
    }

    public void addEvent(Event event) {

        this.schedule.add(event);

        sortSchedule();

    }

    public void addIcs(String url) {
        // ArrayList<Event> newEvents = icsImporter.fromUrl(url);
        // addEvents(newEvents);
    }

    public void addEvents(ArrayList<Event> events) {
        schedule.addAll(events);
        sortSchedule();
    }

    public void sortSchedule() {

        this.schedule.sort((a, b) -> a.getStart().compareTo(b.getStart())); // Ai generated line

    }

    public void show_Schedule() {

        for (Event e : this.schedule) {

            e.show_event();

        }

    }

    // public static void main(String[] args) {

    // UUID uuid = new UUID(0, 0);

    // UserSchedule s = new UserSchedule(uuid,
    // "https://cloud.timeedit.net/chalmers/web/public/ri6y7YQQu1QZnZQ98Z45beZ35685Q.ics");

    // s.show_Schedule();

    // }

}