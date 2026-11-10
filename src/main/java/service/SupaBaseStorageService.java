package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.UUID;

public class SupaBaseStorageService {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String url = System.getenv("SUPABASE_URL");
    private final String apiKey = System.getenv("SUPABASE_PUBLISHABLE_KEY");

    public HttpResponse<String> upload(
            Path file,
            String userJwt,
            String userId
    ) throws Exception {
        String objectKey = "users/" + userId + "/" + UUID.randomUUID();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/storage/v1/object/"
                        + "private-files/" + objectKey))
                .header("apiKey", apiKey)
                .header("Authorization", "Bearer " + userJwt)
                .header("Content-Type", "application/octet-stream")
                .POST(HttpRequest.BodyPublishers.ofFile(file))
                .build();

        return client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
    }
}
