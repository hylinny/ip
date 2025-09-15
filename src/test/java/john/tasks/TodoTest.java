package john.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Test class for Todo task functionality.
 */
public class TodoTest {

    @Test
    public void testTodoCreation() {
        Todo todo = new Todo("Buy groceries");
        assertEquals("Buy groceries", todo.getDescription());
        assertFalse(todo.isDone);
    }

    @Test
    public void testTodoToString() {
        Todo todo = new Todo("Read book");
        assertEquals("[T][ ] Read book", todo.toString());

        todo.markDone();
        assertEquals("[T][X] Read book", todo.toString());
    }

    @Test
    public void testTodoToFileString() {
        Todo todo = new Todo("Exercise");
        assertEquals("T | 0 | Exercise", todo.toFileString());

        todo.markDone();
        assertEquals("T | 1 | Exercise", todo.toFileString());
    }

    @Test
    public void testTodoWithEmptyDescription() {
        Todo todo = new Todo("");
        assertEquals("", todo.getDescription());
        assertEquals("[T][ ] ", todo.toString());
        assertEquals("T | 0 | ", todo.toFileString());
    }
}
