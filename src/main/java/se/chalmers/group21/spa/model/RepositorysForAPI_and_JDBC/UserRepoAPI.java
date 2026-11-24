package se.chalmers.group21.spa.model.RepositorysForAPI_and_JDBC;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import se.chalmers.group21.spa.model.Database.SupabaseClient;
import se.chalmers.group21.spa.model.Interfaces.UserRepository;
import se.chalmers.group21.spa.model.User.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class UserRepoAPI implements UserRepository {
    private final SupabaseClient supabase;
    private final Gson gson = new Gson();

    public UserRepoAPI(SupabaseClient supabase) {
        this.supabase = supabase;
    }

    @Override
    public void saveUser(User user) {
        String json = gson.toJson(Map.of(
                "id", user.getUserId().toString(),
                "username", user.username));
        try {
            supabase.post("users", json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserById(UUID userId) {
        return findUser("users?id=eq." + userId + "&select=id,username");
    }

    @Override
    public User findByUsername(String username) {
        String encoded = URLEncoder.encode(username, StandardCharsets.UTF_8);
        return findUser("users?username=eq." + encoded + "&select=id,username");
    }


    private User findUser(String path) {
        try {
            String body = supabase.get(path);
            JsonArray rows = JsonParser.parseString(body).getAsJsonArray();
            if (rows.isEmpty()) {
                return null;
            }
            JsonObject row = rows.get(0).getAsJsonObject();
            return new User(
                    UUID.fromString(row.get("id").getAsString()),
                    row.get("username").getAsString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}