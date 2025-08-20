import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class John {
    protected List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        John.printLine();
        System.out.println("Hello! I'm John :)\nWhat can I do for you?");
        John.printLine();
        System.out.println();

        John john = new John();
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            John.printLine();
            if (input.equals("list")) {
                for (int i = 0; i < john.tasks.size(); i++) {
                    Task task = john.tasks.get(i);
                    System.out.println((i + 1) + "." + task);
                }
            } else if (input.startsWith("mark")) {
                int taskIndex = Integer.parseInt(input.split(" ")[1]) - 1;
                if (taskIndex < 0 || taskIndex >= john.tasks.size()) {
                    System.out.println("Invalid task number.");
                } else {
                    Task task = john.tasks.get(taskIndex);
                    task.isDone = true;
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(task);
                }
            } else if (input.startsWith("unmark")) {
                int taskIndex = Integer.parseInt(input.split(" ")[1]) - 1;
                if (taskIndex < 0 || taskIndex >= john.tasks.size()) {
                    System.out.println("Invalid task number.");
                } else {
                    Task task = john.tasks.get(taskIndex);
                    task.isDone = false;
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(task);
                }
            } else {
                System.out.println("added: " + input);
                john.tasks.add(new Task(input));
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
