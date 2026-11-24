package se.chalmers.group21.spa.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import se.chalmers.group21.spa.model.entities.Board;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.entities.TaskList;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * This class handles the tests of the logic for the class Board.
 * @author Thyra Öfverström
 */

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
        Task task = Task.createGroup("Test", "testing", LocalDate.parse("2026-11-30"), board.getId(), taskListOne.getId(), Set.of());
        board.moveTask(task, taskListSecond.getId());

        assertEquals(task.getListId(), taskListSecond.getId());
    }

    @Test
    void testMoveTaskInvalidList(){
        UUID groupId = UUID.randomUUID();
        UUID invalidListId = UUID.randomUUID();
        Board board = new Board(groupId);
        TaskList taskListOne = new TaskList("Test tasklist", 0);
        board.addList(taskListOne);
        Task task = Task.createGroup("Test", "testing", LocalDate.parse("2026-11-30"), board.getId(), taskListOne.getId(), Set.of());

        assertThrows(IllegalArgumentException.class,
                () -> board.moveTask(task, invalidListId));
        assertEquals(taskListOne.getId(), task.getListId());
    }

    @Test
    void testMoveTaskAnotherBoard(){
        UUID groupIdOne = UUID.randomUUID();
        UUID groupIdTwo = UUID.randomUUID();
        Board boardGroupOne = new Board(groupIdOne);
        Board boardGroupTwo = new Board(groupIdTwo);
        TaskList taskListOne = new TaskList("Test tasklist", 0);
        TaskList taskListTwo = new TaskList("Test tasklist for group 2", 0);
        boardGroupOne.addList(taskListOne);
        boardGroupTwo.addList(taskListTwo);
        Task taskOne = Task.createGroup("Test", "testing", LocalDate.parse("2026-11-30"), boardGroupOne.getId(), taskListOne.getId(), Set.of());

        assertThrows(IllegalArgumentException.class,
                () -> boardGroupOne.moveTask(taskOne, taskListTwo.getId()));
        assertEquals(taskListOne.getId(), taskOne.getListId());
    }

    @Test
    void testReorderTaskList(){
        UUID groupId = UUID.randomUUID();
        Board board = new Board(groupId);
        TaskList taskListOne = new TaskList("Test tasklist", 0);
        TaskList taskListTwo = new TaskList("Test tasklist two", 1);
        board.addList(taskListOne);
        board.addList(taskListTwo);
        board.reorderTaskList(taskListOne.getId(), 1);

        assertEquals(1, taskListOne.getPosition());
        assertEquals(0, taskListTwo.getPosition());
    }

    @Test
    void testReorderTaskListInvalidPosition(){
        UUID groupId = UUID.randomUUID();
        Board board = new Board(groupId);
        TaskList taskListOne = new TaskList("Test tasklist", 0);
        TaskList taskListTwo = new TaskList("Test tasklist two", 1);
        board.addList(taskListOne);
        board.addList(taskListTwo);

        assertThrows(IllegalArgumentException.class,
                () ->  board.reorderTaskList(taskListOne.getId(), 3));
        assertEquals(0, taskListOne.getPosition());
    }
}
