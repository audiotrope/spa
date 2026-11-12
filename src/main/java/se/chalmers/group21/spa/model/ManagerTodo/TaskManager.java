package se.chalmers.group21.spa.model.ManagerTodo;

import se.chalmers.group21.spa.model.Interfaces.TaskRepository;

import java.util.UUID;

public class TaskManager {
    private TaskRepository taskRepository;

    public TaskManager(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public void createTask(UUID taskListId){

    }
}
