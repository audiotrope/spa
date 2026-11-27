package se.chalmers.group21.spa.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;


public class HeaderView extends HBox {
    public HeaderView() {
        getStylesheets().add(HeaderView.class.getResource("/header.css").toExternalForm());
        getStyleClass().add("header");

        Label title = new Label("SPA");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        setPadding(new Insets(0, 20, 0, 20));

        Label userLabel = new Label("Profile"); // Replace with a profile icon

        getChildren().addAll(title, spacer, userLabel);
    }
}
