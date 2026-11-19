package se.chalmers.group21.spa.model.ManagerTodo;

import se.chalmers.group21.spa.model.Interfaces.BoardRepository;
import se.chalmers.group21.spa.model.Interfaces.TaskRepository;
import se.chalmers.group21.spa.model.entities.Board;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.entities.TaskList;

import java.util.List;
import java.util.UUID;

/**
 * This class handles board related operations. It creates and retrieves boards associated whit groups. It also
 * creates, deletes, renames and reoder task lists. It also handles moving tasks between lists on a board. Board and
 * task repositories are used to access presist data.
 *
 * Methods genereated by AI: createBoard(UUID groupId), createTaskList(UUID boardId, String title),
 * deleteTaskList(UUID boardId, TaskList taskList), renameTaskList(UUID boardId, TaskList wantedTaskList, String title),
 * moveTask(UUID boardId, UUID taskId, UUID taskListId), getBoardById(UUID boardId),
 * getBoardByGroupId(UUID groupId)
 * @author Thyra Öfverström
 */

public class BoardManager {
    private final BoardRepository boardRepository;
    private final TaskRepository taskRepository;

    public BoardManager(TaskRepository taskRepository,BoardRepository boardRepository){
        this.taskRepository = taskRepository;
        this.boardRepository = boardRepository;
    }

    //AI
    public Board createBoard(UUID groupId) {
        if (boardRepository.findByGroupId(groupId).isPresent()) {
            throw new IllegalArgumentException(
                    "Group already has a board");
        }
        Board board = new Board(groupId);
        boardRepository.save(board);
        return board;
    }

    //AI
    public TaskList createTaskList(UUID boardId, String title) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Board not found"));
        TaskList taskList = new TaskList(title, board.getTaskLists().size());
        board.addList(taskList);
        boardRepository.save(board);
        return taskList;
    }

    //AI
    public void deleteTaskList(UUID boardId, TaskList taskList) {
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

    //AI
    public void renameTaskList(UUID boardId, TaskList wantedTaskList, String title) {
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

    //AI
    public void moveTask(UUID boardId, UUID taskId, UUID taskListId) {
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

    //AI
    public Board getBoardById(UUID boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Board not found"));
    }

    //AI
    public Board getBoardByGroupId(UUID groupId) {
        return boardRepository.findByGroupId(groupId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Board not found"));
    }

}
