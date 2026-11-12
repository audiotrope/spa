package se.chalmers.group21.spa.model.ManagerTodo;

import se.chalmers.group21.spa.model.Interfaces.BoardRepository;
import se.chalmers.group21.spa.model.Interfaces.TaskRepository;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.enums.Status;
import se.chalmers.group21.spa.model.enums.TaskType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskManager {
    private TaskRepository taskRepository;
    private BoardRepository boardRepository;

    public TaskManager(TaskRepository taskRepository, BoardRepository boardRepository){
        this.taskRepository = taskRepository;
        this.boardRepository = boardRepository;
    }

    public void createTask(String title, String description, LocalDate deadline, TaskType type, UUID ownerId, UUID boardId, UUID listId){
        Task task = new Task(title, description, deadline, type, ownerId, boardId, listId);
        taskRepository.save(task);
    }
    public void deleteTask(UUID boardId, UUID taskId){
        Task task = taskRepository.findById(taskId);
        task.changeListId(null);
        taskRepository.save(task);
    }

    public void changeDescription(String description, UUID taskId){
        Task task = taskRepository.findById(taskId);
        task.changeDescription(description);
        taskRepository.save(task);
    }

    public void changeTitle(String title, UUID taskId){
        Task task = taskRepository.findById(taskId);
        task.changeTitle(title);
        taskRepository.save(task);
    }
    public void changeDeadline(LocalDate deadline, UUID taskId){
        Task task = taskRepository.findById(taskId);
        task.changeDeadline(deadline);
        taskRepository.save(task);
    }

    public void changeStatus(Status status, UUID taskId){
        Task task = taskRepository.findById(taskId);
        task.changeStatus(status);
        taskRepository.save(task);
    }

    public List<Task> getTasksByListId(UUID listId){ // inte färdig
        //Board board = boardRepository.findById(boardId);
        //board.getTaskLists();
        List<Task> tasks = new ArrayList<>();

        return tasks;
    }

    public List<Task> getPersonalTasks(UUID userId){
        return List.of();
    }

    public Task getTaskById(UUID taskId){
        return taskRepository.findById(taskId);
    }

    public List<Task> getAssignedTasks(){
        return List.of();
    }

    public void assignUser(){

    }

    public void unassignuser(){

    }

}

