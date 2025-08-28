package John.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import John.Exceptions.JohnException;
import John.Tasks.Deadline;
import John.Tasks.Event;

public class ParserTest {
    @Test
    public void testParse() {
        // Add test cases for the parse method
        String input = "deadline return book /by Sunday";
        String[] result1 = Parser.parse(input);
        assertEquals(2, result1.length);
        assertEquals("deadline", result1[0]);
        assertEquals("return book /by Sunday", result1[1]);

        input = "event project meeting /from Mon 2pm /to Mon 4pm";
        String[] result2 = Parser.parse(input);
        assertEquals(2, result2.length);
        assertEquals("event", result2[0]);
        assertEquals("project meeting /from Mon 2pm /to Mon 4pm", result2[1]);
    }

    @Test
    public void testParse_exceptionThrown() {
        String input = "";
        String[] result1 = Parser.parse(input);
        assertEquals("", result1[0]);
        assertEquals("", result1[1]);

        input = "   ";
        String[] result2 = Parser.parse(input);
        assertEquals("", result2[0]);
        assertEquals("", result2[1]);

        input = "random command";
        String[] result3 = Parser.parse(input);
        assertEquals("random", result3[0]);
        assertEquals("command", result3[1]);

        input = "single";
        String[] result4 = Parser.parse(input);
        assertEquals("single", result4[0]);
        assertEquals("", result4[1]);
    }

    @Test
    public void getDeadline() {
        String input = "return book /by 2025-01-01 1800";
        try {
            Deadline deadline = Parser.getDeadline(input);
            assertEquals(new Deadline("return book", "2025-01-01 1800"), deadline);
        } catch (JohnException e) {
            fail();
        }
    }

    @Test
    public void getDeadline_exceptionThrown() {
        String input = "/by";
        try {
            Parser.getDeadline(input);
            fail();
        } catch (JohnException e) {
            assertEquals("Deadline command must include a description and a deadline.", e.getMessage());
        }
    }

    @Test
    public void getEvent() {
        String input = "project meeting /from 2025-01-01 1400 /to 2025-01-01 1600";
        try {
            Event event = Parser.getEvent(input);
            assertEquals(new Event("project meeting", "2025-01-01 1400", "2025-01-01 1600"), event);
        } catch (JohnException e) {
            fail();
        }
    }

    @Test
    public void getEvent_exceptionThrown() {
        String input = "meeting /from 2025-01-01 1400";
        try {
            Parser.getEvent(input);
            fail();
        } catch (JohnException e) {
            assertEquals("Event command must include /from and /to keywords, in the correct order.", e.getMessage());
        }
    }
}
