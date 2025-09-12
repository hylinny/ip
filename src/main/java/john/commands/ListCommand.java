package john.commands;

import john.storage.Storage;
import john.tasks.TaskList;

/**
 * Command to list all tasks in the task list.
 * Displays all tasks with their current status and details.
 */
public class ListCommand implements Command {

    /**
     * Executes the list command to display all tasks.
     *
     * @param taskList The task list containing all tasks
     * @param storage The storage system (not used for listing)
     * @param description The command description (not used for listing)
     * @return A formatted string containing all tasks in the list
     */
    @Override
    public String execute(TaskList taskList, Storage storage, String description) {
        return "Here are the tasks in your list:\n" + taskList.listTasks();
    }
}
