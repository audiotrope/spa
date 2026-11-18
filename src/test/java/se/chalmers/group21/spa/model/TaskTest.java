package se.chalmers.group21.spa.model;

import org.junit.jupiter.api.Test;
import se.chalmers.group21.spa.model.entities.Board;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.entities.TaskAssignment;
import se.chalmers.group21.spa.model.entities.TaskList;
import se.chalmers.group21.spa.model.enums.Status;
import se.chalmers.group21.spa.model.enums.TaskType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class handles the tests of the logic for the class Task.
 * @author Thyra Öfverström
 */

public class TaskTest {
    @Test
    void testCreatePersonalTask(){
        UUID userId = UUID.randomUUID();
        Task task = Task.createPersonal("Test", "testning", LocalDate.parse("2026-11-30"),userId);

        assertEquals(TaskType.PERSONAL, task.getType());
    }

    @Test
    void testCreateGroupTask(){
        UUID groupId = UUID.randomUUID();
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        Board board = new Board(groupId);
        TaskList taskList = new TaskList("Doing", 0);
        Set<UUID> assignments = Set.of(user1, user2);
        Task task = Task.createGroup("Test", "testning", LocalDate.parse("2026-11-30"),board.getId(), taskList.getId(), assignments);

        assertEquals(TaskType.GROUP, task.getType());
    }

    @Test
    void testCreateGroupTaskWithoutAssignments(){
        UUID groupId = UUID.randomUUID();
        Board board = new Board(groupId);
        TaskList taskList = new TaskList("Doing", 0);
        Task task = Task.createGroup("Test", "testning", LocalDate.parse("2026-11-30"),board.getId(), taskList.getId(), Set.of());

        assertEquals(0, task.getAssignments().size());
    }

    @Test
    void testChangeTitle(){
        UUID userId = UUID.randomUUID();
        Task task = Task.createPersonal("Test", "testning", LocalDate.parse("2026-11-30"),userId);
        task.changeTitle("Done");

        assertEquals("Done", task.getTitle());
    }

    @Test
    void testChangeDescription(){
        UUID userId = UUID.randomUUID();
        Task task = Task.createPersonal("Test", "testning", LocalDate.parse("2026-11-30"),userId);
        task.changeDescription("Done testing");

        assertEquals("Done testing", task.getDescription());
    }

    @Test
    void testChangeStatus(){
        UUID userId = UUID.randomUUID();
        Task task = Task.createPersonal("Test", "testning", LocalDate.parse("2026-11-30"),userId);

        assertEquals(Status.TODO, task.getStatus());
        task.changeStatus(Status.DOING);
        assertEquals(Status.DOING, task.getStatus());
    }

    @Test
    void testChangeDeadline(){
        UUID userId = UUID.randomUUID();
        Task task = Task.createPersonal("Test", "testning", LocalDate.parse("2026-11-30"),userId);
        task.changeDeadline(LocalDate.parse("2026-12-15"));

        assertEquals(LocalDate.parse("2026-12-15"), task.getDeadline());
    }

    @Test
    void testAssignUser(){
        UUID groupId = UUID.randomUUID();
        UUID user1 = UUID.randomUUID();
        Board board = new Board(groupId);
        TaskList taskList = new TaskList("Doing", 0);
        Set<TaskAssignment> assignments = new HashSet<>();
        assignments.add(new TaskAssignment(user1));
        Task task = Task.createGroup("Test", "testning", LocalDate.parse("2026-11-30"),board.getId(), taskList.getId(), Set.of());
        task.assignUser(user1);

        assertEquals(assignments, task.getAssignments());
        assertEquals(1, task.getAssignments().size());
    }

    @Test
    void testAssignMultipleUsers(){
        UUID groupId = UUID.randomUUID();
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        UUID user3 = UUID.randomUUID();
        Board board = new Board(groupId);
        TaskList taskList = new TaskList("Doing", 0);
        Set<TaskAssignment> assignments = new HashSet<>();
        assignments.add(new TaskAssignment(user1));
        assignments.add(new TaskAssignment(user2));
        assignments.add(new TaskAssignment(user3));
        Task task = Task.createGroup("Test", "testning", LocalDate.parse("2026-11-30"),board.getId(), taskList.getId(), Set.of());
        task.assignUser(user1);
        task.assignUser(user2);
        task.assignUser(user3);

        assertEquals(assignments, task.getAssignments());
        assertEquals(3, task.getAssignments().size());
    }

    @Test
    void testAssignSameUserTwice(){
        UUID groupId = UUID.randomUUID();
        UUID user1 = UUID.randomUUID();
        Board board = new Board(groupId);
        TaskList taskList = new TaskList("Doing", 0);
        Task task = Task.createGroup("Test", "testning", LocalDate.parse("2026-11-30"),board.getId(), taskList.getId(), Set.of());
        task.assignUser(user1);
        task.assignUser(user1);

        assertEquals(1, task.getAssignments().size());
    }

    @Test
    void testUnassignUser(){
        UUID groupId = UUID.randomUUID();
        UUID user1 = UUID.randomUUID();
        Board board = new Board(groupId);
        TaskList taskList = new TaskList("Doing", 0);
        Task task = Task.createGroup("Test", "testning", LocalDate.parse("2026-11-30"),board.getId(), taskList.getId(), Set.of());
        task.assignUser(user1);
        task.unassignUser(user1);

        assertEquals(0, task.getAssignments().size());
    }

    @Test
    void testAssignUserToPersonalTask(){
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        Task task = Task.createPersonal("Test", "testning", LocalDate.parse("2026-11-30"),user1);

        assertThrows(IllegalStateException.class,
                () -> task.assignUser(user2));
        assertEquals(0, task.getAssignments().size());
    }
}
