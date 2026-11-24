package se.chalmers.group21.spa.service;

public final class SignInException extends Exception {
    private final int statusCode;

    public SignInException(int statusCode) {
        super("Supabase sign in failed with HTTP: " + statusCode);
        this.statusCode = statusCode;
    }

    public int statusCode() {
        return statusCode;
    }
}