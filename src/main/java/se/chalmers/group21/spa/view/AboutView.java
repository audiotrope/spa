package se.chalmers.group21.spa.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AboutView extends VBox {
    private Label title;
    private Label information;

    public AboutView(){
        createView();
    }

    public void createView(){
        setSpacing(10);
        setAlignment(Pos.TOP_CENTER);
        getStyleClass().add("sub-category1");

        title = new Label("About us");
        title.getStyleClass().add("title-dark");

        //Here we can change what it says under the "about us" title
        information = new Label(
                "This is the best group project app with the best views");
        information.setWrapText(true);
        getChildren().addAll(title,information);


    }
}
