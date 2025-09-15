package john.commands;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
     * Uses Java streams for efficient filtering and processing.
     *
     * @param taskList The task list to search through
     * @param storage The storage system (not used for finding)
     * @param description The keyword to search for in task descriptions
     * @return A formatted string containing all matching tasks
     * @throws JohnException If no keyword is provided
     */
    @Override
    public String execute(TaskList taskList, Storage storage, String description) throws JohnException {
        assert taskList != null : "TaskList should not be null";
        assert description != null : "Description should not be null";

        if (description.isBlank()) {
            throw new JohnException("Find command must include a keyword.");
        }

        // Use streams to filter and collect matching tasks
        List<Task> matchingTasks = taskList.getTasks()
                .stream()
                .filter(task -> {
                    assert task != null : "Task from task list should not be null";
                    return task.getDescription().contains(description);
                })
                .toList();

        StringBuilder output = new StringBuilder();
        output.append("Here are the matching tasks in your list:\n");

        if (matchingTasks.isEmpty()) {
            output.append("No matching tasks found.\n");
        } else {
            // Use streams with atomic counter for numbering
            AtomicInteger counter = new AtomicInteger(1);
            String formattedTasks = matchingTasks.stream()
                    .map(task -> counter.getAndIncrement() + ". " + task)
                    .collect(Collectors.joining("\n"));
            output.append(formattedTasks).append("\n");
        }

        return output.toString();
    }
}
