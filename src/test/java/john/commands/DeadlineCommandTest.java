package john.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.exceptions.JohnException;
import john.storage.Storage;
import john.tasks.Deadline;
import john.tasks.TaskList;

/**
 * Test class for DeadlineCommand functionality.
 */
public class DeadlineCommandTest {

    private DeadlineCommand deadlineCommand;
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        deadlineCommand = new DeadlineCommand();
        taskList = new TaskList();
        storage = new Storage("./test_data.txt");
    }

    @Test
    public void testExecuteValidDeadline() throws JohnException {
        String result = deadlineCommand.execute(taskList, storage, "Submit assignment /by 2023-12-25T23:59");

        assertEquals(1, taskList.getSize());
        assertTrue(result.contains("Submit assignment"));

        Deadline addedTask = (Deadline) taskList.getTask(0);
        assertEquals("Submit assignment", addedTask.getDescription());
    }

    @Test
    public void testExecuteMultipleDeadlines() throws JohnException {
        deadlineCommand.execute(taskList, storage, "First deadline /by 2023-12-25T10:00");
        deadlineCommand.execute(taskList, storage, "Second deadline /by 2023-12-26T15:30");

        assertEquals(2, taskList.getSize());
        assertEquals("First deadline", taskList.getTask(0).getDescription());
        assertEquals("Second deadline", taskList.getTask(1).getDescription());
    }

    @Test
    public void testExecuteWithDifferentDateFormats() throws JohnException {
        String result1 = deadlineCommand.execute(taskList, storage, "Task 1 /by 2023-12-25");
        String result2 = deadlineCommand.execute(taskList, storage, "Task 2 /by 25/12/2023 1400");

        assertEquals(2, taskList.getSize());
        assertTrue(result1.contains("Task 1"));
        assertTrue(result2.contains("Task 2"));
    }

    @Test
    public void testExecuteInvalidDeadlineFormat() {
        assertThrows(JohnException.class, () ->
                deadlineCommand.execute(taskList, storage, "Invalid deadline format")
        );

        assertThrows(JohnException.class, () ->
                deadlineCommand.execute(taskList, storage, "Task /by invalid-date")
        );

        assertThrows(JohnException.class, () ->
                deadlineCommand.execute(taskList, storage, "/by 2023-12-25T23:59")
        );

        assertThrows(JohnException.class, () ->
                deadlineCommand.execute(taskList, storage, "Submit assignment /by")
        );

        assertEquals(0, taskList.getSize());
    }

}
