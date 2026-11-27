package se.chalmers.group21.spa.model.schedule.providers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import se.chalmers.group21.spa.model.schedule.Event;
public class IcsParser {


  protected ArrayList<Event> parseStream(Reader reader) throws IOException {
    ArrayList<Event> schedule = new ArrayList<Event>();

    try (IcsReader icsReader = new IcsReader(reader)) {
      String line;
      while ((line = icsReader.readLogicalLine()) != null) {
        if (line.equals("BEGIN:VEVENT")) {
          Event event = parseVEvent(icsReader); // icsReader positioned after BEGIN:VEVENT
          if (event != null) schedule.add(event);
        } 
      }
    }
    return schedule;
  }

  //Parsing vevent component of calendar
  private Event parseVEvent(IcsReader r) throws IOException {
    Map<String, String> map = parseComponentProperties(r, "VEVENT");
    if (map.isEmpty()) return null;
    ArrayList<String> locations = new ArrayList<String>();
    locations.add(map.get("LOCATION"));
    return new Event(
        parseIcsDate(map.get("DTSTART")),
        parseIcsDate(map.get("DTEND")),
        map.get("SUMMARY"),
        map.get("DESCRIPTION"),
        locations
        );
  }


  private Map<String, String> parseComponentProperties(IcsReader r, String componentName)
      throws IOException {
    Map<String, String> map = new HashMap<>();
    String line;
    String endTag = "END:" + componentName;

    while ((line = r.readLogicalLine()) != null) {
      if (line.equals(endTag)) {
        break;
      }

      String[] parts = line.split(":", 2);
      if (parts[0].startsWith("LOCATION")) {
        parts = line.split("Lokalnamn:", 2); 
        map.put(parts[0], parts[1]);
      } else if (parts.length == 2) {
        parts[0] = parts[0].split(";")[0]; 
        map.put(parts[0], parts[1]);
      }
    }
    return map;
  }

  private LocalDateTime parseIcsDate(String s) {
    if (s == null || s.isEmpty()) return null;
    try {
        String cleanDate = s.replace("Z", "");
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss").withZone(ZoneId.of("UTC"));
        return LocalDateTime.from(f.parse(cleanDate));
    } catch (Exception e) {
      System.err.println("Error parsing date : " + s);
      return null;
    }
  }

  public ArrayList<Event> parseUrl(String urlString){
        try { 
      URL url = URI.create(urlString).toURL();
      URLConnection connection = url.openConnection();
      try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
        return parseStream(reader); 
      }
    } catch (IOException e) {
      System.err.println("Error downloading .ics file : " + e.getMessage());
      return new ArrayList<Event>();
    }
  };
}