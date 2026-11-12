package se.chalmers.group21.spa.model.ManagerTodo;

import se.chalmers.group21.spa.model.entities.Board;
import se.chalmers.group21.spa.model.entities.TaskList;

import java.util.UUID;

public class BoardManager {
    public void createBoard(UUID groupId){
       new Board(groupId);
    }
    public void createTaskList(String title){
        TaskList taskList = new TaskList(title);
    }


    // delete Tasklist, renametasklist, move task
}
