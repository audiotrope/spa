package se.chalmers.group21.spa.controller;

import se.chalmers.group21.spa.model.ManagerTodo.TaskManager;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.enums.DeadlineState;
import se.chalmers.group21.spa.view.ToDo.TaskCardView;
import se.chalmers.group21.spa.view.ToDo.TaskCardViewModel;

import java.util.UUID;

public class TaskCardController {
    private final TaskManager taskManager;

    public TaskCardController(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public TaskCardView createView(Task task) {
        TaskCardView view = new TaskCardView(toViewModel(task));
        view.setOnClicked(id -> openDetails(id));
        return view;
    }

    private void openDetails(UUID taskId) {
        Task task = taskManager.getTaskById(taskId);
        // visa detaljvy senare
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