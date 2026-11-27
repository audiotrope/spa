package se.chalmers.group21.spa;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.chalmers.group21.spa.controller.TaskCardController;
import se.chalmers.group21.spa.model.Interfaces.BoardRepository;
import se.chalmers.group21.spa.model.Interfaces.TaskRepository;
import se.chalmers.group21.spa.model.ManagerTodo.BoardManager;
import se.chalmers.group21.spa.model.ManagerTodo.TaskManager;
import se.chalmers.group21.spa.model.Memory.InMemoryBoard;
import se.chalmers.group21.spa.model.Memory.InMemoryTask;
import se.chalmers.group21.spa.model.entities.Board;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.entities.TaskList;
import se.chalmers.group21.spa.view.ToDo.TaskCardView;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class TaskCardPreviewApp extends Application {

    @Override
    public void start(Stage stage) {
        TaskRepository taskRepository = new InMemoryTask();
        BoardRepository boardRepository = new InMemoryBoard();
        TaskManager taskManager = new TaskManager(taskRepository, boardRepository);
        BoardManager boardManager = new BoardManager(taskRepository, boardRepository);

        Board board = boardManager.createBoard(UUID.randomUUID());
        TaskList list = boardManager.createTaskList(board.getId(), "To do");

        Task task = taskManager.createGroupTask("Test", "Draft the introduction for the data structures report",
                LocalDate.now().plusDays(2), board.getId(), list.getId(),
                Set.of(UUID.randomUUID(), UUID.randomUUID()));

        VBox content = new VBox();
        content.setPadding(new Insets(20));
        content.getStyleClass().add("board-background");
        StackPane overlayHost = new StackPane(content);

        TaskCardController controller = new TaskCardController(taskManager, overlayHost);

        TaskCardView card = controller.createView(task);
        card.setMaxWidth(300);
        content.getChildren().add(card);

        Scene scene = new Scene(overlayHost, 480, 560);
        scene.getStylesheets().add(getClass().getResource("/View/taskboard.css").toExternalForm());
        stage.setTitle("TaskCard preview");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}