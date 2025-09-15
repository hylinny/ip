package john.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.exceptions.JohnException;
import john.storage.Storage;
import john.tasks.TaskList;
import john.tasks.Todo;

/**
 * Test class for DeleteCommand functionality.
 */
public class DeleteCommandTest {

    private DeleteCommand deleteCommand;
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        deleteCommand = new DeleteCommand();
        taskList = new TaskList();
        storage = new Storage("./test_data.txt");

        // Add some test tasks
        taskList.addTask(new Todo("Task 1"));
        taskList.addTask(new Todo("Task 2"));
        taskList.addTask(new Todo("Task 3"));
    }

    @Test
    public void testExecuteValidDelete() throws JohnException {
        assertEquals(3, taskList.getSize());

        String result = deleteCommand.execute(taskList, storage, "2");

        assertEquals(2, taskList.getSize());
        assertTrue(result.contains("Task 2"));
        assertTrue(result.contains("You now have 2 tasks in the list"));

        // Verify the correct task was removed
        assertEquals("Task 1", taskList.getTask(0).getDescription());
        assertEquals("Task 3", taskList.getTask(1).getDescription());
    }

    @Test
    public void testExecuteDeleteTask() throws JohnException {
        String result = deleteCommand.execute(taskList, storage, "1");

        assertEquals(2, taskList.getSize());
        assertTrue(result.contains("Task 1"));
        assertEquals("Task 2", taskList.getTask(0).getDescription());
        assertEquals("Task 3", taskList.getTask(1).getDescription());
    }

    @Test
    public void testExecuteInvalidInput() {
        assertThrows(JohnException.class, () -> {
            deleteCommand.execute(taskList, storage, "0");
        });

        assertThrows(JohnException.class, () -> {
            deleteCommand.execute(taskList, storage, "4");
        });

        assertThrows(JohnException.class, () -> {
            deleteCommand.execute(taskList, storage, "-1");
        });

        assertThrows(JohnException.class, () -> {
            deleteCommand.execute(taskList, storage, "abc");
        });

        assertThrows(JohnException.class, () -> {
            deleteCommand.execute(taskList, storage, "");
        });

        assertThrows(JohnException.class, () -> {
            deleteCommand.execute(taskList, storage, "1.5");
        });

        assertEquals(3, taskList.getSize());
    }
}
