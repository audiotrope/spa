package se.chalmers.group21.spa.model.entities;

import java.util.ArrayList;
import java.util.UUID;

public class Board {
    private ArrayList<TaskList> taskLists;
    private UUID id;
    private UUID groupId;
    //private String title;

    public Board(UUID groupId){
        this.groupId = groupId;
        this.id = UUID.randomUUID();
    }

    public void addList(TaskList list){
        taskLists.add(list);
    }
    public void removeList(UUID taskListId){
        taskLists.removeIf(
                list -> list.getId().equals(taskListId)
        );
    }
    public void moveTask(UUID task, UUID targetListId){
        // hitta först rätt task med det UUID i denna listan
        // spara den i en temporär variabel
        // ta bort den från första listan
        // hitta rätt lista med rätt UUID
        // lägg till task i dens lista
    }
}
