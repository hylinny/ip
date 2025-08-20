public class John {
    public static void main(String[] args) {
        John.printLine();
        System.out.println("Hello! I'm John :)");
        System.out.println("What can I do for you?");
        John.printLine();
        System.out.println("Bye. Hope to see you again soon!");
        John.printLine();
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
