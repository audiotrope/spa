package se.chalmers.group21.spa.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.chalmers.group21.spa.model.entities.Board;
import se.chalmers.group21.spa.model.entities.TaskList;

import java.util.UUID;

public class BoardTest {

    @Test
    void testCreateBoard(){
        UUID groupId = UUID.randomUUID();
        Board board = new Board(groupId);

        assertEquals(groupId, board.getGroupId());
    }

    @Test
    void testAddList(){
        UUID groupId = UUID.randomUUID();
        Board board = new Board(groupId);
        TaskList taskList = new TaskList("Test tasklist", 0);
        board.addList(taskList);

        assertEquals(1, board.getTaskLists().size());
    }

    @Test
    void testRemoveList(){
        UUID groupId = UUID.randomUUID();
        Board board = new Board(groupId);
        TaskList taskListOne = new TaskList("Test tasklist", 0);
        TaskList taskListSecond = new TaskList("Test another tasklist", 1);
        board.addList(taskListOne);
        board.addList(taskListSecond);
        board.removeList(taskListOne.getId());

        assertEquals(1, board.getTaskLists().size());
    }

    @Test
    void testCointainsList(){
        UUID groupId = UUID.randomUUID();
        Board board = new Board(groupId);
        TaskList taskList = new TaskList("Test tasklist", 0);
        board.addList(taskList);

        assertEquals(taskList.getId(), board.getTaskLists().getFirst().getId());
    }

    @Test
    void testMoveTask(){
        UUID groupId = UUID.randomUUID();
        Board board = new Board(groupId);
        TaskList taskListOne = new TaskList("Test tasklist", 0);
        TaskList taskListSecond = new TaskList("Test another tasklist", 1);
        board.addList(taskListOne);
        board.addList(taskListSecond);

        //inte klar
    }

    @Test
    void testMoveTaskInvalidList(){}

    @Test
    void testMoveTaskAnotherBoard(){}

    @Test
    void testReorderTaskList(){}

    @Test
    void testReorderTaskListInvaligPosition(){}
}
