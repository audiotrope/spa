package se.chalmers.group21.spa;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import se.chalmers.group21.spa.controller.TaskCardController;
import se.chalmers.group21.spa.model.entities.Task;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class TaskCardPreviewApp extends Application {

    @Override
    public void start(Stage stage) {
        TaskCardController controller = new TaskCardController(null);

        Task task = Task.createGroup("Test", "Draft the introduction for the data structures report",
                LocalDate.now().plusDays(2), UUID.randomUUID(), UUID.randomUUID(),
                Set.of(UUID.randomUUID(), UUID.randomUUID()));

        StackPane root = new StackPane(controller.createView(task));
        root.setPadding(new Insets(20));
        root.getStyleClass().add("board-background");

        Scene scene = new Scene(root, 340, 180);
        scene.getStylesheets().add(getClass().getResource("/View/taskboard.css").toExternalForm());
        stage.setTitle("TaskCard preview");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}