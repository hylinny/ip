package john.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for creating command objects based on command strings.
 * Maintains a mapping of command names to their corresponding command implementations.
 */
public class CommandFactory {
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("LIST", new ListCommand());
        commands.put("FIND", new FindCommand());
        commands.put("MARK", new MarkCommand());
        commands.put("UNMARK", new UnmarkCommand());
        commands.put("DELETE", new DeleteCommand());
        commands.put("TODO", new TodoCommand());
        commands.put("DEADLINE", new DeadlineCommand());
        commands.put("EVENT", new EventCommand());
    }

    /**
     * Creates a command object based on the given command string.
     *
     * @param commandString The command string (case-insensitive)
     * @return The corresponding command object
     * @throws IllegalArgumentException If the command string is not recognized
     */
    public static Command getCommand(String commandString) {
        Command command = commands.get(commandString.toUpperCase());
        if (command == null) {
            throw new IllegalArgumentException("Unknown command: " + commandString);
        }
        return command;
    }
}
