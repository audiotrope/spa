package se.chalmers.group21.spa;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import se.chalmers.group21.spa.controller.ToDoController.TaskCardController;
import se.chalmers.group21.spa.controller.ToDoController.TaskListController;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Interfaces.BoardRepository;
import se.chalmers.group21.spa.model.Interfaces.TaskRepository;
import se.chalmers.group21.spa.model.ManagerTodo.BoardManager;
import se.chalmers.group21.spa.model.ManagerTodo.TaskManager;
import se.chalmers.group21.spa.model.Memory.InMemoryBoard;
import se.chalmers.group21.spa.model.Memory.InMemoryTask;
import se.chalmers.group21.spa.model.User.User;
import se.chalmers.group21.spa.model.entities.Board;
import se.chalmers.group21.spa.model.entities.TaskList;

import java.time.LocalDate;
import java.util.Set;

public class TaskListPreviewApp extends Application {

    @Override
    public void start(Stage stage) {
        TaskRepository taskRepository = new InMemoryTask();
        BoardRepository boardRepository = new InMemoryBoard();
        TaskManager taskManager = new TaskManager(taskRepository, boardRepository);
        BoardManager boardManager = new BoardManager(taskRepository, boardRepository);

        User creator = new User("Thyra");
        Group group = new Group("Data structure", creator);
        group.addMember(new User("Calle"));

        Board board = boardManager.createBoard(group.getGroupCode());
        TaskList ongoing = boardManager.createTaskList(board.getId(), "Ongoing");
        TaskList todo = boardManager.createTaskList(board.getId(), "To do");

        taskManager.createGroupTask("RAD", "Skriv dokumentet",
                LocalDate.now().plusDays(2), board.getId(), ongoing.getId(), Set.of(creator.getUserId()));
        taskManager.createGroupTask("SDD", "fixa med UML diagrammet",
                LocalDate.now().plusDays(10), board.getId(), ongoing.getId(), null);
        taskManager.createGroupTask("MVP", "Kämpa",
                LocalDate.now().minusDays(1), board.getId(), ongoing.getId(), null);

        StackPane overlayHost = new StackPane();
        TaskCardController cardController = new TaskCardController(taskManager, overlayHost, group);
        TaskListController listController = new TaskListController(taskManager, cardController);

        HBox lists = new HBox(20, listController.createView(ongoing), listController.createView(todo));
        lists.setPadding(new Insets(20));
        lists.getStyleClass().add("board-background");
        overlayHost.getChildren().add(lists);

        Scene scene = new Scene(overlayHost, 700, 520);
        scene.getStylesheets().add(getClass().getResource("/view/taskboard.css").toExternalForm());  // din sökväg
        stage.setTitle("TaskList preview");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
