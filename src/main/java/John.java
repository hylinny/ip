import john.commands.Command;
import john.commands.CommandFactory;
import john.exceptions.JohnException;
import john.parser.Parser;
import john.storage.Storage;
import john.tasks.TaskList;

/**
 * Entry point and command loop for the John task manager application.
 * This class handles user input processing and delegates command execution
 * to appropriate command objects using the command pattern.
 */
public class John {
    /** The list of tasks managed by this application */
    private Storage storage;
    private String filePath = "./data/john.txt";
    private TaskList tasklist;

    /**
     * Constructs a John application instance bound to a storage file path.
     * It attempts to load tasks from storage, falling back to an empty list
     */
    public John() {
        storage = new Storage(filePath);
        try {
            tasklist = new TaskList(storage.load());
        } catch (JohnException e) {
            tasklist = new TaskList();
        }
    }

    /**
     * Generates a response for the user's chat message by parsing the input
     * and executing the appropriate command.
     *
     * @param input The user's input string containing command and parameters
     * @return A string response to be displayed to the user
     */
    public String getResponse(String input) {
        try {
            String[] pair = Parser.parse(input);
            String commandString = pair[0];
            String description = pair[1];

            Command command = CommandFactory.getCommand(commandString);
            return command.execute(tasklist, storage, description);

        } catch (JohnException e) {
            return "Error: " + e.getMessage();
        } catch (IllegalArgumentException e) {
            return "Error: Please input a valid command.";
        }
    }

    /**
     * Main method to start the John application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        new John();
    }
}
