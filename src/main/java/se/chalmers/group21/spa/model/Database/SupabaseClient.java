package se.chalmers.group21.spa.model.Database;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class SupabaseClient {
    private final String baseUrl;
    private final String apiKey;
    private final HttpClient client = HttpClient.newHttpClient();

    public SupabaseClient() {
        String url = SupabaseConfig.getUrl();
        String key = SupabaseConfig.getKey();
        if (url == null || key == null) {
            throw new IllegalStateException(
                    "SUPABASE_URL och SUPABASE_PUBLISHABLE_KEY måste vara satta");
        }
        this.baseUrl = url + "/rest/v1/";
        this.apiKey = key;
    }

    private HttpRequest.Builder request(String path) {
        return HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .timeout(Duration.ofSeconds(10))
                .header("apikey", apiKey)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json");
    }

    private String send(HttpRequest req) throws IOException, InterruptedException {
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() >= 300) {
            throw new IOException("Supabase-fel " + res.statusCode() + ": " + res.body());
        }
        return res.body();
    }

    public String get(String path) throws IOException, InterruptedException {
        return send(request(path).GET().build());
    }

    public String post(String table, String json) throws IOException, InterruptedException {
        return send(request(table).POST(HttpRequest.BodyPublishers.ofString(json)).build());
    }

    public String patch(String path, String json) throws IOException, InterruptedException {
        return send(request(path).method("PATCH", HttpRequest.BodyPublishers.ofString(json)).build());
    }

    public String delete(String path) throws IOException, InterruptedException {
        return send(request(path).DELETE().build());
    }
}
