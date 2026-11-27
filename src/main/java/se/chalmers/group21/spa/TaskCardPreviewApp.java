package se.chalmers.group21.spa;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.chalmers.group21.spa.controller.TaskCardController;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Interfaces.BoardRepository;
import se.chalmers.group21.spa.model.Interfaces.TaskRepository;
import se.chalmers.group21.spa.model.ManagerTodo.BoardManager;
import se.chalmers.group21.spa.model.ManagerTodo.TaskManager;
import se.chalmers.group21.spa.model.Memory.InMemoryBoard;
import se.chalmers.group21.spa.model.Memory.InMemoryTask;
import se.chalmers.group21.spa.model.User.User;
import se.chalmers.group21.spa.model.entities.Board;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.entities.TaskList;
import se.chalmers.group21.spa.view.ToDo.TaskCardView;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * The structure of this file was originally created by AI as a mockup. It has afterwards been changed by me to fit
 * our application.
 * This file is just to preview the view for a kanban board and will be extended and after that deleted when
 * all the views are done.
 * @author Thyra Öfverström
 */
public class TaskCardPreviewApp extends Application {

    @Override
    public void start(Stage stage) {
        User creator = new User("Thyra");
        Group group = new Group("Data structure", creator);
        group.addMember(new User("Calle"));
        group.addMember(new User("Fredrik"));
        TaskRepository taskRepository = new InMemoryTask();
        BoardRepository boardRepository = new InMemoryBoard();
        TaskManager taskManager = new TaskManager(taskRepository, boardRepository);
        BoardManager boardManager = new BoardManager(taskRepository, boardRepository);

        Board board = boardManager.createBoard(UUID.randomUUID());
        TaskList list = boardManager.createTaskList(board.getId(), "To do");

        Task taskPersonal = taskManager.createPersonalTask("test personal", "something", LocalDate.now().plusDays(3),creator.getUserId());

        Task taskGroup = taskManager.createGroupTask("Test", "...", LocalDate.now().plusDays(2),
                board.getId(), list.getId(), Set.of(creator.getUserId()));

        VBox content = new VBox();
        content.setPadding(new Insets(20));
        content.getStyleClass().add("board-background");
        StackPane overlayHost = new StackPane(content);

        TaskCardController controller = new TaskCardController(taskManager, overlayHost, group);

        TaskCardView card = controller.createView(taskPersonal);
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