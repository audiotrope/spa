package schedule;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Week {

    public HashMap<String, ArrayList<Event>> events = new HashMap<>();

    public Week() {
        events.put("Monday", new ArrayList<>());
        events.put("Tuesday", new ArrayList<>());
        events.put("Wednesday", new ArrayList<>());
        events.put("Thursday", new ArrayList<>());
        events.put("Friday", new ArrayList<>());
    }

    public void add_Event(String day, LocalTime start, LocalTime slut, String eventName, String activity,
            ArrayList<String> locations, ArrayList<String> userData) {

        Event event = new Event(start, slut, eventName, activity, locations, userData);

        events.get(day).add(event);

    }

}