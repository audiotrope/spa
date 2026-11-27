package se.chalmers.group21.spa.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.function.BiConsumer;

public class SignInView extends VBox {
    private Label errorLabel = new Label();

    public SignInView(BiConsumer<String, String> onSignIn) {
        // Load css
        getStylesheets().add(SignInView.class.getResource("/signin.css").toExternalForm());
        getStyleClass().add("sign-in");



        Label title = new Label("Welcome to SPA, the Student Planning App");
        title.getStyleClass().add("welcome-title");

        TextField email = new TextField();
        email.setPromptText("Email address");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button signIn = new Button("Sign in");
        signIn.getStyleClass().add("login-button");
        signIn.setDefaultButton(true);
        signIn.disableProperty().bind(
                email.textProperty().isEmpty().or(passwordField.textProperty().isEmpty())
        );

        signIn.setOnAction(event -> {
           errorLabel.setText("");
           onSignIn.accept(email.getText().strip(), passwordField.getText());
        });

        errorLabel.setWrapText(true);
        errorLabel.getStyleClass().add("error-label");
        errorLabel.managedProperty().bind(errorLabel.visibleProperty());

        VBox form = new VBox(email, passwordField, signIn, errorLabel);
        VBox.setMargin(signIn, new Insets(8, 0, 0, 0));
        form.getStyleClass().add("login-form");

        setAlignment(Pos.CENTER);
        getChildren().addAll(title, form);
    }

    public void showError(String message) {
        errorLabel.setText(message);
    }
}
