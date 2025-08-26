package Ui;

public class JohnUi {
    public static final String LINE = "__________________________________________________";

    public void printLine() {
        System.out.println(LINE);
    }

    public void showLoadingError() {
        System.out.println("Error loading tasks from file. Initialising an empty task list.");
    }
}
