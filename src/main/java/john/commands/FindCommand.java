package john.commands;

import john.exceptions.JohnException;
import john.storage.Storage;
import john.tasks.Task;
import john.tasks.TaskList;

/**
 * Command to find tasks containing a specific keyword.
 * Searches through all task descriptions and returns matching tasks.
 */
public class FindCommand implements Command {

    /**
     * Executes the find command to search for tasks containing the given keyword.
     *
     * @param taskList The task list to search through
     * @param storage The storage system (not used for finding)
     * @param description The keyword to search for in task descriptions
     * @return A formatted string containing all matching tasks
     * @throws JohnException If no keyword is provided
     */
    @Override
    public String execute(TaskList taskList, Storage storage, String description) throws JohnException {
        if (description.isBlank()) {
            throw new JohnException("Find command must include a keyword.");
        }

        StringBuilder output = new StringBuilder();
        output.append("Here are the matching tasks in your list:\n");
        int count = 0;

        for (int i = 0; i < taskList.getSize(); i++) {
            Task t = taskList.getTask(i);
            if (t.getDescription().contains(description)) {
                output.append((count + 1)).append(". ").append(t).append("\n");
                count++;
            }
        }

        if (count == 0) {
            output.append("No matching tasks found.\n");
        }

        return output.toString();
    }
}
