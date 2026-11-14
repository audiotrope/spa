package se.chalmers.group21.spa.model.ManagerTodo;

import se.chalmers.group21.spa.model.Interfaces.BoardRepository;
import se.chalmers.group21.spa.model.Interfaces.TaskRepository;
import se.chalmers.group21.spa.model.entities.Board;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.enums.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TaskManager {
    private final TaskRepository taskRepository;
    private final BoardRepository boardRepository;

    public TaskManager(TaskRepository taskRepository, BoardRepository boardRepository){
        this.taskRepository = taskRepository;
        this.boardRepository = boardRepository;
    }

    public Task createPersonalTask(String title, String description, LocalDate deadline, UUID ownerId) {
        Task task = Task.createPersonal(title, description, deadline, ownerId);
        taskRepository.save(task);
        return task;
    }

    public void updateTask(UUID taskId, String title, String description, Status status, LocalDate deadline) {
        Task task = getTaskById(taskId);
        task.changeTitle(title);
        task.changeDescription(description);
        task.changeStatus(status);
        task.changeDeadline(deadline);
        taskRepository.save(task);
    }

    public Task createGroupTask(String title,String description, LocalDate deadline, UUID boardId, UUID listId, Set<UUID> assignedUserIds) { //AI
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Board not found"));
        if (!board.containsList(listId)) {
            throw new IllegalArgumentException(
                    "TaskList does not belong to this board");
        }
        Task task = Task.createGroup(title, description, deadline, boardId, listId, assignedUserIds);
        taskRepository.save(task);
        return task;
    }

    public void deleteTask(UUID taskId) {
        getTaskById(taskId);
        taskRepository.delete(taskId);
    }

    public void changeDescription(String description, UUID taskId) {
        Task task = getTaskById(taskId);
        task.changeDescription(description);
        taskRepository.save(task);
    }

    public void changeTitle(String title, UUID taskId) {
        Task task = getTaskById(taskId);
        task.changeTitle(title);
        taskRepository.save(task);
    }

    public void changeDeadline(LocalDate deadline, UUID taskId) {
        Task task = getTaskById(taskId);
        task.changeDeadline(deadline);
        taskRepository.save(task);
    }

    public void changeStatus(Status status, UUID taskId) {
        Task task = getTaskById(taskId);
        task.changeStatus(status);
        taskRepository.save(task);
    }

    public List<Task> getTasksByListId(UUID listId) {
        return taskRepository.findByListId(listId);
    }

    public List<Task> getPersonalTasks(UUID userId) {
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(taskRepository.findPersonal(userId));
        tasks.addAll(taskRepository.findAssignedGroup(userId));
        return tasks;
    }

    public Task getTaskById(UUID taskId) { //AI
        return taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Task not found"));
    }

    public List<Task> getAssignedTasks(UUID userId) {
        return taskRepository.findAssignedGroup(userId);
    }

    public void assignUser(UUID taskId, UUID userId) {
        Task task = getTaskById(taskId);
        task.assignUser(userId);
        taskRepository.save(task);
    }

    public void unassignUser(UUID taskId, UUID userId) {
        Task task = getTaskById(taskId);
        task.unassignUser(userId);
        taskRepository.save(task);
    }

}

