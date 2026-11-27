package se.chalmers.group21.spa.view.ToDo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import se.chalmers.group21.spa.model.enums.DeadlineState;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * The structure of this file was originally created by AI as a mockup. It has afterwards been changed by me to fit
 * our application.
 * This file handles the view for a task card by creating it as a VBox.
 * For the design of the card the file uses resourses-view-taskborad.css.
 * @author Thyra Öfverström
 */

public class TaskCardView extends VBox {
    private static final DateTimeFormatter DEADLINE_FORMAT =
            DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH);

    private final UUID taskId;
    private Consumer<UUID> onClicked;

    public TaskCardView(TaskCardViewModel viewModel) {
        this.taskId = viewModel.id();
        getStyleClass().add("task-card");
        setSpacing(8);
        setPadding(new Insets(14, 16, 14, 16));
        setCursor(Cursor.HAND);
        setOnMouseClicked(event -> onClicked.accept(taskId));
        update(viewModel);
    }

    public void update(TaskCardViewModel viewModel) {
        getChildren().clear();
        getChildren().add(createTitle(viewModel));
        if (hasText(viewModel.description())) {
            getChildren().add(createDescription(viewModel));
        }
        if (hasFooterContent(viewModel)) {
            getChildren().add(createFooter(viewModel));
        }
    }

    public void setOnClicked(Consumer<UUID> onClicked) {
        this.onClicked = Objects.requireNonNull(onClicked);
    }

    private Label createTitle(TaskCardViewModel viewModel) {
        Label title = new Label(viewModel.title());
        title.getStyleClass().add("task-card-title");
        title.setWrapText(true);
        return title;
    }

    private Label createDescription(TaskCardViewModel viewModel) {
        Label description = new Label(viewModel.description());
        description.getStyleClass().add("task-card-description");
        description.setWrapText(true);
        description.setMaxHeight(48);
        return description;
    }

    private HBox createFooter(TaskCardViewModel viewModel) {
        HBox footer = new HBox(8);
        footer.setAlignment(Pos.CENTER_LEFT);

        if (viewModel.deadlineState() != DeadlineState.NONE) {
            footer.getChildren().add(createDeadlineBadge(viewModel));
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        footer.getChildren().add(spacer);

        if (viewModel.assigneeCount() > 0) {
            footer.getChildren().add(createAssigneeLabel(viewModel));
        }
        return footer;
    }

    private Label createDeadlineBadge(TaskCardViewModel viewModel) {
        Label badge = new Label(DEADLINE_FORMAT.format(viewModel.deadline()));
        badge.getStyleClass().addAll("deadline-badge", styleClassFor(viewModel.deadlineState()));
        return badge;
    }

    private Label createAssigneeLabel(TaskCardViewModel viewModel) {
        Label assignees = new Label(viewModel.assigneeCount() + " assigned");
        assignees.getStyleClass().add("task-card-assignees");
        return assignees;
    }

    private String styleClassFor(DeadlineState state) {
        return switch (state) {
            case OVERDUE -> "deadline-overdue";
            case SOON -> "deadline-soon";
            case NORMAL, NONE -> "deadline-normal";
        };
    }

    private boolean hasFooterContent(TaskCardViewModel viewModel) {
        return viewModel.deadlineState() != DeadlineState.NONE || viewModel.assigneeCount() > 0;
    }

    private boolean hasText(String text) {
        return text != null && !text.isBlank();
    }
}
