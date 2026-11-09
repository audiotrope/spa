package Model.entities;

import Model.enums.TaskType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class TaskList {
    private ArrayList<Task> tasks;
    private String title;
    private UUID id;
    private Integer position;

    public TaskList(String title){
        this.title = title;
        this.id = UUID.randomUUID();
        this.position = 0;
        this.tasks = new ArrayList<Task>();
    }

    public void addTask(Task task){
        tasks.add(task);
    }
    public void removeTask(UUID taskId){
        tasks.removeIf(
                task -> task.getId().equals(taskId)
        );
    }
    public void rename(String title){
        this.title = title;
    }
    public void reorder(Integer position){
        this.position = position;
    }

    public UUID getId(){
        return id;
    }
}
