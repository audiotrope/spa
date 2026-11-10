package service;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;

// TODO: Start using the keys from supabase.properties instead of environment variables
// TODO: Handle token refresh
// TODO: Handle sign out

public class SupabaseAuthService {
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final Gson gson = new Gson();
    private final URI signInUri;
    private final String publishableKey;

    // For reference see: https://supabase.github.io/auth/#post-token

    /**
     * Initializes the Supabase authentication service
     * using environment variables.
     */
    public SupabaseAuthService() {
        String projectUrl = requireEnv("SUPABASE_URL").replaceFirst("/+$", "");
        signInUri = URI.create(projectUrl + "/auth/v1/token?grant_type=password");
        publishableKey = requireEnv("SUPABASE_PUBLISHABLE_KEY");
    }

    /**
     * Sign in to the supabase backendd with email and password
     * @param email the user's email
     * @param password the user's password
     * @return Returns a {@link(Session)} containing the access tokens
     * @throws IOException Returned when the server data is incorrect
     * @throws InterruptedException if the HTTP request is interupted
     * @throws SignInException if Supabase rejects the sign in request
     */
    public Session signIn(String email, String password)
        throws IOException, InterruptedException, SignInException {
        HttpRequest request = HttpRequest.newBuilder(signInUri)
                .timeout(Duration.ofSeconds(10))
                .header("apikey", publishableKey) // Why is this spelled like this? I don't know
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        gson.toJson(new Credentials(email, password))))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new SignInException(response.statusCode());
        }

        TokenResponse tokens;
        try {
            tokens = gson.fromJson(response.body(), TokenResponse.class);
        } catch (JsonParseException e) {
            throw new IOException("Supabase returned an invalid sign in response.");
        }

        if (tokens == null ||
                tokens.accessToken() == null || tokens.accessToken().isBlank() ||
                tokens.refreshToken() == null || tokens.refreshToken().isBlank() ||
                tokens.user() == null ||
                tokens.user().id() == null ||
                tokens.expiresIn() <= 0) {
            throw new IOException("Supabase returned an incomplete sign in response.");
        }

        return new Session(
                tokens.accessToken(),
                tokens.refreshToken(),
                tokens.user().id(),
                Instant.now().plusSeconds(tokens.expiresIn()));
    }

    ///  Fancy environemnet variable getter
    private static String requireEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing environement variable!!");
        }
        return value;
    }

    private record Credentials(String email, String password) {}

    // Again see https://supabase.github.io/auth/#post-token, also https://supabase.com/docs/guides/auth/passwords?queryGroups=flow&flow=implicit&queryGroups=language&language=http
    // was a little help
    private record TokenResponse(
            @SerializedName("access_token") String accessToken,
            @SerializedName("refresh_token") String refreshToken,
            @SerializedName("expires_in") long expiresIn,
            User user) {}

    private record User(String id) {}

    /**
     * User session
     * @param accessToken User access token
     * @param refreshToken User refresh token
     * @param userId User id
     * @param expiresAt When the access token expires
     */
    public record Session(String accessToken, String refreshToken, String userId, Instant expiresAt) {}

    public static final class SignInException extends Exception {
        private final int statusCode;

        public SignInException(int statusCode) {
            super("Supabase sign in failed with HTTP: " + statusCode);
            this.statusCode = statusCode;
        }

        public int statusCode() {
            return statusCode;
        }
    }
}
