package John.Parser;

import John.Exceptions.JohnException;
import John.Tasks.Deadline;
import John.Tasks.Event;

/**
 * Parses user input into commands and domain objects.
 * <p>
 * This helper exposes only static methods and performs validation,
 * throwing {@link JohnException} when a command is malformed.
 */
public class Parser {
    // Helper class to parse user input, static methods only

    /**
     * Splits the raw input into a command word and the remaining description.
     *
     * @param input full user input line
     * @return a pair [command, description] where description may be empty
     * @throws IllegalArgumentException if the input cannot be split (should be rare)
     */
    public static String[] parse(String input) throws IllegalArgumentException {
        String[] userInput = input.split(" ", 2);
        String command = userInput[0];
        String description = (userInput.length > 1) ? userInput[1].trim() : "";
        return new String[] {command, description};
    }

    /**
     * Parses a deadline description in the form:
     * <pre>
     *   <description> /by <dateTime>
     * </pre>
     *
     * @param description user-provided description including "/by"
     * @return a constructed {@link Deadline}
     * @throws JohnException if required parts are missing or invalid
     */
    public static Deadline getDeadline(String description) throws JohnException {
        String[] deadlineDescription = description.split("\\s*/by\\s*", 2);
        if (deadlineDescription.length < 2
                || deadlineDescription[0].isBlank()
                || deadlineDescription[1].isBlank()
                || deadlineDescription[1].contains("/by")) { // ensure only one /by
            throw new JohnException("Deadline command must include a description and a deadline.");
        }
        return new Deadline(deadlineDescription[0], deadlineDescription[1]);
    }

    /**
     * Parses an event description in the form:
     * <pre>
     *   <description> /from <startDateTime> /to <endDateTime>
     * </pre>
     *
     * @param description user-provided description including "/from" and "/to"
     * @return a constructed {@link Event}
     * @throws JohnException if keywords are missing, misordered, or parts are blank/invalid
     */
    public static Event getEvent(String description) throws JohnException {
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
