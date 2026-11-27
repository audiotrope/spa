package se.chalmers.group21.spa.view.ToDo;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.*;
import java.util.function.Function;

/**
 * The structure of this file was originally created by AI as a mockup. It has afterwards been changed by me to fit
 * our application.
 * This file handles the detail view for a task card, it comes up when you click on a task card. This is a StackPane
 * that has a VBox in it because we want it to lay on top of everything else when the detail view is opened. Also
 * a HBox for the buttons in the bottom.
 * @author Thyra Öfverström
 */

public class TaskDetailView extends StackPane {
    private final TextField titleField = new TextField();
    private final TextArea descriptionArea = new TextArea();
    private final DatePicker deadlinePicker = new DatePicker();
    private final Map<UUID, CheckBox> assigneeBoxes = new LinkedHashMap<>();
    private final Label errorLabel = new Label();

    private StackPane host;
    private Function<TaskDetailResult, Optional<String>> onSave = result -> Optional.empty();

    public TaskDetailView(TaskDetailViewModel viewModel, List<AssigneeOption> assigneeOptions) {
        getStyleClass().add("overlay-backdrop");
        setAlignment(Pos.CENTER);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setOnMouseClicked(event -> {
            if (event.getTarget() == this) {
                close();
            }
        });

        titleField.setText(viewModel.title());
        descriptionArea.setText(viewModel.description());
        descriptionArea.setPrefRowCount(4);
        descriptionArea.setWrapText(true);
        deadlinePicker.setValue(viewModel.deadline());
        deadlinePicker.setMaxWidth(Double.MAX_VALUE);
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setWrapText(true);

        getChildren().add(createPanel(assigneeOptions));
    }

    public void setOnSave(Function<TaskDetailResult, Optional<String>> onSave) {
        this.onSave = Objects.requireNonNull(onSave);
    }

    public void showIn(StackPane host) {
        this.host = host;
        host.getChildren().add(this);
        Platform.runLater(titleField::requestFocus);
    }

    public void close() {
        if (host != null) {
            host.getChildren().remove(this);
        }
    }

    private VBox createPanel(List<AssigneeOption> assigneeOptions) {
        Label heading = new Label("Edit task");
        heading.getStyleClass().add("detail-heading");

        VBox panel = new VBox(14);
        panel.getStyleClass().add("detail-panel");
        panel.setMaxWidth(420);
        panel.setMaxHeight(Region.USE_PREF_SIZE);
        panel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                close();
            }
        });

        panel.getChildren().addAll(
                heading,
                labeled("Title", titleField),
                labeled("Description", descriptionArea),
                labeled("Deadline", deadlinePicker));
        if (!assigneeOptions.isEmpty()) {
            panel.getChildren().add(labeled("Assigned", createAssigneeList(assigneeOptions)));
        }
        panel.getChildren().addAll(errorLabel, createButtons());
        return panel;
    }

    private VBox labeled(String text, Node field) {
        Label label = new Label(text);
        label.getStyleClass().add("field-label");
        return new VBox(4, label, field);
    }

    private VBox createAssigneeList(List<AssigneeOption> options) {
        VBox box = new VBox(6);
        for (AssigneeOption option : options) {
            CheckBox checkBox = new CheckBox(option.name());
            checkBox.setSelected(option.selected());
            assigneeBoxes.put(option.id(), checkBox);
            box.getChildren().add(checkBox);
        }
        return box;
    }

    private HBox createButtons() {
        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("button-secondary");
        cancel.setOnAction(event -> close());

        Button save = new Button("Save");
        save.getStyleClass().add("button-primary");
        save.setOnAction(event -> save());

        HBox buttons = new HBox(10, cancel, save);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        return buttons;
    }

    private void save() {
        Optional<String> error = onSave.apply(readInput());
        if (error.isPresent()) {
            errorLabel.setText(error.get());   // stay open and show the reason
        } else {
            close();
        }
    }

    private TaskDetailResult readInput() {
        Set<UUID> selected = new HashSet<>();
        assigneeBoxes.forEach((id, box) -> {
            if (box.isSelected()) {
                selected.add(id);
            }
        });
        return new TaskDetailResult(titleField.getText(), descriptionArea.getText(),
                deadlinePicker.getValue(), selected);
    }
}