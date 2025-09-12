package john.commands;

import john.exceptions.JohnException;
import john.parser.Parser;
import john.storage.Storage;
import john.tasks.Deadline;
import john.tasks.TaskList;

/**
 * Command to create and add a new Deadline task to the task list.
 * Deadline tasks have a description and a due date/time.
 */
public class DeadlineCommand implements Command {

    /**
     * Executes the deadline command to create and add a new Deadline task.
     *
     * @param taskList The task list to add the new deadline to
     * @param storage The storage system for persisting changes
     * @param description The description and due date information for the deadline
     * @return A confirmation message with the added task details and updated count
     * @throws JohnException If parsing the deadline fails or saving to storage fails
     */
    @Override
    public String execute(TaskList taskList, Storage storage, String description) throws JohnException {
        Deadline deadline = Parser.getDeadline(description);
        taskList.addTask(deadline);
        storage.save(taskList);

        return "Got it. I've added this task:\n"
                + deadline
                + "\n"
                + "You now have "
                + taskList.getSize()
                + " tasks in the list.";
    }
}
