package john.commands;

import john.exceptions.JohnException;
import john.storage.Storage;
import john.tasks.TaskList;
import john.tasks.Todo;

/**
 * Command to create and add a new Todo task to the task list.
 * Todo tasks are simple tasks with just a description.
 */
public class TodoCommand implements Command {

    /**
     * Executes the todo command to create and add a new Todo task.
     *
     * @param taskList The task list to add the new todo to
     * @param storage The storage system for persisting changes
     * @param description The description of the todo task
     * @return A confirmation message with the added task details and updated count
     * @throws JohnException If no description is provided
     * @throws JohnException If saving to storage fails
     */
    @Override
    public String execute(TaskList taskList, Storage storage, String description) throws JohnException {
        if (description.isBlank()) {
            throw new JohnException("Todo command must include a description.");
        }

        Todo todo = new Todo(description);
        taskList.addTask(todo);
        storage.save(taskList);

        return "Got it. I've added this task:\n"
                + todo
                + "\n"
                + "You now have "
                + taskList.getSize()
                + " tasks in the list.";
    }
}
