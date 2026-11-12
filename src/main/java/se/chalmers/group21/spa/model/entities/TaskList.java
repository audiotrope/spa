package se.chalmers.group21.spa.model.entities;

import java.util.ArrayList;
import java.util.UUID;

public class TaskList {
    private String title;
    private final UUID id;
    private Integer position;

    public TaskList(String title, Integer position){
        this.title = title;
        this.id = UUID.randomUUID();
        this.position = position;
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

    public int getPosition(){
        return position;
    }

    public String getTitle(){
        return title;
    }
}
