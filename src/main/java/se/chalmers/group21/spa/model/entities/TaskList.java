package se.chalmers.group21.spa.model.entities;

import java.util.ArrayList;
import java.util.UUID;

public class TaskList {
    private ArrayList<Task> tasks;
    private String title;
    private final UUID id;
    private Integer position;

    public TaskList(String title, Integer position){
        this.title = title;
        this.id = UUID.randomUUID();
        this.position = position;
        this.tasks = new ArrayList<>();
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
    public ArrayList<Task> getTasks(){
        return tasks;
    }
}
