package se.chalmers.group21.spa.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GroupsView extends VBox {
    private Label groupsLabel;
    private Label role;

    public GroupsView(){
        createView();
    }

    public void createView(){
        setSpacing(15);
        setAlignment(Pos.TOP_CENTER);
        getStyleClass().add("sub-category2");
        groupsLabel = new Label("Groups");
        groupsLabel.getStyleClass().add("title");
        groupsLabel.getStyleClass().add("accent-text-dark");


        getChildren().add(groupsLabel);
    }

    public void setGroupDetails(String groupName, String role){
        VBox groupBox = new VBox(5);
        Label group = new Label(groupName);
        Label roleLabel = new Label("Role: " + role);
        group.getStyleClass().add("subtitle");
        group.getStyleClass().add("accent-text-dark");

        roleLabel.getStyleClass().add("body-text");
        roleLabel.getStyleClass().add("accent-text-dark");

        groupBox.getChildren().addAll(group, roleLabel);


        getChildren().add(groupBox);
    }
}
