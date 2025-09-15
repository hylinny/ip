package john.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.exceptions.JohnException;

/**
 * Test class for TaskList functionality including adding, deleting, and retrieving tasks.
 */
public class TaskListTest {

    private TaskList taskList;
    private Todo todo1;
    private Todo todo2;
    private Deadline deadline1;

    @BeforeEach
    public void setUp() throws JohnException {
        taskList = new TaskList();
        todo1 = new Todo("Buy groceries");
        todo2 = new Todo("Walk the dog");
        deadline1 = new Deadline("Submit assignment", "2023-12-25T23:59");
    }

    @Test
    public void testEmptyTaskList() {
        assertEquals(0, taskList.getSize());
        assertEquals("", taskList.listTasks());
        assertTrue(taskList.getTasks().isEmpty());
    }

    @Test
    public void testAddTask() {
        taskList.addTask(todo1);
        assertEquals(1, taskList.getSize());
        assertEquals(todo1, taskList.getTasks().get(0));

        taskList.addTask(todo2);
        assertEquals(2, taskList.getSize());
        assertEquals(todo2, taskList.getTasks().get(1));
    }

    @Test
    public void testGetTask() throws JohnException {
        taskList.addTask(todo1);
        taskList.addTask(deadline1);

        assertEquals(todo1, taskList.getTask(0));
        assertEquals(deadline1, taskList.getTask(1));
    }

    @Test
    public void testGetTaskOutOfBounds() {
        assertThrows(JohnException.class, () -> {
            taskList.getTask(0); // task list is empty
        });

        taskList.addTask(todo1);
        assertThrows(JohnException.class, () -> {
            taskList.getTask(1); // only one task at index 0
        });

        assertThrows(JohnException.class, () -> {
            taskList.getTask(-1);
        });
    }

    @Test
    public void testDeleteTask() throws JohnException {
        taskList.addTask(todo1);
        taskList.addTask(todo2);
        taskList.addTask(deadline1);

        assertEquals(3, taskList.getSize());

        taskList.deleteTask(1); // Remove todo2
        assertEquals(2, taskList.getSize());
        assertEquals(todo1, taskList.getTask(0));
        assertEquals(deadline1, taskList.getTask(1));
    }

    @Test
    public void testDeleteTaskOutOfBounds() {
        assertThrows(JohnException.class, () -> {
            taskList.deleteTask(0);
        });

        taskList.addTask(todo1);
        assertThrows(JohnException.class, () -> {
            taskList.deleteTask(1);
        });

        assertThrows(JohnException.class, () -> {
            taskList.deleteTask(-1);
        });
    }

    @Test
    public void testListTasks() {
        assertEquals("", taskList.listTasks());

        taskList.addTask(todo1);
        taskList.addTask(todo2);

        String expected = "1. [T][ ] Buy groceries\n2. [T][ ] Walk the dog\n";
        assertEquals(expected, taskList.listTasks());
    }

    @Test
    public void testConstructorWithExistingTasks() throws JohnException {
        List<Task> existingTasks = new ArrayList<>();
        existingTasks.add(todo1);
        existingTasks.add(deadline1);

        TaskList newTaskList = new TaskList(existingTasks);
        assertEquals(2, newTaskList.getSize());
        assertEquals(todo1, newTaskList.getTask(0));
        assertEquals(deadline1, newTaskList.getTask(1));
    }

    @Test
    public void testTaskListWithMarkedTasks() throws JohnException {
        taskList.addTask(todo1);
        taskList.addTask(todo2);

        todo1.markDone();

        String expected = "1. [T][X] Buy groceries\n2. [T][ ] Walk the dog\n";
        assertEquals(expected, taskList.listTasks());
    }
}
