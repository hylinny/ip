package John;

import java.util.Scanner;

import John.Exceptions.JohnException;
import John.Parser.Parser;
import John.Storage.Storage;
import John.Tasks.*;
import John.Ui.JohnUi;

/**
 * Entry point and command loop for the John task manager application.
 */
public class John {

    private Storage storage;
    private TaskList tasklist;
    private JohnUi ui;

    enum Command {
        LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT
    }

    /**
     * Constructs a John application instance bound to a storage file path.
     * It attempts to load tasks from storage, falling back to an empty list
     * and showing a UI message if loading fails.
     *
     * @param filePath path to the persistence file used by {@link Storage}
     */
    public John(String filePath) {
        ui = new JohnUi();
        storage = new Storage(filePath);
        try {
            tasklist = new TaskList(storage.load());
        } catch (JohnException e) {
            ui.showLoadingError();
            tasklist = new TaskList();
        }
    }

    /**
     * Starts the interactive command loop, processing user input until "bye".
     * Commands are parsed by {@link Parser} and may modify the {@link TaskList}
     * and underlying {@link Storage}.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        ui.printLine();
        System.out.println("Hello! I'm John. :)\nWhat can I do for you?");
        ui.printLine();
        System.out.println();

        String input = sc.nextLine().trim();
        while (!input.equals("bye")) {
            ui.printLine();
            try {
                String[] pair = Parser.parse(input);
                String command = pair[0];
                String description = pair[1];
                Command cmd = Command.valueOf(command.toUpperCase());
                Task task;

                switch (cmd) {
                case LIST:
                    System.out.println("Here are the tasks in your list:");
                    tasklist.printTasks();
                    break;
                case MARK:
                    if (!description.matches("\\d+")) {
                        throw new JohnException("Please input a valid task number.");
                    }
                    task = tasklist.getTask(Integer.parseInt(description) - 1);
                    task.markDone();
                    storage.save(tasklist);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(task);
                    break;
                case UNMARK:
                    if (!description.matches("\\d+")) {
                        throw new JohnException("Please input a valid task number.");
                    }
                    task = tasklist.getTask(Integer.parseInt(description) - 1);
                    task.markUndone();
                    storage.save(tasklist);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(task);
                    break;
                case DELETE:
                    if (!description.matches("\\d+")) {
                        throw new JohnException("Please input a valid task number.");
                    }
                    task = tasklist.getTask(Integer.parseInt(description) - 1);
                    tasklist.deleteTask(Integer.parseInt(description) - 1);
                    storage.save(tasklist);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(task);
                    System.out.println("You now have " + tasklist.getSize() + " tasks in the list.");
                    break;
                case TODO:
                    if (description.isBlank()) {
                        throw new JohnException("Todo command must include a description.");
                    }
                    Todo todo = new Todo(description);
                    tasklist.addTask(todo);
                    storage.save(tasklist);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(todo);
                    System.out.println("You now have " + tasklist.getSize() + " tasks in the list.");
                    break;
                case DEADLINE:
                    Deadline deadline = Parser.getDeadline(description);
                    tasklist.addTask(deadline);
                    storage.save(tasklist);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(deadline);
                    System.out.println("You now have " + tasklist.getSize() + " tasks in the list.");
                    break;
                case EVENT:
                    Event event = Parser.getEvent(description);
                    tasklist.addTask(event);
                    storage.save(tasklist);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(event);
                    System.out.println("You now have " + tasklist.getSize() + " tasks in the list.");
                    break;
                }
            } catch (JohnException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Please input a valid command.");
            }
            ui.printLine();
            System.out.println();
            input = sc.nextLine().trim();
        }
        ui.printLine();
        System.out.println("Bye. Hope to see you again soon!");
        ui.printLine();
        sc.close();
    }

    public static void main(String[] args) {
        new John("./data/john.txt").run();
    }
}
