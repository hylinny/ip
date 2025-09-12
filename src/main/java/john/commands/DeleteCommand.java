package john.commands;

import john.exceptions.JohnException;
import john.storage.Storage;
import john.tasks.Task;
import john.tasks.TaskList;

/**
 * Command to delete a task from the task list.
 * Removes the specified task and saves the changes to storage.
 */
public class DeleteCommand implements Command {

    /**
     * Executes the delete command to remove a specified task from the list.
     *
     * @param taskList The task list containing the task to delete
     * @param storage The storage system for persisting changes
     * @param description The task number to delete (1-indexed)
     * @return A confirmation message with the deleted task details and updated count
     * @throws JohnException If the task number is invalid
     */
    @Override
    public String execute(TaskList taskList, Storage storage, String description) throws JohnException {
        if (!description.matches("\\d+")) {
            throw new JohnException("Please input a valid task number.");
        }

        Task task = taskList.getTask(Integer.parseInt(description) - 1);
        taskList.deleteTask(Integer.parseInt(description) - 1);
        storage.save(taskList);

        return "Noted. I've removed this task:\n"
                + task
                + "\n"
                + "You now have "
                + taskList.getSize()
                + " tasks in the list.";
    }
}
