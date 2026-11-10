package schedule.providers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Builder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.time.Duration;
import schedule.Event;
import java.io.Console;
import java.io.IOException;

public class EventProvider {
    public static String getIcalString(String link) {
        Builder builder = HttpClient.newBuilder();
        HttpClient client = HttpClient.newHttpClient();
        BodyHandler<String> handler = BodyHandlers.ofString();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "text/calendar")
                .header("charset", "UTF-8")
                .GET()
                .build();
        String icalString;
        HttpResponse<String> response;
        try {
            response = client.send(request, handler);
            icalString = response.body();
            return icalString;
        } catch (IOException e) {
            System.err.println(e);
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        return null;
    }

    public static Event fromIcal(String link) {
        Builder builder = HttpClient.newBuilder();
        HttpClient client = HttpClient.newHttpClient();
        BodyHandler<String> handler = BodyHandlers.ofString();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "text/calendar")
                .header("charset", "UTF-8")
                .GET()
                .build();
        String icalString;
        HttpResponse<String> response;
        try {
            response = client.send(request, handler);
            icalString = response.body();
        } catch (IOException e) {
            System.err.println(e);
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        return new Event(null, null, null, null, null);

    }

    public static void main(String[] args) {
        Event e = EventProvider.fromIcal(
                "https://cloud.timeedit.net/chalmers/web/public/ri6y6YQ0u1QZnnQ9Zk451163518kQ8802b9Zd5eQtZ7Q363Z32937Fl08576CnA31493t1DDB835Q087E181A8993Z6F.ics");

    }
}
