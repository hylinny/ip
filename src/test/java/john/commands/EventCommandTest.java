package john.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.exceptions.JohnException;
import john.storage.Storage;
import john.tasks.Event;
import john.tasks.TaskList;

/**
 * Test class for EventCommand functionality.
 */
public class EventCommandTest {

    private EventCommand eventCommand;
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        eventCommand = new EventCommand();
        taskList = new TaskList();
        storage = new Storage("./test_data.txt");
    }

    @Test
    public void testExecuteValidEvent() throws JohnException {
        String result = eventCommand.execute(taskList, storage,
            "Team meeting /from 2023-12-25T10:00 /to 2023-12-25T11:00");

        assertEquals(1, taskList.getSize());
        assertTrue(result.contains("Team meeting"));

        Event addedTask = (Event) taskList.getTask(0);
        assertEquals("Team meeting", addedTask.getDescription());
    }

    @Test
    public void testExecuteMultipleEvents() throws JohnException {
        String result1 = eventCommand.execute(taskList, storage,
            "First meeting /from 2023-12-25T10:00 /to 2023-12-25T11:00");
        String result2 = eventCommand.execute(taskList, storage,
            "Second meeting /from 2023-12-26T14:00 /to 2023-12-26T15:30");

        assertEquals(2, taskList.getSize());
        assertEquals("First meeting", taskList.getTask(0).getDescription());
        assertEquals("Second meeting", taskList.getTask(1).getDescription());
        assertTrue(result1.contains("First meeting"));
        assertTrue(result2.contains("Second meeting"));
    }

    @Test
    public void testExecuteWithDifferentDateFormats() throws JohnException {
        String result1 = eventCommand.execute(taskList, storage,
            "Conference /from 2023-12-25 /to 2023-12-26");
        String result2 = eventCommand.execute(taskList, storage,
            "Workshop /from 25/12/2023 1400 /to 25/12/2023 1600");

        assertEquals(2, taskList.getSize());
        assertEquals("Conference", taskList.getTask(0).getDescription());
        assertEquals("Workshop", taskList.getTask(1).getDescription());
        assertTrue(result1.contains("Conference"));
        assertTrue(result2.contains("Workshop"));
    }

    @Test
    public void testExecuteInvalidEventFormat() {
        assertThrows(JohnException.class, () ->
                eventCommand.execute(taskList, storage, "Invalid event format")
        );

        assertThrows(JohnException.class, () ->
                eventCommand.execute(taskList, storage, "Meeting /from invalid-date /to valid-date")
        );

        assertEquals(0, taskList.getSize());
    }

    @Test
    public void testExecuteMissingDescription() {
        assertThrows(JohnException.class, () ->
                eventCommand.execute(taskList, storage, "/from 2023-12-25T10:00 /to 2023-12-25T11:00")
        );
    }

    @Test
    public void testExecuteMissingFromDate() {
        assertThrows(JohnException.class, () ->
                eventCommand.execute(taskList, storage, "Meeting /from /to 2023-12-25T11:00")
        );
    }

    @Test
    public void testExecuteMissingToDate() {
        assertThrows(JohnException.class, () ->
                eventCommand.execute(taskList, storage, "Meeting /from 2023-12-25T10:00 /to")
        );
    }

    @Test
    public void testExecuteInvalidStartDate() {
        assertThrows(JohnException.class, () ->
                eventCommand.execute(taskList, storage, "Meeting /from invalid-start /to 2023-12-25T11:00")
        );
    }

    @Test
    public void testExecuteInvalidEndDate() {
        assertThrows(JohnException.class, () ->
                eventCommand.execute(taskList, storage, "Meeting /from 2023-12-25T10:00 /to invalid-end")
        );
    }
}
