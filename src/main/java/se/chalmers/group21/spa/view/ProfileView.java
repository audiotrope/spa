package se.chalmers.group21.spa.view;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ProfileView {

    private VBox root;
    private Button profileButton;
    private UserDetailsView userDetails;
    private GroupsView groupsView;
    private AboutView aboutView;

    public ProfileView() {
        createView();

    }

    private void createView() {
        userDetails = new UserDetailsView();
        groupsView = new GroupsView();
        aboutView = new AboutView();
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
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



        //userDetails = createBox("red");

        //VBox options = new VBox();
        //    options.getStyleClass().add("sub-category2");

        VBox navigation = new VBox();
            navigation.getStyleClass().add("sub-category1");

        //VBox aboutBox = new VBox();
        //    aboutBox.getStyleClass().add("sub-category1");

        grid.add(userDetails, 0, 0, 1, 2);
        grid.add(groupsView, 1, 0, 2, 1);
        grid.add(aboutView, 1, 1);
        grid.add(navigation, 2, 1);

        root.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

        root.getChildren().add(grid);
        //VBox.setVgrow(grid, Priority.ALWAYS);
        grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    }

    public VBox getView() {
        return root;
    }


    public void setUserDetailsView(VBox view){
        userDetails.getChildren().add(view);
        //view.setVisible(true);
        //view.setManaged(true);
        //view.setMinSize(300, 200);
    }
    public UserDetailsView getUserDetailsView() {
        return userDetails;
    }

    public GroupsView getGroupsView(){
        return groupsView;
    }
    public String getStylesheet() {
        return getClass()
                .getResource("/css/theme.css")
                .toExternalForm();
    }
}