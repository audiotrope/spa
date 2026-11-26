package se.chalmers.group21.spa.view;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ProfileView {

    private VBox root;
    private Button profileButton;

    public ProfileView() {
        createView();
    }

    private void createView() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setPadding(new Insets(10));
        root = new VBox(20);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(58);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(21);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(21);

        grid.getColumnConstraints().addAll(col1, col2, col3);


        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(45);

        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(55);

        grid.getRowConstraints().addAll(row1, row2);



        VBox profileView = createBox("red");
        VBox options = createBox("blue");
        VBox navigation = createBox("blue");
        VBox aboutBox = createBox("black");

        grid.add(profileView, 0, 0, 1, 2);
        grid.add(options, 1, 0, 2, 1);
        grid.add(aboutBox, 1, 1);
        grid.add(navigation, 2, 1);

        root.setPrefSize(1000, 800);

        root.getChildren().add(grid);
        VBox.setVgrow(grid, Priority.ALWAYS);
        grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    }

    public VBox getView() {
        return root;
    }

    public Button getProfileButton() {
        return profileButton;
    }

    private VBox createBox(String color) {
        VBox box = new VBox();
        box.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        box.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-background-radius: 25;"
        );

        return box;
    }
}