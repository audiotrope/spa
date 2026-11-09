package Model.entities;

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
    public void removeList(TaskList list){
        taskLists.remove(list);
    }
    public void moveTask(UUID newlistId, UUID task, UUID oldlistId){
        //TODO
    }
}
