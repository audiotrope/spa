package se.chalmers.group21.spa.controller.ToDoController;

import se.chalmers.group21.spa.model.ManagerTodo.TaskManager;
import se.chalmers.group21.spa.model.entities.TaskList;
import se.chalmers.group21.spa.view.ToDo.TaskCardView;
import se.chalmers.group21.spa.view.ToDo.TaskListView;
import se.chalmers.group21.spa.view.ToDo.TaskListViewModel;

import java.util.List;
import java.util.UUID;

public class TaskListController {
    private final TaskManager taskManager;
    private final TaskCardController taskCardController;

    public TaskListController(TaskManager taskManager, TaskCardController taskCardController) {
        this.taskManager = taskManager;
        this.taskCardController = taskCardController;
    }

    public TaskListView createView(TaskList taskList) {
        TaskListView view = new TaskListView(toViewModel(taskList));
        view.setCards(createCards(taskList.getId()));
        return view;
    }

    private List<TaskCardView> createCards(UUID listId) {
        return taskManager.getTasksByListId(listId).stream()
                .map(taskCardController::createView)
                .toList();
    }

    private TaskListViewModel toViewModel(TaskList taskList) {
        return new TaskListViewModel(taskList.getTitle(), taskList.getPosition());
    }
}
