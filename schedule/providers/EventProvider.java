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
    public static Event fromIcal(String link) {
        Builder builder = HttpClient.newBuilder();
        HttpClient client = HttpClient.newHttpClient();
        BodyHandler<String> handler = BodyHandlers.ofString();
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(link))
        .timeout(Duration.ofMinutes(2))
        .header("Content-Type", "text/calendar")
        .header("charset","UTF-8")
        .GET()
        .build();
        
        HttpResponse<String> response;
        try {
            response = client.send(request,handler);
            System.err.println(response);
            return Event(response.body());
        } catch (IOException e) {
            throw e;
        } catch (InterruptedException e) {
            throw e;
        }
        

    }
    public static void main(String[] args) {
        EventProvider.fromIcal("2sds");
        
    }
}
