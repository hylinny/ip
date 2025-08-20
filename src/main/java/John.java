import java.util.Scanner;

public class John {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        John.printLine();
        System.out.println("Hello! I'm John :)\nWhat can I do for you?");
        John.printLine();
        System.out.println();

        String input = sc.nextLine();
        while (!input.equals("bye")) {
            John.printLine();
            System.out.println(input);
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
