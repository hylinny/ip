import java.util.Scanner;

public class John {

    enum Command {
        LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        John.printLine();
        System.out.println("Hello! I'm John :)\nWhat can I do for you?");
        John.printLine();
        System.out.println();

        TaskList tasklist = new TaskList(100);
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            John.printLine();
            try {
                String[] parts = input.split(" ", 2);
                String command = parts[0];
                String description = "-1";
                if (!input.equals("list")) {
                    description = parts[1];
                }
                Command cmd = Command.valueOf(command.toUpperCase());
                Task task;
                String desc;
                String startDate;
                String endDate;
                switch (cmd) {
                case LIST:
                    System.out.println("Here are the tasks in your list:");
                    tasklist.printTasks();
                    break;
                case MARK:
                    task = tasklist.getTask(Integer.parseInt(description) - 1);
                    task.isDone = true;
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(task);
                    break;
                case UNMARK:
                    task = tasklist.getTask(Integer.parseInt(description) - 1);
                    task.isDone = false;
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(task);
                    break;
                case DELETE:
                    task = tasklist.getTask(Integer.parseInt(description) - 1);
                    tasklist.deleteTask(Integer.parseInt(description) - 1);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(task);
                    System.out.println("You now have " + tasklist.getSize() + " tasks in the list.");
                    break;
                case TODO:
                    Todo todo = new Todo(description);
                    tasklist.addTask(todo);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(todo);
                    System.out.println("You now have " + tasklist.getSize() + " tasks in the list.");
                    break;
                case DEADLINE:
                    desc = description.split(" /by ", 2)[0];
                    endDate = description.split(" /by ", 2)[1];
                    Deadline deadline = new Deadline(desc, endDate);
                    tasklist.addTask(deadline);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(deadline);
                    System.out.println("You now have " + tasklist.getSize() + " tasks in the list.");
                    break;
                case EVENT:
                    desc = description.split(" /from ", 2)[0];
                    startDate = description.split(" /from ", 2)[1].split(" /to ", 2)[0];
                    endDate = description.split(" /to ", 2)[1];
                    Event event = new Event(desc, startDate, endDate);
                    tasklist.addTask(event);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(event);
                    System.out.println("You now have " + tasklist.getSize() + " tasks in the list.");
                    break;
                default:
                    throw new JohnException("Unexpected error occurred.");
                }
            } catch (JohnException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: Command used incorrectly. Please check the command format.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please input a valid task number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Please input a valid command.");
            }
            John.printLine();
            System.out.println();
            input = sc.nextLine();
        }
        John.printLine();
        System.out.println("Bye. Hope to see you again soon!");
        John.printLine();
        sc.close();
    }

    public static void printLine() {
        char lineChar = '_';
        int lineLength = 50;
        for (int i = 0; i < lineLength; i++) {
            System.out.print(lineChar);
        }
        System.out.println();
    }
}
