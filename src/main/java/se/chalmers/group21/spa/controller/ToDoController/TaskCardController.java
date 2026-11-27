package se.chalmers.group21.spa.controller.ToDoController;

import javafx.scene.layout.StackPane;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.ManagerTodo.TaskManager;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.entities.TaskAssignment;
import se.chalmers.group21.spa.model.enums.DeadlineState;
import se.chalmers.group21.spa.model.enums.TaskType;
import se.chalmers.group21.spa.view.ToDo.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The structure of this file was originally created by AI as a mockup. It has afterwards been changed by me to fit
 * our application.
 * This file handles the collaboration between the view for a task card and the model for a task.
 * @author Thyra Öfverström
 */
public class TaskCardController {
    private final TaskManager taskManager;
    private final StackPane overlayHost;
    private final Group group;

    public TaskCardController(TaskManager taskManager, StackPane overlayHost, Group group) {
        this.taskManager = taskManager;
        this.overlayHost = overlayHost;
        this.group = group;
    }

    public TaskCardView createView(Task task) {
        TaskCardView view = new TaskCardView(toViewModel(task));
        view.setOnClicked(id -> openDetails(id, view));
        return view;
    }

    private void openDetails(UUID taskId, TaskCardView cardView) {
        Task task = taskManager.getTaskById(taskId);
        TaskDetailView detail = new TaskDetailView(toDetailViewModel(task), assigneeOptionsFor(task));
        detail.setOnSave(result -> save(taskId, result, cardView));
        detail.showIn(overlayHost);
    }

    private Optional<String> save(UUID taskId, TaskDetailResult result, TaskCardView cardView) {
        try {
            Task task = taskManager.getTaskById(taskId);
            taskManager.updateTask(taskId, result.title(), result.description(), task.getStatus(), result.deadline());
            updateAssignments(taskId, result.assigneeIds());
            cardView.update(toViewModel(taskManager.getTaskById(taskId)));
            return Optional.empty();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Optional.of(e.getMessage());
        }
    }

    private void updateAssignments(UUID taskId, Set<UUID> selected) {
        Set<UUID> current = taskManager.getTaskById(taskId).getAssignments().stream()
                .map(TaskAssignment::getUserId)
                .collect(Collectors.toSet());
        for (UUID userId : selected) {
            if (!current.contains(userId)) {
                taskManager.assignUser(taskId, userId);
            }
        }
        for (UUID userId : current) {
            if (!selected.contains(userId)) {
                taskManager.unassignUser(taskId, userId);
            }
        }
    }
    private TaskCardViewModel toViewModel(Task task) {
        return new TaskCardViewModel(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDeadline(),
                deadlineStateOf(task),
                task.getAssignments().size()
        );
    }

    private TaskDetailViewModel toDetailViewModel(Task task) {
        return new TaskDetailViewModel(task.getTitle(), task.getDescription(), task.getDeadline());
    }

    private List<AssigneeOption> assigneeOptionsFor(Task task) {
        if (task.getType() != TaskType.GROUP) {
            return List.of();
        }
        Set<UUID> assigned = task.getAssignments().stream()
                .map(TaskAssignment::getUserId)
                .collect(Collectors.toSet());

        return group.getMembers().keySet().stream()
                .map(user -> new AssigneeOption(user.getUserId(), user.getUsername(), assigned.contains(user.getUserId())))
                .sorted(Comparator.comparing(AssigneeOption::name))
                .toList();
    }

    private DeadlineState deadlineStateOf(Task task) {
        if (task.getDeadline() == null) {
            return DeadlineState.NONE;
        }
        if (task.overDeadline()) {
            return DeadlineState.OVERDUE;
        }
        if (task.isDeadlineSoon()) {
            return DeadlineState.SOON;
        }
        return DeadlineState.NORMAL;
    }}