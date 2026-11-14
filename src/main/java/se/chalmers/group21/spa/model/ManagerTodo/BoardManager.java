package se.chalmers.group21.spa.model.ManagerTodo;

import se.chalmers.group21.spa.model.Interfaces.BoardRepository;
import se.chalmers.group21.spa.model.Interfaces.TaskRepository;
import se.chalmers.group21.spa.model.entities.Board;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.entities.TaskList;

import java.util.List;
import java.util.UUID;

public class BoardManager {
    private BoardRepository boardRepository;
    private TaskRepository taskRepository;

    public BoardManager(TaskRepository taskRepository,BoardRepository boardRepository){
        this.taskRepository = taskRepository;
        this.boardRepository = boardRepository;
    }
    public Board createBoard(UUID groupId) { //AI

        if (boardRepository.findByGroupId(groupId).isPresent()) {
            throw new IllegalArgumentException(
                    "Group already has a board");
        }

        Board board = new Board(groupId);
        boardRepository.save(board);

        return board;
    }

    public TaskList createTaskList(UUID boardId, String title) { //AI

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Board not found"));

        TaskList taskList = new TaskList(
                title,
                board.getTaskLists().size()
        );

        board.addList(taskList);
        boardRepository.save(board);

        return taskList;
    }

    public void deleteTaskList(UUID boardId, TaskList taskList) { //AI

        Board board = getBoardById(boardId);

        if (!board.containsList(taskList.getId())) {
            throw new IllegalArgumentException("TaskList not found");
        }

        if (!taskRepository.findByListId(taskList.getId()).isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete a list that contains tasks");
        }

        board.removeList(taskList.getId());
        boardRepository.save(board);
    }

    public void renameTaskList( //AI
            UUID boardId,
            TaskList wantedTaskList,
            String title) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Board not found"));

        List<TaskList> taskLists = board.getTaskLists();

        for (TaskList taskList : taskLists) {
            if (taskList.getId().equals(wantedTaskList.getId())) {
                taskList.rename(title);
                boardRepository.save(board);
                return;
            }
        }

        throw new IllegalArgumentException("TaskList not found");
    }

    public void moveTask(UUID boardId, UUID taskId, UUID taskListId) { //AI

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Board not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Task not found"));

        board.moveTask(task, taskListId);

        taskRepository.save(task);
    }

    public void reorderTaskList(UUID boardId, UUID listId, int newPosition) {
        Board board = getBoardById(boardId);

        board.reorderTaskList(listId, newPosition);

        boardRepository.save(board);
    }

    public List<TaskList> getTaskLists(UUID boardId) {
        Board board = getBoardById(boardId);
        return List.copyOf(board.getTaskLists());
    }

    public Board getBoardById(UUID boardId) { // AI
        return boardRepository.findById(boardId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Board not found"));
    }

    public Board getBoardByGroupId(UUID groupId) { //AI
        return boardRepository.findByGroupId(groupId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Board not found"));
    }

}
