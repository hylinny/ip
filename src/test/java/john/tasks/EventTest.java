package john.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import john.exceptions.JohnException;

/**
 * Test class for Event task functionality.
 */
public class EventTest {

    @Test
    public void testEventCreation() throws JohnException {
        Event event = new Event("Team meeting", "2023-12-25T10:00", "2023-12-25T11:00");
        assertEquals("Team meeting", event.getDescription());
        assertFalse(event.isDone);
    }

    @Test
    public void testEventCreationInvalidStartDate() {
        assertThrows(JohnException.class, () ->
                new Event("Meeting", "invalid date", "2023-12-25T11:00")
        );
    }

    @Test
    public void testEventCreationInvalidEndDate() {
        assertThrows(JohnException.class, () ->
                new Event("Meeting", "2023-12-25T10:00", "invalid date")
        );
    }

    @Test
    public void testEventToString() throws JohnException {
        Event event = new Event("Conference", "2023-12-25T09:00", "2023-12-25T17:00");
        assertEquals("[E][ ] Conference (from: Dec 25 2023 09:00 to: Dec 25 2023 17:00)", event.toString());

        event.markDone();
        assertEquals("[E][X] Conference (from: Dec 25 2023 09:00 to: Dec 25 2023 17:00)", event.toString());
    }

    @Test
    public void testEventToFileString() throws JohnException {
        Event event = new Event("Workshop", "2023-12-25T14:00", "2023-12-25T16:00");
        assertEquals("E | 0 | Workshop | 2023-12-25T14:00 | 2023-12-25T16:00", event.toFileString());

        event.markDone();
        assertEquals("E | 1 | Workshop | 2023-12-25T14:00 | 2023-12-25T16:00", event.toFileString());
    }

    @Test
    public void testEventEquals() throws JohnException {
        Event event1 = new Event("Meeting", "2023-12-25T10:00", "2023-12-25T11:00");
        Event event2 = new Event("Meeting", "2023-12-25T10:00", "2023-12-25T11:00");
        Event event3 = new Event("Different Meeting", "2023-12-25T10:00", "2023-12-25T11:00");
        Event event4 = new Event("Meeting", "2023-12-25T09:00", "2023-12-25T11:00");
        Event event5 = new Event("Meeting", "2023-12-25T10:00", "2023-12-25T12:00");

        assertEquals(event1, event2);
        assertNotEquals(event1, event3);
        assertNotEquals(event1, event4);
        assertNotEquals(event1, event5);

        event1.markDone();
        assertNotEquals(event1, event2);
    }

    @Test
    public void testEventHashCode() throws JohnException {
        Event event1 = new Event("Meeting", "2023-12-25T10:00", "2023-12-25T11:00");
        Event event2 = new Event("Meeting", "2023-12-25T10:00", "2023-12-25T11:00");

        assertEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    public void testEventWithDateOnlyFormat() throws JohnException {
        Event event = new Event("All day event", "2023-12-25", "2023-12-26");
        assertTrue(event.toString().contains("Dec 25 2023 00:00"));
        assertTrue(event.toString().contains("Dec 26 2023 00:00"));
    }

    @Test
    public void testEventWithDifferentDateFormats() throws JohnException {
        Event event = new Event("Mixed format event", "2023-12-25 1400", "25/12/2023 1600");
        assertTrue(event.toString().contains("Dec 25 2023 14:00"));
        assertTrue(event.toString().contains("Dec 25 2023 16:00"));
    }
}
