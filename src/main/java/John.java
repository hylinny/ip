import john.exceptions.JohnException;
import john.parser.Parser;
import john.storage.Storage;
import john.tasks.Deadline;
import john.tasks.Event;
import john.tasks.Task;
import john.tasks.TaskList;
import john.tasks.Todo;

/**
 * Entry point and command loop for the John task manager application.
 */
public class John {
    private final String filePath = "./data/john.txt";

    private Storage storage;
    private TaskList tasklist;

    enum Command {
        LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, FIND
    }

    /**
     * Constructs a John application instance bound to a storage file path.
     * It attempts to load tasks from storage, falling back to an empty list
     * and showing a UI message if loading fails.
     *
     */
    public John() {
        storage = new Storage(filePath);
        try {
            tasklist = new TaskList(storage.load());
        } catch (JohnException e) {
            tasklist = new TaskList();
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        StringBuilder output = new StringBuilder();
        try {
            String[] pair = Parser.parse(input);
            String command = pair[0];
            String description = pair[1];
            Command cmd = Command.valueOf(command.toUpperCase());
            Task task;

            switch (cmd) {
            case LIST:
                output.append("Here are the tasks in your list:\n");
                output.append(tasklist.listTasks());
                break;
            case FIND:
                if (description.isBlank()) {
                    throw new JohnException("Find command must include a keyword.");
                }
                output.append("Here are the matching tasks in your list:\n");
                int count = 0;
                for (int i = 0; i < tasklist.getSize(); i++) {
                    Task t = tasklist.getTask(i);
                    if (t.getDescription().contains(description)) {
                        output.append((count + 1)).append(". ").append(t).append("\n");
                        count++;
                    }
                }
                if (count == 0) {
                    output.append("No matching tasks found.\n");
                }
                break;
            case MARK:
                if (!description.matches("\\d+")) {
                    throw new JohnException("Please input a valid task number.");
                }
                task = tasklist.getTask(Integer.parseInt(description) - 1);
                task.markDone();
                storage.save(tasklist);
                output.append("Nice! I've marked this task as done:\n");
                output.append(task).append("\n");
                break;
            case UNMARK:
                if (!description.matches("\\d+")) {
                    throw new JohnException("Please input a valid task number.");
                }
                task = tasklist.getTask(Integer.parseInt(description) - 1);
                task.markUndone();
                storage.save(tasklist);
                output.append("OK, I've marked this task as not done yet:\n");
                output.append(task).append("\n");
                break;
            case DELETE:
                if (!description.matches("\\d+")) {
                    throw new JohnException("Please input a valid task number.");
                }
                task = tasklist.getTask(Integer.parseInt(description) - 1);
                tasklist.deleteTask(Integer.parseInt(description) - 1);
                storage.save(tasklist);
                output.append("Noted. I've removed this task:\n");
                output.append(task).append("\n");
                output.append("You now have ").append(tasklist.getSize()).append(" tasks in the list.");
                break;
            case TODO:
                if (description.isBlank()) {
                    throw new JohnException("Todo command must include a description.");
                }
                Todo todo = new Todo(description);
                tasklist.addTask(todo);
                storage.save(tasklist);
                output.append("Got it. I've added this task:\n");
                output.append(todo).append("\n");
                output.append("You now have ").append(tasklist.getSize()).append(" tasks in the list.");
                break;
            case DEADLINE:
                Deadline deadline = Parser.getDeadline(description);
                tasklist.addTask(deadline);
                storage.save(tasklist);
                output.append("Got it. I've added this task:\n");
                output.append(deadline).append("\n");
                output.append("You now have ").append(tasklist.getSize()).append(" tasks in the list.");
                break;
            case EVENT:
                Event event = Parser.getEvent(description);
                tasklist.addTask(event);
                storage.save(tasklist);
                output.append("Got it. I've added this task:\n");
                output.append(event).append("\n");
                output.append("You now have ").append(tasklist.getSize()).append(" tasks in the list.");
                break;
            default:
                throw new JohnException("This line should not be reached.");
            }
        } catch (JohnException e) {
            output = new StringBuilder("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            output = new StringBuilder("Error: Please input a valid command.");
        }
        return output.toString();
    }

    public static void main(String[] args) {
        new John();
    }
}
