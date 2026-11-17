package service;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.function.IntFunction;

// TODO: Start using the keys from supabase.properties instead of environment variables
// TODO: Handle token refresh
// TODO: Handle sign out

///  Make this service agnostic by implemnting an AuthInterface which the Supabase class can implement


public class SupabaseAuthService implements AuthService{
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final Gson gson = new Gson();
    private final URI signInUri;
    private final URI refreshUri;
    private final String publishableKey;

    // For reference see: https://supabase.github.io/auth/#post-token

    /**
     * Initializes the Supabase authentication service
     * using environment variables.
     */
    public SupabaseAuthService() {
        String projectUrl = requireEnv("SUPABASE_URL").replaceFirst("/+$", "");
        signInUri = URI.create(projectUrl + "/auth/v1/token?grant_type=password");
        refreshUri = URI.create(projectUrl + "/auth/v1/token?grant_type=refresh_token");
        publishableKey = requireEnv("SUPABASE_PUBLISHABLE_KEY");
    }

    @Override
    public Session signIn(String email, String password)
        throws IOException, InterruptedException, SignInException {

        String response = callApi(signInUri, new Credentials(email, password), SignInException::new);

        return getUserSession(response);
    }

    public Session renewSession(String refresh_token)
            throws IOException, InterruptedException, RefreshTokenException {
        String response = callApi(refreshUri, Map.of("refresh_token", refresh_token), RefreshTokenException::new);

        return getUserSession(response);
    }

    private Session getUserSession(String response) throws IOException{
        TokenResponse tokens;
        try {
            tokens = gson.fromJson(response, TokenResponse.class);
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
                tokens.user().email(),
                Instant.now().plusSeconds(tokens.expiresIn()));
    }

    private <E extends Exception> String callApi(URI endpoint, Object Content, IntFunction<E> exceptionFactory)
            throws IOException, InterruptedException, E {
        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .timeout(Duration.ofSeconds(10))
                .header("apikey", publishableKey) // Why is this spelled like this? I don't know
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        gson.toJson(Content)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw exceptionFactory.apply(response.statusCode());
        }

        return response.body();
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

    private record User(String id, String email) {}

    public static final class RefreshTokenException extends Exception {
        private final int statusCode;

        public RefreshTokenException(int statusCode) {
            super("Supabase sign in failed with HTTP: " + statusCode);
            this.statusCode = statusCode;
        }

        public int statusCode() {
            return statusCode;
        }
    }
}
