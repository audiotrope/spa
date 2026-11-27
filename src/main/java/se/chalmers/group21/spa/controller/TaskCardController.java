package se.chalmers.group21.spa.controller;

import javafx.scene.layout.StackPane;
import se.chalmers.group21.spa.model.ManagerTodo.TaskManager;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.entities.TaskAssignment;
import se.chalmers.group21.spa.model.enums.DeadlineState;
import se.chalmers.group21.spa.model.enums.TaskType;
import se.chalmers.group21.spa.view.ToDo.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class TaskCardController {
    private final TaskManager taskManager;
    private final StackPane overlayHost;

    public TaskCardController(TaskManager taskManager, StackPane overlayHost) {
        this.taskManager = taskManager;
        this.overlayHost = overlayHost;
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
        // TODO: replace with the group's members from the user module, so users can also be added.
        // For now only the current assignees are listed, so they can be removed.
        if (task.getType() != TaskType.GROUP) {
            return List.of();
        }
        return task.getAssignments().stream()
                .map(a -> new AssigneeOption(a.getUserId(), "User " + a.getUserId().toString().substring(0, 8), true))
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