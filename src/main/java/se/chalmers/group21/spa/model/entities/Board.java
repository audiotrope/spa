package se.chalmers.group21.spa.model.entities;

import java.util.ArrayList;
import java.util.UUID;

public class Board {
    private ArrayList<TaskList> taskLists;
    private final UUID id;
    private final UUID groupId;
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
    public UUID getId(){
        return id;
    }
    public UUID getGroupId(){
        return groupId;
    }
    public ArrayList<TaskList> getTaskLists(){
        return taskLists;
    }
    public void moveTask(Task task, UUID targetListId){
        task.changeListId(targetListId);
    }

    public boolean containsList(UUID listId) {
        return taskLists.stream()
                .anyMatch(list -> list.getId().equals(listId));
    }
}
