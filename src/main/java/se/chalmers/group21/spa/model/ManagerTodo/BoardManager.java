package se.chalmers.group21.spa.model.ManagerTodo;

import se.chalmers.group21.spa.model.Interfaces.BoardRepository;
import se.chalmers.group21.spa.model.entities.Board;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.entities.TaskList;

import java.util.List;
import java.util.UUID;

public class BoardManager {
    private BoardRepository boardRepository;

    public BoardManager(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }
    public void createBoard(UUID groupId){
       Board board = new Board(groupId);
       boardRepository.save(board);
    }
    public void createTaskList(UUID boardId, String title){
        Board board = boardRepository.findById(boardId);
        TaskList taskList = new TaskList(title, board.getTaskLists().size());
        board.addList(taskList);
        boardRepository.save(board);
    }

    public void deleteTaskList(UUID boardId, TaskList taskList){
        Board board = boardRepository.findById(boardId);
        board.removeList(taskList.getId());
        boardRepository.save(board);
    }

    public void renameTaskList(UUID boardId, TaskList wantedTaskList, String title){
        Board board = boardRepository.findById(boardId);
        List<TaskList> tasklists = board.getTaskLists();
        for(TaskList tasklist: tasklists){
            if(tasklist.getId() == wantedTaskList.getId()){
                tasklist.rename(title);
            }
        }
        boardRepository.save(board);
    }
    public void moveTask(UUID boardId, UUID taskId, UUID taskListId){
        Board board = boardRepository.findById(boardId);
        board.moveTask(taskId, taskListId);
        boardRepository.save(board);
    }

    public void reoderTasklist(){ // osäker

    }
}
