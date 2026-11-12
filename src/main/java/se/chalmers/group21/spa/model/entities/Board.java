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
    public void moveTask(UUID taskId, UUID targetListId){
        // hitta först rätt task med det UUID i denna listan
        // spara den i en temporär variabel
        // ta bort den från första listan
        // hitta rätt lista med rätt UUID
        // lägg till task i dens lista

        Task taskToMove = null;
        for(TaskList tasklist: taskLists){
            for(Task task: tasklist.getTasks()){
                if(task.getId() == taskId){
                    taskToMove = task;
                    tasklist.removeTask(task.getId());
                    break;
                }
            }
        }
        for(TaskList tasklist: taskLists){
            if(tasklist.getId() == targetListId){
                tasklist.addTask(taskToMove);
                break;
            }
        }
    }
}
