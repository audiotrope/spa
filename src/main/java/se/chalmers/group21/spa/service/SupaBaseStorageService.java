package se.chalmers.group21.spa.service;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

///  Make this service agnostic by implemnting an AuthInterface which the Supabase class can implement


public class SupaBaseStorageService {
    private static final String BUCKET = "private-files";
    private static final int PAGE_SIZE = 100;
    private final Gson gson = new Gson();
    private final HttpClient client = HttpClient.newHttpClient();
    private final String url = System.getenv("SUPABASE_URL");
    private final String apiKey = System.getenv("SUPABASE_PUBLISHABLE_KEY");

    public HttpResponse<String> upload(
            Path file,
            String userJwt,
            String userId
    ) throws Exception {
        String filename = URLEncoder.encode(file.getFileName().toString(), StandardCharsets.UTF_8)
                .replace("+", "%20"); // Treat spaces correctly
        String objectKey = "users/" + userId + "/" + UUID.randomUUID() + "/" + filename;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/storage/v1/object/"
                        + BUCKET + "/" + objectKey))
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

    public List<StoredFile> listFiles(String userJwt, String userId)
            throws IOException, InterruptedException {
        var files = new ArrayList<StoredFile>();
        var folders = new ArrayDeque<String>();
        folders.add("users/" + userId);

        while (!folders.isEmpty()) {
            String prefix = folders.remove();
            int offset = 0;
            ListEntry[] page;
            do {
                page = listPage(userJwt, prefix, offset);
                for (ListEntry entry : page) {
                    if (entry == null || entry.name() == null || entry.name().isBlank()) {
                        throw new IOException("Supabase returned an incomplete storage entry.");
                    }
                    String objectKey = prefix + "/" + entry.name();
                    if (entry.id() == null) {
                        folders.add(objectKey);
                    } else {
                        files.add(new StoredFile(entry.name(), objectKey));
                    }
                }
                offset += page.length;
            } while (page.length == PAGE_SIZE);
        }

        return List.copyOf(files);
    }

    private ListEntry[] listPage(String userJwt, String prefix, int offset)
            throws IOException, InterruptedException {
        var body = new ListRequest(prefix, PAGE_SIZE, offset, new SortBy("name", "asc"));
        var request = HttpRequest.newBuilder(URI.create(url + "/storage/v1/object/list/" + BUCKET))
                .timeout(Duration.ofSeconds(10))
                .header("apikey", apiKey)
                .header("Authorization", "Bearer " + userJwt)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(body)))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Supabase file listing failed with HTTP " + response.statusCode());
        }

        ListEntry[] entries;
        try {
            entries = gson.fromJson(response.body(), ListEntry[].class);
        } catch (JsonParseException e) {
            throw new IOException("Supabase returned an invalid file listing.", e);
        }
        if (entries == null) {
            throw new IOException("Supabase returned an empty file listing response.");
        }
        return entries;
    }

    public record StoredFile(String name, String objectKey) {}

    private record ListEntry(String id, String name) {}

    private record ListRequest(String prefix, int limit, int offset, SortBy sortBy) {}

    private record SortBy(String column, String order) {}
}
