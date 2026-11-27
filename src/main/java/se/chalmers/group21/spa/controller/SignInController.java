package se.chalmers.group21.spa.controller;

import javafx.concurrent.Task;
import se.chalmers.group21.spa.service.AuthService;
import se.chalmers.group21.spa.service.Session;
import se.chalmers.group21.spa.view.SignInView;

import java.util.function.Consumer;

public class SignInController {
    private final AuthService authService;
    private final Consumer<Session> onSuccess;
    private final SignInView view;

    public SignInController(AuthService authService, Consumer<Session> onSuccess) {
        this.authService = authService;
        this.onSuccess = onSuccess;
        this.view = new SignInView(this::signIn);
    }

    public SignInView getView() {
        return view;
    }

    public void signIn(String email, String password) {
        view.setDisable(true); // Make it impossible to do a second sign in when a sign in is already in progress

        // Run the sign in on another thread so the app does not freeze
        Task<Session> request = new Task<>() {
            @Override
            protected Session call() throws Exception {
                return authService.signIn(email, password);
            }
        };

        request.setOnSucceeded(event -> {
            view.setDisable(false);
            onSuccess.accept(request.getValue());
        });

        request.setOnFailed(event -> {
            view.setDisable(false);
            view.showError("Could not log in");
        });

        Thread.startVirtualThread(request);
    }
}
