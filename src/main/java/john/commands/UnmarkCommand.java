package john.commands;

import john.exceptions.JohnException;
import john.storage.Storage;
import john.tasks.Task;
import john.tasks.TaskList;

/**
 * Command to unmark a task, setting it back to incomplete status.
 * Updates the task status and saves the changes to storage.
 */
public class UnmarkCommand implements Command {

    /**
     * Executes the unmark command to mark a specified task as not done.
     *
     * @param taskList The task list containing the task to unmark
     * @param storage The storage system for persisting changes
     * @param description The task number to unmark (1-indexed)
     * @return A confirmation message with the unmarked task details
     * @throws JohnException If the task number is invalid
     */
    @Override
    public String execute(TaskList taskList, Storage storage, String description) throws JohnException {
        if (!description.matches("\\d+")) {
            throw new JohnException("Please input a valid task number.");
        }

        Task task = taskList.getTask(Integer.parseInt(description) - 1);
        task.markUndone();
        storage.save(taskList);

        return "OK, I've marked this task as not done yet:\n"
                + task
                + "\n";
    }
}
