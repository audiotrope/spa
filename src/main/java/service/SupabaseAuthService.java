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
    private Session session;

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
    public synchronized Session signIn(String email, String password)
        throws IOException, InterruptedException, SignInException {

        String response = callApi(signInUri, new Credentials(email, password), SignInException::new);

        session = getUserSession(response);
        return session;
    }

    /**
     * Renew the user session
     * @param refresh_token token that is supplied in the user object
     * @return A new user session with a new access token
     * @throws IOException
     * @throws InterruptedException
     * @throws RefreshTokenException
     */
    public Session renewSession(String refresh_token)
            throws IOException, InterruptedException, RefreshTokenException {
        String response = callApi(refreshUri, Map.of("refresh_token", refresh_token), RefreshTokenException::new);

        return getUserSession(response);
    }

    /**
     * Gets the user session
     * @param response The supabase response that should include the user object
     * @return Session containing the user object and the access tokens
     * @throws IOException If the supplied response is incorrect or malfomred
     */
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

    /**
     * Sends a JSON POST request to a Supabase Auth endpoint.
     * @param <E> the exception type created for a non-200 response
     * @param endpoint the URI to send the request to
     * @param Content the object to serialize as the JSON request body
     * @param exceptionFactory a function that creates an exception from the HTTP status code
     * @return the response body when the status is HTTP 200
     * @throws IOException if sending the request or reading the response fails
     * @throws InterruptedException if the thread is interrupted while waiting for the response
     * @throws E if Supabase returns a status other than HTTP 200
    */
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

    /**
     * Gets the user access token, checks beforehand if its expired, if it is renew it.
     * @return The access token
     * @throws IOException
     * @throws InterruptedException
     * @throws RefreshTokenException
     */
    private String getAcessToken() throws IOException, InterruptedException, RefreshTokenException {
        if (session == null) {
            throw new IllegalStateException("You need to be logged in before refreshing a session");
        }

        if (!session.expiresAt().isAfter(Instant.now().minusSeconds(30))) {
            session = renewSession(session.refreshToken());
        }

        return session.accessToken();
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
