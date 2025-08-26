package Parser;

import Exceptions.JohnException;
import Tasks.Deadline;
import Tasks.Event;

public class Parser {
    // Helper class to parse user input, static methods only

    public static String[] parse(String input) throws IllegalArgumentException {
        String[] userInput = input.split(" ", 2);
        String command = userInput[0];
        String description = (userInput.length > 1) ? userInput[1].trim() : "";
        return new String[] {command, description};
    }

    public static Deadline getDeadline(String description) throws JohnException {
        String[] deadlineDescription = description.split("\\s*/by\\s*", 2);
        if (deadlineDescription.length < 2
                || deadlineDescription[0].isBlank()
                || deadlineDescription[1].isBlank()
                || deadlineDescription[1].contains("/by")) { // this line's check ensures only one /by is used
            throw new JohnException("Deadline command must include a description and a deadline.");
        }
        return new Deadline(deadlineDescription[0], deadlineDescription[1]);
    }

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
