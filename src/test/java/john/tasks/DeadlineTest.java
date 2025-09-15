package john.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import john.exceptions.JohnException;

/**
 * Test class for Deadline task functionality.
 */
public class DeadlineTest {

    @Test
    public void testDeadlineCreation() throws JohnException {
        Deadline deadline = new Deadline("Submit assignment", "2023-12-25T23:59");
        assertEquals("Submit assignment", deadline.getDescription());
        assertFalse(deadline.isDone);
        assertEquals("Dec 25 2023 23:59", deadline.getEndDate());
    }

    @Test
    public void testDeadlineCreationInvalidDate() {
        assertThrows(JohnException.class, () -> {
            new Deadline("Submit assignment", "invalid date");
        });
    }

    @Test
    public void testDeadlineToStringFormat() throws JohnException {
        Deadline deadline = new Deadline("Finish report", "2023-12-25T18:00");
        assertEquals("[D][ ] Finish report (by: Dec 25 2023 18:00)", deadline.toString());

        deadline.markDone();
        assertEquals("[D][X] Finish report (by: Dec 25 2023 18:00)", deadline.toString());
    }

    @Test
    public void testDeadlineToFileString() throws JohnException {
        Deadline deadline = new Deadline("Exercise", "2023-12-25T06:00");
        assertEquals("D | 0 | Exercise | 2023-12-25T06:00", deadline.toFileString());

        deadline.markDone();
        assertEquals("D | 1 | Exercise | 2023-12-25T06:00", deadline.toFileString());
    }

    @Test
    public void testSetEndDate() throws JohnException {
        Deadline deadline = new Deadline("Submit project", "2023-12-25T23:59");
        assertEquals("Dec 25 2023 23:59", deadline.getEndDate());

        deadline.setEndDate("2023-12-26T10:00");
        assertEquals("Dec 26 2023 10:00", deadline.getEndDate());
    }

    @Test
    public void testSetEndDateInvalid() throws JohnException {
        Deadline deadline = new Deadline("Submit project", "2023-12-25T23:59");

        assertThrows(JohnException.class, () -> {
            deadline.setEndDate("invalid date");
        });
    }

    @Test
    public void testDeadlineEquals() throws JohnException {
        Deadline deadline1 = new Deadline("Task 1", "2023-12-25T10:00");
        Deadline deadline2 = new Deadline("Task 1", "2023-12-25T10:00");
        Deadline deadline3 = new Deadline("Task 2", "2023-12-25T10:00");
        Deadline deadline4 = new Deadline("Task 1", "2023-12-26T10:00");

        assertEquals(deadline1, deadline2);
        assertNotEquals(deadline1, deadline3);
        assertNotEquals(deadline1, deadline4);

        deadline1.markDone();
        assertNotEquals(deadline1, deadline2);
    }

    @Test
    public void testDeadlineHashCode() throws JohnException {
        Deadline deadline1 = new Deadline("Task 1", "2023-12-25T10:00");
        Deadline deadline2 = new Deadline("Task 1", "2023-12-25T10:00");

        assertEquals(deadline1.hashCode(), deadline2.hashCode());
    }

    @Test
    public void testDeadlineWithDateOnlyFormat() throws JohnException {
        Deadline deadline = new Deadline("Pay bills", "2023-12-25");
        assertEquals("Dec 25 2023 00:00", deadline.getEndDate());
        assertTrue(deadline.toString().contains("Dec 25 2023 00:00"));
    }
}
