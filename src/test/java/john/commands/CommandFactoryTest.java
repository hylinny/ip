package john.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test class for CommandFactory functionality.
 */
public class CommandFactoryTest {

    @Test
    public void testGetValidCommands() {
        assertNotNull(CommandFactory.getCommand("LIST"));
        assertNotNull(CommandFactory.getCommand("FIND"));
        assertNotNull(CommandFactory.getCommand("MARK"));
        assertNotNull(CommandFactory.getCommand("UNMARK"));
        assertNotNull(CommandFactory.getCommand("DELETE"));
        assertNotNull(CommandFactory.getCommand("TODO"));
        assertNotNull(CommandFactory.getCommand("DEADLINE"));
        assertNotNull(CommandFactory.getCommand("EVENT"));
        assertNotNull(CommandFactory.getCommand("POSTPONE"));
    }

    @Test
    public void testGetCommandCaseInsensitive() {
        assertNotNull(CommandFactory.getCommand("list"));
        assertNotNull(CommandFactory.getCommand("List"));
        assertNotNull(CommandFactory.getCommand("LIST"));
        assertNotNull(CommandFactory.getCommand("todo"));
        assertNotNull(CommandFactory.getCommand("TODO"));
    }

    @Test
    public void testGetInvalidCommand() {
        assertThrows(IllegalArgumentException.class, () -> {
            CommandFactory.getCommand("INVALID");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            CommandFactory.getCommand("random");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            CommandFactory.getCommand("");
        });
    }

    @Test
    public void testCommandTypes() {
        assertEquals(ListCommand.class, CommandFactory.getCommand("LIST").getClass());
        assertEquals(TodoCommand.class, CommandFactory.getCommand("TODO").getClass());
        assertEquals(DeadlineCommand.class, CommandFactory.getCommand("DEADLINE").getClass());
        assertEquals(EventCommand.class, CommandFactory.getCommand("EVENT").getClass());
        assertEquals(DeleteCommand.class, CommandFactory.getCommand("DELETE").getClass());
        assertEquals(MarkCommand.class, CommandFactory.getCommand("MARK").getClass());
        assertEquals(UnmarkCommand.class, CommandFactory.getCommand("UNMARK").getClass());
        assertEquals(FindCommand.class, CommandFactory.getCommand("FIND").getClass());
        assertEquals(PostponeCommand.class, CommandFactory.getCommand("POSTPONE").getClass());
    }
}
