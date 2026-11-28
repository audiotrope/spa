package se.chalmers.group21.spa.model.schedule;

import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class GroupSchedule implements Schedule {

    private ArrayList<Event> schedule;

    private final UUID id;
    private final UUID groupId;

    public GroupSchedule(UUID groupId, ArrayList<ArrayList<Event>> grupp_Schedules) { // ÄNDRAT: ny grupp, tar bort
                                                                                      // oanvänt id
        this(UUID.randomUUID(), groupId, grupp_Schedules);
    }

    public GroupSchedule(UUID id, UUID groupId, ArrayList<ArrayList<Event>> grupp_Schedules) { // grupp Schedule

        this.schedule = new ArrayList<Event>();
        this.id = id;
        this.groupId = groupId;

        for (ArrayList<Event> grupp_Memer_Schedule : grupp_Schedules) {

            this.schedule.addAll(grupp_Memer_Schedule);

        }

        sortSchedule();

    }

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public ArrayList<Event> getSchedule() {
        return schedule;
    }

    public ArrayList<Event> getMetingTimes() {

        ArrayList<Event> meetingschedule = new ArrayList<Event>();

        LocalDateTime beforeEnd = null;

        for (Event e : this.schedule) {

            if (e.getStart().getDayOfWeek() == DayOfWeek.SATURDAY || e.getStart().getDayOfWeek() == DayOfWeek.SUNDAY) {

                continue;
            }

            LocalDate day = e.getStart().toLocalDate();

            // kollar första och om vi har bytt dag
            if (beforeEnd == null || !beforeEnd.toLocalDate().equals(day)) {

                if (beforeEnd != null) {
                    LocalDateTime dayEnd = beforeEnd.toLocalDate().atTime(17, 0);
                    if (beforeEnd.isBefore(dayEnd)) { // kollar att inte båda är 17:00
                        meetingschedule.add(new Event(beforeEnd, dayEnd, null, null, null));
                    }
                }

                beforeEnd = day.atTime(8, 0);

            }

            if (e.getStart().isAfter(beforeEnd)) { // kollar om vi börjar 08:00
                meetingschedule.add(new Event(beforeEnd, e.getStart(), null, null, null));
            }

            if (e.getEnd().isAfter(beforeEnd)) {// flyttar fram dagtiden.
                beforeEnd = e.getEnd();
            }

        }

        // ÄNDRAT: lägger till sista dagens tid fram till 17:00
        if (beforeEnd != null) {
            LocalDateTime dayEnd = beforeEnd.toLocalDate().atTime(17, 0);
            if (beforeEnd.isBefore(dayEnd)) {
                meetingschedule.add(new Event(beforeEnd, dayEnd, null, null, null));
            }
        }

        return meetingschedule;
    }

    public void addEvent(Event event) {

        this.schedule.add(event);

        sortSchedule();

    }

    public void sortSchedule() {

        this.schedule.sort((a, b) -> a.getStart().compareTo(b.getStart())); // Ai generated line

    }

    public void show_MetingTimes() {

        for (Event e : this.getMetingTimes()) {

            e.show_event();

        }

    }

    public static void main(String[] args) {

    }

}