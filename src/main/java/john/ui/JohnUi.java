package john.ui;

/**
 * UI utilities for the chatbot John. REPLACED by a GUI in new version.
 */
public class JohnUi {
    private static final String LINE = "__________________________________________________";

    /**
     * Prints a horizontal separator line to standard output.
     */
    public void printLine() {
        System.out.println(LINE);
    }

    /**
     * Prints a user-friendly message when tasks cannot be loaded from storage.
     */
    public void showLoadingError() {
        System.out.println("Error loading tasks from file. Initialising an empty task list.");
    }
}
