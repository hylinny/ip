package john.commands;

import john.exceptions.JohnException;
import john.storage.Storage;
import john.tasks.TaskList;

/**
 * Represents an executable command in the John task manager application.
 * All command implementations should extend this interface to provide
 * consistent command execution behavior.
 */
public interface Command {

    /**
     * Executes the command with the given parameters.
     *
     * @param taskList The task list to operate on
     * @param storage The storage system for persistence
     * @param description The command description or parameters
     * @return A string response to be displayed to the user
     * @throws JohnException If the command execution fails
     */
    String execute(TaskList taskList, Storage storage, String description) throws JohnException;
}
