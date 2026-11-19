package se.chalmers.group21.spa.model;

import org.junit.jupiter.api.Test;
import se.chalmers.group21.spa.model.entities.TaskList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class handles the tests of the logic for the class TaskList.
 * @author Thyra Öfverström
 */

public class TaskListTest {
    @Test
    void testCreateTaskList(){
        TaskList taskList = new TaskList("Test", 0);

        assertNotNull(taskList);
        assertEquals("Test", taskList.getTitle());
    }

    @Test
    void testRenameTaskList(){
        TaskList taskList = new TaskList("Test", 0);
        taskList.rename("done");

        assertEquals("done", taskList.getTitle());
    }

    @Test
    void testReoderTaskList(){
        TaskList taskList = new TaskList("Test", 0);
        int newPosition = 2;
        taskList.reorder(newPosition);

        assertEquals(newPosition, taskList.getPosition());
    }
}
