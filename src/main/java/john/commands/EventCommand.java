package john.commands;

import john.exceptions.JohnException;
import john.parser.Parser;
import john.storage.Storage;
import john.tasks.Event;
import john.tasks.TaskList;

/**
 * Command to create and add a new Event task to the task list.
 * Event tasks have a description and a time period (from/to).
 */
public class EventCommand implements Command {

    /**
     * Executes the event command to create and add a new Event task.
     *
     * @param taskList The task list to add the new event to
     * @param storage The storage system for persisting changes
     * @param description The description and time period information for the event
     * @return A confirmation message with the added task details and updated count
     * @throws JohnException If parsing the event fails or saving to storage fails
     */
    @Override
    public String execute(TaskList taskList, Storage storage, String description) throws JohnException {
        Event event = Parser.getEvent(description);
        taskList.addTask(event);
        storage.save(taskList);

        return "My pleasure to assist you. I've added this task:\n"
                + event
                + "\n"
                + "You now have "
                + taskList.getSize()
                + " tasks in the list.";
    }
}
