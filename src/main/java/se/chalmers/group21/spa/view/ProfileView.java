package se.chalmers.group21.spa.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ProfileView extends VBox {
    private final Label usernameLabel = new Label();
    private final Label userIdLabel = new Label();
    private final Button logoutButton = new Button("log out");

    public ProfileView(){
        setSpacing(10);
        setPadding(new Insets(20));
        setAlignment(Pos.TOP_CENTER);

        getChildren().addAll(usernameLabel, userIdLabel, logoutButton);
    }

    public void setUsername(String username) {
        usernameLabel.setText("User: " + username);
    }

    public void setRole(String Id) {
        userIdLabel.setText("UserId: " + Id);
    }

    public Button getLogoutButton() {
        return logoutButton;
    }
}
