package john.commands;

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
 * Test class for PostponeCommand functionality.
 */
public class PostponeCommandTest {

    private PostponeCommand postponeCommand;
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() throws JohnException {
        postponeCommand = new PostponeCommand();
        taskList = new TaskList();
        storage = new Storage("./test_data.txt");

        // Add test tasks
        taskList.addTask(new Todo("Regular task"));
        taskList.addTask(new Deadline("Submit assignment", "2023-12-25T23:59"));
        taskList.addTask(new Deadline("Pay bills", "2023-12-30T17:00"));
    }

    @Test
    public void testExecuteValidPostpone() throws JohnException {
        String result = postponeCommand.execute(taskList, storage, "2 /to 2024-01-15T23:59");

        assertTrue(result.contains("postponed this deadline"));
        assertTrue(result.contains("Submit assignment"));
        assertTrue(result.contains("Previous date:"));
        assertTrue(result.contains("New date:"));

        // Verify the deadline was actually updated
        Deadline updatedTask = (Deadline) taskList.getTask(1);
        assertTrue(updatedTask.getEndDate().contains("Jan 15 2024"));
    }

    @Test
    public void testExecuteMultiplePostpones() throws JohnException {
        // First postpone
        postponeCommand.execute(taskList, storage, "2 /to 2024-01-15T23:59");

        // Second postpone of the same task
        String result = postponeCommand.execute(taskList, storage, "2 /to 2024-01-20T12:00");

        assertTrue(result.contains("Jan 20 2024"));

        // Verify the final date
        Deadline updatedTask = (Deadline) taskList.getTask(1);
        assertTrue(updatedTask.getEndDate().contains("Jan 20 2024"));
    }

    @Test
    public void testExecutePostponeNonDeadlineTask() {
        assertThrows(JohnException.class, () ->
                postponeCommand.execute(taskList, storage, "1 /to 2024-01-15T23:59")
        );
    }

    @Test
    public void testExecuteInvalidFormat() {
        assertThrows(JohnException.class, () ->
                postponeCommand.execute(taskList, storage, "2 2024-01-15T23:59")
        );

        assertThrows(JohnException.class, () ->
                postponeCommand.execute(taskList, storage, "2 /to")
        );

        assertThrows(JohnException.class, () ->
                postponeCommand.execute(taskList, storage, "/to 2024-01-15T23:59")
        );
    }

    @Test
    public void testExecuteInvalidTaskNumber() {
        assertThrows(JohnException.class, () ->
                postponeCommand.execute(taskList, storage, "0 /to 2024-01-15T23:59")
        );

        assertThrows(JohnException.class, () ->
                postponeCommand.execute(taskList, storage, "5 /to 2024-01-15T23:59")
        );

        assertThrows(JohnException.class, () ->
                postponeCommand.execute(taskList, storage, "abc /to 2024-01-15T23:59")
        );
    }

    @Test
    public void testExecuteInvalidDate() {
        assertThrows(JohnException.class, () ->
                postponeCommand.execute(taskList, storage, "2 /to invalid-date")
        );
    }

    @Test
    public void testExecuteBlankDate() {
        assertThrows(JohnException.class, () ->
                postponeCommand.execute(taskList, storage, "2 /to   ")
        );
    }
}
