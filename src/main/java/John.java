import java.util.Scanner;

public class John {

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
            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                tasklist.printTasks();
            } else if (input.startsWith("mark")) {
                int taskIndex = Integer.parseInt(input.split(" ")[1]) - 1;
                Task task = tasklist.getTask(taskIndex);
                task.isDone = true;
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(task);
            } else if (input.startsWith("unmark")) {
                int taskIndex = Integer.parseInt(input.split(" ")[1]) - 1;
                Task task = tasklist.getTask(taskIndex);
                task.isDone = false;
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(task);
            } else {
                String[] parts = input.split(" ", 2);
                String command = parts[0];
                String description = parts[1];
                if (command.equals("todo")) {
                    Todo todo = new Todo(description);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(todo);
                    tasklist.addTask(todo);
                } else if (command.equals("deadline")) {
                    String desc = description.split(" /by ", 2)[0];
                    String endDate = description.split(" /by ", 2)[1];
                    Deadline deadline = new Deadline(desc, endDate);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(deadline);
                    tasklist.addTask(deadline);
                } else if (command.equals("event")) {
                    String desc = description.split(" /from ", 2)[0];
                    String startDate = description.split(" /from ", 2)[1].split(" /to ", 2)[0];
                    String endDate = description.split(" /to ", 2)[1];
                    Event event = new Event(desc, startDate, endDate);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(event);
                    tasklist.addTask(event);
                } else {
                    System.out.println("I'm sorry, I don't understand that command.");
                }
                System.out.println("You now have " + tasklist.getSize() + " tasks in the list.");
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
        int linelength = 50;
        for (int i = 0; i < linelength; i++) {
            System.out.print(lineChar);
        }
        System.out.println();
    }
}
