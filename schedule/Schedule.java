package schedule;

import schedule.providers.icsFile;

import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Schedule {

    String url;
    public ArrayList<Event> schedule;

    public Schedule(String url) { // enskild person Schedule

        this.url = url;

        // this.schedule = icsFile.fromString(url.toString());

        this.schedule = new ArrayList<Event>();

        // Week1
        schedule.add(new Event(LocalDateTime.of(2026, 11, 16, 8, 15), LocalDateTime.of(2026, 11, 16, 10, 0),
                "Objektorienterad programmering", "Föreläsning", new ArrayList<>(List.of("HA2"))));
        schedule.add(new Event(LocalDateTime.of(2026, 11, 16, 13, 15), LocalDateTime.of(2026, 11, 16, 15, 0),
                "Linjär algebra", "Övning", new ArrayList<>(List.of("EL41", "EL42"))));
        schedule.add(new Event(LocalDateTime.of(2026, 11, 18, 10, 0), LocalDateTime.of(2026, 11, 18, 11, 45),
                "Objektorienterad programmering", "Labb", new ArrayList<>(List.of("ED3354"))));
        schedule.add(new Event(LocalDateTime.of(2026, 11, 19, 8, 15), LocalDateTime.of(2026, 11, 19, 10, 0),
                "Linjär algebra", "Föreläsning", new ArrayList<>(List.of("HB1"))));
        schedule.add(new Event(LocalDateTime.of(2026, 11, 20, 13, 15), LocalDateTime.of(2026, 11, 20, 15, 0),
                "Objektorienterad programmering", "Handledning", new ArrayList<>()));

        short_Schedule();

    }

    public Schedule(ArrayList<ArrayList<Event>> grupp_Schedules) { // grupp Schedule

        this.schedule = new ArrayList<Event>();

        for (ArrayList<Event> grupp_Memer_Schedule : grupp_Schedules) {

            this.schedule.addAll(grupp_Memer_Schedule);

        }

        short_Schedule();

    }

    public ArrayList<Event> getSchedule() {
        return schedule;
    }

    public ArrayList<Event> getMetingTimes() {

        ArrayList<Event> meetingschedule = new ArrayList<Event>();

        LocalDateTime startingdey = this.schedule.get(0).getStart();

        for (Event e : this.schedule) {

        }

        return new ArrayList<Event>();

    }

    private void short_Schedule() {

        this.schedule.sort((a, b) -> a.getStart().compareTo(b.getStart()));

    }

    private void show_Schedule() {

        for (Event e : this.schedule) {

            e.show_event();

        }

    }

    public static void main(String[] args) {

        Schedule s = new Schedule("https://cloud.timeedit.net/chalmers/web/public/ri6y7YQQu1QZnZQ98Z45beZ35685Q.ics");

        s.show_Schedule();

    }

}
