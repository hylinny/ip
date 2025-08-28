package John.Ui;

public class JohnUi {
    private static final String LINE = "__________________________________________________";

    public void printLine() {
        System.out.println(LINE);
    }

    public void showLoadingError() {
        System.out.println("Error loading tasks from file. Initialising an empty task list.");
    }
}
