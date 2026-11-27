package se.chalmers.group21.spa.view.ToDo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Objects;

public class TaskListView extends VBox {
    private static final int COLOR_COUNT = 4;   // must match the number of .list-color-N classes in the CSS

    private final Label countLabel = new Label();
    private final Label emptyLabel = new Label("No tasks yet");
    private final VBox cardBox = new VBox(12);
    private final ScrollPane scrollPane = new ScrollPane(cardBox);
    private final Button addButton = new Button("+");

    public TaskListView(TaskListViewModel viewModel) {
        getStyleClass().addAll("list-column", "list-color-" + viewModel.position() % COLOR_COUNT);
        setSpacing(16);
        setPrefWidth(300);

        getChildren().addAll(createHeader(viewModel), createAddButton(), createContent());
    }

    public void setCards(List<TaskCardView> cards) {
        cardBox.getChildren().setAll(cards);
        countLabel.setText(String.valueOf(cards.size()));
        boolean empty = cards.isEmpty();
        emptyLabel.setVisible(empty);
        scrollPane.setVisible(!empty);
    }

    public void setOnAddClicked(Runnable onAddClicked) {
        Objects.requireNonNull(onAddClicked);
        addButton.setOnAction(event -> onAddClicked.run());
    }

    private HBox createHeader(TaskListViewModel viewModel) {
        Label title = new Label(viewModel.title());
        title.getStyleClass().add("list-title");
        countLabel.getStyleClass().add("list-badge");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(8, title, spacer, countLabel);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private Button createAddButton() {
        addButton.getStyleClass().add("add-card");
        addButton.setMaxWidth(Double.MAX_VALUE);
        return addButton;
    }

    private StackPane createContent() {
        cardBox.setPadding(new Insets(4));   // room for the cards' shadows
        scrollPane.getStyleClass().add("list-scroll");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        emptyLabel.getStyleClass().add("list-empty");

        StackPane content = new StackPane(scrollPane, emptyLabel);
        VBox.setVgrow(content, Priority.ALWAYS);
        setCards(List.of());
        return content;
    }
}
