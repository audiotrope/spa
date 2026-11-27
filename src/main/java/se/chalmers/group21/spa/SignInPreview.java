package se.chalmers.group21.spa;

import java.time.Instant;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import se.chalmers.group21.spa.controller.SignInController;
import se.chalmers.group21.spa.service.AuthService;
import se.chalmers.group21.spa.service.Session;
import se.chalmers.group21.spa.service.SignInException;
import se.chalmers.group21.spa.view.HeaderView;


// AI generated not meant to be used only for testing
public class SignInPreview {
    public static void main(String[] args) {
        Platform.startup(SignInPreview::show);
    }

    private static void show() {
        AuthService demoAuth = (email, password) -> {
            if (!email.equals("demo@example.com") || !password.equals("demo")) {
                throw new SignInException(400);
            }
            return new Session("demo-access", "demo-refresh", "demo-user",
                    email, Instant.now().plusSeconds(3600));
        };

        BorderPane root = new BorderPane();
        root.setTop(new HeaderView());

        Stage stage = new Stage();
        SignInController controller = new SignInController(demoAuth, session -> {
            Label message = new Label("Signed in as " + session.userEmail());
            stage.getScene().setRoot(new StackPane(message));
        });
        stage.setTitle("Sign-in preview");
        root.setCenter(controller.getView());
        stage.setScene(new Scene(root, 1000, 620));
        stage.show();
    }
}
