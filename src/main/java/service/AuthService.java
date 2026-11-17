package service;

import java.io.IOException;

public interface AuthService {
    /**
     * Sign in to the supabase backendd with email and password
     * @param email the user's email
     * @param password the user's password
     * @return Returns a {@link(Session)} containing the access tokens
     * @throws IOException Returned when the server data is incorrect
     * @throws InterruptedException if the HTTP request is interupted
     * @throws SignInException if Supabase rejects the sign in request
     */
    Session signIn(String email, String password)
            throws IOException, InterruptedException, SignInException;
}
