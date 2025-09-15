package john.commands;

import john.exceptions.JohnException;
import john.storage.Storage;
import john.tasks.Deadline;
import john.tasks.Task;
import john.tasks.TaskList;

/**
 * Command to postpone/reschedule a deadline task to a new date.
 * Only works with deadline tasks - other task types will throw an error.
 */
public class PostponeCommand implements Command {

    /**
     * Executes the postpone command to reschedule a deadline task.
     * Format: "postpone [task_number] /to [new_date]"
     *
     * @param taskList The task list containing the task to postpone
     * @param storage The storage system for persisting changes
     * @param description The task number and new date (format: "1 /to 2024-12-25")
     * @return A confirmation message with the updated task details
     * @throws JohnException If the task number is invalid, task is not a deadline, or date format is wrong
     */
    @Override
    public String execute(TaskList taskList, Storage storage, String description) throws JohnException {
        // Parse input: "1 /to 2024-12-25"
        String[] parts = description.split("\\s+/to\\s+", 2);
        if (parts.length != 2) {
            throw new JohnException("Postpone command format: postpone [task_number] /to [new_date]");
        }

        String taskNumberString = parts[0].trim();
        String date = parts[1].trim();

        // Check that task number is numeric
        if (!taskNumberString.matches("\\d+")) {
            throw new JohnException("Please input a valid task number.");
        }

        if (date.isBlank()) {
            throw new JohnException("Please provide a new date for the deadline.");
        }

        int taskIndex = Integer.parseInt(taskNumberString) - 1;
        Task task = taskList.getTask(taskIndex);

        // Check if the task is a deadline
        if (!(task instanceof Deadline deadline)) {
            throw new JohnException(
                    "Only deadline tasks can be postponed. Task " + (taskIndex + 1) + " is not a deadline."
            );
        }

        String oldDate = deadline.getEndDate();

        // Update the deadline
        deadline.setEndDate(date);
        storage.save(taskList);

        return "My pleasure to assist you. I've postponed this deadline:\n"
                + deadline + "\n"
                + "Previous date: " + oldDate + "\n"
                + "New date: " + date;
    }
}
