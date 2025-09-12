package john.commands;

import john.exceptions.JohnException;
import john.storage.Storage;
import john.tasks.Task;
import john.tasks.TaskList;

/**
 * Command to mark a task as completed.
 * Updates the task status and saves the changes to storage.
 */
public class MarkCommand implements Command {

    /**
     * Executes the mark command to mark a specified task as done.
     *
     * @param taskList The task list containing the task to mark
     * @param storage The storage system for persisting changes
     * @param description The task number to mark (1-indexed)
     * @return A confirmation message with the marked task details
     * @throws JohnException If the task number is invalid
     */
    @Override
    public String execute(TaskList taskList, Storage storage, String description) throws JohnException {
        if (!description.matches("\\d+")) {
            throw new JohnException("Please input a valid task number.");
        }

        Task task = taskList.getTask(Integer.parseInt(description) - 1);
        task.markDone();
        storage.save(taskList);

        return "Nice! I've marked this task as done:\n" + task + "\n";
    }
}
