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
                System.out.println("added: " + input);
                tasklist.addTask(new Task(input));
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
