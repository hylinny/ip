package john.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.exceptions.JohnException;
import john.storage.Storage;
import john.tasks.Deadline;
import john.tasks.TaskList;
import john.tasks.Todo;

/**
 * Test class for FindCommand functionality including stream-based filtering.
 */
public class FindCommandTest {

    private FindCommand findCommand;
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() throws JohnException {
        findCommand = new FindCommand();
        taskList = new TaskList();
        storage = new Storage("./test_data.txt");

        // Add test tasks
        taskList.addTask(new Todo("Buy groceries"));
        taskList.addTask(new Todo("Read book"));
        taskList.addTask(new Deadline("Submit assignment", "2023-12-25T23:59"));
        taskList.addTask(new Todo("Buy milk"));
        taskList.addTask(new Todo("Exercise"));
    }

    @Test
    public void testExecuteValidFind() throws JohnException {
        String result = findCommand.execute(taskList, storage, "Buy");

        assertTrue(result.contains("Buy groceries"));
        assertTrue(result.contains("Buy milk"));
        assertFalse(result.contains("Exercise"));
        assertFalse(result.contains("Submit assignment"));
        assertFalse(result.contains("Read book"));
    }

    @Test
    public void testExecuteSingleMatch() throws JohnException {
        String result = findCommand.execute(taskList, storage, "Exercise");

        assertTrue(result.contains("Exercise"));
        assertFalse(result.contains("Submit assignment"));
        assertFalse(result.contains("Buy groceries"));
        assertFalse(result.contains("Buy milk"));
        assertFalse(result.contains("Read book"));

    }

    @Test
    public void testExecuteNoMatches() throws JohnException {
        String result = findCommand.execute(taskList, storage, "nonexistent");

        assertTrue(result.contains("No matching tasks found"));
    }

    @Test
    public void testExecuteCaseSensitive() throws JohnException {
        String result = findCommand.execute(taskList, storage, "buy");

        assertTrue(result.contains("No matching tasks found"));
    }

    @Test
    public void testExecuteEmptyKeyword() {
        assertThrows(JohnException.class, () -> findCommand.execute(taskList, storage, ""));

        assertThrows(JohnException.class, () -> findCommand.execute(taskList, storage, "   "));
    }
}
