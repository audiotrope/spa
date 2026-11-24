package se.chalmers.group21.spa.model.entities;

import java.util.UUID;

/**
 * Represents a customizable list on a board with a title this can be for example "To do", "Backlog" ect. It stores its
 * title unique ID and position on the board. It also handles renaming and reordering itself.
 * @author Thyra Öfverström
 */

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
