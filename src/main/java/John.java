import java.util.Scanner;

import Exceptions.JohnException;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.TaskList;
import Tasks.Todo;
import Ui.JohnUi;

public class John {

    private JohnUi ui;

    enum Command {
        LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT
    }

    public John() {
        ui = new JohnUi();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        ui.printLine();
        System.out.println("Hello! I'm John :)\nWhat can I do for you?");
        ui.printLine();
        System.out.println();

        TaskList tasklist = new TaskList();
        String input = sc.nextLine().trim();
        while (!input.equals("bye")) {
            ui.printLine();
            try {
                String[] userInput = input.split(" ", 2);
                String command = userInput[0];
                String description = (userInput.length > 1) ? userInput[1].trim() : "";
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
                    tasklist.save();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(task);
                    break;
                case UNMARK:
                    if (!description.matches("\\d+")) {
                        throw new JohnException("Please input a valid task number.");
                    }
                    task = tasklist.getTask(Integer.parseInt(description) - 1);
                    task.markUndone();
                    tasklist.save();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(task);
                    break;
                case DELETE:
                    if (!description.matches("\\d+")) {
                        throw new JohnException("Please input a valid task number.");
                    }
                    task = tasklist.getTask(Integer.parseInt(description) - 1);
                    tasklist.deleteTask(Integer.parseInt(description) - 1);
                    tasklist.save();
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
                    tasklist.save();
                    System.out.println("Got it. I've added this task:");
                    System.out.println(todo);
                    System.out.println("You now have " + tasklist.getSize() + " tasks in the list.");
                    break;
                case DEADLINE:
                    Deadline deadline = getDeadline(description);
                    tasklist.addTask(deadline);
                    tasklist.save();
                    System.out.println("Got it. I've added this task:");
                    System.out.println(deadline);
                    System.out.println("You now have " + tasklist.getSize() + " tasks in the list.");
                    break;
                case EVENT:
                    Event event = getEvent(description);
                    tasklist.addTask(event);
                    tasklist.save();
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
        new John().run();
    }

    private static Deadline getDeadline(String description) throws JohnException {
        String[] deadlineDescription = description.split("\\s*/by\\s*", 2);
        if (deadlineDescription.length < 2
                || deadlineDescription[0].isBlank()
                || deadlineDescription[1].isBlank()
                || deadlineDescription[1].contains("/by")) { // this line's check ensures only one /by is used
            throw new JohnException("Deadline command must include a description and a deadline.");
        }
        return new Deadline(deadlineDescription[0], deadlineDescription[1]);
    }

    private static Event getEvent(String description) throws JohnException {
        int fromIndex = description.indexOf("/from");
        int toIndex = description.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
            throw new JohnException("Event command must include /from and /to keywords, in the correct order.");
        }

        String[] eventDescription = description.split("\\s*/from\\s*|\\s*/to\\s*", 3);
        if (eventDescription.length < 3
                || eventDescription[0].isBlank()
                || eventDescription[1].isBlank()
                || eventDescription[2].isBlank()
                || eventDescription[2].contains("/from")
                || eventDescription[2].contains("/to")) {
            throw new JohnException("Event command must include a description, start date, and end date.");
        }
        return new Event(eventDescription[0], eventDescription[1], eventDescription[2]);
    }
}
