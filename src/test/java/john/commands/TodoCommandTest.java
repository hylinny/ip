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
 * Test class for TodoCommand functionality.
 */
public class TodoCommandTest {

    private TodoCommand todoCommand;
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        todoCommand = new TodoCommand();
        taskList = new TaskList();
        storage = new Storage("./test_data.txt");
    }

    @Test
    public void testExecuteValidTodo() throws JohnException {
        String result = todoCommand.execute(taskList, storage, "Buy groceries");

        assertEquals(1, taskList.getSize());
        assertTrue(result.contains("Buy groceries"));
        assertTrue(result.contains("You now have 1 tasks in the list"));

        Todo addedTask = (Todo) taskList.getTask(0);
        assertEquals("Buy groceries", addedTask.getDescription());
    }

    @Test
    public void testExecuteEmptyDescription() {
        assertThrows(JohnException.class, () -> {
            todoCommand.execute(taskList, storage, "");
        });

        assertThrows(JohnException.class, () -> {
            todoCommand.execute(taskList, storage, "   ");
        });

        assertEquals(0, taskList.getSize());
    }

    @Test
    public void testExecuteMultipleTodos() throws JohnException {
        todoCommand.execute(taskList, storage, "First task");
        todoCommand.execute(taskList, storage, "Second task");

        assertEquals(2, taskList.getSize());
        assertEquals("First task", taskList.getTask(0).getDescription());
        assertEquals("Second task", taskList.getTask(1).getDescription());
    }
}
