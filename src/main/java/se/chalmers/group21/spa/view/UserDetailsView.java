package se.chalmers.group21.spa.view;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;


public class UserDetailsView extends VBox{
    private Label nameLabel;
    private Label userIdLabel;


    public UserDetailsView() {
        createView();
    }

    private void createView() {
        setSpacing(10);
        setAlignment(Pos.CENTER);
        getStyleClass().add("profile-details");
        nameLabel = new Label();
        userIdLabel = new Label();

        nameLabel.getStyleClass().add("title");
        nameLabel.getStyleClass().add("accent-text");

        userIdLabel.getStyleClass().add("body-text");



        getChildren().addAll(nameLabel, userIdLabel);


    }
    public void setUserDetails(String username, String userId){
        Label text = new Label("User-ID: ");

        text.setStyle("\"-fx-font-size: 16px;\" +\n"
                + "    \"-fx-text-fill: black;\"");
        nameLabel.setText(username);
        userIdLabel.setText("User-ID: " + userId);
    }



}
