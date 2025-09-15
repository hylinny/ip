package john.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import john.exceptions.JohnException;

/**
 * Test class for Task functionality including date parsing and status management.
 */
public class TaskTest {

    /**
     * Concrete implementation of Task for testing purposes.
     */
    private static class TestTask extends Task {
        public TestTask(String description) {
            super(description);
        }

        @Override
        public String toFileString() {
            return "TEST | " + (isDone ? "1" : "0") + " | " + description;
        }
    }

    @Test
    public void testTaskCreation() {
        Task task = new TestTask("Test task");
        assertEquals("Test task", task.getDescription());
        assertFalse(task.isDone);
        assertEquals("[ ]", task.getStatusIcon());
    }

    @Test
    public void testMarkDone() {
        Task task = new TestTask("Test task");
        task.markDone();
        assertTrue(task.isDone);
        assertEquals("[X]", task.getStatusIcon());
    }

    @Test
    public void testMarkUndone() {
        Task task = new TestTask("Test task");
        task.markDone();
        task.markUndone();
        assertFalse(task.isDone);
        assertEquals("[ ]", task.getStatusIcon());
    }

    @Test
    public void testToString() {
        Task task = new TestTask("Buy groceries");
        assertEquals("[ ] Buy groceries", task.toString());

        task.markDone();
        assertEquals("[X] Buy groceries", task.toString());
    }

    @Test
    public void testParseDateTimeInvalidFormat() {
        Task task = new TestTask("Test");

        assertThrows(JohnException.class, () -> task.parseDateTime("invalid date"));

        assertThrows(JohnException.class, () -> task.parseDateTime("2023/13/45"));

        assertThrows(JohnException.class, () -> task.parseDateTime(""));
    }
}
